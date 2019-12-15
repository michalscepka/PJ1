package bulanci;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.text.DecimalFormat;

public class StaticGameObject {

    private Rectangle view;
    private String name;
    protected DecimalFormat numberFormat = new DecimalFormat("#0.00");

    public StaticGameObject(String name) {
        this.name = name;
        this.view = new Rectangle();
    }

    public StaticGameObject(String name, String filename) {
        this(name);
        setImage(filename);
    }

    public StaticGameObject(String name, String filename, double positionX, double positionY) {
        this(name, filename);
        this.view.setTranslateX(positionX);
        this.view.setTranslateY(positionY);
    }

    public void setImage(Image image) {
        view = new Rectangle(image.getWidth(), image.getHeight());
        view.setFill(new ImagePattern(image));
    }

    public Rectangle getView() {
        return view;
    }

    public void setImage(String filename) {
        setImage(new Image(filename));
    }

    public String getName() {
        return name;
    }

    public Point2D getPosition() {
        return new Point2D(view.getTranslateX(), view.getTranslateY());
    }

    public void setPosition(double x, double y) {
        this.view.setTranslateX(x);
        this.view.setTranslateY(y);
    }

    public double getWidth() {
        return view.getWidth();
    }

    public double getHeight() {
        return  view.getHeight();
    }

    public boolean isColliding(GameObject other) {
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }

    public boolean isColliding(StaticGameObject other) {
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }

    @Override
    public String toString() {
        return getName() + ": pos[" + numberFormat.format(view.getTranslateX()) + ", " + numberFormat.format(view.getTranslateY()) + "]";
    }
}
