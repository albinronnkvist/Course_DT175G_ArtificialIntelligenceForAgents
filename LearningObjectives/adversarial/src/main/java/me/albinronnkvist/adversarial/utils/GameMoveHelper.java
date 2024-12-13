package me.albinronnkvist.adversarial.utils;

import java.util.ArrayList;
import java.util.List;

import me.albinronnkvist.adversarial.BoardState;
import me.albinronnkvist.adversarial.Symbol;
import me.albinronnkvist.adversarial.exceptions.InvalidMoveException;
import me.albinronnkvist.adversarial.Action;

public class GameMoveHelper {
    public static List<Action> actions(BoardState board) {
        List<Action> legalMoves = new ArrayList<>();

        for (int row = 0; row < BoardState.SIZE; row++) {
            for (int col = 0; col < BoardState.SIZE; col++) {
                var action = new Action(row, col);
                if (board.getCell(action) == Symbol.EMPTY) {
                    legalMoves.add(action);
                }
            }
        }

        return legalMoves;
    }

    public static BoardState result(BoardState state, Action action, Symbol symbol) throws InvalidMoveException {
        var clonedState = state.clone();

        clonedState.setCell(action, symbol);
        
        return clonedState;
    }
}
