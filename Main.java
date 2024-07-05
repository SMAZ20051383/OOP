import controller.Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:mydatabase.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                // Create tables
                try (Statement stmt = conn.createStatement()) {
                    String createUsersTableSQL = "CREATE TABLE IF NOT EXISTS users (\n"
                            + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                            + "    username TEXT NOT NULL,\n"
                            + "    password TEXT NOT NULL,\n"
                            + "    nickname TEXT NOT NULL,\n"
                            + "    email TEXT,\n"
                            + "    level INTEGER,\n"
                            + "    hp INTEGER,\n"
                            + "    xp INTEGER,\n"
                            + "    coin INTEGER\n"
                            + ");";
                    String createUserCardsTableSQL = "CREATE TABLE IF NOT EXISTS user_cards (\n"
                            + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                            + "    user_id INTEGER NOT NULL,\n"
                            + "    card_name TEXT NOT NULL,\n"
                            + "    FOREIGN KEY (user_id) REFERENCES users (id)\n"
                            + ");";
                    String createUserSecurityQuestionsTableSQL = "CREATE TABLE IF NOT EXISTS user_security_questions (\n"
                            + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                            + "    user_id INTEGER NOT NULL,\n"
                            + "    question TEXT NOT NULL,\n"
                            + "    answer TEXT NOT NULL,\n"
                            + "    FOREIGN KEY (user_id) REFERENCES users (id)\n"
                            + ");";

                    stmt.execute(createUsersTableSQL);
                    stmt.execute(createUserCardsTableSQL);
                    stmt.execute(createUserSecurityQuestionsTableSQL);

                    System.out.println("Tables created successfully.");
                    new Controller().run();                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
