package com.example.demo.view;

import com.example.demo.controller.Controller;
import com.example.demo.controller.GameScoreDatabase;
import com.example.demo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

public class HistoryMenu {
    public User user ;

    @FXML
    private TableView<String[]> scoreTable;
    @FXML
    private TableColumn<String[], String> timeColumn;
    @FXML
    private TableColumn<String[], String> resultColumn;
    @FXML
    private TableColumn<String[], String> opponentColumn;
    @FXML
    private TableColumn<String[], String> levelColumn;
    @FXML
    private TableColumn<String[], String> achievementColumn;
    @FXML
    private Button Sort_by_Date;
    @FXML
    private Button Sort_by_WinLose;
    @FXML
    private Button Sort_by_OponentName;
    @FXML
    private Button Sort_by_Oponentlevel;
    @FXML
    private Button Back;

    private GameScoreDatabase database;

    public HistoryMenu() {
        database = new GameScoreDatabase();
    }

    @FXML
    public void initialize() {
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        opponentColumn.setCellValueFactory(new PropertyValueFactory<>("opponent"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        achievementColumn.setCellValueFactory(new PropertyValueFactory<>("achievement"));
        displayScores();
    }

    private void displayScores() {
        scoreTable.getItems().clear();
        scoreTable.getItems().addAll(database.getAllGameScores());
    }

    @FXML
    private void sortByDate() {
        Comparator<String[]> comparator = Comparator.comparing(a -> a[0]);
        Collections.sort(scoreTable.getItems(), comparator);
    }

    @FXML
    private void sortByWinLose() {
        Comparator<String[]> comparator = Comparator.comparing(a -> a[5]);
        Collections.sort(scoreTable.getItems(), comparator);
    }

    @FXML
    private void sortByOpponentName() {
        Comparator<String[]> comparator = Comparator.comparing(a -> a[3]);
        Collections.sort(scoreTable.getItems(), comparator);
    }

    @FXML
    private void sortByOpponentLevel() {
        Comparator<String[]> comparator = Comparator.comparing(a -> Integer.parseInt(a[4]));
        Collections.sort(scoreTable.getItems(), comparator);
    }

    @FXML
    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Main_Menu.fxml"));
        Parent root = loader.load();
        MainMenu controller = loader.getController();
        controller.setUser(Controller.getUserByUsername(user.getUsername()));
        controller.update();
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));

    }
}
