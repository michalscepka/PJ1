package minesweeperfx.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import minesweeperfx.board.Board;

public class MinesweeperFX extends Application {

    private StackPane root;

    @Override
    public void start(Stage primaryStage) {
        root = new StackPane();

        initBoard(0, 0);

        Label label_size = new Label("Size:");
        label_size.setFont(new Font("Times", 14));
        label_size.setTranslateX(-235);
        label_size.setTranslateY(-300);
        root.getChildren().add(label_size);

        TextField tf_size = new TextField();
        tf_size.setTranslateX(-150);
        tf_size.setTranslateY(-300);
        tf_size.setMaxWidth(100);
        root.getChildren().add(tf_size);

        Label label_mines = new Label("Mines:");
        label_mines.setFont(new Font("Times", 14));
        label_mines.setTranslateX(-50);
        label_mines.setTranslateY(-300);
        root.getChildren().add(label_mines);

        TextField tf_mines = new TextField();
        tf_mines.setTranslateX(50);
        tf_mines.setTranslateY(-300);
        tf_mines.setMaxWidth(100);
        root.getChildren().add(tf_mines);

        Button btn_start = new Button("Start");
        btn_start.setTranslateX(200);
        btn_start.setTranslateY(-300);
        btn_start.setMaxWidth(100);
        btn_start.setOnAction(event -> initBoard(Integer.parseInt(tf_size.getText()), Integer.parseInt(tf_mines.getText())));
        root.getChildren().add(btn_start);

        Scene scene = new Scene(root, 600, 650);

        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initBoard(int size, int mines) {
        Board board = new Board(size, mines);
        board.initializeBoard();
        BoardCanvas canvas = new BoardCanvas(board, 500, 500);
        canvas.printBoard();
        root.getChildren().add(canvas);
    }
}
