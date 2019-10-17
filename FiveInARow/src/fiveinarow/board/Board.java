/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiveinarow.board;

/**
 *
 * @author Michal
 */
public class Board {
    private final int size;
    private Symbol board[][];
    
    public Board(int size){
        this.size = size;
        board = new Symbol[size][size];
    }
    
    public int getSize() {
        return size;
    }
    
    public Symbol getSymbolAtPosition(int x, int y) {
        return board[x][y];
    }
    
    public void setSymbol(int x, int y, Symbol symbol) {
        board[x][y] = symbol;
    }

    //CTRL + SPACE
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(board[j][i] == Symbol.CROSS) {
                    sb.append('X');
                }
                else if (board[j][i] == Symbol.CIRCLE){
                    sb.append('O');
                }
                else {
                    sb.append('.');
                }
            }
            sb.append('\n');
        }
        return sb.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
            
}
