package bulanci;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class GameManager {

    private GameMap map;
    private Pane root;
    private GraphicsContext gc;
    private HumanPlayer player;

    private long lastNanoTime;
    private long startNanoTime;
    private int roundTime = 60;

    private Label labelScore;
    private Label labelDeaths;
    private Label labelTime;
    private Label labelAmmo;
    private Button btnRestart;
    private TextField tfName;
    private Font font = new Font("Consolas", 20);

    private DecimalFormat numberFormat = new DecimalFormat("#0.0");

    public GameManager(GameMap map, Pane root, GraphicsContext gc) {
        this.map = map;
        this.root = root;
        this.gc = gc;
    }

    public HumanPlayer getPlayer() {
        return player;
    }

    public void initUI(Scene scene, Canvas canvas) {
        labelScore = new Label("Score: 0");
        labelScore.setTextFill(Color.LIGHTGREEN);
        labelScore.setFont(font);
        labelScore.setTranslateX(10);
        labelScore.setTranslateY(scene.getHeight() - 26);
        root.getChildren().add(labelScore);

        labelDeaths = new Label("Deaths: 0");
        labelDeaths.setTextFill(Color.ORANGERED);
        labelDeaths.setFont(font);
        labelDeaths.setTranslateX(120);
        labelDeaths.setTranslateY(scene.getHeight() - 26);
        root.getChildren().add(labelDeaths);

        labelTime = new Label("Time: " + roundTime);
        labelTime.setTextFill(Color.WHITE);
        labelTime.setFont(font);
        labelTime.setTranslateX((scene.getWidth() / 2) - 50);
        labelTime.setTranslateY(scene.getHeight() - 26);
        root.getChildren().add(labelTime);

        btnRestart = new Button("Restart");
        btnRestart.setMaxWidth(100);
        btnRestart.setTranslateX((scene.getWidth() / 2) - 25);
        btnRestart.setTranslateY((scene.getHeight() / 2) + 50);
        btnRestart.setOnAction(event -> {
            try {
                starGame(canvas);
            } catch (IOException ignored) {}
        });
        root.getChildren().add(btnRestart);

        tfName = new TextField();
        tfName.setMaxWidth(100);
        tfName.setTranslateX((scene.getWidth() / 2) - 50);
        tfName.setTranslateY((scene.getHeight() / 2) + 18);
        root.getChildren().add(tfName);

        labelAmmo = new Label("Ammo: ");
        labelAmmo.setTextFill(Color.WHITE);
        labelAmmo.setFont(font);
        labelAmmo.setTranslateX(scene.getWidth() - 220);
        labelAmmo.setTranslateY(scene.getHeight() - 26);
        root.getChildren().add(labelAmmo);
    }

    public void starGame(Canvas canvas) throws IOException {

        FileWriter writer = new FileWriter("scores.txt", true);
        try {
            if(tfName.getText().length() == 0)
                tfName.setText("_");
            writer.write(tfName.getText() + ";" + player.getScore() + ";" + player.getDeaths() + System.lineSeparator());
        } catch (java.lang.NullPointerException ignore) {}
        writer.close();
        tfName.setText("");

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 600, canvas.getWidth(), 25);

        map.setGrass(createStaticGameObjects(30, new String[]{"images/grass1.png", "images/grass2.png", "images/grass3.png"}));
        map.setObstacles(createStaticGameObjects(10, new String[]{"images/bush1.png", "images/bush2.png", "images/bush3.png", "images/rock.png"}));
        map.setEnemies(createRandomPlayers(map.getEnemiesCount()));
        map.setGuns(new ArrayList<>());
        GameMap.setEnemiesCounter(map.getEnemies().size());
        createPlayer();

        gc.setFill(Color.GREENYELLOW);
        gc.fillRect(0, 0, map.getWidth(), map.getHeight());

        labelScore.setText("Score: 0");
        labelDeaths.setText("Deaths: 0");
        labelTime.setText("Time: " + roundTime);
        labelAmmo.setText("Ammo: " + player.getActiveGun().getAmmo());

        lastNanoTime = System.nanoTime();
        startNanoTime = System.nanoTime();
        btnRestart.setVisible(false);
        tfName.setVisible(false);
    }

    public void update(long currentNanoTime, ArrayList<String> input) {
        double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
        lastNanoTime = currentNanoTime;

        double t = (currentNanoTime - startNanoTime) / 1000000000.0;

        if(t >= roundTime) {
            if(!btnRestart.isVisible()) {
                try {
                    gameOver();
                } catch (IOException ignored) { }
            }
        } else {
            map.spawnEnemy(this, t, root);
            movement(t, input);
            collisionDetection();
            updatePlayers(elapsedTime);
            map.spawnGuns(this, t, root);
            labelTime.setText("Time: " + numberFormat.format(roundTime - t));
            if(player.getActiveGun().getAmmo() <= 0) {
                if(player.getActiveGun().reload(t)) {
                    labelAmmo.setTextFill(Color.WHITE);
                    labelAmmo.setText("Ammo: " + player.getActiveGun().getAmmo());
                } else {
                    labelAmmo.setTextFill(Color.ORANGERED);
                    labelAmmo.setText("Ammo: Reloading...");
                }
            } else {
                labelAmmo.setTextFill(Color.WHITE);
                labelAmmo.setText("Ammo: " + player.getActiveGun().getAmmo());
            }
        }
    }

    private void movement(double t, ArrayList<String> input) {
        player.setVelocity(0, 0);
        if(!input.isEmpty()) {
            if (input.get(input.size() - 1).equals("UP"))
                player.moveUp();
            if (input.get(input.size() - 1).equals("DOWN"))
                player.moveDown();
            if (input.get(input.size() - 1).equals("LEFT"))
                player.moveLeft();
            if (input.get(input.size() - 1).equals("RIGHT"))
                player.moveRight();
        }

        for(RandomPlayer bot : map.getEnemies()) {
            bot.setVelocity(0, 0);
            bot.makeMove(t);
            bot.shoot(t, root);
        }
    }

    private void updatePlayers(double time) {
        player.update(time, root);
        for(RandomPlayer bot : map.getEnemies()) {
            bot.update(time, root);
        }
    }

    private void collisionDetection() {
        playerBulletsCollision();
        botsBulletsCollision();
        movementCollision();
        gunPickFromGround();
    }

    private void playerBulletsCollision() {
        Iterator<Bullet> bulletsIterator = player.getActiveGun().getBullets().iterator();
        while (bulletsIterator.hasNext()) {
            GameObject bullet = bulletsIterator.next();
            Iterator<RandomPlayer> enemiesIterator = map.getEnemies().iterator();
            while(enemiesIterator.hasNext()) {
                RandomPlayer enemy = enemiesIterator.next();
                if (enemy.isColliding(bullet)) {
                    //System.out.println(bullet.toString());
                    root.getChildren().removeAll(bullet.getView());
                    root.getChildren().removeAll(enemy.getView());
                    root.getChildren().removeAll(enemy.getActiveGun().getView());
                    for(Bullet botBullet : enemy.getActiveGun().getBullets())
                        root.getChildren().removeAll(botBullet.getView());
                    try {
                        bulletsIterator.remove();
                    } catch (Exception ignored) {}
                    enemiesIterator.remove();
                    player.addScore();
                    labelScore.setText("Score: " + player.getScore());
                }
            }
            for (StaticGameObject obstacle : map.getObstacles()) {
                if (bullet.isColliding(obstacle)) {
                    try {
                        bulletsIterator.remove();
                    } catch (Exception ignored) {}
                    root.getChildren().removeAll(bullet.getView());
                }
            }
        }
    }

    private void botsBulletsCollision() {
        for (RandomPlayer enemy : map.getEnemies()) {
            Iterator<Bullet> bulletsIterator = enemy.getActiveGun().getBullets().iterator();
            while (bulletsIterator.hasNext()) {
                GameObject bullet = bulletsIterator.next();
                if (player.isColliding(bullet)) {
                    root.getChildren().removeAll(bullet.getView());
                    //root.getChildren().removeAll(player1.getView());
                    try {
                        bulletsIterator.remove();
                    } catch (Exception ignored) {}
                    player.addDeaths();
                    labelDeaths.setText("Deaths: " + player.getDeaths());
                    respawnPlayer();
                    //root.getChildren().add(player1.getView());
                }
                for (StaticGameObject obstacle : map.getObstacles()) {
                    if (bullet.isColliding(obstacle)) {
                        root.getChildren().removeAll(bullet.getView());
                        try {
                            bulletsIterator.remove();
                        } catch (Exception ignored) {}
                    }
                }
            }
        }
    }

    private void movementCollision() {
        player.enableMoveDirections();
        player.detectCollisionStatic(map.getObstacles());
        player.detectCollisionDynamic(map.getEnemies());
        for(RandomPlayer enemy : map.getEnemies()) {
            enemy.enableMoveDirections();
            enemy.detectCollisionStatic(map.getObstacles());
            enemy.detectCollisionDynamic(map.getEnemies());
            enemy.detectCollisionDynamic(player);
        }
    }

    private void gunPickFromGround() {
        Iterator<Gun> gunsIterator = map.getGuns().iterator();
        while (gunsIterator.hasNext()) {
            Gun gun = gunsIterator.next();
            if(player.isColliding(gun)) {
                if(player.addGun(gun))
                    gun.getView().setVisible(false);
                else
                    root.getChildren().removeAll(gun.getView());
                gunsIterator.remove();
            }
        }
    }

    public void respawnPlayer() {
        Random random = new Random();
        do {
            player.setPosition(random.nextInt(map.getWidth() - 64), random.nextInt(map.getHeight() - 64));
        } while(canNotPlacePlayer(map.getEnemies(), map.getObstacles()));
        for(Bullet bullet : player.getActiveGun().getBullets())
            root.getChildren().removeAll(bullet.getView());
        root.getChildren().removeAll(player.getActiveGun().getView());
        player.setActiveGun(0);
        player.setGuns(new ArrayList<>());
        player.addGun(new Pistol("Pistol", "images/gun.png", player, 7));
        root.getChildren().add(player.getActiveGun().getView());
        player.rotateDown();
    }

    public void shoot() {
        getPlayer().getActiveGun().shoot(root);
        labelAmmo.setText("Ammo: " + player.getActiveGun().getAmmo());
    }

    private void gameOver() throws IOException {
        gc.setFill(Color.BLACK);
        gc.fillRect(map.getWidth() / 3.0, map.getHeight() / 3.0, map.getWidth() / 3.0, map.getHeight() / 3.0);
        gc.setFill(Color.WHITE);
        gc.setFont(font);
        gc.fillText("Game Over\nScore: " + player.getScore() + "\nDeaths: " + player.getDeaths(),
                map.getWidth() / 2.0 - 50, map.getHeight() / 2.0 - 45);
        gc.setFont(new Font("Consolas", 11));
        gc.fillText("Enter your name:", map.getWidth() / 2.0 - 50, map.getHeight() / 2.0 + 22);
        clearGameObjects();
        btnRestart.setVisible(true);
        tfName.setVisible(true);
        showLeaderBoard();
    }

    private void showLeaderBoard() throws IOException {
        Loader loader = new Loader();
        loader.load("scores.txt");

        ArrayList<HumanPlayer> players = loader.getPlayers();
        Collections.sort(players);
        StringBuilder sb = new StringBuilder();
        sb.append("Top 10 scores\n# Name\tScore\tDeaths\n");
        for (int i = 0; i < 10; i++) {
            if (i < players.size())
                sb.append(i + 1).append(' ').append(players.get(i).getName()).append('\t').append(players.get(i).getScore()).append('\t').append(players.get(i).getDeaths()).append('\n');
            else break;
        }

        System.out.println(loader.getAllNames());

        gc.setFill(Color.BLACK);
        gc.setFont(font);
        gc.fillText(sb.toString(), 10, 20);
    }

    private void clearGameObjects() {
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

        for(StaticGameObject grass: map.getGrass())
            root.getChildren().removeAll(grass.getView());

        for(Gun gun : map.getGuns())
            root.getChildren().removeAll(gun.getView());
    }

    private void createPlayer() {
        Random random = new Random();
        player = new HumanPlayer("hrac1", 0);
        player.setImage("images/player.png");
        player.addGun(new Pistol("Pistol", "images/gun.png", player, 7));

        do {
            player.setPosition(random.nextInt(map.getWidth() - 64), random.nextInt(map.getHeight() - 64));
        } while(canNotPlacePlayer(map.getEnemies(), map.getObstacles()));
        player.rotateDown();

        System.out.println(player.toString());

        root.getChildren().add(player.getView());
        root.getChildren().add(player.getActiveGun().getView());
    }

    private ArrayList<RandomPlayer> createRandomPlayers(int count) {
        Random random = new Random();
        ArrayList<RandomPlayer> enemies = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            enemies.add(createRandomPlayer(i));
            do {
                enemies.get(i).setPosition(
                        random.nextInt(map.getWidth() - (int)enemies.get(i).getWidth()),
                        random.nextInt(map.getHeight() - (int)enemies.get(i).getHeight()));
            } while(canNotPlaceEnemy(enemies, map.getObstacles()));
            root.getChildren().add(enemies.get(i).getView());
            root.getChildren().add(enemies.get(i).getActiveGun().getView());
            System.out.println(enemies.size());
        }
        for(RandomPlayer bot : enemies) {
            System.out.println(bot.toString());
        }
        return enemies;
    }

    public RandomPlayer createRandomPlayer(int index) {
        RandomPlayer bot = new RandomPlayer("bot_" + index, 0);
        bot.setImage("images/player_bot.png");
        bot.addGun(new Pistol("Pistol", "images/gun.png", bot, 100));

        return bot;
    }

    private ArrayList<StaticGameObject> createStaticGameObjects(int count, String[] files) {

        ArrayList<StaticGameObject> obstacles = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i < count; i++) {
            obstacles.add(new StaticGameObject("staticObject", files[random.nextInt(files.length)]));
            do {
                obstacles.get(i).setPosition(random.nextInt(map.getWidth() - 64), random.nextInt(map.getHeight() - 64));
            } while(canNotPlaceObstacle(obstacles));

            root.getChildren().add(obstacles.get(i).getView());
        }

        return obstacles;
    }

    private boolean canNotPlaceObstacle(ArrayList<StaticGameObject> obstacles) {
        for(int i = 0; i < obstacles.size() - 1; i++)
            if(obstacles.get(i).isColliding(obstacles.get(obstacles.size() - 1)))
                return true;
        return false;
    }

    public boolean canNotPlaceEnemy(ArrayList<RandomPlayer> enemies, ArrayList<StaticGameObject> obstacles) {
        for(int i = 0; i < enemies.size() - 1; i++)
            if(enemies.get(i).isColliding(enemies.get(enemies.size() - 1)))
                return true;
        for (StaticGameObject obstacle : obstacles)
            if (obstacle.isColliding(enemies.get(enemies.size() - 1)))
                return true;
        return false;
    }

    private boolean canNotPlacePlayer(ArrayList<RandomPlayer> enemies, ArrayList<StaticGameObject> obstacles) {
        for(RandomPlayer enemy : enemies)
            if(enemy.isColliding(player))
                return true;
        for (StaticGameObject obstacle : obstacles)
            if (obstacle.isColliding(player))
                return true;
        return false;
    }

    public boolean canNotPlaceGun(ArrayList<RandomPlayer> enemies, ArrayList<StaticGameObject> obstacles, ArrayList<Gun> guns) {
        for(RandomPlayer enemy : enemies)
            if(enemy.isColliding(guns.get(guns.size() - 1)))
                return true;
        for (StaticGameObject obstacle : obstacles)
            if (obstacle.isColliding(guns.get(guns.size() - 1)))
                return true;
        for(int i = 0; i < guns.size() - 1; i++)
            if(guns.get(i).isColliding(guns.get(guns.size() - 1)))
                return true;
        return false;
    }
}
