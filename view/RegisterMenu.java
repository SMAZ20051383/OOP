package view;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
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
            }
            else if ((matcher = Command.getMatcher(input, Command.SHOW_CURRENT_MENU)) != null) {
                System.out.println("Register/Login Menu");
            }
            else if ((matcher = Command.getMatcher(input, Command.REGISTER)) != null) {
                System.out.println(register(matcher));
            }
            else if ((matcher = Command.getMatcher(input, Command.LOGIN)) != null) {
                boolean result = login(matcher);
                if (result==true) {
                    new MainMenu().run(scanner, controller, matcher.group("username"));
                }
                if(result==false){
                    Timer timer = new Timer();
                    TimerTask countdownTask = new TimerTask() {
                        private int remainingSeconds = 5;

                        @Override
                        public void run() {
                            if (remainingSeconds > 0) {
                                System.out.println("ثانیه‌های باقی‌مانده: " + remainingSeconds);
                                remainingSeconds--;
                            } else {
                                System.out.println("خوش آمدی");
                                timer.cancel();
                            }
                        }
                    };

                    // برنامه‌ریزی وظیفه برای اجرا هر 1 ثانیه
                    timer.scheduleAtFixedRate(countdownTask, 0, 1000);
                } else {
                    System.out.println("عدد وارد شده صحیح نیست");
                }
            }
            else {
                System.out.println("Invalid command!");
            }
        }
    }

    private String register(Matcher matcher) {
        if (matcher.group("username").matches("^$")) {
            return "Incorrect format for user  empty!";
        } else if (matcher.group("password").matches("^$")){
            return "Incorrect format for password empty!";
        }else if (!matcher.group("username").matches("[a-zA-Z]+")) {
            return "Incorrect format for username!";
        }else if (controller.getUserByUsername(matcher.group("username")) != null) {
            return "Username already exists!";
        }else if (controller.passwordFormatIsBad(matcher.group("password"))){
            return "Password format is incorrect for length";
        }else if (controller.passwordFormatIsBad2(matcher.group("password"))){
            return "Password format is incorrect for structur";
        }else if (controller.emailFormatIsBad2(matcher.group("email"))){
            return "Email format is incorrect";
        } else {
            controller.register(matcher.group("username"), matcher.group("password"), matcher.group("nikname"), matcher.group("email") );
            return "User " + matcher.group("username") + " created successfully!";
        }
    }

    private boolean login(Matcher matcher) {
         if (controller.getUserByUsername(matcher.group("username")) == null) {
             System.out.println("Username doesn’t exist!");
             return false ;
        } else if (controller.passwordIsWrong(matcher.group("username"), matcher.group("password"))) {
             System.out.println("Password and Username don’t match!");
            return false;
        } else {
             System.out.println("user logged in successfully!");
             return true ;
        }
    }
}
