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
    private ArrayList<StaticGameObject> grass;
    private ArrayList<Gun> guns;
    private boolean spawnedEnemies = false;
    private boolean spawnedGuns = false;
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

    public void setGrass(ArrayList<StaticGameObject> grass) {
        this.grass = grass;
    }

    public void setGuns(ArrayList<Gun> guns) {
        this.guns = guns;
    }

    public ArrayList<RandomPlayer> getEnemies() {
        return enemies;
    }

    public ArrayList<StaticGameObject> getObstacles() {
        return obstacles;
    }

    public ArrayList<StaticGameObject> getGrass() {
        return grass;
    }

    public ArrayList<Gun> getGuns() {
        return guns;
    }

    public void spawnEnemy(GameManager gameManager, double time, Pane root) {
        int index = (int)(time % (7));

        if(index != 1)
            spawnedEnemies = false;

        if(enemies.size() < enemiesCount && index == 1 && !spawnedEnemies) {
            Random random = new Random();
            while (enemies.size() < enemiesCount) {
                enemies.add(gameManager.createRandomPlayer(enemiesCounter++));
                do {
                    enemies.get(enemies.size() - 1).setPosition(
                            random.nextInt(getWidth() - (int)enemies.get(enemies.size() - 1).getWidth()),
                            random.nextInt(getHeight() - (int)enemies.get(enemies.size() - 1).getHeight()));
                } while(gameManager.canNotPlaceEnemy(enemies, obstacles));

                root.getChildren().add(enemies.get(enemies.size() - 1).getView());
                root.getChildren().add(enemies.get(enemies.size() - 1).getActiveGun().getView());
            }
            spawnedEnemies = true;
        }
    }

    public void spawnGuns(GameManager gameManager, double time, Pane root) {
        int index = (int)(time % (10));

        if(index != 1)
            spawnedGuns = false;

        if(index == 1 && !spawnedGuns) {
            Random random = new Random();
            //Gun[] newGuns = new Gun[]{new Shotgun("Shotgun", "images/shotgun.png", 4), new AssaultRifle("AR", "images/ar.png", 15)};
            Gun[] newGuns = new Gun[]{new Shotgun("Shotgun", "images/shotgun.png", 4)};

            guns.add(newGuns[random.nextInt(newGuns.length)]);
            do {
                guns.get(guns.size() - 1).setPosition(
                        random.nextInt(getWidth() - (int)guns.get(guns.size() - 1).getWidth()),
                        random.nextInt(getHeight() - (int)guns.get(guns.size() - 1).getHeight()));
            } while(gameManager.canNotPlaceGun(enemies, obstacles, guns));

            guns.get(guns.size() - 1).getView().setRotate(90);
            root.getChildren().add(guns.get(guns.size() - 1).getView());

            spawnedGuns = true;
        }
    }
}
