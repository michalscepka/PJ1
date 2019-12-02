package bulanci;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;

public class Main extends Application {

    private HumanPlayer player1;
    private long lastNanoTime;
    private GameMap map;
    private ArrayList<String> input;
    private Label labelScore;

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();
        primaryStage.setTitle("Bulanci");

        Scene scene = new Scene(root, 800, 625);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 600, 800, 25);

        labelScore = new Label("Score: 0");
        labelScore.setTextFill(Color.WHITE);
        labelScore.setFont(new Font("Consolas", 20));
        labelScore.setTranslateX(10);
        labelScore.setTranslateY(scene.getHeight() - 26);
        root.getChildren().add(labelScore);

        Button btnRestart = new Button("Restart");
        btnRestart.setTranslateX(100);
        btnRestart.setTranslateY(100);
        btnRestart.setMaxWidth(100);
        //btn_start.setOnAction(event -> initBoard(Integer.parseInt(tf_size.getText()), Integer.parseInt(tf_mines.getText())));
        btnRestart.setVisible(false);
        root.getChildren().add(btnRestart);

        map = new GameMap((int) canvas.getWidth(), 600, 3);//(int) canvas.getHeight(), 3);

        Rectangle rectangle = new Rectangle(100, 100, 64, 64);
        Image img = new Image("/images/player_F.png");
        rectangle.setTranslateX(100);
        rectangle.setTranslateY(100);
        rectangle.setFill(new ImagePattern(img));
        //rectangle.setFill(Color.BLACK);
        rectangle.setRotate(90);
        root.getChildren().add(rectangle);

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

                //System.out.println(t);

                if(t >= 20) {
                    gameOver(gc);
                    //System.out.println("KONEC");
                } else {
                    map.spawnEnemy(t);
                    movement(t);
                    collisionDetection();
                    update(elapsedTime);
                    render(gc);
                }
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
                    player1.addScore();
                    labelScore.setText("Score: " + player1.getScore());
                    System.out.println(player1.toString());
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
        /*gc.setFill(Color.BLACK);
        gc.setFont(new Font("Times", 20));
        gc.fillText("Score: " + player1.getScore(), 10, 620);*/
        player1.render(gc);
        for(RandomPlayer bot : map.getEnemies()) {
            bot.render(gc);
        }
        map.render(gc);
    }

    private void gameOver(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(map.getWidth() / 3.0, map.getHeight() / 3.0, map.getWidth() / 3.0, map.getHeight() / 3.0);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Consolas", 20));
        gc.fillText("Game Over\nScore: " + player1.getScore(), map.getWidth() / 2.0 - 50, map.getHeight() / 2.0 - 10);
    }
}
