package bulanci;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Main extends Application {

    private Pane root;
    private Canvas canvas;
    private GraphicsContext gc;
    private GameMap map;
    private GameManager gameManager;
    private ArrayList<String> input;
    private long lastNanoTime;
    private long startNanoTime;
    private HumanPlayer player1;
    private Label labelScore;
    private Label labelDeaths;
    private Label labelTime;
    private Label labelAmmo;
    private Button btnRestart;
    private int roundTime = 60;
    private DecimalFormat numberFormat = new DecimalFormat("#0.0");
    Font font = new Font("Consolas", 20);

    @Override
    public void start(Stage primaryStage) {

        root = new Pane();
        primaryStage.setTitle("Bulanci");
        Scene scene = new Scene(root, 800, 625);
        primaryStage.setScene(scene);

        canvas = new Canvas(scene.getWidth(), scene.getHeight());
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

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
        btnRestart.setTranslateY((scene.getHeight() / 2) + 20);
        btnRestart.setOnAction(event -> starGame());
        root.getChildren().add(btnRestart);

        starGame();

        labelAmmo = new Label("Ammo: " + player1.getActiveGun().getAmmo());
        labelAmmo.setTextFill(Color.WHITE);
        labelAmmo.setFont(font);
        labelAmmo.setTranslateX(scene.getWidth() - 220);
        labelAmmo.setTranslateY(scene.getHeight() - 26);
        root.getChildren().add(labelAmmo);

        input = new ArrayList<>();

        scene.setOnKeyPressed(e -> {
            String code = e.getCode().toString();
            if (e.getCode() == KeyCode.CONTROL) {
                player1.getActiveGun().shoot(root);
                labelAmmo.setText("Ammo: " + player1.getActiveGun().getAmmo());
            } else if (!input.contains(code) && (code.equals("UP") || code.equals("DOWN") || code.equals("LEFT") || code.equals("RIGHT"))) {
                input.add(code);
            }
        });

        scene.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            input.remove(code);
        });

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {

                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;

                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                if(t >= roundTime) {
                    gameOver(gc);
                } else {
                    map.spawnEnemy(t, root);
                    movement(t);
                    collisionDetection();
                    update(elapsedTime);
                    labelTime.setText("Time: " + numberFormat.format(roundTime - t));
                    if(player1.getActiveGun().getAmmo() <= 0) {
                        if(player1.getActiveGun().reload(t)) {
                            labelAmmo.setText("Ammo: " + player1.getActiveGun().getAmmo());
                        } else {
                            labelAmmo.setText("Ammo: Reloading...");
                        }
                    }
                }
            }
        }.start();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void starGame() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 600, canvas.getWidth(), 25);

        map = new GameMap((int)canvas.getWidth(), (int)canvas.getHeight() - 25, 3);
        gameManager = new GameManager();
        player1 = gameManager.initPlayer(map, root);
        map.setEnemies(gameManager.initRandomPlayers(map, map.getEnemiesCount(), root));
        GameMap.setEnemiesCounter(map.getEnemies().size());
        map.setObstacles(gameManager.initStaticGameObjects(map, root));

        gc.setFill(Color.GREENYELLOW);
        gc.fillRect(0, 0, map.getWidth(), map.getHeight());

        labelScore.setText("Score: 0");
        labelDeaths.setText("Deaths: 0");
        labelTime.setText("Time: " + roundTime);

        lastNanoTime = System.nanoTime();
        startNanoTime = System.nanoTime();
        btnRestart.setVisible(false);
    }

    private void movement(double t) {
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

        for(RandomPlayer bot : map.getEnemies()) {
            bot.setVelocity(0, 0);
            bot.makeMove(t, root);
        }
    }

    private void collisionDetection() {
        Iterator<Bullet> bulletsIterator = player1.getActiveGun().getBullets().iterator();
        Iterator<RandomPlayer> enemiesIterator = map.getEnemies().iterator();
        while (bulletsIterator.hasNext()) {
            GameObject bullet = bulletsIterator.next();
            while(enemiesIterator.hasNext()) {
                RandomPlayer enemy = enemiesIterator.next();
                if (enemy.isColliding(bullet)) {
                    root.getChildren().removeAll(bullet.getView());
                    root.getChildren().removeAll(enemy.getView());
                    root.getChildren().removeAll(enemy.getActiveGun().getView());
                    for(Bullet botBullet : enemy.getActiveGun().getBullets())
                        root.getChildren().removeAll(botBullet.getView());
                    bulletsIterator.remove();
                    enemiesIterator.remove();
                    player1.addScore();
                    labelScore.setText("Score: " + player1.getScore());
                }
            }
            for (StaticGameObject obstacle : map.getObstacles()) {
                if (bullet.isColliding(obstacle)) {
                    bulletsIterator.remove();
                    root.getChildren().removeAll(bullet.getView());
                }
            }
        }

        for (RandomPlayer enemy : map.getEnemies()) {
            bulletsIterator = enemy.getActiveGun().getBullets().iterator();
            while (bulletsIterator.hasNext()) {
                GameObject bullet = bulletsIterator.next();
                if (player1.isColliding(bullet)) {
                    root.getChildren().removeAll(bullet.getView());
                    root.getChildren().removeAll(player1.getView());
                    bulletsIterator.remove();
                    player1.addDeaths();
                    labelDeaths.setText("Deaths: " + player1.getDeaths());
                    Random random = new Random();
                    player1.setPosition(random.nextInt((int)(map.getWidth() - player1.getWidth())), random.nextInt((int)(map.getHeight() - player1.getHeight())));
                    root.getChildren().add(player1.getView());
                }
                for (StaticGameObject obstacle : map.getObstacles()) {
                    if (bullet.isColliding(obstacle)) {
                        root.getChildren().removeAll(bullet.getView());
                        bulletsIterator.remove();
                    }
                }
            }
        }

        player1.enableMoveDirections();
        player1.detectCollisionStatic(map.getObstacles());
        player1.detectCollisionDynamic(map.getEnemies());
        for(RandomPlayer enemy : map.getEnemies()) {
            enemy.enableMoveDirections();
            enemy.detectCollisionStatic(map.getObstacles());
            enemy.detectCollisionDynamic(map.getEnemies());
            enemy.detectCollisionDynamic(player1);
        }
    }

    private void update(double time) {
        player1.update(time, root);
        for(RandomPlayer bot : map.getEnemies()) {
            bot.update(time, root);
        }
    }

    private void gameOver(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(map.getWidth() / 3.0, map.getHeight() / 3.0, map.getWidth() / 3.0, map.getHeight() / 3.0);
        gc.setFill(Color.WHITE);
        gc.setFont(font);
        gc.fillText("Game Over\nScore: " + player1.getScore(), map.getWidth() / 2.0 - 50, map.getHeight() / 2.0 - 10);
        gameManager.clearGameObjects(root, player1, map);
        btnRestart.setVisible(true);
    }
}
