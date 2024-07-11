package com.example.demo;

import com.example.demo.controller.Controller;
import com.example.demo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ForgetpasswordController {
    public static User user = User.allUsers.get(0)  ;

    @FXML
    private Label Question_CangPass;
    @FXML
    private TextField Awnser_CangPas;
    @FXML
    private PasswordField New_Pass__CangPas;
    @FXML
    private Label answerBox;


    @FXML
    public void initialize() {
        Question_CangPass.setText(user.getSecurityQuestion()[0]); // مثال: نمایش سوال امنیتی
    }


    @FXML
    public void ersal_CangPas(ActionEvent actionEvent) {
        String answer = Awnser_CangPas.getText();
        String password = New_Pass__CangPas.getText();
        if(!(answer.equals(user.getSecurityQuestion()[1]))){
            answerBox.setText("answer incorrect");
        }
        else if(Controller.passwordFormatIsBad(password) || Controller.passwordFormatIsBad2(password)){
            answerBox.setText("password is bad");
        }
        else {
            answerBox.setText("password changed successfully");
            user.setPassword(password);
        }
    }

    public void back(MouseEvent event)throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Login.fxml"));
        Parent root = loader.load();

        // دریافت کنترلر صفحه جدید و تنظیم پارامتر

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));

    }
}
