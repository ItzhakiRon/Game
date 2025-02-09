package com.example.projectpentago;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.projectpentago.model.GameState;

public class PentagoGame extends Application {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 800;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showWelcomeScreen();
    }

    private void showWelcomeScreen() {
        WelcomeView welcomeView = new WelcomeView();
        Scene welcomeScene = new Scene(welcomeView, WINDOW_WIDTH, WINDOW_HEIGHT);

        welcomeView.getStartButton().setOnAction(e -> startGame());

        primaryStage.setTitle("Pentago");
        primaryStage.setScene(welcomeScene);
        primaryStage.setMinWidth(WINDOW_WIDTH);
        primaryStage.setMinHeight(WINDOW_HEIGHT);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    private void startGame() {
        GameState gameState = new GameState();
        PentagoView gameView = new PentagoView();
        PentagoController controller = new PentagoController(gameState, gameView);

        Scene gameScene = new Scene(gameView, WINDOW_WIDTH, WINDOW_HEIGHT);

        primaryStage.setScene(gameScene);
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}