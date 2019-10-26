/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import minesweeper.board.Board;

/**
 *
 * @author Michal
 */
public class Minesweeper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Board board = new Board(10);
        board.initializeBoard(10);
        System.out.println(board);
        
        board.makeMove(0, 0);
        System.out.println(board);
        
        /*if (board.placeFlag(2, 2)) {
            System.out.println("Flag on 2, 2");
        } else {
            System.out.println("Flag not on 2, 2");
        }
        System.out.println(board);
        
        if (board.placeFlag(2, 2)) {
            System.out.println("Flag on 2, 2");
        } else {
            System.out.println("Flag not on 2, 2");
        }
        System.out.println(board);*/
    }
    
}
