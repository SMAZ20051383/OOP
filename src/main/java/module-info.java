module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.demo.view to javafx.fxml;
    exports com.example.demo.view;
    exports com.example.demo;
}