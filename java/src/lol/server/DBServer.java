package lol.server;

import lol.DButil.DButil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBServer {
    private static final int PORT = 9999;
    private static final int THREAD_POOL_SIZE = 10;
    private static final Connection conn = DButil.getConnection();

    public static void main(String[] args) {
        try (ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
             ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("服务器启动，监听端口 " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (Socket socket = clientSocket;
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String input;
            while ((input = in.readLine()) != null) {
                String response = processCommand(input);
                out.println(response);

                // 添加连接保持机制
                if ("EXIT".equalsIgnoreCase(input.trim())) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("客户端连接异常: " + e.getMessage()); // 改进错误日志
        } finally {
            try {
                clientSocket.close(); // 确保socket关闭
            } catch (IOException e) {
                System.err.println("关闭socket时出错: " + e.getMessage());
            }
        }
    }

    private static String processCommand(String command) {
        try {

            // 添加握手协议处理
            if ("CONNECT_DB".equals(command)) {
                return "CONNECT_OK";
            }

            if (command.startsWith("REGISTER ")) {
                String[] parts = command.split(" ", 4);
                if (parts.length != 4) {
                    return "ERROR: 无效格式，需要4个参数（命令+用户ID+昵称+密码哈希）";
                }

                // 添加参数有效性检查
                String userId = parts[1];
                if (userId.length() < 4 || userId.length() > 20) {
                    return "ERROR: 用户ID长度需在4-20个字符之间";
                }

                // 检查用户ID是否已存在
                if (userExists(userId)) {
                    return "ERROR: 该用户ID已被注册";
                }

                try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO sys_user (user_id, user_name, user_password) VALUES (?, ?, ?)")) {
                    stmt.setString(1, userId);
                    stmt.setString(2, parts[2]); // 昵称
                    stmt.setString(3, parts[3]); // 密码哈希

                    return stmt.executeUpdate() > 0 ? "REGISTER_SUCCESS" : "REGISTER_FAILED";
                }
            }
            if (command.startsWith("AUTH ")) {
                String[] parts = command.split(" ", 3); // 分解为：命令+用户名+密码
                if (parts.length != 3) return "ERROR: 无效的认证格式";

                String userId = parts[1];
                String rawPassword = parts[2]; // 获取原始密码

                try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT user_password FROM sys_user WHERE user_id = ?")) {
                    stmt.setString(1, userId);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        String storedHash = rs.getString(1);
                        // 添加服务端哈希验证
                        String inputHash = UserAuthDao.hashPassword(rawPassword);
                        return storedHash.equals(inputHash) ? "AUTH_SUCCESS" : "AUTH_FAILED";
                    }
                    return "AUTH_FAILED";
                }
            }
        } catch (SQLException e) {
            System.err.println("数据库错误: " + e.getMessage()); // 添加详细日志
            return "ERROR: 数据库操作失败 - " + e.getMessage().split(":")[0]; // 简化错误信息
        }
        return command;
    }

    // 新增用户存在性检查方法
    private static boolean userExists(String userId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
            "SELECT user_id FROM sys_user WHERE user_id = ?")) {
            stmt.setString(1, userId);
            return stmt.executeQuery().next();
        }
    }
}


