package com.example.paintio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    @FXML
    private ChoiceBox<Integer> enemy;
    private Integer[] numberOfEnemies ={1,2,3};
    private int selectedEnemy;
    @FXML
    private ChoiceBox<Level> level;
    private Level[] difficalty={Level.EASY,Level.HARD};
    private Level selectedLevel;
    @FXML
    private ChoiceBox<String> speed;
    private String[] speedPrs ={"100%","50%","25%"};
    private int selectedSpeed;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     enemy.getItems().addAll(numberOfEnemies);
     level.getItems().addAll(difficalty);
     speed.getItems().addAll(speedPrs);
     enemy.setOnAction(this::setEnemy);
     level.setOnAction(this::setLevel);
     speed.setOnAction(this::setSpeed);
    }
    public void setEnemy(ActionEvent actionEvent) {
        int i=enemy.getValue();
        selectedEnemy=i;
    }
    public void setLevel(ActionEvent actionEvent) {
        Level l=level.getValue();
        selectedLevel=l;
    }
    public void setSpeed(ActionEvent actionEvent) {
        String s=speed.getValue();
        int sp=0;
        if(s=="100%")
            sp=50;
        if(s=="50%")
            sp=100;
        if(s=="25%")
            sp=200;
        selectedSpeed=sp;
    }
    public void next(ActionEvent actionEvent){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("start.fxml"));
        try {
            root=loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StartController startController=loader.getController();
        startController.speed=selectedSpeed;
        startController.enemies=selectedEnemy;
        startController.level=selectedLevel;
        stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
