package stopky;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Vlakno t1;
    private long startTime;
    private Label label1;
    private boolean started = false;

    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        label1 = new Label("Time:");
        root.add(label1, 0, 1);

        Button btn_start = new Button();
        btn_start.setText("Start");
        btn_start.setOnAction(event -> startInput());
        root.add(btn_start, 0, 0);

        Button btn_snap = new Button();
        btn_snap.setText("Snap");
        btn_snap.setOnAction(event -> snapInput());
        root.add(btn_snap, 1, 0);

        Button btn_pause = new Button();
        btn_pause.setText("Pause");
        btn_pause.setOnAction(event -> {
            try {
                pauseInput();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        root.add(btn_pause, 2, 0);

        Button btn_stop = new Button();
        btn_stop.setText("Stop");
        btn_stop.setOnAction(event -> stopInput());
        root.add(btn_stop, 3, 0);

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if(started)
                    label1.setText(String.valueOf("Time: " + t1.getCurrentTime()));
            }
        }.start();

        /*Thread t1 = new Vlakno("Vlakno 1");
        Thread t2 = new Vlakno("Vlakno 2");
        Thread t3 = new Vlakno("Vlakno 3");

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(3000);
        t1.interrupt();*/
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void startInput() {
        //System.out.println("Start");
        startTime = System.currentTimeMillis();
        t1 = new Vlakno(startTime);
        t1.start();
        started = true;
    }

    private void snapInput() {
        //System.out.println("Snap");
        //System.out.println(System.nanoTime() / 1000000000.0);
        //System.out.println(System.currentTimeMillis() - startTime);
        //label1.setText(String.valueOf(System.currentTimeMillis() - startTime));
    }

    private void pauseInput() throws InterruptedException {
        System.out.println("Pause");
    }

    private void stopInput() {
        System.out.println("Stop");
        t1.interrupt();
    }
}
