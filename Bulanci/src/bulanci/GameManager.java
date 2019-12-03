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

        ArrayList<RandomPlayer> enemies = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            enemies.add(createRandomPlayer(map, i));
            root.getChildren().add(enemies.get(i).getView());
            root.getChildren().add(enemies.get(i).getActiveGun().getView());
        }
        for(RandomPlayer bot : enemies) {
            System.out.println(bot.toString());
        }
        return enemies;
    }

    public RandomPlayer createRandomPlayer(GameMap map, int index) {

        Random random = new Random();
        RandomPlayer bot = new RandomPlayer("bot_" + index, map, 0);
        bot.setImage("images/bot_down.png");
        bot.addGun(new Gun("Pistol", "images/gun-up.png", bot, 100));
        double x = random.nextInt(map.getWidth() - (int)bot.getWidth());
        double y = random.nextInt(map.getHeight() - (int)bot.getHeight());
        bot.setPosition(x, y);

        return bot;
    }

    public ArrayList<StaticGameObject> initStaticGameObjects(GameMap map, Pane root) {
        ArrayList<StaticGameObject> obstacles = new ArrayList<>();

        Random random = new Random();
        for(int i = 0; i < 10; i++) {
            obstacles.add(new Bush("images/bush.png"));
            do {
                obstacles.get(i).setPosition(random.nextInt(map.getWidth() - 64), random.nextInt(map.getHeight() - 64));
            } while(!canBePlaced(obstacles));

            root.getChildren().add(obstacles.get(i).getView());
            System.out.println(obstacles.get(i).toString());
        }

        return obstacles;
    }

    private boolean canBePlaced(ArrayList<StaticGameObject> obstacles) {
        for(int i = 0; i < obstacles.size() - 1; i++) {
            if(obstacles.get(i).isColliding(obstacles.get(obstacles.size() - 1)))
                return false;
        }
        return true;
    }

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
