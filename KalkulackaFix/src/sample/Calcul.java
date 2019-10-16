package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Calcul extends Application {

    private Label label1 = new Label();
    private String[] znaky = {"+", "-", "*", "/", "=", "C"};
    private int znaky_pocet = znaky.length;
    private double cislo1 = 0.0;
    private double cislo2 = 0.0;
    private double vysledek = 0.0;
    private boolean prvni_cislo = true;
    private boolean label_erase = false;
    private String operand = "";
    private String cislo1S = "";
    private String cislo2S = "";

    @Override
    public void start(Stage primaryStage) {
        GridPane g = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints();
        //column1.setPercentWidth(50);
        g.getColumnConstraints().addAll(column1);

        g.add(label1, 0, 0);

        for(int i = 0; i < 10; i++) {
            Button btn = new Button();
            btn.setText(Integer.toString(i));
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    newInput(btn.getText());
                }
            });
            //g.getChildren().add(btn);
            g.add(btn, i, 1);
        }

        for(int i = 0; i < znaky_pocet; i++) {
            Button btn = new Button();
            btn.setText(znaky[i]);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    operationInput(btn.getText());
                }
            });
            g.add(btn, i, 2);
        }

        Scene scene = new Scene(g, 600, 250);

        primaryStage.setTitle("Super kalkulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void newInput(String x) {
        System.out.println("Key" + x + " was pressed");

        if(label_erase) {
            label1.setText(x.toString());
            label_erase = false;
        } else {
            String label_text = label1.getText();
            label1.setText(label_text + x.toString());
        }
    }

    private void operationInput(String x) {
        System.out.println("Operation '" + x + "' was pressed");

        if(!label1.getText().equals("")) {
            if(cislo1S.equals("")) {
                cislo1S = label1.getText();
                System.out.println("cislo_1: " + cislo1S);
            } else {
                cislo2S = label1.getText();
                System.out.println("cislo_2: " + cislo2S);
            }
        }

        label_erase = true;
        if(!x.equals("=")) {
            operand = x;
        }
        System.out.println(operand);
        System.out.println(cislo1S + " " + operand + " " + cislo2S + " = " + vysledek);

        if(!cislo1S.equals("") && !cislo2S.equals("")) {
            cislo1 = Double.parseDouble(cislo1S);
            cislo2 = Double.parseDouble(cislo2S);

            vypocitat();

        }

        System.out.println(cislo1S + " " + operand + " " + cislo2S + " = " + vysledek);
    }

    private void equalsInput(String x) {

    }

    private void vypocitat() {
        switch(operand) {
            case "+":
                vysledek = cislo1 + cislo2;
                bordel();
                break;
            case "-":
                vysledek = cislo1 - cislo2;
                bordel();
                break;
            case "*":
                vysledek = cislo1 * cislo2;
                bordel();
                break;
            case "/":
                vysledek = cislo1 / cislo2;
                bordel();
                break;
            default:
                break;
        }
    }

    private void bordel() {
        label1.setText(Double.toString(vysledek));
        cislo1 = vysledek;
        cislo1S = Double.toString(vysledek);
        cislo2S = "";
        System.out.println("vysledek: " + Double.toString(vysledek));
    }
}
