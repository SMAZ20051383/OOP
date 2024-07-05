import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectUser {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:mydatabase.db";

        // SQL statement for selecting all users
        String sql = "SELECT id, username, password, nickname, security_question, email, level, hp, xp, coin, cards FROM users";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Loop through the result set
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String nickname = rs.getString("nickname");
                String securityQuestion = rs.getString("security_question");
                String email = rs.getString("email");
                int level = rs.getInt("level");
                int hp = rs.getInt("hp");
                int xp = rs.getInt("xp");
                int coin = rs.getInt("coin");
                int cards = rs.getInt("cards");

                // Display user information
                System.out.println("ID: " + id);
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
                System.out.println("Nickname: " + nickname);
                System.out.println("Security Question: " + securityQuestion);
                System.out.println("Email: " + email);
                System.out.println("Level: " + level);
                System.out.println("HP: " + hp);
                System.out.println("XP: " + xp);
                System.out.println("Coin: " + coin);
                System.out.println("Cards: " + cards);
                System.out.println("-------------------------");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
