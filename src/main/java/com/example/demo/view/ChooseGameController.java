package com.example.demo.view;

import com.example.demo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ChooseGameController {
    int car_1 = 0 ,  car_2 = 0 ;
    public User user_1, user_2;

    public void setUser_1(User user_1) {
        this.user_1 = user_1;
    }

    public void setUser_2(User user_2) {
        this.user_2 = user_2;
    }

    @FXML
    private RadioButton radioButton1;

    @FXML
    private RadioButton radioButton2;
    @FXML
    private Button click;
    @FXML
    private Label coin_print;
    @FXML
    private TextField coin;
    @FXML
    private Label answer;
    @FXML
    private Button accept;

    // تعریف گروه‌های جدید RadioButton
    @FXML
    private RadioButton radio1_1;
    @FXML
    private RadioButton radio1_2;
    @FXML
    private RadioButton radio1_3;
    @FXML
    private RadioButton radio1_4;

    @FXML
    private RadioButton radio2_1;
    @FXML
    private RadioButton radio2_2;
    @FXML
    private RadioButton radio2_3;
    @FXML
    private RadioButton radio2_4;

    private ToggleGroup optionsGroup;
    private ToggleGroup group1;
    private ToggleGroup group2;

    public void initialize_1() {
        // گروه‌بندی RadioButton ها
        optionsGroup = new ToggleGroup();
        radioButton1.setToggleGroup(optionsGroup);
        radioButton2.setToggleGroup(optionsGroup);

        // گروه‌بندی RadioButton های جدید
        group1 = new ToggleGroup();
        radio1_1.setToggleGroup(group1);
        radio1_2.setToggleGroup(group1);
        radio1_3.setToggleGroup(group1);
        radio1_4.setToggleGroup(group1);

        group2 = new ToggleGroup();
        radio2_1.setToggleGroup(group2);
        radio2_2.setToggleGroup(group2);
        radio2_3.setToggleGroup(group2);
        radio2_4.setToggleGroup(group2);

        click.setOnAction(event -> {
            RadioButton selectedRadioButton = (RadioButton) optionsGroup.getSelectedToggle();
            if (selectedRadioButton != null) {
                String selectedOption = selectedRadioButton.getText();
                if(selectedOption.equals("betting Game")){
                    coin.setVisible(true);
                    coin_print.setVisible(true);
                    accept.setVisible(true);
                } else {
                    coin.setVisible(false);
                    coin_print.setVisible(false);
                    accept.setVisible(false);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/GameMap.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    GameMenuGraphic controller = loader.getController();
                    controller.user_1 = user_1;
                    controller.user_2 = user_2 ;
                    controller.initialize_1();
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();

                }
            }
        });
    }


    @FXML
    public void accept(ActionEvent actionEvent) throws IOException {
        int number = Integer.parseInt(coin.getText());
        if(user_1.getGold() >= number && user_2.getGold() >= number) {
            user_1.changeGold(-1 * number);
            user_2.changeGold(-1 * number);
            answer.setText("decrease successful");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/GameMap.fxml"));
            Parent root = loader.load();
            GameMenuGraphic controller = loader.getController();
            controller.user_1 = this.user_1;
            controller.user_2 = this.user_2 ;
            controller.initialize_1(); ;
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } else if (user_1.getGold() < number) {
            answer.setText("user1 has not enough gold");
        } else if(user_2.getGold() < number) {
            answer.setText("user2 has not enough gold");
        }
    }
    @FXML
    public void Accept(ActionEvent actionEvent) {
        RadioButton selectedRadio1 = (RadioButton) group1.getSelectedToggle();
        RadioButton selectedRadio2 = (RadioButton) group2.getSelectedToggle();

        if (selectedRadio1 != null && selectedRadio2 != null) {
            car_1 = Integer.parseInt(selectedRadio1.getText());
            car_2 =  Integer.parseInt(selectedRadio2.getText());
            user_1.character = car_1 ;
            user_2.character = car_2 ;

        }
    }
}
