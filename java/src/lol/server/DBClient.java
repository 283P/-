package lol.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class DBClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9999;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public DBClient() throws IOException, InterruptedException {
        socket = new Socket(SERVER_HOST, SERVER_PORT);
        socket.setSoTimeout(5000);  // 设置5秒连接超时

        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // 添加握手失败重试机制
        int retryCount = 0;
        while (retryCount < 3) {
            try {
                String response = sendCommand("CONNECT_DB");
                if ("CONNECT_OK".equals(response)) {
                    return;
                }
                throw new IOException("服务器返回无效握手响应: " + response);
            } catch (IOException e) {
                if (++retryCount >= 3) {
                    throw new IOException("握手失败，已重试3次", e);
                }
                Thread.sleep(1000); // 等待1秒后重试
            }
        }
    }


    public String sendCommand(String command) throws IOException {
        out.println(command);
        // 添加超时处理和重试机制
        socket.setSoTimeout(5000); // 设置5秒读取超时
        try {
            String response = in.readLine();
            if (response == null) {
                throw new IOException("Connection reset by server");
            }
            return response;
        } catch (SocketTimeoutException e) {
            throw new IOException("服务器响应超时", e);
        }
    }

    public String authenticate(String username, String password) throws IOException {
        // 修正认证协议格式（使用空格分隔）
        return sendCommand("AUTH " + username + " " + password);
    }

    public void disconnect() {
        try {
            if (out != null) {
                out.println("EXIT");
                out.close();
            }
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // 示例：创建多个客户端实例
            DBClient client1 = new DBClient();
            DBClient client2 = new DBClient();

            // 分别使用不同客户端进行认证
            String result1 = client1.authenticate("user1", "pass1");
            String result2 = client2.authenticate("user2", "pass2");

            System.out.println("客户端1认证结果: " + result1);
            System.out.println("客户端2认证结果: " + result2);

            client1.disconnect();
            client2.disconnect();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
