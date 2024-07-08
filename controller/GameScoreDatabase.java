package controller;

import java.sql.*;
import java.util.ArrayList;

public class GameScoreDatabase {
    private Connection connection;

    public GameScoreDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:gameScores.db");
            createGameScoreTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createGameScoreTable() {
        String sql = "CREATE TABLE IF NOT EXISTS game_scores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT NOT NULL, " +
                "time TEXT NOT NULL, " +
                "player_name TEXT NOT NULL, " +
                "opponent_name TEXT NOT NULL, " +
                "opponent_level INTEGER NOT NULL, " +
                "result TEXT NOT NULL, " +
                "rewards TEXT)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addGameScore(String date, String time, String playerName, String opponentName, int opponentLevel, String result, String rewards) {
        String sql = "INSERT INTO game_scores(date, time, player_name, opponent_name, opponent_level, result, rewards) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, date);
            pstmt.setString(2, time);
            pstmt.setString(3, playerName);
            pstmt.setString(4, opponentName);
            pstmt.setInt(5, opponentLevel);
            pstmt.setString(6, result);
            pstmt.setString(7, rewards);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String[]> getAllGameScores() {
        ArrayList<String[]> scores = new ArrayList<>();
        String sql = "SELECT * FROM game_scores";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String[] score = {
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("player_name"),
                        rs.getString("opponent_name"),
                        rs.getInt("opponent_level") + "",
                        rs.getString("result"),
                        rs.getString("rewards")
                };
                scores.add(score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }
}
