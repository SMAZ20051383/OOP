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
        putValuesInMenu(controller, username1, username2, turns);
        BuildGame(controller, username1, username2);
        String input;
        Matcher matcher;
        while (true) {
            input = scanner.nextLine();
            if ((matcher = Command.getMatcher(input, Command.SHOW_CURRENT_MENU)) != null) {
                System.out.println("Game Menu");
            } else if ((matcher = Command.getMatcher(input, Command.SHOW_OPPONENT_CASTLE_HIT_POINT)) != null) {
                System.out.println(controller.showCastleHitPoint(stoppingUsername));
            } else if ((matcher = Command.getMatcher(input, Command.SHOW_LINE_INFO)) != null) {
                System.out.print(showLineInfo(matcher));
            } else if ((matcher = Command.getMatcher(input, Command.CARDS_TO_PLAY)) != null) {
                int cardsToPlay = controller.cardsToPlay(playingUsername);
                System.out.println("You can play " + cardsToPlay + " cards more!");
            } else if ((matcher = Command.getMatcher(input, Command.MOVES_LEFT)) != null) {
                int movesLeft = controller.movesLeft(playingUsername);
                System.out.println("You have " + movesLeft + " moves left!");
            } else if ((matcher = Command.getMatcher(input, Command.MOVE_TROOP)) != null) {
                System.out.println(moveTroop(matcher));
            } else if ((matcher = Command.getMatcher(input, Command.DEPLOY_TROOP)) != null) {
                System.out.println(deployTroop(matcher, username1));
            } else if ((matcher = Command.getMatcher(input, Command.DEPLOY_HEAL)) != null) {
                System.out.println(deployHeal(matcher));
            } else if ((matcher = Command.getMatcher(input, Command.DEPLOY_FIREBALL)) != null) {
                System.out.println(deployFireball(matcher));
            } else if ((matcher = Command.getMatcher(input, Command.NEXT_TURN)) != null) {
                String result = nextTurn(username1, username2);
                System.out.println(result);
                if (result.matches("Game has ended.+")) {
                    controller.makeLevelAndExperienceAndGold(playingUsername, stoppingUsername);
                    break;
                }
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private void putValuesInMenu(Controller controller, String username1, String username2, int turns) {
        this.controller = controller;
        this.playingUsername = username1;
        this.stoppingUsername = username2;
        this.turnsLeft = turns;
    }


    private String showLineInfo(Matcher matcher) {
        if (controller.isDirectionWrong(matcher.group("direction"))) {
            return "Incorrect line direction!\n";
        } else {
            return controller.showLineInfo(matcher.group("direction"));
        }
    }

    private String moveTroop(Matcher matcher) {
        int row = Integer.parseInt(matcher.group("rowNumber"));
        if (controller.isDirectionWrong(matcher.group("lineDirection"))) {
            return "Incorrect line direction!";
        } else if (row < 1 || row > 15) {
            return "Invalid row number!";
        } else if (!matcher.group("direction").equals("upward")
                && !matcher.group("direction").equals("downward")) {
            return "you can only move troops upward or downward!";
        } else if (controller.movesLeft(playingUsername) == 0) {
            return "You are out of moves!";
        } else if (controller.getTroopFromCell(playingUsername, matcher.group("lineDirection"), row) == null) {
            return "You don't have any troops in this place!";
        } else if (row == 1 && matcher.group("direction").equals("downward")
                || row == 15 && matcher.group("direction").equals("upward")) {
            return "Invalid move!";
        } else {
            return realMoveTroop(matcher, row);
        }
    }

    private String realMoveTroop(Matcher matcher, int row) {
        String troopName = controller.getTroopFromCell(
                playingUsername,
                matcher.group("lineDirection"),
                row).getName();
        controller.moveTroop(playingUsername,
                matcher.group("lineDirection"),
                row,
                matcher.group("direction"));
        if (matcher.group("direction").equals("upward")) row++;
        if (matcher.group("direction").equals("downward")) row--;
        return troopName + " moved successfully to row " + row + " in line " + matcher.group("lineDirection");
    }

    private String deployTroop(Matcher matcher, String username1) {
        int row = Integer.parseInt(matcher.group("rowNumber"));
        if (!matcher.group("troopName").equals("Archer")
                && !matcher.group("troopName").equals("Dragon")
                && !matcher.group("troopName").equals("Wizard")) {
            return "Invalid troop name!";
        } else if (controller.getCardFromDeck(playingUsername, matcher.group("troopName")) == null) {
            return "You don't have " + matcher.group("troopName") + " card in your battle deck!";
        } else if (controller.isDirectionWrong(matcher.group("lineDirection"))) {
            return "Incorrect line direction!";
        } else if (row < 1 || row > 15) {
            return "Invalid row number!";
        } else if (playingUsername.equals(username1) && row > 4
                || !playingUsername.equals(username1) && row < 12) {
            return "Deploy your troops near your castles!";
        } else if (controller.cardsToPlay(playingUsername) == 0) {
            return "You have deployed a troop or spell this turn!";
        } else {
            controller.deployCard(playingUsername,
                    matcher.group("troopName"),
                    matcher.group("lineDirection"),
                    row);
            return "You have deployed " + matcher.group("troopName") + " successfully!";
        }

    }

    private String deployHeal(Matcher matcher) {
        int row = Integer.parseInt(matcher.group("rowNumber"));
        if (controller.isDirectionWrong(matcher.group("lineDirection"))) {
            return "Incorrect line direction!";
        } else if (controller.getCardFromDeck(playingUsername, "Heal") == null) {
            return "You don't have Heal card in your battle deck!";
        } else if (row < 1 || row > 15) {
            return "Invalid row number!";
        } else if (controller.cardsToPlay(playingUsername) == 0) {
            return "You have deployed a troop or spell this turn!";
        } else {
            controller.deployCard(playingUsername, "Heal", matcher.group("lineDirection"), row);
            return "You have deployed Heal successfully!";
        }
    }

    private String deployFireball(Matcher matcher) {
        if (controller.isDirectionWrong(matcher.group("lineDirection"))) {
            return "Incorrect line direction!";
        } else if (controller.getCardFromDeck(playingUsername, "Fireball") == null) {
            return "You don't have Fireball card in your battle deck!";
        } else if (controller.cardsToPlay(playingUsername) == 0) {
            return "You have deployed a troop or spell this turn!";
        } else if (controller.getCastleHitPoint(stoppingUsername, matcher.group("lineDirection")) <= 0) {
            return "This castle is already destroyed!";
        } else {
            controller.doFireball(playingUsername, stoppingUsername, matcher.group("lineDirection"));
            return "You have deployed Fireball successfully!";
        }
    }

    private String nextTurn(String hostUsername, String guestUsername) {
        String tmp = stoppingUsername;
        stoppingUsername = playingUsername;
        playingUsername = tmp;
        controller.reassignMovesAndCards(stoppingUsername);

        if (playingUsername.equals(guestUsername)) return "Player " + playingUsername + " is now playing!";

        controller.attack(hostUsername, guestUsername);
        turnsLeft--;
        if (controller.getWinner(playingUsername, stoppingUsername) != null) {
            return "Game has ended. Winner: " + controller.getWinner(playingUsername, stoppingUsername);
        } else if (turnsLeft == 0) {
            if (controller.getWinner2(playingUsername, stoppingUsername) != null) {
                return "Game has ended. Winner: " + controller.getWinner2(playingUsername, stoppingUsername);
            }
            return "Game has ended. Result: Tie";
        } else {
            return "Player " + playingUsername + " is now playing!";
        }
    }
}
