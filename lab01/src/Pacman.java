public class Pacman {

    public Pacman() {
        this(1, 25, 50, 50);
    }

    public Pacman(int x, int y, int width, int height) {
        this(x, y, width, height, MyColor.YELLOW, Direction8.WEST, 60);
    }

    public Pacman(int x, int y, int width, int height, MyColor color) {
        this(x, y, width, height, color, Direction8.WEST, 60);
    }

    public Pacman(int x, int y, int width, int height, Direction8 direction) {
        this(x, y, width, height, MyColor.YELLOW, direction, 60);
    }

    public Pacman(int x, int y, int width, int height, MyColor color, Direction8 direction) {
        this(x, y, width, height, color, direction, 60);
    }

    public Pacman(int x, int y, int width, int height, MyColor color1, Direction8 direction, int angle) {
        //Test platnosti parametru
        if ((x < 0) || (y < 0) || (width <= 0) || (height <= 0)) {
            throw new IllegalArgumentException("\nParametry nemaji povolene hodnoty: x=" + x + ", y=" + y + ", sirka=" + width + ", vyska=" + height);
        }

        new Arc(x, y, width, height, color1, direction, 360 - angle);
    }
}
