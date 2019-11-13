package minesweeperfx.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import minesweeperfx.board.Board;

public class MinesweeperFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        Board board = new Board(10, 10);
        //board.initializeBoard();
        BoardCanvas canvas = new BoardCanvas(board, 500, 500);
        canvas.printBoard();

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, 600, 650);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
