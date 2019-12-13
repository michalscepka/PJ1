package bulanci;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    public GameManager() {}

    public HumanPlayer initPlayer(GameMap map, Pane root) {

        HumanPlayer player = new HumanPlayer("hrac1", map, 0);
        player.setImage("images/player.png");
        player.addGun(new Gun("Pistol", "images/gun-up.png", player, 7));
        player.setPosition((double)map.getWidth() / 2.0, (double)map.getHeight() / 2.0);

        System.out.println(player.toString());

        root.getChildren().add(player.getView());
        root.getChildren().add(player.getActiveGun().getView());
        return player;
    }

    public ArrayList<RandomPlayer> initRandomPlayers(GameMap map, int count, Pane root) {
        Random random = new Random();
        ArrayList<RandomPlayer> enemies = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            enemies.add(createRandomPlayer(map, i));
            do {
                enemies.get(i).setPosition(
                        random.nextInt(map.getWidth() - (int)enemies.get(i).getWidth()),
                        random.nextInt(map.getHeight() - (int)enemies.get(i).getHeight()));
            } while(cantPlaceEnemy(enemies, map.getObstacles()));
            root.getChildren().add(enemies.get(i).getView());
            root.getChildren().add(enemies.get(i).getActiveGun().getView());
            System.out.println(enemies.size());
        }
        for(RandomPlayer bot : enemies) {
            System.out.println(bot.toString());
        }
        return enemies;
    }

    public RandomPlayer createRandomPlayer(GameMap map, int index) {
        RandomPlayer bot = new RandomPlayer("bot_" + index, map, 0);
        bot.setImage("images/player_bot.png");
        bot.addGun(new Gun("Pistol", "images/gun-up.png", bot, 100));

        return bot;
    }

    public ArrayList<StaticGameObject> initStaticGameObjects(GameMap map, Pane root) {
        ArrayList<StaticGameObject> obstacles = new ArrayList<>();

        int[] angles = {0, 90, 180, 270};
        Random random = new Random();
        for(int i = 0; i < 10; i++) {
            obstacles.add(new Bush("images/bush.png"));
            do {
                obstacles.get(i).setPosition(random.nextInt(map.getWidth() - 64), random.nextInt(map.getHeight() - 64));
            } while(cantPlaceObstacle(obstacles));
            obstacles.get(i).getView().setRotate(angles[random.nextInt(4)]);

            root.getChildren().add(obstacles.get(i).getView());
        }

        return obstacles;
    }

    public boolean cantPlaceObstacle(ArrayList<StaticGameObject> obstacles) {
        for(int i = 0; i < obstacles.size() - 1; i++)
            if(obstacles.get(i).isColliding(obstacles.get(obstacles.size() - 1)))
                return true;
        return false;
    }

    public boolean cantPlaceEnemy(ArrayList<RandomPlayer> enemies, ArrayList<StaticGameObject> obstacles) {
        for(int i = 0; i < enemies.size() - 1; i++)
            if(enemies.get(i).isColliding(enemies.get(enemies.size() - 1)))
                return true;
        for (StaticGameObject obstacle : obstacles)
            if (obstacle.isColliding(enemies.get(enemies.size() - 1)))
                return true;
        return false;
    }

    /*public boolean cantPlacePlayer(GameMap map) {
        for(int i = 0; i < enemies.size() - 1; i++)
            if(enemies.get(i).isColliding(enemies.get(enemies.size() - 1)))
                return true;
        for (StaticGameObject obstacle : obstacles)
            if (obstacle.isColliding(enemies.get(enemies.size() - 1)))
                return true;
        return false;
    }*/

    public void clearGameObjects(Pane root, HumanPlayer player, GameMap map) {
        root.getChildren().remove(player.getView());
        root.getChildren().remove(player.getActiveGun().getView());
        for(Bullet bullet : player.getActiveGun().getBullets())
            root.getChildren().removeAll(bullet.getView());

        for(StaticGameObject obstacle : map.getObstacles())
            root.getChildren().removeAll(obstacle.getView());

        for(RandomPlayer enemy : map.getEnemies()) {
            root.getChildren().removeAll(enemy.getView());
            root.getChildren().removeAll(enemy.getActiveGun().getView());
            for(Bullet botBullet : enemy.getActiveGun().getBullets())
                root.getChildren().removeAll(botBullet.getView());
        }
    }
}
