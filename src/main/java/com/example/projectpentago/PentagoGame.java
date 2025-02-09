package com.example.projectpentago;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.projectpentago.controller.PentagoController;
import com.example.projectpentago.model.GameState;
import com.example.projectpentago.view.PentagoView;

public class PentagoGame extends Application {
    private static final int INITIAL_WINDOW_WIDTH = 600;
    private static final int INITIAL_WINDOW_HEIGHT = 800;

    @Override
    public void start(Stage primaryStage) {
        GameState gameState = new GameState();
        PentagoView view = new PentagoView();
        PentagoController controller = new PentagoController(gameState, view);

        Scene scene = new Scene(view, INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT);

        primaryStage.setTitle("Pentago");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(INITIAL_WINDOW_WIDTH);
        primaryStage.setMinHeight(INITIAL_WINDOW_HEIGHT);
        primaryStage.show();

        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
