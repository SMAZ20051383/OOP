package com.example.demo.view;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Kapcha {

    public int mathematic_kapch() {
        Random random = new Random();
        int randomNumber = random.nextInt(3) + 1;
        int num_1 = random.nextInt(20) + 1;
        int num_2 = random.nextInt(20) + 1;
        switch (randomNumber) {
            case 1:
                System.out.println(num_1 + " plus " + num_2);
                return num_1 + num_2;
            case 2:
                System.out.println(num_1 + " minus " + num_2);
                return num_1 - num_2;
            case 3:
                System.out.println(num_1 + " multiply " + num_2);
                return num_1 * num_2;
        }
        return 0;
    }
    public static int mathematic_kapch(TextArea label) {
        Random random = new Random();
        int randomNumber = random.nextInt(3) + 1;
        int num_1 = random.nextInt(20) + 1;
        int num_2 = random.nextInt(20) + 1;
        switch (randomNumber) {
            case 1:
                label.setText(num_1 + " plus " + num_2);
                return num_1 + num_2;
            case 2:
                label.setText(num_1 + " minus " + num_2);
                return num_1 - num_2;
            case 3:
                label.setText(num_1 + " multiply " + num_2);
                return num_1 * num_2;
        }
        return 0;
    }

    public String ascii_art_kapcha() {
        String ans = "";
        Map<Character, Command_kapcha> artMap = new HashMap<>();
        for (int i = 1; i <= 9; i++) {
            artMap.put(Character.forDigit(i, 10), Command_kapcha.values()[i - 1]);
        }
        for (int i = 0; i < 26; i++) {
            artMap.put((char) ('A' + i), Command_kapcha.values()[9 + i]);
        }

        Random random = new Random();
        ans += (char) ('1' + random.nextInt(9));
        ans += (char) ('A' + random.nextInt(26));
        ans += (char) ('1' + random.nextInt(9));
        ans += (char) ('A' + random.nextInt(26));
        StringBuilder[] lines = new StringBuilder[5];
        for (int i = 0; i < 5; i++) {
            lines[i] = new StringBuilder();
        }
        for (char c : ans.toCharArray()) {
            Command_kapcha art = artMap.get(c);
            if (art != null) {
                String[] artLines = art.getArt().split("\n");
                for (int i = 0; i < 5; i++) {
                    lines[i].append(artLines[i]).append("  ");
                }
            }
        }

        for (StringBuilder line : lines) {
            System.out.println(line.toString());
        }
        return ans;
    }
    public String ascii_art_kapcha(TextArea label) {
        String ans = "";
        Map<Character, Command_kapcha> artMap = new HashMap<>();
        for (int i = 1; i <= 9; i++) {
            artMap.put(Character.forDigit(i, 10), Command_kapcha.values()[i - 1]);
        }
        for (int i = 0; i < 26; i++) {
            artMap.put((char) ('A' + i), Command_kapcha.values()[9 + i]);
        }

        Random random = new Random();
        ans += (char) ('1' + random.nextInt(9));
        ans += (char) ('A' + random.nextInt(26));
        ans += (char) ('1' + random.nextInt(9));
        ans += (char) ('A' + random.nextInt(26));
        StringBuilder[] lines = new StringBuilder[5];
        for (int i = 0; i < 5; i++) {
            lines[i] = new StringBuilder();
        }
        for (char c : ans.toCharArray()) {
            Command_kapcha art = artMap.get(c);
            if (art != null) {
                String[] artLines = art.getArt().split("\n");
                for (int i = 0; i < 5; i++) {
                    lines[i].append(artLines[i]).append("  ");
                }
            }
        }

        StringBuilder fullArt = new StringBuilder();
        for (StringBuilder line : lines) {
            fullArt.append(line.toString()).append("\n");
        }

        // تنظیم متن لیبل
        label.setText(fullArt.toString());
        System.out.println(fullArt.toString());

        return ans;
    }

}
