package lol.register;

import lol.DButil.DButil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuthDaoImpl implements UserAuthDao {
    private static final String INSERT_USER_SQL = 
        "INSERT INTO sys_user(user_id, user_password) VALUES (?, ?)";
    private static final String AUTHENTICATE_SQL = 
        "SELECT user_password FROM sys_user WHERE user_id = ?";

    @Override
    public void registerUser(User user) throws SQLException {
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_USER_SQL)) {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getHashedPassword());
            pstmt.executeUpdate();
        }
    }

    @Override
    public boolean authenticateUser(String userId, String password) throws SQLException {
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(AUTHENTICATE_SQL)) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("user_password");
                    String inputHash = UserAuthDao.hashPassword(password);
                    return storedHash.equals(inputHash);
                }
                return false;
            }
        }
    }
}
