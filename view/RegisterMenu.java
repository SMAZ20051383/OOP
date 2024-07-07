package view;

import controller.Controller;
import model.User;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
            }
            else if ((matcher = Command.getMatcher(input, Command.Forget_password)) != null) {
                change_password_from_forgetpassword(matcher.group("username"));
            }
            else if ((matcher = Command.getMatcher(input, Command.REGISTER)) != null) {
                System.out.println(register(matcher));
            } else if ((matcher = Command.getMatcher(input, Command.LOGIN)) != null) {
                boolean result = login(matcher);
                if (result) {
                    // User logged in successfully, proceed to main menu
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
        if(matcher.group("password").equals("random")){
            password = generatePassword();
            System.out.println("your random password is: "+ password);
            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.print("please enter the pass for check:");
                String password_check = scanner.nextLine();
                if(password_check.equals(password)){
                    System.out.println("correct");
                    break;
                }
                else {
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
        }
        else if (controller.getUserByUsername(matcher.group("username")) != null) {
            return "Username already exists!";
        }
        else if (controller.passwordFormatIsBad(password)) {
            return "Password format is incorrect for length!";
        }
        else if (controller.passwordFormatIsBad2(password)) {
            return "Password format is incorrect for structure!";
        }
        else if (controller.emailFormatIsBad2(matcher.group("email"))) {
            return "Email format is incorrect!";
        } else {
            controller.register(matcher.group("username"), password, matcher.group("nickname"), matcher.group("email"));
            System.out.println("User created successfully. Please choose a security question(enter number) : ");
            System.out.println("1-What is your fathers name ?");
            System.out.println("2-What is your favourite color ? ");
            System.out.println("3-What was the name of your first pet? ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            System.out.print("your answer:");
            String input_2 = scanner.nextLine();
            set_security_question(matcher.group("username"),Integer.parseInt(input),input_2);
            Kapcha kapcha = new Kapcha() ;
            Random random = new Random() ;
            int randomnumber = random.nextInt(2);
            switch (randomnumber){
                case 0:
                    while (true){
                        int number = kapcha.mathematic_kapch();
                        input= scanner.nextLine();
                        if(Integer.parseInt(input)==number){
                            break;
                        }
                    }
                    break;
                case 1:
                    while (true){
                        String kapcha1 = kapcha.ascii_art_kapcha();
                        input= scanner.nextLine();
                        if(input.equals(kapcha1)){
                            break;
                        }
                    }

                    break;
            }
            return "Set successfully";
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
    private void set_security_question(String username,int question , String answer){
        for(User user : User.allUsers){
            if(user.getUsername().equals(username)){
                String []securityQuestions = new String[2];
                switch (question){
                    case 1:
                        String input = "What is your fathers name ? ";
                        securityQuestions[0]=input;
                        break;
                    case 2:
                        input = "What is your favourite color ? ";
                        securityQuestions[0]=input;
                        break;
                    case 3:
                        input= "What was the name of your first pet? ";
                        securityQuestions[0]=input;
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
    private void change_password_from_forgetpassword(String username){
        User user = controller.getUserByUsername(username);
        System.out.println("whats the answer?");
        System.out.println(user.getSecurityQuestion()[0]);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine() ;
        if(input.equals(user.getSecurityQuestion()[1])){
            while (true){
                System.out.println("now set your password");
                input = scanner.nextLine() ;
                if(input.equals("random")){
                    String password = generatePassword();
                    System.out.println("your random password is: "+ password);
                        System.out.print("please enter the pass for check:");
                        String password_check = scanner.nextLine();
                        if(password_check.equals(password)){
                            System.out.println("correct");
                            user.setPassword(password);
                            break;
                        }
                        else {
                            System.out.println("wrong answer . try again");
                        }
                }
                else if (controller.passwordFormatIsBad(input)) {
                    System.out.println("Password format is incorrect for length!");
                }
                else if (controller.passwordFormatIsBad2(input)) {
                    System.out.println("Password format is incorrect for structure!");
                }
                else{
                    user.setPassword(input);
                    System.out.println("password set succssfully");
                    break;
                }
            }
        }
        else {
            System.out.println("wrong answer . please try again");
        }
    }
}
