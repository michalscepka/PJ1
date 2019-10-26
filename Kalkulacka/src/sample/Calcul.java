package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Calcul extends Application {

    private Label label1 = new Label();
    private String[] znaky = {"+", "-", "*", "/"};
    private int znaky_pocet = znaky.length;
    private List<String> operands = new ArrayList<>();
    private boolean can_clear_label = false;

    @Override
    public void start(Stage primaryStage) {
        GridPane g = new GridPane();
        //ColumnConstraints column1 = new ColumnConstraints();
        //column1.setPercentWidth(50);
        //g.getColumnConstraints().addAll(column1);

        g.add(label1, 0, 0);

        for(int i = 0; i < 10; i++) {
            Button btn = new Button();
            btn.setText(Integer.toString(i));
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    numbersInput(btn.getText());
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
                    operandsInput(btn.getText());
                }
            });
            g.add(btn, i, 2);
        }

        Button btn_eqv = new Button();
        btn_eqv.setText("=");
        btn_eqv.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                equalsInput(btn_eqv.getText());
            }
        });
        g.add(btn_eqv, 4, 2);

        Button btn_C = new Button();
        btn_C.setText("C");
        btn_C.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cInput(btn_C.getText());
            }
        });
        g.add(btn_C, 5, 2);

        Button btn_dot = new Button();
        btn_dot.setText(".");
        btn_dot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                numbersInput(btn_dot.getText());
            }
        });
        g.add(btn_dot, 6, 2);

        Scene scene = new Scene(g, 600, 250);

        primaryStage.setTitle("Super kalkulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void numbersInput(String x) {
        System.out.println("Key" + x + " was pressed");

        if(can_clear_label) {
            label1.setText(x.toString());
            can_clear_label = false;
        }
        else {
            String label_text = label1.getText();
            label1.setText(label_text + x.toString());
        }
    }

    private void operandsInput(String x) {
        System.out.println("Key" + x + " was pressed");

        String label_text = label1.getText();
        label1.setText(label_text + x.toString());
        operands.add(x);
        can_clear_label = false;
    }

    private void equalsInput(String x) {
        String[] cislaS = label1.getText().split("\\+|-|\\*|/");
        double[] cisla = new double[cislaS.length];

        for(int i = 0; i < cisla.length; i++) {
            System.out.println(cislaS[i]);
            cisla[i] = Double.parseDouble(cislaS[i]);
        }
        for(int i = 0; i < operands.size(); i++) {
            System.out.println(operands.get(i));
        }

        double vysledek = cisla[0];
        for(int i = 0; i < cisla.length - 1; i++) {
            switch(operands.get(i)) {
                case "+":
                    vysledek += cisla[i + 1];
                    label1.setText(Double.toString(vysledek));
                    break;
                case "-":
                    vysledek -= cisla[i + 1];
                    label1.setText(Double.toString(vysledek));
                    break;
                case "*":
                    vysledek *= cisla[i + 1];
                    label1.setText(Double.toString(vysledek));
                    break;
                case "/":
                    vysledek /= cisla[i + 1];
                    label1.setText(Double.toString(vysledek));
                    break;
                default:
                    break;
            }
        }
        operands.clear();
        can_clear_label = true;
    }

    private void cInput(String x) {
        clear();
    }

    private void clear() {
        label1.setText("");
        operands.clear();
    }
}