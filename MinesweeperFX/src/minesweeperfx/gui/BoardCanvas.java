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
    }

    public void printBoard() {
        GraphicsContext gc = getGraphicsContext2D();
        for(int i = 0; i < board.getSize() + 1; i++) {
            gc.strokeLine(i * widthTick, 0, i * widthTick, getHeight());
        }
        for(int i = 0; i < board.getSize() + 1; i++) {
            gc.strokeLine(0, i * heightTick, getWidth(), i * heightTick);
        }
    }

    private void makeMove(int x, int y) {
        LinkedList<Field> list = new LinkedList<>();
        board.makeMove(x, y, list);
        GraphicsContext gc = getGraphicsContext2D();
        for(Field f : list) {
            if(f.getMark() == Mark.MINE) {
                gc.setFill(Color.RED);
                gc.fillOval(f.getX() * widthTick + 2, f.getY() * heightTick + 2, widthTick - 4, heightTick - 4);
                gc.setFill(Color.BLACK);
            } else if(f.getMark() == Mark.NUMBER) {
                gc.setFont(new Font("Times", 20));
                gc.fillText(Integer.toString(board.getNumber(
                        f.getX(), f.getY())),
                        f.getX() * widthTick + (widthTick - 20) / 2,
                        (f.getY() + 1) * heightTick - (heightTick - 20) / 2);
            }
        }
    }

    private void placeFlag(int x, int y) {
        GraphicsContext gc = getGraphicsContext2D();
        if(board.placeFlag(x, y)) {
            gc.fillOval(x * widthTick + 2, y * heightTick + 2, widthTick - 4, heightTick - 4);
        } else {
            gc.setFill(Color.WHITE);
            gc.fillOval(x * widthTick + 1, y * heightTick + 1, widthTick - 2, heightTick - 2);
            gc.setFill(Color.BLACK);
        }
    }
}
