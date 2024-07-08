package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Command {
    SHOW_CURRENT_MENU("show current menu"),
    REGISTER("register -u (?<username>.+) -p (?<password>.+) (?<passwordConfirmation>.*) -email (?<email>.+) -n (?<nickname>.*\\S)"),
    LOGIN("user login -u (?<username>.+) -p (?<password>.*\\S)"),
    EXIT("Exit"),
    LOGOUT("logout"),
    LIST_OF_USERS("list of users"),
    SCOREBOARD("scoreboard"),
    PROFILE_MENU("profile menu"),
    SHOP_MENU("shop menu"),
    START_GAME("go to game"),
    BACK("back"),
    CHANGE_PASSWORD("profile change password -o (?<oldPassword>.+) -n (?<newPassword>.+)"),
    INFO("Show information"),
    REMOVE_FROM_DECK("remove from battle deck (?<cardName>.*\\S)"),
    ADD_TO_DECK("add to battle deck (?<cardName>.*\\S)"),
    SHOW_DECK("show battle deck"),
    BUY_CARD("buy card (?<cardName>.*\\S)"),
    SELL_CARD("sell card (?<cardName>.*\\S)"),
    SHOW_OPPONENT_CASTLE_HIT_POINT("show the hitpoints left of my opponent"),
    SHOW_LINE_INFO("show line info (?<direction>.*\\S)"),
    CARDS_TO_PLAY("number of cards to play"),
    MOVES_LEFT("number of moves left"),
    MOVE_TROOP("move troop in line (?<lineDirection>.+) and row (?<rowNumber>-?\\d+) (?<direction>.*\\S)"),
    DEPLOY_TROOP("deploy troop (?<troopName>.+) in line (?<lineDirection>.+) and row (?<rowNumber>-?\\d+)"),
    DEPLOY_HEAL("deploy spell Heal in line (?<lineDirection>.+) and row (?<rowNumber>-?\\d+)"),
    DEPLOY_FIREBALL("deploy spell Fireball in line (?<lineDirection>.*\\S)"),
    NEXT_TURN("next turn"),
    Forget_password("Forgot my password -u (?<username>.+)"),
    Change_username("Profile change -u (?<username>.+)"),
    Change_nickname("Profile change -n (?<nickname>.+)"),
    Change_Email("profile change -e (?<email>.+)"),
    HISTORY("history"),
    Admin_login("Login admin (?<password>.+)")
    ;

    private final String regex;

    Command(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, Command command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
