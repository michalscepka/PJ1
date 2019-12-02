package bulanci;

public class Bullet extends GameObject {

    public Bullet(double positionX, double positionY, double velocityX, double velocityY) {
        super("Bullet", velocityX, velocityY);
        setImage("images/bullet.png");
        setPosition(positionX, positionY);
    }

    public boolean isOutOfBounds() {
        return (getView().getTranslateX() < 0) || (getView().getTranslateX() > 800) || (getView().getTranslateY() < 0) || (getView().getTranslateY() > 600);
    }
}
