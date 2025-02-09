package com.example.projectpentago.model;

public class Board {
    private Cell[][] board;
    private static final int BOARD_SIZE = 6;
    private static final int QUADRANT_SIZE = 3;

    public Board() {
        board = new Cell[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = Cell.EMPTY;
            }
        }
    }

    public boolean placePiece(int row, int col, Cell player) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE || board[row][col] != Cell.EMPTY) {
            return false;
        }
        board[row][col] = player;
        return true;
    }

    public void rotateQuadrant(int quadrant, boolean clockwise) {
        int startRow = (quadrant / 2) * QUADRANT_SIZE;
        int startCol = (quadrant % 2) * QUADRANT_SIZE;
        Cell[][] temp = new Cell[QUADRANT_SIZE][QUADRANT_SIZE];

        // Copy quadrant to temporary array
        for (int i = 0; i < QUADRANT_SIZE; i++) {
            for (int j = 0; j < QUADRANT_SIZE; j++) {
                temp[i][j] = board[startRow + i][startCol + j];
            }
        }

        // Rotate the temporary array
        Cell[][] rotated = new Cell[QUADRANT_SIZE][QUADRANT_SIZE];
        for (int i = 0; i < QUADRANT_SIZE; i++) {
            for (int j = 0; j < QUADRANT_SIZE; j++) {
                if (clockwise) {
                    rotated[j][QUADRANT_SIZE - 1 - i] = temp[i][j];
                } else {
                    rotated[QUADRANT_SIZE - 1 - j][i] = temp[i][j];
                }
            }
        }

        // Copy back to the main board
        for (int i = 0; i < QUADRANT_SIZE; i++) {
            for (int j = 0; j < QUADRANT_SIZE; j++) {
                board[startRow + i][startCol + j] = rotated[i][j];
            }
        }
    }

    public boolean checkWin() {
        // Check horizontal
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j <= BOARD_SIZE - 5; j++) {
                if (checkFiveInARow(i, j, 0, 1)) {
                    return true;
                }
            }
        }

        // Check vertical
        for (int i = 0; i <= BOARD_SIZE - 5; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (checkFiveInARow(i, j, 1, 0)) {
                    return true;
                }
            }
        }

        // Check diagonals
        for (int i = 0; i <= BOARD_SIZE - 5; i++) {
            for (int j = 0; j <= BOARD_SIZE - 5; j++) {
                if (checkFiveInARow(i, j, 1, 1)) {
                    return true;
                }
            }
        }

        // Check anti-diagonals
        for (int i = 0; i <= BOARD_SIZE - 5; i++) {
            for (int j = 4; j < BOARD_SIZE; j++) {
                if (checkFiveInARow(i, j, 1, -1)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkFiveInARow(int startRow, int startCol, int deltaRow, int deltaCol) {
        Cell first = board[startRow][startCol];
        if (first == Cell.EMPTY) {
            return false;
        }

        for (int i = 1; i < 5; i++) {
            int newRow = startRow + (i * deltaRow);
            int newCol = startCol + (i * deltaCol);
            if (board[newRow][newCol] != first) {
                return false;
            }
        }
        return true;
    }

    public Cell getCell(int row, int col) {
        return board[row][col];
    }

    public boolean isFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == Cell.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}