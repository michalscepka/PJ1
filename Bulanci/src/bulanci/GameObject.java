package bulanci;

import javafx.geometry.Point2D;

public class GameObject extends StaticGameObject {

    private Point2D velocity = new Point2D(0, 0);

    public GameObject(String name) {
        super(name);
    }

    public GameObject(String name, String filename) {
        super(name, filename);
    }

    public GameObject(String name, String filename, double positionX, double positionY, double velocityX, double velocityY) {
        super(name, filename, positionX, positionY);
        velocity = new Point2D(velocityX, velocityY);
    }

    public void setVelocity(double x, double y) {
        this.velocity = new Point2D(x, y);
    }

    public void addVelocity(double x, double y) {
        velocity = velocity.add(x, y);
    }

    public void update(double time) {
        getView().setTranslateX(getView().getTranslateX() + velocity.getX() * time);
        getView().setTranslateY(getView().getTranslateY() + velocity.getY() * time);
    }

    @Override
    public String toString() {
        return super.toString() + ", vel[" + numberFormat.format(velocity.getX()) + ", " + numberFormat.format(velocity.getY()) + "]";
    }
}
