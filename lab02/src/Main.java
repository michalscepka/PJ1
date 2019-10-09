/*******************************************************************************
 * Jan Kožusznik
 * Copyright (c) 2014 All Right Reserved, http://www.kozusznik.cz
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 ******************************************************************************/

import java.util.EventListener;

/**
 * @author Jan Kožusznik
 * @version 0.1
 */
public class Main {
  public static void main(String[] args) {

      Canvas.getInstance();

      Pacman pac1 = new Pacman(10, 10, Direction8.WEST);
      pac1.erase();
      pac1.paint();
      pac1.setPosition(100, 100);
      pac1.setDirection(Direction8.NORTH);
  }
}
