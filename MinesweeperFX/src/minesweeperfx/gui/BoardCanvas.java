package minesweeperfx.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import minesweeperfx.board.Board;
import minesweeperfx.board.Field;
import minesweeperfx.board.Mark;

import java.time.LocalTime;
import java.util.LinkedList;

import static java.time.temporal.ChronoUnit.SECONDS;

public class BoardCanvas extends Canvas {

    private Board board;
    private final double widthTick;
    private final double heightTick;
    private boolean game_over;
    private int flags_on_mines;
    private int bad_flags;
    private int moves_count;
    private LocalTime start_time;

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
        game_over = false;
        flags_on_mines = 0;
        bad_flags = 0;
        moves_count = 0;
        start_time = LocalTime.now();
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
        if(!game_over) {
            LinkedList<Field> list = new LinkedList<>();
            board.makeMove(x, y, list);
            GraphicsContext gc = getGraphicsContext2D();
            for(Field f : list) {
                moves_count++;
                if(f.getMark() == Mark.MINE) {
                    //zobrazeni miny
                    gc.setFill(Color.ORANGERED);
                    gc.fillRect(x * widthTick + 1, y * heightTick + 1, widthTick - 2, heightTick - 2);
                    gc.setFill(Color.BLACK);
                    gc.setFont(new Font("Times", 20));
                    gc.fillText("\uD83D\uDCA3", x * widthTick + (widthTick - 20) / 1.9, (y + 1) * heightTick - (heightTick - 20) / 1.8);
                    gc.setFill(Color.BLACK);
                    //game over
                    game_over = true;
                    gc.fillRect(175, 225, 150, 50);
                    gc.setFill(Color.WHITE);
                    gc.setFont(new Font("Times", 20));
                    gc.fillText("Game Over", 200, 255);
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
            //vyhra
            if((Math.pow(board.getSize(), 2) - moves_count) == board.getMinesNumber()) {
                LocalTime end_time = LocalTime.now();
                gc.fillRect(175, 225, 150, 50);
                gc.setFill(Color.WHITE);
                gc.setFont(new Font("Times", 20));
                gc.fillText("Winner " + SECONDS.between(start_time, end_time) + " s", 200, 255);
                game_over = true;
            }
        }
    }

    private void placeFlag(int x, int y) {
        if(!game_over) {
            GraphicsContext gc = getGraphicsContext2D();
            if(board.placeFlag(x, y)) {
                gc.setFill(Color.YELLOW);
                gc.fillRect(x * widthTick + 1, y * heightTick + 1, widthTick - 2, heightTick - 2);
                gc.setFill(Color.rgb(128, 0, 32));
                gc.setFont(new Font("Times", 20));
                gc.fillText("\u2691", x * widthTick + (widthTick - 20) / 1.5, (y + 1) * heightTick - (heightTick - 20) / 1.8);
                gc.setFill(Color.BLACK);

                if(board.getNumber(x, y) == -1)
                    flags_on_mines++;
                else
                    bad_flags++;
            } else {
                gc.setFill(Color.CYAN);
                gc.fillRect(x * widthTick + 1, y * heightTick + 1, widthTick - 2, heightTick - 2);
                gc.setFill(Color.BLACK);

                if(board.getNumber(x, y) == -1)
                    flags_on_mines--;
                else
                    bad_flags--;
            }
            //vyhra
            if(flags_on_mines == board.getMinesNumber() && bad_flags == 0) {
                LocalTime end_time = LocalTime.now();
                gc.fillRect(175, 225, 150, 50);
                gc.setFill(Color.WHITE);
                gc.setFont(new Font("Times", 20));
                gc.fillText("Winner " + SECONDS.between(start_time, end_time) + " s", 200, 255);
                game_over = true;
            }
        }
    }
}
