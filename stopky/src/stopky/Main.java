package stopky;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.ArrayList;

public class Main extends Application {

    private Vlakno t1;
    private Label currentTimeLabel;
    private Label timesListLabel;
    private ArrayList<String> times;
    private Button btn_start;
    private Button btn_snap;
    private Button btn_pause;
    private Button btn_stop;

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();
        primaryStage.setTitle("Stopky");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        currentTimeLabel = new Label("000");
        currentTimeLabel.setTranslateX(10);
        currentTimeLabel.setTranslateY(10);
        currentTimeLabel.setFont(new Font("Consolas", 24));
        root.getChildren().add(currentTimeLabel);

        timesListLabel = new Label();
        timesListLabel.setTranslateX(15);
        timesListLabel.setTranslateY(80);
        timesListLabel.setFont(new Font("Consolas", 14));
        root.getChildren().add(timesListLabel);

        btn_start = new Button();
        btn_start.setText("Start");
        btn_start.setTranslateX(10);
        btn_start.setTranslateY(45);
        btn_start.setOnAction(event -> startInput());
        root.getChildren().add(btn_start);

        btn_snap = new Button();
        btn_snap.setText("Snap");
        btn_snap.setTranslateX(60);
        btn_snap.setTranslateY(45);
        btn_snap.setDisable(true);
        btn_snap.setOnAction(event -> snapInput());
        root.getChildren().add(btn_snap);

        btn_pause = new Button();
        btn_pause.setText("Pause");
        btn_pause.setTranslateX(110);
        btn_pause.setTranslateY(45);
        btn_pause.setDisable(true);
        btn_pause.setOnAction(event -> pauseInput());
        root.getChildren().add(btn_pause);

        btn_stop = new Button();
        btn_stop.setText("Stop");
        btn_stop.setTranslateX(175);
        btn_stop.setTranslateY(45);
        btn_stop.setDisable(true);
        btn_stop.setOnAction(event -> stopInput());
        root.getChildren().add(btn_stop);

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                try {
                    currentTimeLabel.setText(t1.toString());
                } catch (Exception ignored) {}

            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void startInput() {
        t1 = new Vlakno(LocalTime.now());
        t1.start();

        times = new ArrayList<>();
        timesListLabel.setText("");

        btn_pause.setText("Pause");

        btn_start.setDisable(true);
        btn_snap.setDisable(false);
        btn_pause.setDisable(false);
        btn_stop.setDisable(false);
    }

    private void snapInput() {
        times.add(t1.toString());
        StringBuilder sb = new StringBuilder();
        for (int i = times.size() - 1; i >= 0; i--) {
            sb.append(i).append(":\t").append(times.get(i)).append('\n');
        }
        timesListLabel.setText(sb.toString());
    }

    private void pauseInput() {
        t1.setWait();
        if(!t1.isWait()) {
            btn_pause.setText("Pause");
            t1.setResumedTime(LocalTime.now());
            t1.calculatePauseDuration();

            btn_start.setDisable(true);
            btn_snap.setDisable(false);
        } else {
            btn_pause.setText("Resume");
            snapInput();
            t1.setPausedTime(LocalTime.now());

            btn_start.setDisable(true);
            btn_snap.setDisable(true);
        }
        btn_pause.setDisable(false);
        btn_stop.setDisable(false);
    }

    private void stopInput() {
        t1.interrupt();
        btn_start.setDisable(false);
        btn_snap.setDisable(true);
        btn_pause.setDisable(true);
        btn_stop.setDisable(true);
    }
}
