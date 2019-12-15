package bulanci;

import javafx.scene.layout.Pane;

import java.util.Random;

public class RandomPlayer extends Player {

    private boolean moved = false;
    private int newDirection;
    private double shootTime = -1;

    public RandomPlayer(String name, int direction) {
        super(name, direction);
    }

    public void makeMove(double time) {

        double moveDuration = 0.5;
        int index = (int)((time % (2 * moveDuration)) / moveDuration);

        if(index != 1)
            moved = false;

        if(index == 1 && !moved) {
            Random random = new Random();
            newDirection = random.nextInt(4);
            moved = true;
            //System.out.println(toString() + " NEW DIRECTION: " + newDirection);
        }

        switch (newDirection) {
            case 0:
                moveUp();
                break;
            case 1:
                moveDown();
                break;
            case 2:
                moveLeft();
                break;
            case 3:
                moveRight();
                break;
        }
    }

    public void shoot(double time, Pane root) {
        if(shootTime == -1)
            shootTime = time;

        if(shootTime + 0.75 < time) {
            getActiveGun().shoot(root);
            shootTime = -1;
        }
    }
}
