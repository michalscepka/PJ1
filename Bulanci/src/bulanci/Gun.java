package bulanci;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Iterator;

public class Gun extends GameObject {

    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Player owner;

    public Gun(String filename, String name, Player player, double positionX, double positionY, double velocityX, double velocityY) {
        super(filename, name, positionX, positionY, velocityX, velocityY);
        this.owner = player;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void shoot() {
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
    }

    @Override
    public void update(double time) {
        super.update(time);

        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            if (bullet.isOutOfBounds()) {
                bulletIterator.remove();
            } else {
                bullet.update(time);
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        if(!bullets.isEmpty())
            for(Bullet bullet : bullets)
                bullet.render(gc);
    }
}
