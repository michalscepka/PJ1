package bulanci;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Player extends GameObject {

    private String name;
    private ArrayList<Gun> guns = new ArrayList<>();
    private int gunIndex;
    private int speed;
    private String direction;

    public Player(String name) {
        super();
        this.name = name;
        gunIndex = 0;
        speed = 125;
        direction = "RIGHT";
    }

    public void addGun(Gun gun) {
        guns.add(gun);
    }

    public Gun getActiveGun() {
        return guns.get(gunIndex);
    }

    public String getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void moveUp() {
        addVelocity(0, -speed);
        setImage("sprites/player_up.png");
        getActiveGun().setImage("sprites/gun-up.png");
        getActiveGun().addVelocity(getVelocity().getX(), getVelocity().getY());
        getActiveGun().setPosition(getPosition().getX() + 10, getPosition().getY() - getActiveGun().getHeight());
        direction = "UP";
    }

    public void moveDown() {
        addVelocity(0, speed);
        setImage("sprites/player_down.png");
        getActiveGun().setImage("sprites/gun-down.png");
        getActiveGun().addVelocity(getVelocity().getX(), getVelocity().getY());
        getActiveGun().setPosition(getPosition().getX() + getWidth() - 10 * 2, getPosition().getY() + getHeight());
        direction = "DOWN";
    }

    public void moveRight() {
        addVelocity(speed, 0);
        setImage("sprites/player_right.png");
        getActiveGun().setImage("sprites/gun-right.png");
        getActiveGun().addVelocity(getVelocity().getX(), getVelocity().getY());
        getActiveGun().setPosition(getPosition().getX() + getWidth(), getPosition().getY() + 10);
        direction = "RIGHT";
    }

    public void moveLeft() {
        addVelocity(-speed, 0);
        setImage("sprites/player_left.png");
        getActiveGun().setImage("sprites/gun-left.png");
        getActiveGun().addVelocity(getVelocity().getX(), getVelocity().getY());
        getActiveGun().setPosition(getPosition().getX() - getActiveGun().getWidth(), getPosition().getY() + getHeight() - 10 * 2);
        direction = "LEFT";
    }

    @Override
    public void update(double time) {
        super.update(time);
        getActiveGun().update(time);
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        getActiveGun().render(gc);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": ").append(super.toString()).append("; Guns: ");
        for (Gun gun : guns ) {
            sb.append(gun.toString());
        }
        return sb.toString();
    }
}
