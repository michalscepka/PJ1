package bulanci;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        Pane root = new Pane();
        primaryStage.setTitle("Bulanci");
        Scene scene = new Scene(root, 800, 625);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        root.getChildren().add(canvas);

        GameManager gameManager = new GameManager(new GameMap((int)canvas.getWidth(), (int)canvas.getHeight() - 25, 4), root, canvas.getGraphicsContext2D());
        gameManager.initUI(scene, canvas);
        gameManager.starGame(scene, canvas);

        ArrayList<String> input = new ArrayList<>();

        scene.setOnKeyPressed(e -> {
            String code = e.getCode().toString();
            if (e.getCode() == KeyCode.CONTROL)
                gameManager.shoot();
            else if (!input.contains(code) && (code.equals("UP") || code.equals("DOWN") || code.equals("LEFT") || code.equals("RIGHT")))
                input.add(code);
            else if (e.getCode() == KeyCode.R)
                gameManager.respawnPlayer();
        });

        scene.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            input.remove(code);
        });

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                gameManager.update(currentNanoTime, input);
            }
        }.start();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
