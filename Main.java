import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:mydatabase.db";

        // SQL statement for creating a new table
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    username TEXT NOT NULL,\n"
                + "    password TEXT NOT NULL,\n"
                + "    nickname TEXT NOT NULL,\n"
                + "    security_question TEXT,\n"
                + "    email TEXT,\n"
                + "    level INTEGER,\n"
                + "    hp INTEGER,\n"
                + "    xp INTEGER,\n"
                + "    coin INTEGER,\n"
                + "    cards INTEGER\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Table 'users' has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // User details
        String username = "john_doe";
        String password = "secret_password";
        String nickname = "John";
        String securityQuestion = "What is your favorite color?";
        String email = "john.doe@example.com";
        int level = 5;
        int hp = 100;
        int xp = 5000;
        int cards = 10;

        // SQL statement for inserting a new user
        String insertSQL = "INSERT INTO users(username, password, nickname, security_question, email, level, hp, xp, cards) VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // Set the parameters
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, nickname);
            pstmt.setString(4, securityQuestion);
            pstmt.setString(5, email);
            pstmt.setInt(6, level);
            pstmt.setInt(7, hp);
            pstmt.setInt(8, xp);
            pstmt.setInt(9, cards);

            // Execute the insert statement
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
