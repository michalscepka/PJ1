package bulanci;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;

public class GameMap {

    private static int enemiesCounter = 0;
    private int width;
    private int height;
    private ArrayList<StaticGameObject> obstacles;
    private ArrayList<RandomPlayer> enemies;
    private boolean spawned = false;
    private int enemiesCount;

    public GameMap(int width, int height, int enemiesCount) {
        this.width = width;
        this.height = height;
        this.enemiesCount = enemiesCount;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getEnemiesCount() {
        return enemiesCount;
    }

    public static void setEnemiesCounter(int enemiesCounter) {
        GameMap.enemiesCounter = enemiesCounter;
    }

    public void setObstacles(ArrayList<StaticGameObject> obstacles) {
        this.obstacles = obstacles;
    }

    public void setEnemies(ArrayList<RandomPlayer> enemies) {
        this.enemies = enemies;
    }

    public ArrayList<RandomPlayer> getEnemies() {
        return enemies;
    }

    public ArrayList<StaticGameObject> getObstacles() {
        return obstacles;
    }

    public void spawnEnemy(double time, Pane root) {
        int index = (int)((time % (2 * 4)) / 4);

        if(index != 1)
            spawned = false;

        GameManager gameManager = new GameManager();
        Random random = new Random();

        if(enemies.size() < enemiesCount && index == 1 && !spawned) {
            while (enemies.size() < enemiesCount) {
                enemies.add(gameManager.createRandomPlayer(this, enemiesCounter++));
                do {
                    enemies.get(enemies.size() - 1).setPosition(
                            random.nextInt(getWidth() - (int)enemies.get(enemies.size() - 1).getWidth()),
                            random.nextInt(getHeight() - (int)enemies.get(enemies.size() - 1).getHeight()));
                } while(gameManager.canNotPlaceEnemy(enemies, getObstacles()));

                root.getChildren().add(enemies.get(enemies.size() - 1).getView());
                root.getChildren().add(enemies.get(enemies.size() - 1).getActiveGun().getView());
            }

            spawned = true;
        }
    }
}
