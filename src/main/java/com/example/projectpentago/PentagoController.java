package com.example.projectpentago;

import javafx.scene.control.Alert;
import com.example.projectpentago.model.Cell;
import com.example.projectpentago.model.GameState;

public class PentagoController {
    private GameState gameState;
    private PentagoView view;

    public PentagoController(GameState gameState, PentagoView view) {
        this.gameState = gameState;
        this.view = view;
        initializeHandlers();
        updateView();
    }

    private void initializeHandlers() {
        view.setOnCellClick((row, col) -> {
            if (gameState.placePiece(row, col)) {
                updateView();
            }
        });

        view.setOnRotateClick((quadrant, clockwise) -> {
            if (!gameState.isPlacementPhase()) {
                gameState.rotateQuadrant(quadrant, clockwise);
                updateView();
                checkGameEnd();
            }
        });
    }

    private void updateView() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                view.updateCell(i, j, gameState.getCell(i, j));
            }
        }

        if (gameState.isGameOver()) {
            view.setStatus(String.format("Game Over! %s Wins!",
                    gameState.getCurrentPlayer() == Cell.PLAYER1 ? "Player 1" : "Player 2"));
        } else if (gameState.isPlacementPhase()) {
            view.setStatus(String.format("%s's Turn - Place a piece",
                    gameState.getCurrentPlayer() == Cell.PLAYER1 ? "Player 1" : "Player 2"));
        } else {
            view.setStatus(String.format("%s's Turn - Rotate a quadrant",
                    gameState.getCurrentPlayer() == Cell.PLAYER1 ? "Player 1" : "Player 2"));
        }
    }

    private void checkGameEnd() {
        if (gameState.isGameOver()) {
            showGameOverDialog();
        } else if (gameState.isBoardFull()) {
            showDrawDialog();
        }
    }

    private void showGameOverDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(String.format("%s Wins!",
                gameState.getCurrentPlayer() == Cell.PLAYER1 ? "Player 1" : "Player 2"));
        alert.showAndWait();
    }

    private void showDrawDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("The game is a draw!");
        alert.showAndWait();
    }
}
