package minesweeper.board;

import java.lang.StringBuilder;


public class Board {
    private final int size;
    private int mineBoard[][];

    public Board(int size) {
        this.size = size;
        mineBoard = new int[size][size];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(mineBoard[i][j]).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
