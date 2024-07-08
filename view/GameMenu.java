package view;

import controller.Controller;
import model.User;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private Controller controller;
    private String playingUsername;
    private String stoppingUsername;
    private int turnsLeft;


    public void run(Scanner scanner, Controller controller, User user_1 , int mode) {
        User user_2 ;
        boolean is_check = true ;
        while (is_check){
            System.out.println("--------------------------------------");
            System.out.println("now login the second user");
            System.out.println("for login enter: user login -u (?<username>.+) -p (?<password>.*\\S)");
            System.out.println("--------------------------------------");
            String input = scanner.nextLine() ;
            Matcher matcher = Command.getMatcher(input,Command.LOGIN);
            RegisterMenu registerMenu = new RegisterMenu();
            if(matcher!=null){
                if(registerMenu.login(matcher)==true){
                    user_2 = controller.getUserByUsername(matcher.group("username"));
                    if(user_2.getUsername().equals(user_1.getUsername())){
                        System.out.println("same user. try again");
                    }
                    else {
                        switch (mode){
                            case 1:
                                two_player_game(controller,user_1 ,user_2);
                                is_check = false ;
                                break;
                            case 2:
                                betting_game(controller,user_1,user_2);
                                is_check =false ;
                                break;
                        }
                    }
                }
            }
            else {
                System.out.println("try again");
            }
        }
    }
    public User two_player_game(Controller controller , User user_1 , User user_2){
        return null ;
    }
    public void betting_game(Controller controller , User user_1 , User user_2){
        Scanner scanner = new Scanner(System.in);
        int number = 0 ;
        while (true){
            System.out.println("enter coin:");
            String input = scanner.nextLine();
             number = Integer.parseInt(input);
            if(user_1.getGold()>=number && user_2.getGold()>=number){
                user_1.changeGold(-1*number);
                user_2.changeGold(-1*number);
                System.out.println("decrease succssful");
                break;
            }
            else if ( user_1.getGold()<number) {
                System.out.println("user1 hasnot enough");
            }
            else if(user_2.getGold()<number){
                System.out.println("user2 hasnot enough");
            }
        }
        User user = two_player_game(controller,user_1,user_2);
        if(user_1.getUsername().equals(user.getUsername())){
            user_1.changeGold(2*number);
        }
        else {
            user_2.changeGold(2*number);
        }
    }

}
