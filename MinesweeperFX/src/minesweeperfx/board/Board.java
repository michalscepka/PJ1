package minesweeperfx.board;

import java.util.LinkedList;
import java.util.Random;

public class Board {
    private final int size;
    private int[][] mineBoard;
    private Mark[][] userBoard;
    private final int minesNumber;

    public Board(int size, int minesNumber) {
        this.size = size;
        mineBoard = new int[size][size];
        userBoard = new Mark[size][size];
        this.minesNumber = minesNumber;
    }

    public int getSize() {
        return size;
    }

    public int getNumber(int x, int y) {
        return mineBoard[x][y];
    }

    public void initializeBoard(int mouseX, int mouseY) {
        System.out.println("Board init");
        Random random = new Random();
        for (int i = 0; i < minesNumber; i++) {
            int x, y;
            do {
                x = random.nextInt(size);
                y = random.nextInt(size);
            } while (mineBoard[x][y] == -1 || (x == mouseX && y == mouseY));
            mineBoard[x][y] = -1;
        }
        countSurroundings();
    }

    public void makeMove(int x, int y, LinkedList<Field> list) {
        if(x < 0 || y < 0 || x >= size || y >= size)
            return;
        if(userBoard[x][y] != null)
            return;

        if(list == null)
            list = new LinkedList<>();

        if(mineBoard[x][y] == -1) {
            userBoard[x][y] = Mark.MINE;
            list.add(new Field(x, y, Mark.MINE));
            //System.out.println("Game over");
        } else if(mineBoard[x][y] == 0) {
            userBoard[x][y] = Mark.EMPTY;
            list.add(new Field(x, y, Mark.EMPTY));
            makeMove(x + 1, y - 1, list);
            makeMove(x + 1, y, list);
            makeMove(x + 1, y + 1, list);
            makeMove(x, y + 1, list);
            makeMove(x, y - 1, list);
            makeMove(x - 1, y - 1, list);
            makeMove(x - 1, y, list);
            makeMove(x - 1, y + 1, list);
        } else {
            userBoard[x][y] = Mark.NUMBER;
            list.add(new Field(x, y, Mark.NUMBER));
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
                    catch(Exception ignored) {}
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
