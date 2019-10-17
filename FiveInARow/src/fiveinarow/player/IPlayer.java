/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiveinarow.player;

import fiveinarow.board.Board;
import fiveinarow.board.Symbol;

/**
 *
 * @author Michal
 */
public interface IPlayer {

    public Move makeMove(Board board, Symbol yourSymbol);

    public String getName();
}
