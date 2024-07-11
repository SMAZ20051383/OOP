package com.example.demo;

import com.example.demo.controller.Controller;
import com.example.demo.controller.GameScoreDatabase;
import com.example.demo.model.Card;
import com.example.demo.model.CardDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
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
                    CardDatabase cardDatabase = new CardDatabase();
                    Card.cards = CardDatabase.getAllCards() ;
                    GameScoreDatabase database = new GameScoreDatabase();
                    new Controller().run();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
