package com.example.demo.controller;


import com.example.demo.model.Card;
import com.example.demo.model.User;

import java.sql.*;
import java.util.ArrayList;

public class SqlController {
    static String url = "jdbc:sqlite:mydatabase.db";

    public static ArrayList<User> getAllUsers(ArrayList<Card> allCards) {
        ArrayList<User> users = new ArrayList<>();

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
                            for (Card card : allCards) {
                                if (card.getName().equals(cardRs.getString("card_name"))) {
                                    cards.add(card);
                                }
                            }
                        }
                    }
                }

                // Retrieve user security questions
                String[] securityQuestion = new String[2];
                String questionSql = "SELECT question, answer FROM user_security_questions WHERE user_id = ?";
                try (PreparedStatement questionPstmt = conn.prepareStatement(questionSql)) {
                    questionPstmt.setInt(1, id);
                    try (ResultSet questionRs = questionPstmt.executeQuery()) {
                        if (questionRs.next()) {
                            securityQuestion[0] = questionRs.getString("question");
                            securityQuestion[1] = questionRs.getString("answer");
                        }
                    }
                }

                // Create user object
                User user = new User(username, password, nickname, email, securityQuestion, cards, level, hp, xp, coin);
                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    // Function to insert a new user into the database
    public static void insertUser(User user) {
        String sql = "INSERT INTO users(username, password, nickname, email, level, hp, xp, coin) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getNickname());
            pstmt.setString(4, user.getEmail());
            pstmt.setInt(5, user.getLevel());
            pstmt.setInt(6, user.getHP());
            pstmt.setInt(7, user.getExperience());
            pstmt.setInt(8, user.getGold());
            pstmt.executeUpdate();

            // Insert user security questions if available
            insertUserSecurityQuestions(conn, user);

            // Insert user cards if available
            insertUserCards(conn, user);

            System.out.println("User added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Helper function to insert user security questions into the database
    private static void insertUserSecurityQuestions(Connection conn, User user) throws SQLException {
        String sql = "INSERT INTO user_security_questions(user_id, question, answer) VALUES((SELECT id FROM users WHERE username = ?),?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getSecurityQuestion()[0]);
            pstmt.setString(3, user.getSecurityQuestion()[1]);
            pstmt.executeUpdate();
        }
    }

    // Helper function to insert user cards into the database
    private static void insertUserCards(Connection conn, User user) throws SQLException {
        String sql = "INSERT INTO user_cards(user_id, card_name) VALUES((SELECT id FROM users WHERE username = ?),?)";

        for (Card card : user.getCards()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, card.getName());
                pstmt.executeUpdate();
            }
        }
    }

    public static void updateUser(User user) {
        String sql = "UPDATE users SET password = ?, nickname = ?, email = ?, level = ?, hp = ?, xp = ?, coin = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getNickname());
            pstmt.setString(3, user.getEmail());
            pstmt.setInt(4, user.getLevel());
            pstmt.setInt(5, user.getHP());
            pstmt.setInt(6, user.getExperience());
            pstmt.setInt(7, user.getGold());
            pstmt.setString(8, user.getUsername());

            pstmt.executeUpdate();

            // Update user security questions if available
            updateSecurityQuestions(conn, user);

            // Update user cards if available
            updateCards(conn, user);

            System.out.println("User updated successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Helper function to update user security questions in the database
    private static void updateSecurityQuestions(Connection conn, User user) throws SQLException {
        String deleteSql = "DELETE FROM user_security_questions WHERE user_id = (SELECT id FROM users WHERE username = ?)";
        String insertSql = "INSERT INTO user_security_questions(user_id, question, answer) VALUES((SELECT id FROM users WHERE username = ?),?,?)";

        // First delete existing security questions for the user
        try (PreparedStatement deletePstmt = conn.prepareStatement(deleteSql)) {
            deletePstmt.setString(1, user.getUsername());
            deletePstmt.executeUpdate();
        }

        // Then insert updated security questions
        try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
            insertPstmt.setString(1, user.getUsername());
            insertPstmt.setString(2, user.getSecurityQuestion()[0]);
            insertPstmt.setString(3, user.getSecurityQuestion()[1]);
            insertPstmt.executeUpdate();
        }
    }

    // Helper function to update user cards in the database
    private static void updateCards(Connection conn, User user) throws SQLException {
        String deleteSql = "DELETE FROM user_cards WHERE user_id = (SELECT id FROM users WHERE username = ?)";
        String insertSql = "INSERT INTO user_cards(user_id, card_name) VALUES((SELECT id FROM users WHERE username = ?),?)";

        // First delete existing user cards
        try (PreparedStatement deletePstmt = conn.prepareStatement(deleteSql)) {
            deletePstmt.setString(1, user.getUsername());
            deletePstmt.executeUpdate();
        }

        // Then insert updated user cards
        for (Card card : user.getCards()) {
            try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
                insertPstmt.setString(1, user.getUsername());
                insertPstmt.setString(2, card.getName());
                insertPstmt.executeUpdate();
            }
        }
    }

    public static void deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set the username parameter for the SQL statement
            pstmt.setString(1, username);

            // Execute the delete statement
            pstmt.executeUpdate();

            System.out.println("User " + username + " has been deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting user " + username + ": " + e.getMessage());
        }
    }
    public static int getUserID(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    System.out.println("User not found.");
                    return -1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }
    public static void updateUsername(int userID, String newUsername) {
        String sql = "UPDATE users SET username = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUsername);
            pstmt.setInt(2, userID);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Username updated successfully for user ID " + userID);
            } else {
                System.out.println("User ID " + userID + " not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating username: " + e.getMessage());
        }
    }
}
