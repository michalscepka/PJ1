package bulanci;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;

public class Initializer {

    public Initializer() {}

    public HumanPlayer initPlayer(GameMap map, Pane root) {

        HumanPlayer player = new HumanPlayer("hrac1", map, 0);
        player.setImage("images/player.png");
        player.setPosition((double)map.getWidth() / 2.0, (double)map.getWidth() / 2.0);
        player.addGun(new Gun(
                "Pistol", "images/gun-up.png", player,
                player.getPosition().getX() + 10, player.getPosition().getY() - 20,
                player.getVelocity().getX(), player.getVelocity().getY()));
        System.out.println(player.toString());

        player.getView().setRotate(180);

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
        bot.setImage("images/bot_up.png");
        double x = random.nextInt(map.getWidth() - (int)bot.getWidth());
        double y = random.nextInt(map.getHeight() - (int)bot.getHeight());
        bot.setPosition(x, y);
        bot.addGun(new Gun(
                "Pistol", "images/gun-up.png", bot,
                bot.getPosition().getX() + 10, bot.getPosition().getY() - 20,
                bot.getVelocity().getX(), bot.getVelocity().getY()));

        return bot;
    }

    public ArrayList<StaticGameObject> initStaticGameObjects(GameMap map, Pane root) {
        ArrayList<StaticGameObject> obstacles = new ArrayList<>();

        Random random = new Random();
        for(int i = 0; i < 10; i++) {
            obstacles.add(new Bush("images/bush.png"));
            obstacles.get(i).setPosition(random.nextInt(map.getWidth() - 64), random.nextInt(map.getWidth() - 64));
            root.getChildren().add(obstacles.get(i).getView());
            System.out.println(obstacles.get(i).toString());
        }

        return obstacles;
    }
}
