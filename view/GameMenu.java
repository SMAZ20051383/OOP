package view;

import controller.Controller;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private Controller controller;
    private String playingUsername;
    private String stoppingUsername;
    private int turnsLeft;

    private static void BuildGame(Controller controller, String username1, String username2) {
        controller.buildPlayGround();
        controller.buildCastles(username1);
        controller.buildCastles(username2);
    }

    public void run(Scanner scanner, Controller controller, String username1, String username2, int turns) {
    }

}
