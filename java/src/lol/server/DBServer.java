package lol.server;

import lol.DButil.DButil;
import lol.register.User;
import lol.register.UserAuthDaoImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
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

                if (input.equals("EXIT")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processCommand(String command) {
        try {
            if (command.startsWith("AUTH:")) {
                String[] parts = command.split(":");
                if (parts.length == 3) {
                    String username = parts[1];
                    String password = parts[2];
                    UserAuthDaoImpl dao = new UserAuthDaoImpl();
                    return dao.authenticateUser(username, password) ? "AUTH_SUCCESS" : "AUTH_FAILED";
                }
            } else if (command.startsWith("REGISTER:")) {
                String[] parts = command.split(":");
                if (parts.length == 3) {
                    String username = parts[1];
                    String hashedPassword = parts[2];
                    UserAuthDaoImpl dao = new UserAuthDaoImpl();
                    dao.registerUser(new User(username, hashedPassword));
                    return "REGISTER_SUCCESS";
                }
            } else if (command.equals("CONNECT_DB")) {
                return conn.isValid(0) ? "CONNECT_OK" : "CONNECT_FAILED";
            } else if (command.equals("EXIT")) {
                return "EXIT_OK";
            }
            return "UNKNOWN_COMMAND";
        } catch (SQLException e) {
            return "ERROR: " + e.getMessage();
        }
    }
}
