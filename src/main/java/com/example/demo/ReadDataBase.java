package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReadDataBase {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:mydatabase.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                // Read users table
                String sql = "SELECT id, username, password, nickname, email, level, hp, xp, coin FROM users";
                try (PreparedStatement pstmt = conn.prepareStatement(sql);
                     ResultSet rs = pstmt.executeQuery()) {

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

                        // Read user cards
                        ArrayList<String> cards = new ArrayList<>();
                        String cardSql = "SELECT card_name FROM user_cards WHERE user_id = ?";
                        try (PreparedStatement cardPstmt = conn.prepareStatement(cardSql)) {
                            cardPstmt.setInt(1, id);
                            try (ResultSet cardRs = cardPstmt.executeQuery()) {
                                while (cardRs.next()) {
                                    cards.add(cardRs.getString("card_name"));
                                }
                            }
                        }

                        // Read user security questions
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

                        // Display user information
                        System.out.println("ID: " + id);
                        System.out.println("Username: " + username);
                        System.out.println("Password: " + password);
                        System.out.println("Nickname: " + nickname);
                        System.out.println("Email: " + email);
                        System.out.println("Level: " + level);
                        System.out.println("HP: " + hp);
                        System.out.println("XP: " + xp);
                        System.out.println("Coin: " + coin);
                        System.out.println("Cards: " + cards);
                        System.out.println("Security Questions: " + securityQuestions);
                        System.out.println("-------------------------");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
