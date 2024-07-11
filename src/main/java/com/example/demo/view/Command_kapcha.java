package com.example.demo.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Command_kapcha {
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
    ;

    private final String regex;

    Command_kapcha(String regex) {
        this.regex = regex;
    }
    public String getArt() {
        return this.regex;
    }

    public static Matcher getMatcher(String input, Command_kapcha command) {
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
