package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Command {
    number_1("  *  \n" +
            "  *  \n" +
            "  *  \n" +
            "  *  \n" +
            "*****\n"),
    number_2("*****\n" +
            "    *\n" +
            "*****\n" +
            "*    \n" +
            "*****\n"),
    number_3("*****\n" +
            "    *\n" +
            "*****\n" +
            "    *\n" +
            "*****\n"),
    number_4("*   *\n" +
            "*   *\n" +
            "*****\n" +
            "    *\n" +
            "    *\n"),
    number_5("*****\n" +
            "*    \n" +
            "*****\n" +
            "    *\n" +
            "*****\n"),
    number_6("*****\n" +
            "*    \n" +
            "*****\n" +
            "*   *\n" +
            "*****\n"),
    number_7("*****\n" +
            "    *\n" +
            "    *\n" +
            "    *\n" +
            "    *\n"),
    number_8("*****\n" +
            "*   *\n" +
            "*****\n" +
            "*   *\n" +
            "*****\n"),
    number_9("*****\n" +
            "*   *\n" +
            "*****\n" +
            "    *\n" +
            "*****\n"),
    LETTER_A(
            "  *  \n" +
                    " * * \n" +
                    "*****\n" +
                    "*   *\n" +
                    "*   *\n"
    ),
    LETTER_B(
            "**** \n" +
                    "*   *\n" +
                    "**** \n" +
                    "*   *\n" +
                    "**** \n"
    ),
    LETTER_C(
            "*****\n" +
                    "*    \n" +
                    "*    \n" +
                    "*    \n" +
                    "*****\n"
    ),
    LETTER_D(
            "**** \n" +
                    "*   *\n" +
                    "*   *\n" +
                    "*   *\n" +
                    "**** \n"
    ),
    LETTER_E(
            "*****\n" +
                    "*    \n" +
                    "*****\n" +
                    "*    \n" +
                    "*****\n"
    ),
    LETTER_F(
            "*****\n" +
                    "*    \n" +
                    "*****\n" +
                    "*    \n" +
                    "*    \n"
    ),
    LETTER_G(
            "*****\n" +
                    "*    \n" +
                    "* ***\n" +
                    "*   *\n" +
                    "*****\n"
    ),
    LETTER_H(
            "*   *\n" +
                    "*   *\n" +
                    "*****\n" +
                    "*   *\n" +
                    "*   *\n"
    ),
    LETTER_I(
            "*****\n" +
                    "  *  \n" +
                    "  *  \n" +
                    "  *  \n" +
                    "*****\n"
    ),
    LETTER_J(
            "*****\n" +
                    "   * \n" +
                    "   * \n" +
                    "*  * \n" +
                    "**** \n"
    ),
    LETTER_K(
            "*   *\n" +
                    "*  * \n" +
                    "***  \n" +
                    "*  * \n" +
                    "*   *\n"
    ),
    LETTER_L(
            "*    \n" +
                    "*    \n" +
                    "*    \n" +
                    "*    \n" +
                    "*****\n"
    ),
    LETTER_M(
            "*   *\n" +
                    "** **\n" +
                    "* * *\n" +
                    "*   *\n" +
                    "*   *\n"
    ),
    LETTER_N(
            "*   *\n" +
                    "**  *\n" +
                    "* * *\n" +
                    "*  **\n" +
                    "*   *\n"
    ),
    LETTER_O(
            "*****\n" +
                    "*   *\n" +
                    "*   *\n" +
                    "*   *\n" +
                    "*****\n"
    ),
    LETTER_P(
            "*****\n" +
                    "*   *\n" +
                    "*****\n" +
                    "*    \n" +
                    "*    \n"
    ),
    LETTER_Q(
            "*****\n" +
                    "*   *\n" +
                    "*   *\n" +
                    "*  **\n" +
                    "*****\n"
    ),
    LETTER_R(
            "*****\n" +
                    "*   *\n" +
                    "*****\n" +
                    "*  * \n" +
                    "*   *\n"
    ),
    LETTER_S(
            "*****\n" +
                    "*    \n" +
                    "*****\n" +
                    "    *\n" +
                    "*****\n"
    ),
    LETTER_T(
            "*****\n" +
                    "  *  \n" +
                    "  *  \n" +
                    "  *  \n" +
                    "  *  \n"
    ),
    LETTER_U(
            "*   *\n" +
                    "*   *\n" +
                    "*   *\n" +
                    "*   *\n" +
                    "*****\n"
    ),
    LETTER_V(
            "*   *\n" +
                    "*   *\n" +
                    "*   *\n" +
                    " * * \n" +
                    "  *  \n"
    ),
    LETTER_W(
            "*   *\n" +
                    "*   *\n" +
                    "* * *\n" +
                    "** **\n" +
                    "*   *\n"
    ),
    LETTER_X(
            "*   *\n" +
                    " * * \n" +
                    "  *  \n" +
                    " * * \n" +
                    "*   *\n"
    ),
    LETTER_Y(
            "*   *\n" +
                    " * * \n" +
                    "  *  \n" +
                    "  *  \n" +
                    "  *  \n"
    ),
    LETTER_Z(
            "*****\n" +
                    "   * \n" +
                    "  *  \n" +
                    " *   \n" +
                    "*****\n"
    ),
    SHOW_CURRENT_MENU("show current menu"),
    REGISTER("register -u (?<username>.+) -p (?<password>.+) (?<password confirmation>.+) –email (?<email>.+) -n (?<nickname>.*\\S)" ),
    LOGIN("user login -u (?<username>.+) -p (?<password>.*\\S)"),
    EXIT("Exit"),
    LOGOUT("logout"),
    LIST_OF_USERS("list of users"),
    SCOREBOARD("scoreboard"),
    PROFILE_MENU("profile menu"),
    SHOP_MENU("shop menu"),
    START_GAME("start game turns count (?<turnsCount>-?\\d+) username (?<username>\\S+)"),
    BACK("back"),
    CHANGE_PASSWORD("change password old password (?<oldPassword>.+) new password (?<newPassword>.*\\S)"),
    INFO("Info"),
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

    ;

    private final String art;
    private final String regex;

    Command(String art) {
        this.art = art;
        this.regex = null;
    }

    Command(String regex, String placeholder) {
        this.regex = regex;
        this.art = null;
    }

    public String getArt() {
        return this.art;
    }

    public static Matcher getMatcher(String input, Command command) {
        if (command.regex == null) {
            return null;
        }
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
