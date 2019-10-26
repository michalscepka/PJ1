package minesweeperfx.board;

public class Field {
    private final int x;
    private final int y;
    private final Mark mark;

    public Field(int x, int y, Mark mark) {
        this.x = x;
        this.y = y;
        this.mark = mark;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Mark getMark() {
        return mark;
    }
}
