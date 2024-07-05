package controller;

import model.Card;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SqlController {

    public static ArrayList<User> getAllUsers (ArrayList<Card>allCards) {
        ArrayList<User> users = new ArrayList<>();
        String url = "jdbc:sqlite:../mydatabase.db";

        // SQL statement for selecting all users
        String sql = "SELECT id, username, password, nickname, email, level, hp, xp, coin FROM users";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Loop through the result set
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String nickname = rs.getString("nickname");
                String email = rs.getString("email");
                int level = rs.getInt("level");
                int hp = rs.getInt("hp");
                int xp = rs.getInt("xp");
                int coin = rs.getInt("coin");

                ArrayList<Card> cards = new ArrayList<>();
                String cardSql = "SELECT card_name FROM user_cards WHERE user_id = ?";
                try (PreparedStatement cardPstmt = conn.prepareStatement(cardSql)) {
                    cardPstmt.setInt(1, id);
                    try (ResultSet cardRs = cardPstmt.executeQuery()) {
                        while (cardRs.next()) {
                            for(Card card:allCards){
                                if(card.getName().equals(cardRs)){
                                    cards.add(card);
                                }
                            }
                        }
                    }
                }

                // Retrieve user security questions
                Map<String, String> securityQuestions = new HashMap<>();
                String questionSql = "SELECT question, answer FROM user_security_questions WHERE user_id = ?";
                try (PreparedStatement questionPstmt = conn.prepareStatement(questionSql)) {
                    questionPstmt.setInt(1, id);
                    try (ResultSet questionRs = questionPstmt.executeQuery()) {
                        while (questionRs.next()) {
                            securityQuestions.put(questionRs.getString("question"), questionRs.getString("answer"));
                        }
                    }
                }

                // Create user object
                User user = new User(username, password, nickname, email, securityQuestions, cards, level, hp, xp, coin);
                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
}
