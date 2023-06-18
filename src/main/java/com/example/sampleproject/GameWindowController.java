package com.example.sampleproject;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameWindowController {

    @FXML
    private FlowPane firstPlayerPane;

    @FXML
    private HBox hboxCart;

    @FXML
    private Button take;

    @FXML
    private FlowPane secondPlayerPane;

    @FXML
    private static final int NUM_CARDS_TO_DEAL = 6;

    @FXML
    private List<String> availableCards;

    @FXML
    void initialize() throws FileNotFoundException {

        availableCards = new ArrayList<>();
        for (int i = 6; i <= 14; i++) {
            availableCards.add(i + "h");
            availableCards.add(i + "k");
        }

        take.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    // Создаем генератор случайных чисел
                    Random random = new Random();

                    // Перемешиваем доступные карты
                    List<String> shuffledCards = new ArrayList<>(availableCards);
                    java.util.Collections.shuffle(shuffledCards);

                    // Выбираем первые 6 карт
                    List<String> selectedCards = shuffledCards.subList(0, NUM_CARDS_TO_DEAL);
                    List<String> selectedSecondCards = shuffledCards.subList(6, 12);

                    // Очищаем firstPlayerPane перед раздачей карт
                    firstPlayerPane.getChildren().clear();
                    for (String card : selectedCards) {
                        addImage(card, firstPlayerPane);
                    }
                    for (String card : selectedSecondCards) {
                        addImage(card, secondPlayerPane);
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Добавляем обработчик события начала перетаскивания из firstPlayerPane
        firstPlayerPane.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Получаем выбранный ImageView
                ImageView selectedImage = (ImageView) event.getSource();

                // Создаем Dragboard для передачи данных
                Dragboard dragboard = selectedImage.startDragAndDrop(javafx.scene.input.TransferMode.COPY);

                // Создаем ClipboardContent и устанавливаем в него данные
                ClipboardContent content = new ClipboardContent();
                content.putImage(selectedImage.getImage());
                dragboard.setContent(content);

                event.consume();
            }
        });

        // Добавляем обработчик события перетаскивания в hboxCart
        hboxCart.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                // Проверяем, что перетаскивается картинка
                if (event.getGestureSource() != hboxCart && event.getDragboard().hasImage()) {
                    // Разрешаем перетаскивание в hboxCart
                    event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
                }

                event.consume();
            }
        });

        // Добавляем обработчик события завершения перетаскивания в hboxCart
        hboxCart.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                // Получаем перетаскиваемую картинку из Dragboard
                Dragboard dragboard = event.getDragboard();
                Image draggedImage = dragboard.getImage();

                // Создаем новый ImageView с перетаскиваемой картинкой
                ImageView draggedImageView = new ImageView(draggedImage);
                draggedImageView.setFitWidth(50);
                draggedImageView.setFitHeight(80);

                // Добавляем ImageView в hboxCart
                hboxCart.getChildren().add(draggedImageView);


                // Удаляем картинку из firstPlayerPane
                ImageView imageView = (ImageView) event.getGestureSource();
                firstPlayerPane.getChildren().remove(imageView);
                secondPlayerPane.getChildren().remove(imageView);

                event.setDropCompleted(true);
                event.consume();
            }
        });

        // Добавляем обработчик события изменения внешнего вида при перетаскивании над hboxCart
        hboxCart.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                hboxCart.setStyle("-fx-background-color: #F0F0F0;");
                event.consume();
            }
        });

        // Добавляем обработчик события изменения внешнего вида при окончании перетаскивания над hboxCart
        hboxCart.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                hboxCart.setStyle("");
                event.consume();
            }
        });
    }

    private void addImage(String card, FlowPane pane) throws FileNotFoundException {


        File img = new File("src\\main\\resources\\com\\example\\sampleproject\\" + card + ".gif");
        InputStream isImage = (InputStream) new FileInputStream(img);
        ImageView imageView = new ImageView(new Image(isImage));
        imageView.setFitWidth(70);
        imageView.setFitHeight(100);

        // Добавляем обработчик события начала перетаскивания для нового ImageView
        imageView.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Получаем выбранный ImageView
                ImageView selectedImage = (ImageView) event.getSource();

                // Создаем Dragboard для передачи данных
                Dragboard dragboard = selectedImage.startDragAndDrop(javafx.scene.input.TransferMode.COPY);

                // Создаем ClipboardContent и устанавливаем в него данные
                ClipboardContent content = new ClipboardContent();
                content.putImage(selectedImage.getImage());
                dragboard.setContent(content);

                event.consume();
            }
        });

        pane.getChildren().add(imageView);
        int fileCount = pane.getChildren().size();
        System.out.println("Количество файлов: " + fileCount);
    }
}