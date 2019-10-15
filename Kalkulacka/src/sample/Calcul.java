package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static com.sun.javafx.logging.PulseLogger.newInput;

public class Calcul extends Application {

    @Override
    /*public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }*/
    public void start(Stage primaryStage) {
        Group g = new Group();
        for(int i = 0; i < 10; i++) {
            Button btn = new Button();
            btn.setText(Integer.toString(i));
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    newInput(btn.getText());
                }
            });
            g.getChildren().add(btn);
        }

        Scene scene = new Scene(g, 300, 250);

        primaryStage.setTitle("Super kalkulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void newInput(String x) {
        System.out.println("Key" + x + " was pressed");
    }
}
