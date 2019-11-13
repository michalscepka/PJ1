package minesweeperfx.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import minesweeperfx.board.Board;
import minesweeperfx.board.Field;
import minesweeperfx.board.Mark;

import java.util.LinkedList;

public class BoardCanvas extends Canvas {

    private Board board;
    private final double widthTick;
    private final double heightTick;
    private boolean isFirstMove;

    public BoardCanvas(Board board, double width, double height) {
        super(width, height);
        this.board = board;
        widthTick = width/board.getSize();
        heightTick = height/board.getSize();
        setOnMouseClicked((e) -> {
            int x = (int)(e.getX()/widthTick);
            int y = (int)(e.getY()/heightTick);
            if(e.getButton() == MouseButton.PRIMARY) {
                makeMove(x, y);
            } else if (e.getButton() == MouseButton.SECONDARY) {
                placeFlag(x, y);
            }
        });
        isFirstMove = true;
    }

    public void printBoard() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.CYAN);
        gc.fillRect(0, 0, 500, 500);
        gc.setFill(Color.BLACK);
        for(int i = 0; i < board.getSize() + 1; i++) {
            gc.strokeLine(i * widthTick, 0, i * widthTick, getHeight());
        }
        for(int i = 0; i < board.getSize() + 1; i++) {
            gc.strokeLine(0, i * heightTick, getWidth(), i * heightTick);
        }
    }

    private void makeMove(int x, int y) {
        LinkedList<Field> list = new LinkedList<>();
        if(isFirstMove) {
            board.initializeBoard(x, y);
            isFirstMove = false;
        }
        board.makeMove(x, y, list);
        GraphicsContext gc = getGraphicsContext2D();
        for(Field f : list) {
            if(f.getMark() == Mark.MINE) {
                System.out.println("Game over");
                gc.setFill(Color.ORANGERED);
                gc.fillRect(x * widthTick + 1, y * heightTick + 1, widthTick - 2, heightTick - 2);
                gc.setFill(Color.BLACK);
                gc.setFont(new Font("Times", 20));
                gc.fillText("\uD83D\uDCA3", x * widthTick + (widthTick - 20) / 1.9, (y + 1) * heightTick - (heightTick - 20) / 1.8);
                gc.setFill(Color.BLACK);
            } else if(f.getMark() == Mark.NUMBER) {
                gc.setFill(Color.WHITE);
                gc.fillRect(f.getX() * widthTick + 1, f.getY() * heightTick + 1, widthTick - 2, heightTick - 2);
                gc.setFill(Color.BLACK);
                gc.setFont(new Font("Times", 20));
                gc.fillText(
                        Integer.toString(board.getNumber(f.getX(), f.getY())),
                        f.getX() * widthTick + (widthTick - 20) / 1.5,
                        (f.getY() + 1) * heightTick - (heightTick - 20) / 1.8);
            } else if(f.getMark() == Mark.EMPTY) {
                gc.setFill(Color.WHITE);
                gc.fillRect(f.getX() * widthTick + 1, f.getY() * heightTick + 1, widthTick - 2, heightTick - 2);
                gc.setFill(Color.BLACK);
            }
        }
    }

    private void placeFlag(int x, int y) {
        GraphicsContext gc = getGraphicsContext2D();
        if(board.placeFlag(x, y)) {
            gc.setFill(Color.YELLOW);
            gc.fillRect(x * widthTick + 1, y * heightTick + 1, widthTick - 2, heightTick - 2);
            gc.setFill(Color.rgb(128, 0, 32));
            gc.setFont(new Font("Times", 20));
            gc.fillText("\u2691", x * widthTick + (widthTick - 20) / 1.5, (y + 1) * heightTick - (heightTick - 20) / 1.8);
            gc.setFill(Color.BLACK);
        } else {
            gc.setFill(Color.CYAN);
            gc.fillRect(x * widthTick + 1, y * heightTick + 1, widthTick - 2, heightTick - 2);
            gc.setFill(Color.BLACK);
        }
    }
}
