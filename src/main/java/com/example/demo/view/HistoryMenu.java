package com.example.demo.view;

import com.example.demo.controller.GameScoreDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class HistoryMenu {
    private int currentPage = 0;
    private final int scoresPerPage = 5;
    private static GameScoreDatabase database;
    private Scanner scanner;

    public HistoryMenu() {
        database = new GameScoreDatabase();
        scanner = new Scanner(System.in);
        showMainMenu();
    }

    public void showMainMenu() {
        while (true) {
            System.out.println("1. Show scores");
            System.out.println("2. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayScores();
                    break;
                case 2:
                    System.out.println("back");
                    return;
                default:
                    System.out.println("Invalid");
            }
        }
    }

    private void displayScores() {
        ArrayList<String[]> scores = database.getAllGameScores();
        if (scores.isEmpty()) {
            System.out.println("No data");
            return;
        }

        int totalPages = (int) Math.ceil((double) scores.size() / scoresPerPage);

        while (true) {
            int start = currentPage * scoresPerPage;
            int end = Math.min(start + scoresPerPage, scores.size());

            System.out.printf("%-10s %-10s %-15s %-20s %-10s %-10s %-15s\n", "Date", "Time", "Player Name", "Opponent Name", "Opponent Level", "Result", "Rewards");
            for (int i = start; i < end; i++) {
                String[] score = scores.get(i);
                System.out.printf("%-10s %-10s %-15s %-20s %-10s %-10s %-15s\n", score[0], score[1], score[2], score[3], score[4], score[5], score[6]);
            }

            System.out.println("\nPage " + (currentPage + 1) + " of " + totalPages);
            System.out.println("enter the page ,  0. Quit");
            System.out.print("Choose an option: ");
            System.out.println("for sort enter -1");
            String choice = scanner.nextLine().trim();
            int choice_int = Integer.parseInt(choice);
            if (choice_int < (totalPages + 1) && choice_int >= 1) {
                currentPage = choice_int - 1;
            } else if (choice_int == 0) {
                break;
            }
            else if(choice_int==-1){
                System.out.println("1.sort by date");
                System.out.println("2.win/lose");
                System.out.println("3.opponnetName");
                System.out.println("4.opponnetlevel");
                String input = scanner.nextLine();
                switch (input){
                    case "1":
                        System.out.println("1. Ascending");
                        System.out.println("2. Descending");
                        String sortOrderDate = scanner.nextLine().trim();
                        Comparator<String[]> comparatorDate = (a, b) -> {
                            if (sortOrderDate.equals("1")) {
                                return a[0].compareTo(b[0]); // Sort by Date ascending
                            } else {
                                return b[0].compareTo(a[0]); // Sort by Date descending
                            }
                        };
                        Collections.sort(scores, comparatorDate);
                        break;
                    case "2":
                        System.out.println("1. Ascending");
                        System.out.println("2. Descending");
                        String sortOrderWinLose = scanner.nextLine().trim();
                        Comparator<String[]> comparatorWinLose = (a, b) -> {
                            // Assuming score[5] contains "Win" or "Lose"
                            if (sortOrderWinLose.equals("1")) {
                                return a[5].compareTo(b[5]); // Sort by Win/Lose ascending
                            } else {
                                return b[5].compareTo(a[5]); // Sort by Win/Lose descending
                            }
                        };
                        Collections.sort(scores, comparatorWinLose);
                        break;
                    case "3":
                        System.out.println("1. Ascending");
                        System.out.println("2. Descending");
                        String sortOrderOpponentName = scanner.nextLine().trim();
                        Comparator<String[]> comparatorOpponentName = (a, b) -> {
                            return sortOrderOpponentName.equals("1") ? a[3].compareTo(b[3]) : b[3].compareTo(a[3]);
                        };
                        Collections.sort(scores, comparatorOpponentName);
                        break;
                    case "4": // Sort by Opponent Level
                        System.out.println("1. Ascending");
                        System.out.println("2. Descending");
                        String sortOrderOpponentLevel = scanner.nextLine().trim();
                        Comparator<String[]> comparatorOpponentLevel = (a, b) -> {
                            int levelA = Integer.parseInt(a[4]);
                            int levelB = Integer.parseInt(b[4]);
                            if (sortOrderOpponentLevel.equals("1")) {
                                return Integer.compare(levelA, levelB); // Ascending order
                            } else {
                                return Integer.compare(levelB, levelA); // Descending order
                            }
                        };
                        Collections.sort(scores, comparatorOpponentLevel);
                        break;


                }
            }
            else {
                System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void addNewScore(String date, String time, String playerName, String opponentName, int opponentLevel, String result, String rewards) {
        // Get current date and time from the system
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

//    String date = now.format(dateFormatter);
//    String time = now.format(timeFormatter);
        database.addGameScore(date, time, playerName, opponentName, opponentLevel, result, rewards);
    }
    // نمونه‌ای از داده‌های آزمایشی برای تست تابع
//    static String[][] testData = {
//            {"2023-07-01", "10:00", "PlayerX", "PlayerA", "1", "Win", "Reward1"},
//            {"2023-07-02", "11:00", "PlayerX", "PlayerB", "2", "Lose", "Reward2"},
//            {"2023-07-03", "12:00", "PlayerX", "PlayerC", "3", "Win", "Reward3"},
//            {"2023-07-04", "13:00", "PlayerX", "PlayerD", "1", "Win", "Reward4"},
//            {"2023-07-05", "14:00", "PlayerX", "PlayerE", "2", "Lose", "Reward5"},
//            {"2023-07-06", "15:00", "PlayerX", "PlayerF", "3", "Win", "Reward6"},
//            {"2023-07-07", "16:00", "PlayerX", "PlayerG", "1", "Lose", "Reward7"},
//            {"2023-07-08", "17:00", "PlayerX", "PlayerH", "2", "Win", "Reward8"},
//            {"2023-07-09", "18:00", "PlayerX", "PlayerI", "3", "Lose", "Reward9"},
//            {"2023-07-10", "19:00", "PlayerX", "PlayerJ", "1", "Win", "Reward10"},
//            {"2023-07-11", "20:00", "PlayerX", "PlayerK", "2", "Lose", "Reward11"},
//            {"2023-07-12", "21:00", "PlayerX", "PlayerL", "3", "Win", "Reward12"},
//            {"2023-07-13", "22:00", "PlayerX", "PlayerM", "1", "Lose", "Reward13"},
//            {"2023-07-14", "23:00", "PlayerX", "PlayerN", "2", "Win", "Reward14"},
//            {"2023-07-15", "09:00", "PlayerX", "PlayerO", "3", "Lose", "Reward15"},
//            {"2023-07-16", "08:00", "PlayerX", "PlayerP", "1", "Win", "Reward16"},
//            {"2023-07-17", "07:00", "PlayerX", "PlayerQ", "2", "Lose", "Reward17"},
//            {"2023-07-18", "06:00", "PlayerX", "PlayerR", "3", "Win", "Reward18"},
//            {"2023-07-19", "05:00", "PlayerX", "PlayerS", "1", "Lose", "Reward19"},
//            {"2023-07-20", "04:00", "PlayerX", "PlayerT", "2", "Win", "Reward20"},
//            {"2023-07-21", "03:00", "PlayerX", "PlayerU", "3", "Lose", "Reward21"},
//            {"2023-07-22", "02:00", "PlayerX", "PlayerV", "1", "Win", "Reward22"}
//    };
//
//    // تابع برای افزودن داده‌های آزمایشی به پایگاه داده
//    public static void addTestData() {
//        database = new GameScoreDatabase();
//        for (String[] data : testData) {
//            addNewScore(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), data[5], data[6]);
//        }
//    }
}