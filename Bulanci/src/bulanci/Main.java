package bulanci;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    private Pane root;
    private Player player1;
    private GraphicsContext gc;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private int mapWidth = 800;
    private int mapHeight = 600;

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();

        primaryStage.setTitle("Bulanci");
        Scene scene = new Scene(root, mapWidth, mapHeight);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(mapWidth, mapHeight);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();

        ArrayList<String> input = new ArrayList<>();

        scene.setOnKeyPressed(e -> {
            String code = e.getCode().toString();
            if (e.getCode() == KeyCode.SPACE) {
                System.out.println("shoot");
                bullets.add(new Bullet("bullet.png", player1.getPositionX(), player1.getPositionY(), player1.getVelocityX() * 2, player1.getVelocityY() * 2));
            } else if (!input.contains(code)) {
                input.add( code );
            }
        });

        scene.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            input.remove( code );
        });

        player1 = new Player();
        player1.setImage("player-down.png");
        player1.setPosition((double)mapWidth / 2.0, (double)mapHeight / 2.0);

        final long[] lastNanoTime = {System.nanoTime()};

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {

                double elapsedTime = (currentNanoTime - lastNanoTime[0]) / 1000000000.0;
                lastNanoTime[0] = currentNanoTime;

                //TODO naplnit obrazky do pole
                player1.setVelocity(0, 0);
                if (input.contains("UP")) {
                    player1.addVelocity(0, -100);
                    player1.setImage("player-up.png");
                }
                if (input.contains("DOWN")) {
                    player1.addVelocity(0, 100);
                    player1.setImage("player-down.png");
                }
                if (input.contains("LEFT")) {
                    player1.addVelocity(-100, 0);
                    player1.setImage("player-left.png");
                }
                if (input.contains("RIGHT")) {
                    player1.addVelocity(100, 0);
                    player1.setImage("player-right.png");
                }

                player1.update(elapsedTime);
                for (Bullet bullet : bullets) {
                    bullet.update(elapsedTime);
                }

                /*Iterator<GameObject> moneybagIter = moneybagList.iterator();
                while (moneybagIter.hasNext()) {
                    GameObject moneybag = moneybagIter.next();
                    if (player1.intersects(moneybag)) {
                        moneybagIter.remove();
                        //score.value++;
                    }
                }*/

                // render
                gc.clearRect(0, 0, mapWidth, mapHeight);
                player1.render(gc);
                for (Bullet bullet : bullets) {
                    bullet.render(gc);
                }
            }
        }.start();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
