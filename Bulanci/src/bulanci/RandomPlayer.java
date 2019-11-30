package bulanci;

import java.util.Random;

public class RandomPlayer extends Player {

    private boolean moved = false;
    private double moveDuration = 0.5;
    private int newDirection;

    public RandomPlayer(String name, GameMap map, int direction) {
        super(name, map, direction);
    }

    public void makeMove(double time) {

        int index = (int)((time % (2 * moveDuration)) / moveDuration);

        if(index != 1)
            moved = false;

        //TODO prepsat at negeneruje direction do zdi
        if(index == 1 && !moved) {
            Random random = new Random();
            newDirection = random.nextInt(4);
            moved = true;
            System.out.println(toString() + " NEW DIRECTION: " + newDirection);
            getActiveGun().shoot();
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
}
