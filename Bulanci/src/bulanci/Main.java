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
import java.util.Iterator;

public class Main extends Application {

    private Player player1;
    private Player player2;
    private int mapWidth = 800;
    private int mapHeight = 600;
    private long lastNanoTime;

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();
        primaryStage.setTitle("Bulanci");
        Scene scene = new Scene(root, mapWidth, mapHeight);
        primaryStage.setScene(scene);
        Canvas canvas = new Canvas(mapWidth, mapHeight);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        ArrayList<String> input = new ArrayList<>();

        scene.setOnKeyPressed(e -> {
            String code = e.getCode().toString();
            if (e.getCode() == KeyCode.SPACE) {
                player1.getActiveGun().shoot();
            } else if (e.getCode() == KeyCode.CONTROL) {
                player2.getActiveGun().shoot();
            } else if (!input.contains(code)) {
                input.add(code);
            }
        });

        scene.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            input.remove(code);
        });

        player1 = new Player("hrac1");
        player1.setImage("sprites/player_right.png");
        player1.setPosition((double)mapWidth / 2.0, (double)mapHeight / 2.0);
        player1.addGun(new Gun(
                "sprites/gun-right.png", "Pistol", player1, player1.getPosition().getX() + player1.getWidth(), player1.getPosition().getY() + 10,
                player1.getVelocity().getX(), player1.getVelocity().getY()));
        System.out.println(player1.toString());

        player2 = new Player("hrac2");
        player2.setImage("sprites/player_right.png");
        player2.setPosition((double)mapWidth / 3.0, (double)mapHeight / 2.0);
        player2.addGun(new Gun(
                "sprites/gun-right.png", "Pistol", player2, player2.getPosition().getX() + player2.getWidth(), player2.getPosition().getY() + 10,
                player2.getVelocity().getX(), player2.getVelocity().getY()));
        System.out.println(player2.toString());

        lastNanoTime = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {

                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;

                //movement
                player1.setVelocity(0, 0);
                player1.getActiveGun().setVelocity(0, 0);
                if(!input.isEmpty()) {
                    if (input.get(input.size() - 1).equals("UP"))
                        player1.moveUp();
                    if (input.get(input.size() - 1).equals("DOWN"))
                        player1.moveDown();
                    if (input.get(input.size() - 1).equals("RIGHT"))
                        player1.moveRight();
                    if (input.get(input.size() - 1).equals("LEFT"))
                        player1.moveLeft();
                }

                player2.setVelocity(0, 0);
                player2.getActiveGun().setVelocity(0, 0);
                if(!input.isEmpty()) {
                    if (input.get(input.size() - 1).equals("W"))
                        player2.moveUp();
                    if (input.get(input.size() - 1).equals("S"))
                        player2.moveDown();
                    if (input.get(input.size() - 1).equals("D"))
                        player2.moveRight();
                    if (input.get(input.size() - 1).equals("A"))
                        player2.moveLeft();
                }

                //TODO collision detection
                Iterator<Bullet> bulletsInter = player1.getActiveGun().getBullets().iterator();
                while (bulletsInter.hasNext()) {
                    Bullet bullet = bulletsInter.next();
                    System.out.println("ff");
                    if (player1.intersects(bullet)) {
                        bulletsInter.remove();
                        System.out.println("kkkkkkkkkkkkkkkk");
                    }
                }

                //update
                player1.update(elapsedTime);
                player2.update(elapsedTime);

                //render
                gc.clearRect(0, 0, mapWidth, mapHeight);
                player1.render(gc);
                player2.render(gc);
            }
        }.start();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
