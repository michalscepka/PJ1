package bulanci;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public class Initializer {

    private ArrayList<Image> playerImages;
    private ArrayList<Image> botImages;
    private ArrayList<Image> pistolImages;

    public Initializer() {
        playerImages = new ArrayList<>();
        playerImages.add(new Image("images/player_up.png"));
        playerImages.add(new Image("images/player_down.png"));
        playerImages.add(new Image("images/player_left.png"));
        playerImages.add(new Image("images/player_right.png"));

        botImages = new ArrayList<>();
        botImages.add(new Image("images/bot_up.png"));
        botImages.add(new Image("images/bot_down.png"));
        botImages.add(new Image("images/bot_left.png"));
        botImages.add(new Image("images/bot_right.png"));

        pistolImages = new ArrayList<>();
        pistolImages.add(new Image("images/gun-up.png"));
        pistolImages.add(new Image("images/gun-down.png"));
        pistolImages.add(new Image("images/gun-left.png"));
        pistolImages.add(new Image("images/gun-right.png"));
    }

    public HumanPlayer initPlayer(GameMap map) {

        HumanPlayer player = new HumanPlayer("hrac1", map, 3);
        player.setImages(playerImages);
        player.setImage(player.getImage(3));
        player.setPosition((double)map.getWidth() / 2.0, (double)map.getWidth() / 2.0);
        player.addGun(new Gun(
                "images/gun-right.png", "Pistol", player,
                player.getPosition().getX() + player.getWidth(), player.getPosition().getY() + 10,
                player.getVelocity().getX(), player.getVelocity().getY()));
        player.getActiveGun().setImages(pistolImages);
        System.out.println(player.toString());

        return player;
    }

    public ArrayList<RandomPlayer> initRandomPlayers(GameMap map, int count) {

        ArrayList<RandomPlayer> enemies = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            enemies.add(createRandomPlayer(map, i));
        }
        for(RandomPlayer bot : enemies) {
            System.out.println(bot.toString());
        }
        return enemies;
    }

    public RandomPlayer createRandomPlayer(GameMap map, int index) {

        Random random = new Random();
        RandomPlayer bot = new RandomPlayer("bot_" + index, map, 3);
        bot.setImages(botImages);
        bot.setImage(bot.getImage(3));
        double x = random.nextInt(map.getWidth() - (int)bot.getWidth());
        double y = random.nextInt(map.getHeight() - (int)bot.getHeight());
        bot.setPosition(x, y);
        bot.addGun(new Gun(
                "images/gun-right.png", "Pistol", bot,
                bot.getPosition().getX() + bot.getWidth(), bot.getPosition().getY() + 10,
                bot.getVelocity().getX(), bot.getVelocity().getY()));
        bot.getActiveGun().setImages(pistolImages);

        return bot;
    }

    public ArrayList<StaticGameObject> initStaticGameObjects(GameMap map) {
        ArrayList<StaticGameObject> obstacles = new ArrayList<>();

        Random random = new Random();
        for(int i = 0; i < 10; i++) {
            obstacles.add(new Bush("images/bush.png", random.nextInt(map.getWidth() - 64), random.nextInt(map.getHeight() - 64)));
        }

        return obstacles;
    }
}
