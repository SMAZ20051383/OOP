package com.example.demo.view;

import com.example.demo.controller.Controller;
import com.example.demo.model.User;
import com.example.demo.view.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {
    private Controller controller = new Controller();
    public User user ;

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private TextField Username;
    @FXML
    private TextField NickName;
    @FXML
    private TextField Password ;
    @FXML
    private TextField Email ;
    @FXML
    private Label UserNameLabel;
    @FXML
    private Label EmailLabel ;
    @FXML
    private Label NickNameLabel ;
    @FXML
    private Label PasswordLabel ;
    public void update(){
        Username.setText(user.getUsername());
        NickName.setText(user.getNickname());
        Password.setText(user.getPassword());
        Email.setText(user.getEmail());
    }

    public void run(Scanner scanner, Controller controller, User user) {
        this.controller = controller;
        this.user = user ;

        String input;
        Matcher matcher;
        while (true) {
            System.out.println("--------------------------------------");
            System.out.println("you are now in profile menu");
            System.out.println("for see your information enter : Show information");
            System.out.println("for change your username enter : Profile change -u (?<username>.+)");
            System.out.println("for change your password enter : profile change password -o (?<oldPassword>.+) -n (?<newPassword>.+)");
            System.out.println("for change your nickname enter : Profile change -n (?<nickname>.+)");
            System.out.println("for change your Email enter : profile change -e (?<email>.+)");
            System.out.println("for back to Main menu enter: back");
            System.out.println("--------------------------------------");

            input = scanner.nextLine();
            if ((matcher = Command.getMatcher(input, Command.BACK)) != null) {
                System.out.println("Entered main menu!");
                break;
            } else if ((matcher = Command.getMatcher(input, Command.SHOW_CURRENT_MENU)) != null) {
                System.out.println("Profile Menu");
            }
            else if ((matcher = Command.getMatcher(input, Command.CHANGE_PASSWORD)) != null) {
                System.out.println(changePassword(matcher));
            }
            else if ((matcher = Command.getMatcher(input, Command.Change_username)) != null) {
                System.out.println(change_username(matcher));
            }
            else if ((matcher = Command.getMatcher(input, Command.Change_nickname)) != null) {
                System.out.println(change_nickname(matcher));
            }
            else if ((matcher = Command.getMatcher(input, Command.Change_Email)) != null) {
                System.out.println(change_email(matcher));
            }
            else if ((matcher = Command.getMatcher(input, Command.INFO)) != null) {
                System.out.println(controller.info(user.getUsername()));
            } else if ((matcher = Command.getMatcher(input, Command.REMOVE_FROM_DECK)) != null) {
                System.out.println(removeFromDeck(matcher));
            } else if ((matcher = Command.getMatcher(input, Command.ADD_TO_DECK)) != null) {
                System.out.println(addToDeck(matcher));
            } else if ((matcher = Command.getMatcher(input, Command.SHOW_DECK)) != null) {
                System.out.print(controller.showDeck(user.getUsername()));
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private String changePassword(Matcher matcher) {
        if (controller.passwordIsWrong(user.getUsername(), matcher.group("oldPassword"))) {
            return "Current password is incorrect!";
        } else if (controller.passwordFormatIsBad(matcher.group("newPassword"))) {
            return "Incorrect format for new password!";
        }
        else if(matcher.group("newPassword").equals(matcher.group("oldPassword"))){
            return "Please enter a new password! ";
        }
        else {
            controller.changePassword(user.getUsername(), matcher.group("newPassword"));
            return "Password changed successfully!";
        }
    }
    private void changePassword(String newPassword) {
        if (controller.passwordFormatIsBad(newPassword) ||controller.emailFormatIsBad2(newPassword)) {
            PasswordLabel.setText("Incorrect format for new password!");
        }
        else {
            controller.changePassword(user.getUsername(), newPassword);
            PasswordLabel.setText("Password changed successfully!");
        }
    }

    private String removeFromDeck(Matcher matcher) {
        if (controller.isCardNameWrong(matcher.group("cardName"))) {
            return "Invalid card name!";
        } else if (controller.getCardFromDeck(user.getUsername(), matcher.group("cardName")) == null) {
            return "This card isn't in your battle deck!";
        }
         else {
            controller.removeFromDeck(user.getUsername(), matcher.group("cardName"));
            return "Card " + matcher.group("cardName") + " removed successfully!";
        }
    }

    private String addToDeck(Matcher matcher) {
        if (controller.isCardNameWrong(matcher.group("cardName"))) {
            return "Invalid card name!";
        } else if (!controller.userHasCard(user.getUsername(), matcher.group("cardName"))) {
            return "You don't have this card!";
        } else if (controller.getCardFromDeck(user.getUsername(), matcher.group("cardName")) != null) {
            return "This card is already in your battle deck!";
        }  else {
            controller.addToDeck(user.getUsername(), matcher.group("cardName"));
            return "Card " + matcher.group("cardName") + " added successfully!";
        }
    }
    private String change_username(Matcher matcher){
        if (!matcher.group("username").matches("[a-zA-Z]+")) {
            return "Incorrect format for username!";
        }
        else if (controller.getUserByUsername(matcher.group("username")) != null) {
            return "Username already exists!";
        }
        else {
            controller.changeUsername(user.getUsername(), matcher.group("username"));
            return "username changed succssefully";
        }


    }
    private void change_username(String username){
        if (!username.matches("[a-zA-Z]+")) {
            UserNameLabel.setText("Incorrect format for username!");
        }
        else if (controller.getUserByUsername(username) != null) {
            UserNameLabel.setText("Username already exists!");
        }
        else {
            controller.changeUsername(user.getUsername(), username);
            UserNameLabel.setText("username changed succssefully");
        }


    }

    private String change_nickname(Matcher matcher){
        if (!matcher.group("nickname").matches("[a-zA-Z]+")) {
            return "Incorrect format for nickname!";
        }
        else {
            controller.changeNickname(user.getUsername(), matcher.group("nickname"));
            return "nickname changed succssefully";
        }
    }
    private void change_nickname(String nickname){
        if (!nickname.matches("[a-zA-Z]+")) {
            NickNameLabel.setText("Incorrect format for nickname!");
        }
        else {
            controller.changeNickname(user.getUsername(), nickname);
            NickNameLabel.setText("nickname changed succssefully");
        }
    }

    private String change_email(Matcher matcher){
        if(controller.emailFormatIsBad2(matcher.group("email"))){
            return "Email format is incorrect!";
        }
        else {
            controller.changeEmail(user.getUsername(), matcher.group("email"));
            return "Email changed succssefully";
        }
    }
    private void change_email(String Email){
        if(controller.emailFormatIsBad2(Email)){
            EmailLabel.setText("Email format is incorrect!");
        }
        else {
            controller.changeEmail(user.getUsername(), Email);
            EmailLabel.setText("Email changed succssefully");
        }
    }


    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Main_Menu.fxml"));
        Parent root = loader.load();
        MainMenu controller = loader.getController();
        controller.setUser(Controller.getUserByUsername(user.getUsername()));
        controller.update();
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));

    }
    @FXML
    public void change(MouseEvent event) {
        String usrename = Username.getText();
        String password = Password.getText();
        String Email_new = Email.getText();
        String Nickname = NickName.getText();
        change_email(Email_new);
        change_nickname(Nickname);
        changePassword(password);
        change_username(usrename);
    }
}
