import controller.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:mydatabase.db";
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        connect();
        new Controller().run();
    }
}
