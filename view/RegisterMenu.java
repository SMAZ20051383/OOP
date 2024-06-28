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
                String result = login(matcher);
                System.out.println(result);
                if (result.matches(".+logged in!")) {
                    new MainMenu().run(scanner, controller, matcher.group("username"));
                }
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private String register(Matcher matcher) {
        if (!matcher.group("username").matches("[a-zA-Z]+")) {
            return "Incorrect format for username!";
        } else if (controller.passwordFormatIsBad(matcher.group("password"))) {
            return "Incorrect format for password!";
        } else if (controller.getUserByUsername(matcher.group("username")) != null) {
            return "Username already exists!";
        } else {
            controller.register(matcher.group("username"), matcher.group("password"));
            return "User " + matcher.group("username") + " created successfully!";
        }
    }

    private String login(Matcher matcher) {
        if (!matcher.group("username").matches("[a-zA-Z]+")) {
            return "Incorrect format for username!";
        } else if (controller.passwordFormatIsBad(matcher.group("password"))) {
            return "Incorrect format for password!";
        } else if (controller.getUserByUsername(matcher.group("username")) == null) {
            return "Username doesn't exist!";
        } else if (controller.passwordIsWrong(matcher.group("username"), matcher.group("password"))) {
            return "Password is incorrect!";
        } else {
            return "User " + matcher.group("username") + " logged in!";
        }
    }
}
