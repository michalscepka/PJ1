package bulanci;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public abstract class Player extends GameObject {

    private ArrayList<Gun> guns = new ArrayList<>();
    private int gunIndex;
    private int speed;
    //up    down    left    right
    //0     1       2       3
    private int direction;
    //mozna dat mapu do GameObject
    private GameMap map;
    private boolean canMoveUp, canMoveDown, canMoveLeft, canMoveRight;

    public Player(String name, GameMap map, int direction) {
        super(name);
        gunIndex = 0;
        speed = 200;
        this.direction = direction;
        this.map = map;
        //canMove = true;
    }

    public void addGun(Gun gun) {
        guns.add(gun);
    }

    public Gun getActiveGun() {
        return guns.get(gunIndex);
    }

    public ArrayList<Gun> getGuns() {
        return guns;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    /*public void disableMoveDirection(GameObject other) {
        if(this.getPosition().getY() < (other.getPosition().getY() + other.getHeight())) {
            //this.setPosition(this.getPosition().getX(), this.getPosition().getY());
            setCanMoveUp(false);
        } else if (direction == 1) {
            //this.setPosition(this.getPosition().getX(), this.getPosition().getY());
            setCanMoveDown(false);
        } else if(direction == 2) {
            //this.setPosition(this.getPosition().getX(), this.getPosition().getY());
            setCanMoveLeft(false);
        } else if (direction == 3) {
            //this.setPosition(this.getPosition().getX(), this.getPosition().getY());
            setCanMoveRight(false);
        }
    }

    public void enableMoveDirection() {
        setCanMoveUp(true);
        setCanMoveDown(true);
        setCanMoveLeft(true);
        setCanMoveRight(true);
    }

    public void setCanMoveUp(boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }

    public void setCanMoveDown(boolean canMoveDown) {
        this.canMoveDown = canMoveDown;
    }

    public void setCanMoveLeft(boolean canMoveLeft) {
        this.canMoveLeft = canMoveLeft;
    }

    public void setCanMoveRight(boolean canMoveRight) {
        this.canMoveRight = canMoveRight;
    }*/

    public void moveUp() {
        if(!isOutOnTop()) {
            addVelocity(0, -speed);
            if(direction != 0) {
                direction = 0;
                setImage(getImage(direction));
                getActiveGun().setImage(getActiveGun().getImage(direction));
            }
            getActiveGun().addVelocity(getVelocity().getX(), getVelocity().getY());
            getActiveGun().setPosition(getPosition().getX() + 10, getPosition().getY() - getActiveGun().getHeight());
        }
    }

    public void moveDown() {
        if(!isOutOnBot()) {
            addVelocity(0, speed);
            if(direction != 1) {
                direction = 1;
                setImage(getImage(direction));
                getActiveGun().setImage(getActiveGun().getImage(direction));
            }
            getActiveGun().addVelocity(getVelocity().getX(), getVelocity().getY());
            getActiveGun().setPosition(getPosition().getX() + getWidth() - 10 * 2, getPosition().getY() + getHeight());
        }
    }

    public void moveLeft() {
        if(!isOutOnLeft()) {
            addVelocity(-speed, 0);
            if(direction != 2) {
                direction = 2;
                setImage(getImage(direction));
                getActiveGun().setImage(getActiveGun().getImage(direction));
            }
            getActiveGun().addVelocity(getVelocity().getX(), getVelocity().getY());
            getActiveGun().setPosition(getPosition().getX() - getActiveGun().getWidth(), getPosition().getY() + getHeight() - 10 * 2);
        }
    }

    public void moveRight() {
        if(!isOutOnRight()) {
            addVelocity(speed, 0);
            if(direction != 3) {
                direction = 3;
                setImage(getImage(direction));
                getActiveGun().setImage(getActiveGun().getImage(direction));
            }
            getActiveGun().addVelocity(getVelocity().getX(), getVelocity().getY());
            getActiveGun().setPosition(getPosition().getX() + getWidth(), getPosition().getY() + 10);
        }
    }

    public boolean isOutOnTop() {
        return getPosition().getY() < 0;
    }

    public boolean isOutOnBot() {
        return getPosition().getY() > map.getHeight() - getHeight();
    }

    public boolean isOutOnLeft() {
        return getPosition().getX() < 0;
    }

    public boolean isOutOnRight() {
        return getPosition().getX() > map.getWidth() - getWidth();
    }

    @Override
    public void setVelocity(double x, double y) {
        super.setVelocity(x, y);
        getActiveGun().setVelocity(x, y);
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
        sb.append(super.toString()).append("; Guns: ");
        for (Gun gun : guns) {
            sb.append(gun.toString());
        }
        sb.append("; Direction: ").append(direction);
        return sb.toString();
    }
}
