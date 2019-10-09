
/*******************************************************************************
 * Jan Kožusznik
 * Copyright (c) 2016 All Right Reserved, http://www.kozusznik.cz
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/

import java.awt.Shape;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Jan Kožusznik
 * @version 0.1
 */
public class Canvas {

  private static Canvas cm;

  private CanvasPanel canvasPanel;

  public static class JavaFXApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
      synchronized (Canvas.class) {
        cm = new Canvas(primaryStage);
        Canvas.class.notifyAll();
      }
    }

    public static void launchAsync(String... args) {
      new Thread(() -> launch(args)).start();
      synchronized (Canvas.class) {
        while (cm == null) {
          try {
            Canvas.class.wait();
          } catch (InterruptedException e) {
            //ignore
            return;
          }
        }
      }
    }
  }

  private Canvas(Stage primaryStage) {
    canvasPanel = new CanvasPanel(250, 250);
    Scene scene =
        new Scene(canvasPanel, canvasPanel.getWidth(), canvasPanel.getHeight());
    primaryStage.setTitle("Hello World!");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static Canvas getInstance() {
    if (cm == null) {
      JavaFXApplication.launchAsync();
    }
    return cm;
  }

  /**
   * @param color
   * @see cz.kozusznik.pl1.shapes.CanvasPanel#setColorOfForeground(cz.kozusznik.pl1.shapes.MyColor)
   */
  public void setColorOfForeground(MyColor color) {
    canvasPanel.setColorOfForeground(color);
  }

  /**
   * @return
   * @see cz.kozusznik.pl1.shapes.CanvasPanel#getColorOfBackground()
   */
  public MyColor getColorOfBackground() {
    return canvasPanel.getColorOfBackground();
  }

  /**
   * @param color
   * @see cz.kozusznik.pl1.shapes.CanvasPanel#setColorOfBackground(cz.kozusznik.pl1.shapes.MyColor)
   */
  public void setColorOfBackground(MyColor color) {
    canvasPanel.setColorOfBackground(color);
  }

  /**
   * @see cz.kozusznik.pl1.shapes.CanvasPanel#erase()
   */
  public void erase() {
    canvasPanel.erase();
  }

  /**
   * @param shape
   * @see cz.kozusznik.pl1.shapes.CanvasPanel#fill(java.awt.Shape)
   */
  public void fill(Shape shape) {
    canvasPanel.fill(shape);
  }

  /**
   * @return
   * @see cz.kozusznik.pl1.shapes.CanvasPanel#getWidth()
   */
  public double getWidth() {
    return canvasPanel.getWidth();
  }

  /**
   * @return
   * @see cz.kozusznik.pl1.shapes.CanvasPanel#getHeight()
   */
  public double getHeight() {
    return canvasPanel.getHeight();
  }

}
