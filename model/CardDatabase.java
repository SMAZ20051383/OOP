package model;

import java.sql.*;
import java.util.ArrayList;

public class CardDatabase {
    private static Connection connection;

    public CardDatabase() {
        try {
            // Establish connection to SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:cardDatabase.db");
            createCardTable(); // Ensure table exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void createCardTable() {
        // SQL statement to create the cards table if not exists
        String sql = "CREATE TABLE IF NOT EXISTS cards (" +
                "name TEXT PRIMARY KEY, " +
                "cost INTEGER, " +
                "attack INTEGER, " +
                "defense INTEGER, " +
                "duration INTEGER, " +
                "damagePlayer INTEGER, " +
                "levelUpgrade INTEGER, " +
                "costUpgrade INTEGER)";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql); // Execute SQL statement
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCard(Card card) {
        // SQL statement to insert a card into the cards table
        String sql = "INSERT INTO cards(name, cost, attack, defense, duration, damagePlayer, levelUpgrade, costUpgrade) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, card.getName());
            pstmt.setInt(2, card.getCost());

            if (card instanceof NormalCard) {
                NormalCard normalCard = (NormalCard) card;
                pstmt.setInt(3, normalCard.getAttack());
                pstmt.setInt(4, normalCard.getDefense());
                pstmt.setInt(5, normalCard.getDuration());
                pstmt.setInt(6, normalCard.getDamagePlayer());
                pstmt.setInt(7, normalCard.getLevelUpgrade());
                pstmt.setInt(8, normalCard.getCostUpgrade());
            } else {
                pstmt.setNull(3, Types.INTEGER);
                pstmt.setNull(4, Types.INTEGER);
                pstmt.setNull(5, Types.INTEGER);
                pstmt.setNull(6, Types.INTEGER);
                pstmt.setNull(7, Types.INTEGER);
                pstmt.setNull(8, Types.INTEGER);
            }

            pstmt.executeUpdate(); // Execute SQL update
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Card> getAllCards() {
        ArrayList<Card> cards = new ArrayList<>();
        String sql = "SELECT * FROM cards";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                int cost = rs.getInt("cost");
                int attack = rs.getInt("attack");
                int defense = rs.getInt("defense");
                int duration = rs.getInt("duration");
                int damagePlayer = rs.getInt("damagePlayer");
                int levelUpgrade = rs.getInt("levelUpgrade");
                int costUpgrade = rs.getInt("costUpgrade");

                Card card;

                // Determine the type of card based on its attributes
                if (attack == 0 && defense == 0 && duration == 0 && damagePlayer == 0 && levelUpgrade == 0 && costUpgrade == 0) {
                    if (name.contains("Shield")) {
                        card = new ShieldCard(name, cost);
                    } else if (name.contains("Heal")) {
                        card = new HealCard(name, cost, 0);
                    } else if (name.contains("Power Boost")) {
                        card = new PowerBoostCard(name, cost);
                    } else if (name.contains("Change Hole Location")) {
                        card = new ChangeHoleLocationCard(name, cost);
                    } else if (name.contains("Repair")) {
                        card = new RepairCard(name, cost);
                    } else if (name.contains("Reduce Round")) {
                        card = new ReduceRoundCard(name, cost);
                    } else if (name.contains("Steal")) {
                        card = new StealCard(name, cost);
                    } else if (name.contains("Weaken Opponent")) {
                        card = new WeakenOpponentCard(name, cost);
                    } else if (name.contains("Duplicate")) {
                        card = new DuplicateCard(name, cost);
                    } else if (name.contains("Hide Opponent Cards")) {
                        card = new HideOpponentCardsCard(name, cost);
                    } else {
                        continue;
                    }
                } else {
                    card = new NormalCard(name, cost, attack, defense, duration, damagePlayer, levelUpgrade, costUpgrade);
                }

                cards.add(card); // Add card to ArrayList
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cards;
    }

    public static void updateCard(Card card) {
        // SQL statement to update a card in the cards table
        String sql = "UPDATE cards SET cost = ?, attack = ?, defense = ?, duration = ?, damagePlayer = ?, levelUpgrade = ?, costUpgrade = ? WHERE name = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, card.getCost());

            if (card instanceof NormalCard) {
                NormalCard normalCard = (NormalCard) card;
                pstmt.setInt(2, normalCard.getAttack());
                pstmt.setInt(3, normalCard.getDefense());
                pstmt.setInt(4, normalCard.getDuration());
                pstmt.setInt(5, normalCard.getDamagePlayer());
                pstmt.setInt(6, normalCard.getLevelUpgrade());
                pstmt.setInt(7, normalCard.getCostUpgrade());
            } else {
                pstmt.setNull(2, Types.INTEGER);
                pstmt.setNull(3, Types.INTEGER);
                pstmt.setNull(4, Types.INTEGER);
                pstmt.setNull(5, Types.INTEGER);
                pstmt.setNull(6, Types.INTEGER);
                pstmt.setNull(7, Types.INTEGER);
            }

            pstmt.setString(8, card.getName());
            pstmt.executeUpdate(); // Execute SQL update
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCard(String name) {
        // SQL statement to delete a card from the cards table
        String sql = "DELETE FROM cards WHERE name = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate(); // Execute SQL update
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
