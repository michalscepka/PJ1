package bulanci;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.text.DecimalFormat;
import java.util.ArrayList;

public abstract class StaticGameObject {

    private Image image;
    private ArrayList<Image> images;
    protected Point2D position;
    private double width;
    private double height;
    private String name = "GameObject";
    protected DecimalFormat numberFormat = new DecimalFormat("#0.00");

    public StaticGameObject(String name) {
        this(0, 0);
        this.name = name;
    }

    public StaticGameObject(String filename, String name) {
        this(0, 0);
        setImage(filename);
        this.name = name;
    }

    public StaticGameObject(double positionX, double positionY) {
        position = new Point2D(positionX, positionY);
    }

    public StaticGameObject(String filename, double positionX, double positionY) {
        this(positionX, positionY);
        setImage(filename);
    }

    public StaticGameObject(String filename, String name, double positionX, double positionY) {
        this(positionX, positionY);
        setImage(filename);
        this.name = name;
    }

    public void setImage(Image image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
    }

    public void setImage(String filename) {
        setImage(new Image(filename));
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public Image getImage(int index) {
        return images.get(index);
    }

    public String getName() {
        return name;
    }

    public Point2D getPosition() {
        return position;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setPosition(double x, double y) {
        this.position = new Point2D(x, y);
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, position.getX(), position.getY());
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(position.getX(), position.getY(), width, height);
    }

    public Rectangle2D getBoundary2() {
        return new Rectangle2D(position.getX() - 5, position.getY() - 5, width + 5, height + 5);
    }

    public boolean intersects(GameObject other) {
        return other.getBoundary().intersects(this.getBoundary());
    }

    public boolean isNear(GameObject other) {
        return other.getBoundary2().intersects(this.getBoundary2());
    }

    @Override
    public String toString() {
        return getName() + ": pos[" + numberFormat.format(position.getX()) + ", " + numberFormat.format(position.getY()) + "]";
    }
}
