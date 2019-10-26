/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.board;

import java.util.Random;

/**
 *
 * @author Michal
 */
public class Board {
    private final int size;
    private int mineBoard[][];
    private Mark userBoard[][];

    public Board(int size) {
        this.size = size;
        mineBoard = new int[size][size];
        userBoard = new Mark[size][size];
    }
    
    public void initializeBoard(int minesNumber) {
        Random random = new Random();
        for (int i = 0; i < minesNumber; i++) {
            int x, y;
            do {                
                x = random.nextInt(size);
                y = random.nextInt(size);
            } while (mineBoard[x][y] == -1);
            mineBoard[x][y] = -1;
        }
        countSurroundings();
    }
    
    public void makeMove(int x, int y) {
        if(x < 0 || y < 0 || x >= size || y >= size)
            return;
        if(userBoard[x][y] != null)
            return;
        if(mineBoard[x][y] == -1) {
            userBoard[x][y] = Mark.MINE;
        } else if(mineBoard[x][y] == 0) {
            userBoard[x][y] = Mark.EMPTY;
            makeMove(x + 1, y - 1);
            makeMove(x + 1, y);
            makeMove(x + 1, y + 1);
            makeMove(x, y + 1);
            makeMove(x, y - 1);
            makeMove(x - 1, y - 1);
            makeMove(x - 1, y);
            makeMove(x - 1, y + 1);
        } else {
            userBoard[x][y] = Mark.NUMBER;
        }
    }
    
    private void countSurroundings() {
        int[][] surroundings = { {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1} };
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(mineBoard[i][j] == -1) {
                    continue;
                }
                for(int k = 0; k < 8; k++) {
                    try {
                        if(mineBoard[i + surroundings[k][0]][j + surroundings[k][1]] == -1) {
                            mineBoard[i][j]++;
                        }
                    }
                    catch(Exception e) {}
                }
            }
        }
    }
    
    public boolean placeFlag(int x, int y) {
        if (userBoard[x][y] == Mark.FLAG) {
            userBoard[x][y] = null;
            return false;
        } else {
            userBoard[x][y] = Mark.FLAG;
            return true;
        }
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
        sb.append("\n").append("\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (userBoard[i][j] == Mark.EMPTY) {
                    sb.append('0').append("\t");
                } else if (userBoard[i][j] == Mark.FLAG) {
                    sb.append('F').append("\t");
                } else if (userBoard[i][j] == Mark.MINE) {
                    sb.append('M').append("\t");
                } else if (userBoard[i][j] == Mark.NUMBER) {
                    sb.append(mineBoard[i][j]).append("\t");
                } else {
                    sb.append('.').append("\t");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
