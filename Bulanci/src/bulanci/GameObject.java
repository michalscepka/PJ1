package bulanci;

import javafx.geometry.Point2D;

public class GameObject extends StaticGameObject {

    private Point2D velocity;

    public GameObject(String name) {
        super(name);
        velocity = new Point2D(0, 0);
    }

    public GameObject(String filename, String name) {
        super(filename, name);
        velocity = new Point2D(0, 0);
    }

    public GameObject(double positionX, double positionY, double velocityX, double velocityY) {
        super(positionX, positionY);
        velocity = new Point2D(velocityX, velocityY);
    }

    public GameObject(String filename, double positionX, double positionY, double velocityX, double velocityY) {
        this(positionX, positionY, velocityX, velocityY);
        setImage(filename);
    }

    public GameObject(String filename, String name, double positionX, double positionY, double velocityX, double velocityY) {
        super(filename, name, positionX, positionY);
        velocity = new Point2D(velocityX, velocityY);
    }

    public Point2D getVelocity() {
        return velocity;
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

    public boolean intersects(StaticGameObject other) {
        return other.getBoundary().intersects(this.getBoundary());
    }

    @Override
    public String toString() {
        return super.toString() + ", vel[" + numberFormat.format(velocity.getX()) + ", " + numberFormat.format(velocity.getY()) + "]";
    }
}
