package com.example.projectpentago.model;


public class GameState {
    private Board board;
    private Cell currentPlayer;
    private boolean isPlacementPhase;
    private boolean gameOver;

    public GameState() {
        board = new Board();
        currentPlayer = Cell.PLAYER1;
        isPlacementPhase = true;
        gameOver = false;
    }

    public boolean placePiece(int row, int col) {
        if (!isPlacementPhase || gameOver) {
            return false;
        }

        boolean success = board.placePiece(row, col, currentPlayer);
        if (success) {
            isPlacementPhase = false;
        }
        return success;
    }

    public void rotateQuadrant(int quadrant, boolean clockwise) {
        if (isPlacementPhase || gameOver) {
            return;
        }

        board.rotateQuadrant(quadrant, clockwise);
        if (board.checkWin()) {
            gameOver = true;
        } else {
            currentPlayer = (currentPlayer == Cell.PLAYER1) ? Cell.PLAYER2 : Cell.PLAYER1;
            isPlacementPhase = true;
        }
    }

    public Cell getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isPlacementPhase() {
        return isPlacementPhase;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Cell getCell(int row, int col) {
        return board.getCell(row, col);
    }

    public boolean isBoardFull() {
        return board.isFull();
    }
}
