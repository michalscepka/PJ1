package bulanci;

public class Bullet extends GameObject {

    public Bullet(double positionX, double positionY, double velocityX, double velocityY) {
        super(positionX, positionY, velocityX, velocityY);
        setImage("sprites/bullet.png");
    }

    public boolean isOutOfBounds() {
        return (getPosition().getX() < 0) || (getPosition().getX() > 800) || (getPosition().getY() < 0) || (getPosition().getY() > 600);
    }
}
