package bulanci;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;

public class Main extends Application {

    private HumanPlayer player1;
    private long lastNanoTime;
    private GameMap map;
    private ArrayList<String> input;

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();
        primaryStage.setTitle("Bulanci");
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        map = new GameMap((int) canvas.getWidth(), (int) canvas.getHeight(), 3);

        input = new ArrayList<>();

        scene.setOnKeyPressed(e -> {
            String code = e.getCode().toString();
            if (e.getCode() == KeyCode.SPACE) {
                player1.getActiveGun().shoot();
            } else if (!input.contains(code)) {
                input.add(code);
            }
        });

        scene.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            input.remove(code);
        });

        Initializer initializer = new Initializer();
        player1 = initializer.initPlayer(map);
        map.setEnemies(initializer.initRandomPlayers(map, map.getEnemiesCount()));
        GameMap.setEnemiesCounter(map.getEnemies().size());
        map.setObstacles(initializer.initStaticGameObjects(map));

        lastNanoTime = System.nanoTime();
        final long startNanoTime = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {

                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;

                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                map.spawnEnemy(t);
                movement(t);
                collisionDetection();
                update(elapsedTime);
                render(gc);
            }
        }.start();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
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
            bot.makeMove(t);
        }
    }

    private void collisionDetection() {

        Iterator<Bullet> bulletsIterator = player1.getActiveGun().getBullets().iterator();
        Iterator<RandomPlayer> enemiesIterator = map.getEnemies().iterator();
        while (bulletsIterator.hasNext()) {
            GameObject bullet = bulletsIterator.next();
            while(enemiesIterator.hasNext()) {
                GameObject enemy = enemiesIterator.next();
                if (enemy.intersects(bullet)) {
                    bulletsIterator.remove();
                    enemiesIterator.remove();
                }
            }
            for (StaticGameObject obstacle : map.getObstacles()) {
                if (bullet.intersects(obstacle)) {
                    bulletsIterator.remove();
                }
            }
        }

        for (RandomPlayer enemy : map.getEnemies()) {
            bulletsIterator = enemy.getActiveGun().getBullets().iterator();
            while (bulletsIterator.hasNext()) {
                GameObject bullet = bulletsIterator.next();
                if (player1.intersects(bullet)) {
                    bulletsIterator.remove();
                    player1.setImage("images/player_F.png");
                }
                for (StaticGameObject obstacle : map.getObstacles()) {
                    if (bullet.intersects(obstacle)) {
                        bulletsIterator.remove();
                    }
                }
            }
        }

        /*player1.enableMoveDirection();
        for(RandomPlayer enemy : enemies) {
            if (enemy.isNear(player1)) {
                System.out.println(enemy.toString() + '\n' + player1.toString());
                player1.disableMoveDirection(enemy);
                //enemy.disableMoveDirection();
                //System.out.println("collision");
            }
        }*/
    }

    private void update(double time) {
        player1.update(time);
        for(RandomPlayer bot : map.getEnemies()) {
            bot.update(time);
        }
    }

    private void render(GraphicsContext gc) {
        gc.setFill(Color.GREENYELLOW);
        gc.fillRect(0, 0, map.getWidth(), map.getHeight());
        player1.render(gc);
        for(RandomPlayer bot : map.getEnemies()) {
            bot.render(gc);
        }
        map.render(gc);
    }
}
