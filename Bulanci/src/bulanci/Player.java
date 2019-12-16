package bulanci;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public abstract class Player extends GameObject {

    private ArrayList<Gun> guns = new ArrayList<>();
    private int activeGun;
    private int speed;
    //up    down    left    right
    //0     1       2       3
    private int direction;
    private boolean canMoveUp = true;
    private boolean canMoveDown = true;
    private boolean canMoveLeft = true;
    private boolean canMoveRight = true;

    public Player(String name) {
        super(name);
    }

    public Player(String name, int direction) {
        super(name);
        activeGun = 0;
        speed = 200;
        this.direction = direction;
    }

    public void setGuns(ArrayList<Gun> guns) {
        this.guns = guns;
    }

    public boolean addGun(Gun gun) {
        if (!guns.isEmpty()) {
            for (int i = 1; i < guns.size(); i++) {
                if (guns.get(i).getName().equals(gun.getName())) {
                    guns.get(i).ammo = guns.get(i).getDefaultAmmo();
                    return false;
                }
            }
            gun.setOwner(this);
        }
        guns.add(gun);
        return true;
    }

    public Gun getActiveGun() {
        return guns.get(activeGun);
    }

    public int getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setActiveGun(int gunIndex) {
        this.activeGun = gunIndex;
    }

    public void changeGun() {
        getActiveGun().getView().setVisible(false);

        activeGun = ++activeGun % guns.size();
        //System.out.println(gunIndex);

        getActiveGun().getView().setVisible(true);
        placeGunByDirection();
    }

    private void placeGunByDirection() {
        switch (direction) {
            case 0:
                rotateUp();
                break;
            case 1:
                rotateDown();
                break;
            case 2:
                rotateLeft();
                break;
            case 3:
                rotateRight();
                break;
        }
    }

    public void enableMoveDirections() {
        canMoveUp = true;
        canMoveDown = true;
        canMoveLeft = true;
        canMoveRight = true;
    }

    public void detectCollisionStatic(ArrayList<StaticGameObject> others) {
        for(StaticGameObject other : others)
            whereIsColliding(other);
    }

    public void detectCollisionDynamic(ArrayList<RandomPlayer> others) {
        for(StaticGameObject other : others)
            whereIsColliding(other);
    }

    public void detectCollisionDynamic(HumanPlayer other) {
        whereIsColliding(other);
    }

    private void whereIsColliding(StaticGameObject other) {
        if (isColliding(other)) {
            //System.out.println(toString() + "\n" + obstacle.toString());
            if(other.getView().getTranslateY() < getView().getTranslateY()) {
                //System.out.println("up");
                canMoveUp = false;
            }
            if(other.getView().getTranslateY() > getView().getTranslateY()) {
                //System.out.println("down");
                canMoveDown = false;
            }
            if(other.getView().getTranslateX() < getView().getTranslateX()) {
                //System.out.println("left");
                canMoveLeft = false;
            }
            if(other.getView().getTranslateX() > getView().getTranslateX()) {
                //System.out.println("right");
                canMoveRight = false;
            }
        }
    }

    public void moveUp() {
        if(direction != 0)
            rotateUp();
        if(!isOutOnTop() && canMoveUp) {
            addVelocity(0, -speed);
        }
    }

    public void moveDown() {
        if(direction != 1)
            rotateDown();
        if(!isOutOnBot() && canMoveDown) {
            addVelocity(0, speed);
        }
    }

    public void moveLeft() {
        if(direction != 2)
            rotateLeft();
        if(!isOutOnLeft() && canMoveLeft) {
            addVelocity(-speed, 0);
        }
    }

    public void moveRight() {
        if(direction != 3)
            rotateRight();
        if(!isOutOnRight() && canMoveRight) {
            addVelocity(speed, 0);
        }
    }

    public void rotateUp() {
            direction = 0;
            getView().setRotate(180);
            getActiveGun().getView().setRotate(180);
            getActiveGun().setPosition(getPosition().getX() + 10, getPosition().getY() - getActiveGun().getHeight());
    }

    public void rotateDown() {
            direction = 1;
            getView().setRotate(0);
            getActiveGun().getView().setRotate(0);
            getActiveGun().setPosition(getPosition().getX() + getWidth() - 10 * 2, getPosition().getY() + getHeight());
    }

    public void rotateLeft() {
            direction = 2;
            getView().setRotate(90);
            getActiveGun().getView().setRotate(90);
            getActiveGun().setPosition(getPosition().getX() - getActiveGun().getWidth(), getPosition().getY() + getHeight() - 10 * 2);
    }

    public void rotateRight() {
            direction = 3;
            getView().setRotate(270);
            getActiveGun().getView().setRotate(270);
            getActiveGun().setPosition(getPosition().getX() + getWidth(), getPosition().getY() + 10);
    }

    public boolean isOutOnTop() {
        return getPosition().getY() < 0;
    }

    public boolean isOutOnBot() {
        return getPosition().getY() > 600 - getHeight();
    }

    public boolean isOutOnLeft() {
        return getPosition().getX() < 0;
    }

    public boolean isOutOnRight() {
        return getPosition().getX() > 800 - getWidth();
    }

    @Override
    public void setPosition(double x, double y) {
        super.setPosition(x, y);
        getActiveGun().setPosition(x + 10, y - 20);
    }

    @Override
    public void setVelocity(double x, double y) {
        super.setVelocity(x, y);
        getActiveGun().setVelocity(x, y);
    }

    @Override
    public void addVelocity(double x, double y) {
        super.addVelocity(x, y);
        getActiveGun().addVelocity(x, y);
    }

    public void update(double time, Pane root) {
        super.update(time);
        getActiveGun().update(time, root);
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
