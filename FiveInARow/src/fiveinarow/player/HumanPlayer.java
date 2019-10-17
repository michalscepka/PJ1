/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiveinarow.player;

import fiveinarow.board.Board;
import fiveinarow.board.Symbol;
import java.util.Scanner;

/**
 *
 * @author Michal
 */
public class HumanPlayer implements IPlayer{
    
    private final String playerName;
    
    public HumanPlayer(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public Move makeMove(Board board, Symbol yourSymbol) {
        System.out.println(board);
        
        int x, y;
        
        do {
            System.out.println(playerName + " insert your position move: ");
            Scanner sc = new Scanner(System.in);
            x = sc.nextInt();
            y = sc.nextInt();
        } while (board.getSymbolAtPosition(x, y) != null);
        
        return new Move(x, y);
    }

    @Override
    public String getName() {
        return playerName;
    }
}
