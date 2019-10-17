/*******************************************************************************
 * Jan Kožusznik
 * Copyright (c) 2016 All Right Reserved, http://www.kozusznik.cz
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/

import javafx.scene.shape.Circle;

/**
 * @author Jan Kožusznik
 * @version 0.1
 */
public class Run {

    public static void main(String[] args) {
        //new Rectangle(100, 100, 50, 50, MyColor.MAGENTA);
        //new Triangle(100, 50, 100, 100);

        int x = 100;
        int y = 150;
        int width = 50;
        int height = 50;

        new Pacman(x, y, width, height, MyColor.YELLOW, Direction8.WEST, 60);

        new House();
        new House(70, 50, 50, 50);
        new House(130, 50, 50, 50, MyColor.AZURE, MyColor.BORDO);

    }
}
