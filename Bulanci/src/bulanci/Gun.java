package bulanci;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Gun extends GameObject {

    private int defaultAmmo;
    private double reloadStartTime = -1;

    protected ArrayList<Bullet> bullets = new ArrayList<>();
    protected Player owner;
    protected int ammo;

    public Gun(String name, String filename, int defaultAmmo) {
        super(name, filename);
        this.defaultAmmo = defaultAmmo;
        ammo = defaultAmmo;
    }

    public Gun(String name, String filename, Player player, int defaultAmmo) {
        super(name, filename);
        this.owner = player;
        this.defaultAmmo = defaultAmmo;
        ammo = defaultAmmo;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getAmmo() {
        return ammo;
    }

    public int getDefaultAmmo() {
        return defaultAmmo;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void shoot(Pane root) {
        if(ammo > 0) {
            ammo--;
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
    }

    public boolean reload(double time) {
        if(reloadStartTime == -1)
            reloadStartTime = time;

        if(reloadStartTime + 2.5 < time) {
            ammo = defaultAmmo;
            reloadStartTime = -1;
            return true;
        }
        return false;
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
