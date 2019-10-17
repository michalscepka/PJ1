/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiveinarow;

import fiveinarow.board.Board;
import fiveinarow.board.Symbol;
import fiveinarow.player.HumanPlayer;
import fiveinarow.player.RandomPlayer;

/**
 *
 * @author Michal
 */
public class FiveInARow {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game(new HumanPlayer("George"), new RandomPlayer(), 10);
        //Game game = new Game(new HumanPlayer("George"), new HumanPlayer("Tom"), 10);
        game.playGame();
    }
}
