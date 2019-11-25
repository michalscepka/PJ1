package bulanci;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Main extends Application {

    private HumanPlayer player1;
    //private HumanPlayer player2;
    private long lastNanoTime;
    private GameMap map;
    private ArrayList<String> input;
    private ArrayList<RandomPlayer> enemies;

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();
        primaryStage.setTitle("Bulanci");
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        map = new GameMap((int) canvas.getWidth(), (int) canvas.getHeight());

        input = new ArrayList<>();

        scene.setOnKeyPressed(e -> {
            String code = e.getCode().toString();
            if (e.getCode() == KeyCode.SPACE) {
                player1.getActiveGun().shoot();
            } else if (e.getCode() == KeyCode.CONTROL) {
                //player2.getActiveGun().shoot();
            } else if (!input.contains(code)) {
                input.add(code);
            }
        });

        scene.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            input.remove(code);
        });

        createPlayers();

        lastNanoTime = System.nanoTime();
        final long startNanoTime = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {

                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;

                double randomPlayerTimer = (currentNanoTime - startNanoTime) / 1000000000.0;

                movement(randomPlayerTimer);
                collisionDetection();
                //update(0.015);
                update(elapsedTime);
                render(gc);
            }
        }.start();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void createPlayers() {
        ArrayList<Image> playerImages = new ArrayList<>();
        playerImages.add(new Image("sprites/player_up.png"));
        playerImages.add(new Image("sprites/player_down.png"));
        playerImages.add(new Image("sprites/player_left.png"));
        playerImages.add(new Image("sprites/player_right.png"));

        ArrayList<Image> botImages = new ArrayList<>();
        botImages.add(new Image("sprites/bot_up.png"));
        botImages.add(new Image("sprites/bot_down.png"));
        botImages.add(new Image("sprites/bot_left.png"));
        botImages.add(new Image("sprites/bot_right.png"));

        player1 = new HumanPlayer("hrac1", map, 3);
        player1.setImages(playerImages);
        player1.setImage(player1.getImage(3));
        player1.setPosition((double)map.getWidth() / 2.0, (double)map.getWidth() / 2.0);
        player1.addGun(new Gun(
                "sprites/gun-right.png", "Pistol", player1, player1.getPosition().getX() + player1.getWidth(), player1.getPosition().getY() + 10,
                player1.getVelocity().getX(), player1.getVelocity().getY()));
        System.out.println(player1.toString());

        /*player2 = new HumanPlayer("hrac2", map);
        player2.setImages(playerImages);
        player2.setImage(player2.getImage(3));
        player2.setPosition((double)map.getWidth() / 3.0, (double)map.getHeight() / 2.0);
        player2.addGun(new Gun(
                "sprites/gun-right.png", "Pistol", player2, player2.getPosition().getX() + player2.getWidth(), player2.getPosition().getY() + 10,
                player2.getVelocity().getX(), player2.getVelocity().getY()));
        System.out.println(player2.toString());*/

        enemies = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            RandomPlayer bot = new RandomPlayer("bot_" + i, map, 3);
            bot.setImages(botImages);
            bot.setImage(bot.getImage(3));
            double x = random.nextInt(map.getWidth());
            double y = random.nextInt(map.getHeight());
            bot.setPosition(x, y);
            bot.addGun(new Gun(
                    "sprites/gun-right.png", "Pistol", bot, bot.getPosition().getX() + bot.getWidth(), bot.getPosition().getY() + 10,
                    bot.getVelocity().getX(), bot.getVelocity().getY()));
            enemies.add(bot);
        }

        for(RandomPlayer bot : enemies) {
            System.out.println(bot.toString());
        }
    }

    private void createGuns() {
        ArrayList<Image> pistols = new ArrayList<>();
        pistols.add(new Image("sprites/gun_up.png"));
        pistols.add(new Image("sprites/gun_down.png"));
        pistols.add(new Image("sprites/gun_left.png"));
        pistols.add(new Image("sprites/gun_right.png"));
    }

    private void movement(double randomPlayerTimer) {
        player1.setVelocity(0, 0);
        if(!input.isEmpty()) {
            if (input.get(input.size() - 1).equals("UP"))
                player1.moveUp();
            if (input.get(input.size() - 1).equals("DOWN"))
                player1.moveDown();
            if (input.get(input.size() - 1).equals("LEFT"))
                player1.moveLeft();
            if (input.get(input.size() - 1).equals("RIGHT"))
                player1.moveRight();
        }

        /*player2.setVelocity(0, 0);
        if(!input.isEmpty()) {
            if (input.get(input.size() - 1).equals("W"))
                player2.moveUp();
            if (input.get(input.size() - 1).equals("S"))
                player2.moveDown();
            if (input.get(input.size() - 1).equals("A"))
                player2.moveLeft();
            if (input.get(input.size() - 1).equals("D"))
                player2.moveRight();
        }*/

        for(RandomPlayer bot : enemies) {
            bot.setVelocity(0, 0);
            bot.makeMove(randomPlayerTimer);
        }
    }

    private void collisionDetection() {
        /*Iterator<Bullet> bulletsIterator = player1.getActiveGun().getBullets().iterator();
        while (bulletsIterator.hasNext()) {
            GameObject bullet = bulletsIterator.next();
            if (player2.intersects(bullet)) {
                bulletsIterator.remove();
                player2.setImage("sprites/player_F.png");
                System.out.println("hit");
            }
        }*/

        /*Iterator<Bullet> bulletsIterator2 = player2.getActiveGun().getBullets().iterator();
        while (bulletsIterator2.hasNext()) {
            GameObject bullet = bulletsIterator2.next();
            if (player1.intersects(bullet)) {
                bulletsIterator2.remove();
                player1.setImage("sprites/player_F.png");
                System.out.println("hit");
            }
        }*/

        Iterator<Bullet> bulletsIterator3 = player1.getActiveGun().getBullets().iterator();
        while (bulletsIterator3.hasNext()) {
            GameObject bullet = bulletsIterator3.next();
            for(int i = 0; i < enemies.size(); i++) {
                if (enemies.get(i).intersects(bullet)) {
                    bulletsIterator3.remove();
                    enemies.get(i).setImage("sprites/player_F.png");
                    enemies.remove(i);
                    System.out.println("hit");
                }
            }
        }
    }

    private void update(double time) {
        player1.update(time);
        //player2.update(time);
        for(RandomPlayer bot : enemies) {
            bot.update(time);
        }
    }

    private void render(GraphicsContext gc) {
        //gc.clearRect(0, 0, map.getWidth(), map.getHeight());
        gc.setFill(Color.GREENYELLOW);
        gc.fillRect(0, 0, map.getWidth(), map.getHeight());
        player1.render(gc);
        //player2.render(gc);
        for(RandomPlayer bot : enemies) {
            bot.render(gc);
        }
    }
}
