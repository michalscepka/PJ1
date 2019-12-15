package bulanci;

public class Bullet extends GameObject {

    public Bullet(double positionX, double positionY, double velocityX, double velocityY) {
        super("Bullet", "images/bullet.png", positionX, positionY, velocityX, velocityY);
    }

    public boolean isOutOfBounds() {
        //TODO prepsat na dynamickou velikost mapy
        return (getView().getTranslateX() < 0) || (getView().getTranslateX() > 800) || (getView().getTranslateY() < 0) || (getView().getTranslateY() > 600);
    }
}
