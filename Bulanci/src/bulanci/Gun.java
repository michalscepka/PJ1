package bulanci;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Iterator;

public class Gun extends GameObject {

    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Player owner;

    public Gun(String name, String filename) {
        super(filename, name);
    }

    public Gun(String name, String filename, Player player, double positionX, double positionY, double velocityX, double velocityY) {
        super(name, filename, positionX, positionY, velocityX, velocityY);
        this.owner = player;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void shoot(Pane root) {
        switch (owner.getDirection()) {
            case 0:
                bullets.add(new Bullet(getPosition().getX(), getPosition().getY() - 10, 0, owner.getSpeed() * -5));
                break;
            case 1:
                bullets.add(new Bullet(getPosition().getX(), getPosition().getY() + getHeight(), 0, owner.getSpeed() * 5));
                break;
            case 2:
                bullets.add(new Bullet(getPosition().getX() - 10, getPosition().getY(), owner.getSpeed() * -5, 0));
                break;
            case 3:
                bullets.add(new Bullet(getPosition().getX() + getWidth(), getPosition().getY(), owner.getSpeed() * 5, 0));
                break;
        }
        root.getChildren().add(bullets.get(bullets.size()-1).getView());
    }

    public void update(double time, Pane root) {
        super.update(time);

        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            if (bullet.isOutOfBounds()) {
                root.getChildren().removeAll(bullet.getView());
                bulletIterator.remove();
            } else {
                bullet.update(time);
            }
        }
    }
}
