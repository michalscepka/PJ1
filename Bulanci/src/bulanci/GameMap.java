package bulanci;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

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

    public void render(GraphicsContext gc) {
        for(StaticGameObject obstacle : obstacles) {
            obstacle.render(gc);
        }
    }

    public void spawnEnemy(double time) {
        int index = (int)((time % (2 * 4)) / 4);

        if(index != 1)
            spawned = false;

        Initializer initializer = new Initializer();
        if(enemies.size() < enemiesCount && index == 1 && !spawned) {
            do {
                enemies.add(initializer.createRandomPlayer(this, enemiesCounter++));
            }while (enemies.size() < enemiesCount);

            spawned = true;
        }
    }
}
