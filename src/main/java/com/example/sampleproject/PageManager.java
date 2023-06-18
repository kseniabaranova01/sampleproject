package com.example.sampleproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PageManager {

    public static void goToPage(String fxmlPath, Stage currentStage) {
        try {
            // Загрузка новой сцены из FXML файла
            FXMLLoader loader = new FXMLLoader(PageManager.class.getResource(fxmlPath));
            Parent root = loader.load();

            // Создание нового окна и установка сцены
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Закрытие текущего окна
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
