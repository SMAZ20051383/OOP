package com.example.demo.view;

import com.example.demo.ForgetpasswordController;
import com.example.demo.controller.Controller;
import com.example.demo.model.Admin;
import com.example.demo.model.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;

public class RegisterMenu extends Application {
    private Controller controller;
    String correctKapcha = "";
    private Timeline countdownTimeline;
    private int countdownTime = 10;

    private User user;
    @FXML
    public Label LoginEror;

    @FXML
    private Button ersalLogin;

    @FXML
    private PasswordField passwordLogin;

    @FXML
    private TextField username;
    @FXML
    private Label countdownLabel;
    @FXML
    private Button ersal_login ;


    @FXML
    public void initialize() {
        controller = new Controller(); // Initializing controller
        correctKapcha = getCorrectKapcha(kapcha_text);
    }

    @FXML
    public void ersalLogin(ActionEvent event) {
        String user = username.getText();
        String pass = passwordLogin.getText();
        if (login(user, pass)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Main_Menu.fxml"));
                Parent root = loader.load();
                MainMenu controller = loader.getController();
                controller.setUser(Controller.getUserByUsername(user));
                controller.update();
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            startCountdown();
        }
    }
    @FXML
    public void register(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/SignUp.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void Random_Password_generating(MouseEvent event) {
        String password = generatePassword();
        System.out.println(password);
        error.setText(password);

    }

    @FXML
    private PasswordField password ;
    @FXML
    private TextField input_captcha ;
    @FXML
    private Label error ;
    @FXML
    private TextField NickName ;
    @FXML
    private TextField Email ;
    @FXML
    private TextArea kapcha_text_final ;
    private TextArea kapcha_text = new TextArea();
    @FXML
    public void rememberPassword(MouseEvent event) throws IOException {
            String user_name = username.getText();
            user = controller.getUserByUsername(user_name);
            ForgetpasswordController.user = user ;

            // مسیر فایل FXML را بررسی کنید
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Change_Password.fxml"));
            Parent root = loader.load();

            // دریافت کنترلر صفحه جدید و تنظیم پارامتر

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        }
    public String getCorrectKapcha(TextArea kapcha_text){
        Kapcha kapcha = new Kapcha();
        Random random = new Random();
        int randomnumber = random.nextInt(2);
        String correctKapcha="" ;
        switch (randomnumber) {
            case 0:
                int number = Kapcha.mathematic_kapch(kapcha_text);
                correctKapcha= String.valueOf(number);
                break;
            case 1:
                String kapcha1 = kapcha.ascii_art_kapcha(kapcha_text);
                correctKapcha = kapcha1 ;
                break;
        }
        return correctKapcha ;
    }
    @FXML
    public void Ersal(ActionEvent actionEvent) {

        String username_1 = username.getText();
        String password_1 = password.getText();
        String Nickname_1 = NickName.getText();
        String Email_1 = Email.getText();
        String kapcha_1 = input_captcha.getText();
        if(correctKapcha.equals(kapcha_1)){
            register(username_1,password_1,Email_1,Nickname_1);
        }
        else {
            initialize();
        }

    }
    @FXML
    public void back_SginupMenu(ActionEvent actionEvent) {
        try {
            Parent secondPage = FXMLLoader.load(getClass().getResource("/com/example/demo/Login.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(secondPage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void run(Controller controller) {
        this.controller = controller;
        Scanner scanner = new Scanner(System.in);
        String input;
        Matcher matcher;
        while (true) {
            System.out.println("--------------------------------------");
            System.out.println("for login enter: user login -u (?<username>.+) -p (?<password>.*\\S)");
            System.out.println("for signin enter: register -u (?<username>.+) -p (?<password>.+) (?<passwordConfirmation>.*) -email (?<email>.+) -n (?<nickname>.*\\S)");
            System.out.println("for random password enter random in username and one space in password confirmation");
            System.out.println("for forget password enter : Forgot my password -u (?<username>.+)");
            System.out.println("for admin login : Login admin (?<password>.+)");
            System.out.println("--------------------------------------");
            input = scanner.nextLine();
            if ((matcher = Command.getMatcher(input, Command.EXIT)) != null) {
                break;
            } else if ((matcher = Command.getMatcher(input, Command.SHOW_CURRENT_MENU)) != null) {
                System.out.println("Register/Login Menu");
            } else if ((matcher = Command.getMatcher(input, Command.Forget_password)) != null) {
                changePasswordFromForgetPassword(matcher.group("username"));
            } else if ((matcher = Command.getMatcher(input, Command.Admin_login)) != null) {
                adminAccount(matcher);
            } else if ((matcher = Command.getMatcher(input, Command.REGISTER)) != null) {
                System.out.println(register(matcher));
            } else if ((matcher = Command.getMatcher(input, Command.LOGIN)) != null) {
                boolean result = login(matcher);
                if (result) {
                    User user = controller.getUserByUsername(matcher.group("username"));
                    new MainMenu().run(scanner, controller, user);
                }
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private String register(Matcher matcher) {
        String password = matcher.group("password");
        if (matcher.group("password").equals("random")) {
            password = generatePassword();
            System.out.println("your random password is: " + password);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("please enter the pass for check:");
                String password_check = scanner.nextLine();
                if (password_check.equals(password)) {
                    System.out.println("correct");
                    break;
                } else {
                    System.out.println("wrong answer . try again");
                }
            }
        }
        if (matcher.group("username").isEmpty()) {
            return "Incorrect format for username: empty!";
        } else if (password.isEmpty()) {
            return "Incorrect format for password: empty!";
        } else if (!matcher.group("username").matches("[a-zA-Z]+")) {
            return "Incorrect format for username!";
        } else if (controller.getUserByUsername(matcher.group("username")) != null) {
            return "Username already exists!";
        } else if (controller.passwordFormatIsBad(password)) {
            return "Password format is incorrect for length!";
        } else if (controller.passwordFormatIsBad2(password)) {
            return "Password format is incorrect for structure!";
        } else if (controller.emailFormatIsBad2(matcher.group("email"))) {
            return "Email format is incorrect!";
        } else {
            controller.register(matcher.group("username"), password, matcher.group("nickname"), matcher.group("email"));
            System.out.println("User created successfully. Please choose a security question(enter number) : ");
            System.out.println("1-What is your fathers name ?");
            System.out.println("2-What is your favourite color ? ");
            System.out.println("3-What was the name of your first pet?");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            System.out.print("your answer:");
            String input_2 = scanner.nextLine();
            set_security_question(matcher.group("username"), Integer.parseInt(input), input_2);
            Kapcha kapcha = new Kapcha();
            Random random = new Random();
            int randomnumber = random.nextInt(2);
            switch (randomnumber) {
                case 0:
                    while (true) {
                        int number = kapcha.mathematic_kapch();
                        input = scanner.nextLine();
                        if (Integer.parseInt(input) == number) {
                            break;
                        }
                    }
                    break;
                case 1:
                    while (true) {
                        String kapcha1 = kapcha.ascii_art_kapcha();
                        input = scanner.nextLine();
                        if (input.equals(kapcha1)) {
                            break;
                        }
                    }
                    break;
            }
            return "Set successfully";
        }
    }
    private void register(String username , String password , String Email, String Nickname) {
        if (username.isEmpty()) {
            error.setText("Incorrect format for username: empty!");
        } else if (password.isEmpty()) {
            error.setText( "Incorrect format for password: empty!");
        }  else if (controller.getUserByUsername(username) != null) {
            error.setText( "Username already exists!");
        } else if (controller.passwordFormatIsBad(password)) {
            error.setText( "Password format is incorrect for length!");
        } else if (controller.passwordFormatIsBad2(password)) {
            error.setText( "Password format is incorrect for structure!");
        } else if (controller.emailFormatIsBad2(Email)) {
            error.setText( "Email format is incorrect!");
        } else {
            controller.register(username, password, Nickname, Email);
//            System.out.println("User created successfully. Please choose a security question(enter number) : ");
//            System.out.println("1-What is your fathers name ?");
//            System.out.println("2-What is your favourite color ? ");
//            System.out.println("3-What was the name of your first pet?");
//            Scanner scanner = new Scanner(System.in);
//            String input = scanner.nextLine();
//            System.out.print("your answer:");
//            String input_2 = scanner.nextLine();
//            set_security_question(username, Integer.parseInt(input), input_2);
//            error.setText("Set successfully");
        }
    }


    public boolean login(Matcher matcher) {
        if (Controller.getUserByUsername(matcher.group("username")) == null) {
            System.out.println("Username doesn’t exist!");
            return false;
        } else if (Controller.passwordIsWrong(matcher.group("username"), matcher.group("password"))) {
            System.out.println("Password and Username don’t match!");
            return false;
        } else {
            System.out.println("User logged in successfully!");
            return true;
        }
    }

    public boolean login(String UserName, String Password) {
        if (Controller.getUserByUsername(UserName) == null) {
            LoginEror.setText("Username doesn’t exist!");
            return false;
        } else if (Controller.passwordIsWrong(UserName, Password)) {
            LoginEror.setText("Password and Username don’t match!");
            return false;
        } else {
            LoginEror.setText("User logged in successfully!");
            return true;
        }
    }

    private void set_security_question(String username, int question, String answer) {
        for (User user : User.allUsers) {
            if (user.getUsername().equals(username)) {
                String[] securityQuestions = new String[2];
                switch (question) {
                    case 1:
                        String input = "What is your fathers name?";
                        securityQuestions[0] = input;
                        break;
                    case 2:
                        input = "What is your favourite color?";
                        securityQuestions[0] = input;
                        break;
                    case 3:
                        input = "What was the name of your first pet?";
                        securityQuestions[0] = input;
                        break;
                }
                securityQuestions[1] = answer;
                user.setSecurityQuestion(securityQuestions);
            }
        }
    }

    public static String generatePassword() {
        String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String LOWER = "abcdefghijklmnopqrstuvwxyz";
        String DIGITS = "0123456789";
        String SPECIAL = "_@";
        String ALL = UPPER + LOWER + DIGITS + SPECIAL;
        int PASSWORD_LENGTH = 10;
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        // Ensure the password contains at least one character from each required set
        password.append(UPPER.charAt(random.nextInt(UPPER.length())));
        password.append(LOWER.charAt(random.nextInt(LOWER.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        // Fill the rest of the password length with random characters from ALL
        for (int i = 4; i < PASSWORD_LENGTH; i++) {
            password.append(ALL.charAt(random.nextInt(ALL.length())));
        }

        // Shuffle the characters to ensure randomness
        return shuffleString(password.toString());
    }

    private static String shuffleString(String input) {
        SecureRandom random = new SecureRandom();
        char[] array = input.toCharArray();

        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }

        return new String(array);
    }

    private void changePasswordFromForgetPassword(String username) {
        User user = controller.getUserByUsername(username);
        System.out.println("whats the answer?");
        System.out.println(user.getSecurityQuestion()[0]);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.equals(user.getSecurityQuestion()[1])) {
            while (true) {
                System.out.println("now set your password");
                input = scanner.nextLine();
                if (input.equals("random")) {
                    String password = generatePassword();
                    System.out.println("your random password is: " + password);
                    System.out.print("please enter the pass for check:");
                    String password_check = scanner.nextLine();
                    if (password_check.equals(password)) {
                        System.out.println("correct");
                        user.setPassword(password);
                        break;
                    } else {
                        System.out.println("wrong answer . try again");
                    }
                } else if (controller.passwordFormatIsBad(input)) {
                    System.out.println("Password format is incorrect for length!");
                } else if (controller.passwordFormatIsBad2(input)) {
                    System.out.println("Password format is incorrect for structure!");
                } else {
                    user.setPassword(input);
                    System.out.println("password set successfully");
                    break;
                }
            }
        } else {
            System.out.println("wrong answer . please try again");
        }
    }

    private boolean adminAccount(Matcher matcher) {
        if (Admin.password.equals(matcher.group("password"))) {
            System.out.println("admin logged in successfully!");
            Admin.run();
            return true;
        } else {
            System.out.println("Password is incorrect");
            return false;
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("RegisterMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    private void startCountdown() {
        ersal_login.setDisable(true);
        countdownLabel.setText(  countdownTime + "S Left");

        countdownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            countdownTime--;
            countdownLabel.setText(countdownTime + "S Left");

            if (countdownTime <= 0) {
                countdownTimeline.stop();
                countdownLabel.setText("");
                ersal_login.setDisable(false);
                countdownTime = 10;
            }
        }));
        countdownTimeline.setCycleCount(Timeline.INDEFINITE);
        countdownTimeline.play();
    }

}
