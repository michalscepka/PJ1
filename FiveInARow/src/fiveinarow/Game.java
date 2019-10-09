/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiveinarow;

import fiveinarow.board.Board;
import fiveinarow.board.Symbol;
import fiveinarow.player.IPlayer;
import fiveinarow.player.Move;

/**
 *
 * @author Michal
 */
public class Game {
    private IPlayer player1;
    private IPlayer player2;
    private Board board;
    private int movesCount = 0;
    private int maxMoves;
    
    public Game(IPlayer player1, IPlayer player2, int size) {
        this.player1 = player1;
        this.player2 = player2;
        board = new Board(size);
        maxMoves = board.getSize() * board.getSize();
    }
    
    public Game(IPlayer player1, IPlayer player2) {
        this(player1, player2, 10);
    }
    
    public void playGame() {
        while(true) {
            //System.out.println("maxMoves: " + maxMoves);
            Move player1Move = player1.makeMove(board, Symbol.CROSS);
            movesCount++;
            //System.out.println("movesCount: " + movesCount);
            board.setSymbol(player1Move.getX(), player1Move.getY(), Symbol.CROSS);
            if(isEnd(player1Move, board, Symbol.CROSS)) {
                System.out.println(player1.getName() + " wins!");
                return;
            }
            if (movesCount == maxMoves) {
                System.out.println(board);
                System.out.println("Draw!");
                return;
            }

            Move player2Move = player2.makeMove(board, Symbol.CIRCLE);
            movesCount++;
            //System.out.println("movesCount: " + movesCount);
            board.setSymbol(player2Move.getX(), player2Move.getY(), Symbol.CIRCLE);
            if(isEnd(player2Move, board, Symbol.CIRCLE)) {
                System.out.println(player2.getName() + " wins!");
                return;
            }
            
            if (movesCount == maxMoves) {
                System.out.println(board);
                System.out.println("Draw!");
                return;
            }
        }
    }

    private boolean isEnd(Move playerMove, Board board, Symbol symbol) {
        
        int x = playerMove.getX();
        int y = playerMove.getY();
        int n = 5;
        
        //Horizontal
        int counter = 0;
        
        for (int i = (x - n + 1); i < (x + n); i++) {
            
            if(i >= board.getSize() || i < 0) {
                continue;
            }
            
            if (board.getSymbolAtPosition(i, y) == board.getSymbolAtPosition(x, y)) {
                counter++;
            }
            else {
                counter = 0;
            }
            
            if (counter == n) {
                System.out.println(board);
                return true;
            }
        }
        
        //Vertical
        counter = 0;
        
        for (int i = (y - n + 1); i < (y + n); i++) {
            
            if(i >= board.getSize() || i < 0) {
                continue;
            }
            
            if (board.getSymbolAtPosition(x, i) == board.getSymbolAtPosition(x, y)) {
                counter++;
            }
            else {
                counter = 0;
            }
            
            if (counter == n) {
                System.out.println(board);
                return true;
            }
        }
        
        //Diagonal
        counter = 0;
        int j = (y - n);
        for (int i = (x - n + 1); i < (x + n); i++) {
            j++;
            
            if(i >= board.getSize() || i < 0) {
                //System.out.println("I i: " + i + "; j: " + j);
                continue;
            }
            else if (j >= board.getSize() || j < 0) {
                //System.out.println("J i: " + i + "; j: " + j);
                continue;
            }
            
            //System.out.println("F i: " + i + "; j: " + j);
            
            if (board.getSymbolAtPosition(i, j) == board.getSymbolAtPosition(x, y)) {
                counter++;
            }
            else {
                counter = 0;
            }

            if (counter == n) {
                System.out.println(board);
                return true;
            }
        }
        
        counter = 0;
        j = (y + n);
        for (int i = (x - n + 1); i < (x + n); i++) {
            j--;
            
            if(i >= board.getSize() || i < 0) {
                //System.out.println("I i: " + i + "; j: " + j);
                continue;
            }
            else if (j >= board.getSize() || j < 0) {
                //System.out.println("J i: " + i + "; j: " + j);
                continue;
            }
            
            //System.out.println("F i: " + i + "; j: " + j);
            
            if (board.getSymbolAtPosition(i, j) == board.getSymbolAtPosition(x, y)) {
                counter++;
            }
            else {
                counter = 0;
            }

            if (counter == n) {
                System.out.println(board);
                return true;
            }
        }
        
        //System.out.println("----------------------------------------");
        return false;
    }
}
