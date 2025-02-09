package com.example.projectpentago.view;


import javafx.animation.RotateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import com.example.projectpentago.model.Cell;

public class PentagoView extends VBox {
    private static final int CELL_SIZE = 80;
    private static final int BOARD_SIZE = 6;
    private static final int QUADRANT_SIZE = 3;
    private static final int MINIMUM_WINDOW_WIDTH = 600;
    private static final int MINIMUM_WINDOW_HEIGHT = 800;
    private static final int QUADRANT_SPACING = 10;
    private static final Duration ROTATION_DURATION = Duration.millis(500);

    private Button[][] cells;
    private Label statusLabel;
    private Button[] rotateButtons;
    private GridPane[] quadrantGrids;

    public PentagoView() {
        cells = new Button[BOARD_SIZE][BOARD_SIZE];
        rotateButtons = new Button[8];
        quadrantGrids = new GridPane[4];

        setMinSize(MINIMUM_WINDOW_WIDTH, MINIMUM_WINDOW_HEIGHT);
        setAlignment(Pos.CENTER);
        setSpacing(20);
        setFillWidth(true);

        initializeUI();
    }

    private void initializeUI() {
        VBox contentContainer = new VBox();
        contentContainer.setAlignment(Pos.CENTER);
        contentContainer.setSpacing(20);

        statusLabel = new Label("Player 1's Turn - Place a piece");
        statusLabel.setStyle("-fx-font-size: 24px; -fx-padding: 10px;");
        statusLabel.setMaxWidth(Double.MAX_VALUE);
        statusLabel.setAlignment(Pos.CENTER);

        StackPane boardContainer = new StackPane();
        boardContainer.setAlignment(Pos.CENTER);

        GridPane mainGrid = new GridPane();
        mainGrid.setAlignment(Pos.CENTER);
        mainGrid.setHgap(QUADRANT_SPACING);
        mainGrid.setVgap(QUADRANT_SPACING);
        mainGrid.setPadding(new Insets(QUADRANT_SPACING));
        mainGrid.setStyle("-fx-background-color: #404040;");

        for (int quadRow = 0; quadRow < 2; quadRow++) {
            for (int quadCol = 0; quadCol < 2; quadCol++) {
                int quadrantIndex = quadRow * 2 + quadCol;
                GridPane quadrantGrid = createQuadrant(quadRow, quadCol);
                quadrantGrids[quadrantIndex] = quadrantGrid;
                mainGrid.add(quadrantGrid, quadCol, quadRow);
            }
        }

        boardContainer.getChildren().add(mainGrid);

        HBox rotationControlsContainer = new HBox();
        rotationControlsContainer.setAlignment(Pos.CENTER);
        rotationControlsContainer.setSpacing(30);

        for (int i = 0; i < 4; i++) {
            VBox quadrantControls = new VBox(8);
            quadrantControls.setAlignment(Pos.CENTER);
            Button clockwise = createRotateButton("↻", i);
            Button counterClockwise = createRotateButton("↺", i);
            rotateButtons[i * 2] = clockwise;
            rotateButtons[i * 2 + 1] = counterClockwise;
            quadrantControls.getChildren().addAll(clockwise, counterClockwise);
            rotationControlsContainer.getChildren().add(quadrantControls);
        }

        contentContainer.getChildren().addAll(statusLabel, boardContainer, rotationControlsContainer);
        getChildren().add(contentContainer);
        setPadding(new Insets(20));
    }

    private GridPane createQuadrant(int quadRow, int quadCol) {
        GridPane quadrantGrid = new GridPane();
        quadrantGrid.setAlignment(Pos.CENTER);
        quadrantGrid.setHgap(2);
        quadrantGrid.setVgap(2);
        quadrantGrid.setStyle("-fx-background-color: #808080; -fx-padding: 5;");

        for (int i = 0; i < QUADRANT_SIZE; i++) {
            for (int j = 0; j < QUADRANT_SIZE; j++) {
                int row = quadRow * QUADRANT_SIZE + i;
                int col = quadCol * QUADRANT_SIZE + j;
                Button cell = createCell();
                cells[row][col] = cell;
                quadrantGrid.add(cell, j, i);
            }
        }

        return quadrantGrid;
    }

    private Button createCell() {
        Button cell = new Button();
        cell.setPrefSize(CELL_SIZE, CELL_SIZE);
        cell.setMinSize(CELL_SIZE, CELL_SIZE);
        cell.setMaxSize(CELL_SIZE, CELL_SIZE);
        cell.setStyle("-fx-background-color: white; -fx-border-color: #404040; -fx-border-width: 1;");
        return cell;
    }

    private Button createRotateButton(String symbol, int quadrant) {
        Button button = new Button(symbol);
        button.setPrefSize(50, 50);
        button.setMinSize(50, 50);
        button.setMaxSize(50, 50);
        button.setStyle("-fx-font-size: 20px; -fx-background-radius: 25; -fx-background-color: #e0e0e0;");
        return button;
    }

    public void updateCell(int row, int col, Cell value) {
        Button cell = cells[row][col];
        Circle circle = new Circle(CELL_SIZE / 3);

        switch (value) {
            case PLAYER1:
                circle.setFill(Color.RED);
                break;
            case PLAYER2:
                circle.setFill(Color.BLUE);
                break;
            case EMPTY:
                cell.setGraphic(null);
                return;
        }

        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        cell.setGraphic(circle);
    }

    public void rotateQuadrant(int quadrant, boolean clockwise, Runnable onFinish) {
        GridPane quadrantGrid = quadrantGrids[quadrant];
        RotateTransition rotateTransition = new RotateTransition(ROTATION_DURATION, quadrantGrid);

        double startAngle = quadrantGrid.getRotate();
        double endAngle = startAngle + (clockwise ? 90 : -90);

        rotateTransition.setFromAngle(startAngle);
        rotateTransition.setToAngle(endAngle);

        rotateTransition.setOnFinished(event -> {
            quadrantGrid.setRotate(0);
            onFinish.run();
        });

        rotateTransition.play();
    }

    public void setStatus(String status) {
        statusLabel.setText(status);
    }

    public void setOnCellClick(CellClickHandler handler) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                final int row = i;
                final int col = j;
                cells[i][j].setOnAction(e -> handler.handle(row, col));
            }
        }
    }

    public void setOnRotateClick(RotateClickHandler handler) {
        for (int i = 0; i < rotateButtons.length; i++) {
            final int quadrant = i / 2;
            final boolean clockwise = i % 2 == 0;
            rotateButtons[i].setOnAction(e -> {
                rotateQuadrant(quadrant, clockwise, () -> handler.handle(quadrant, clockwise));
            });
        }
    }

    public interface CellClickHandler {
        void handle(int row, int col);
    }

    public interface RotateClickHandler {
        void handle(int quadrant, boolean clockwise);
    }
}
