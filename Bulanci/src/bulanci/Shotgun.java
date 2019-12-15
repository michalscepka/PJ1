package bulanci;

import javafx.scene.layout.Pane;

public class Shotgun extends Gun {

    int[] velocities = new int[] {0, 100, 200, -100, -200};

    public Shotgun(String name, String filename, int defaultAmmo) {
        super(name, filename, defaultAmmo);
    }

    @Override
    public void shoot(Pane root) {
        if(ammo > 0) {
            ammo--;
            switch (owner.getDirection()) {
                case 0:
                    for (int velocity : velocities) {
                        bullets.add(new Bullet(getPosition().getX(), getPosition().getY() - 10, velocity, owner.getSpeed() * -5));
                    }
                    break;
                case 1:
                    for (int velocity : velocities) {
                        bullets.add(new Bullet(getPosition().getX(), getPosition().getY() - 10, velocity, owner.getSpeed() * 5));
                    }
                    break;
                case 2:
                    for (int velocity : velocities) {
                        bullets.add(new Bullet(getPosition().getX() - 10, getPosition().getY(), owner.getSpeed() * -5, velocity));
                    }
                    break;
                case 3:
                    for (int velocity : velocities) {
                        bullets.add(new Bullet(getPosition().getX() - 10, getPosition().getY(), owner.getSpeed() * 5, velocity));
                    }
                    break;
            }
            for(int i = bullets.size() - 5; i < bullets.size(); i++) {
                root.getChildren().add(bullets.get(i).getView());
            }
        }
    }
}
