/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiveinarow.player;

import fiveinarow.board.Board;
import fiveinarow.board.Symbol;
import java.util.Random;

/**
 *
 * @author Michal
 */
public class RandomPlayer implements  IPlayer {
    
    private final String playerName = "Bot";

    @Override
    public Move makeMove(Board board, Symbol yourSymbol) {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(board.getSize());
            y = random.nextInt(board.getSize());
        } while (board.getSymbolAtPosition(x, y) != null);
        return new Move(x, y);
    }

    @Override
    public String getName() {
        return playerName;
    }
}
