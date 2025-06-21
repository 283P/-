package lol.register;

public class User {
    private String userId;
    private String hashedPassword;

    public User(String userId, String hashedPassword) {
        this.userId = userId;
        this.hashedPassword = hashedPassword;
    }

    // getters and setters
    public String getUserId() { return userId; }
    public String getHashedPassword() { return hashedPassword; }
}
