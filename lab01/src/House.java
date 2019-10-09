public class House {

    public House() {
        this(1, 25, 50, 50);
    }

    public House(int x, int y, int width, int height) {
        this(x, y, width, height, MyColor.BLACK, MyColor.BORDO);
    }

    public House(int x, int y, int width, int height, MyColor color1, MyColor color2) {
        //Test platnosti parametru
        if ((x < 0) || (y < 0) || (width <= 0) || (height <= 0)) {
            throw new IllegalArgumentException("\nParametry nemaji povolene hodnoty: x=" + x + ", y=" + y + ", sirka=" + width + ", vyska=" + height);
        }
        new Rectangle(x, y, width, height, color1);
        new Triangle(x, y - (height / 2), width, height / 2, color2);
    }
}
