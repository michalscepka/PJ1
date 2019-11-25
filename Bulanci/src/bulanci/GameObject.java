package bulanci;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GameObject {

    private Image image;
    private ArrayList<Image> images;
    private Point2D position;
    private Point2D velocity;
    private double width;
    private double height;
    private String name = "GameObject";
    private DecimalFormat numberFormat = new DecimalFormat("#0.00");

    public GameObject(GameMap map) {
        position = new Point2D(0, 0);
        velocity = new Point2D(0, 0);
    }

    public GameObject(String name) {
        position = new Point2D(0, 0);
        velocity = new Point2D(0, 0);
        this.name = name;
    }

    public GameObject(double positionX, double positionY, double velocityX, double velocityY) {
        position = new Point2D(positionX, positionY);
        velocity = new Point2D(velocityX, velocityY);
    }

    public GameObject(String filename, double positionX, double positionY, double velocityX, double velocityY) {
        position = new Point2D(positionX, positionY);
        velocity = new Point2D(velocityX, velocityY);
        setImage(filename);
    }

    public GameObject(String filename, String name, double positionX, double positionY, double velocityX, double velocityY) {
        position = new Point2D(positionX, positionY);
        velocity = new Point2D(velocityX, velocityY);
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

    public Point2D getVelocity() {
        return velocity;
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

    public void setVelocity(double x, double y) {
        this.velocity = new Point2D(x, y);
    }

    public void addVelocity(double x, double y) {
        velocity = velocity.add(x, y);
    }

    public void update(double time) {
        position = position.add(velocity.getX() * time, velocity.getY() * time);
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, position.getX(), position.getY());
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(position.getX(), position.getY(), width, height);
    }

    public boolean intersects(GameObject other) {
        return other.getBoundary().intersects(this.getBoundary());
    }

    @Override
    public String toString() {
        return getName() + ": pos[" + numberFormat.format(position.getX()) + ", " + numberFormat.format(position.getY()) + "]"
                + ", vel[" + numberFormat.format(velocity.getX()) + ", " + numberFormat.format(velocity.getY()) + "]";
    }
}
