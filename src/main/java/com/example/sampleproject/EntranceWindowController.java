package com.example.sampleproject;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EntranceWindowController {

    @FXML
    private Button exitButton;

    @FXML
    private Button startButton;

    @FXML
    void initialize() {
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage controlStage = (Stage) startButton.getScene().getWindow();
                PageManager.goToPage("GameWindow.fxml", controlStage);
            }
        });
    }

    @FXML
    void exitGame(ActionEvent event) {
    	System.exit(0);
    }

}
