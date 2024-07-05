package view;

import controller.Controller;

import java.util.Scanner;
import java.util.regex.Matcher;

public class RegisterMenu {
    private Controller controller;

    public void run(Controller controller) {
        this.controller = controller;
        Scanner scanner = new Scanner(System.in);
        String input;
        Matcher matcher;
        while (true) {
            input = scanner.nextLine();
            if ((matcher = Command.getMatcher(input, Command.EXIT)) != null) {
                break;
            } else if ((matcher = Command.getMatcher(input, Command.SHOW_CURRENT_MENU)) != null) {
                System.out.println("Register/Login Menu");
            } else if ((matcher = Command.getMatcher(input, Command.REGISTER)) != null) {
                System.out.println(register(matcher));
            } else if ((matcher = Command.getMatcher(input, Command.LOGIN)) != null) {
                boolean result = login(matcher);
                if (result) {
                    // User logged in successfully, proceed to main menu
                    new MainMenu().run(scanner, controller, matcher.group("username"));
                }
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private String register(Matcher matcher) {
        if (matcher.group("username").isEmpty()) {
            return "Incorrect format for username: empty!";
        } else if (matcher.group("password").isEmpty()) {
            return "Incorrect format for password: empty!";
        } else if (!matcher.group("username").matches("[a-zA-Z]+")) {
            return "Incorrect format for username!";
        } else if (controller.getUserByUsername(matcher.group("username")) != null) {
            return "Username already exists!";
        } else if (controller.passwordFormatIsBad(matcher.group("password"))) {
            return "Password format is incorrect for length!";
        } else if (controller.passwordFormatIsBad2(matcher.group("password"))) {
            return "Password format is incorrect for structure!";
        } else if (controller.emailFormatIsBad2(matcher.group("email"))) {
            return "Email format is incorrect!";
        } else {
            controller.register(matcher.group("username"), matcher.group("password"), matcher.group("nickname"), matcher.group("email"));
            return "User " + matcher.group("username") + " created successfully!";
        }
    }

    private boolean login(Matcher matcher) {
        if (controller.getUserByUsername(matcher.group("username")) == null) {
            System.out.println("Username doesn’t exist!");
            return false;
        } else if (controller.passwordIsWrong(matcher.group("username"), matcher.group("password"))) {
            System.out.println("Password and Username don’t match!");
            return false;
        } else {
            System.out.println("User logged in successfully!");
            return true;
        }
    }
}
