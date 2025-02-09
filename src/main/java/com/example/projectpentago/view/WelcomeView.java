package com.example.projectpentago.view;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class WelcomeView extends VBox {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 800;
    private Button startButton;

    public WelcomeView() {
        setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setStyle("-fx-background-color: #f0f0f0;");
        setSpacing(30);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(20));

        initializeUI();
    }

    private void initializeUI() {
        // Create title
        Text title = new Text("PENTAGO");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 64));


        startButton = new Button("Start Game");
        startButton.setPrefSize(200, 50);
        startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 20;");
        startButton.setOnMouseEntered(e -> startButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 20;"));
        startButton.setOnMouseExited(e -> startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 20;"));





        getChildren().addAll(title, startButton);
    }

    public Button getStartButton() {
        return startButton;
    }
}
