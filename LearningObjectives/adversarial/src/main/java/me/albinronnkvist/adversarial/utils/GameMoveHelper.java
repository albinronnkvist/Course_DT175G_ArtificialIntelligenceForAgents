package me.albinronnkvist.adversarial.utils;

import java.util.ArrayList;
import java.util.List;

import me.albinronnkvist.adversarial.BoardState;
import me.albinronnkvist.adversarial.Player;
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

    public static int evaluate(BoardState state, Player max, Player min) {
        int score = 0;

        for (int i = 0; i < BoardState.SIZE; i++) {
            score += evaluateLine(state.getRow(i), max, min);
        }

        for (int i = 0; i < BoardState.SIZE; i++) {
            score += evaluateLine(state.getColumn(i), max, min);
        }

        score += evaluateLine(state.getDiagonalFromTopLeft(), max, min);
        score += evaluateLine(state.getDiagonalFromTopRight(), max, min);

        return score;
    }

    private static int evaluateLine(Symbol[] line, Player max, Player min) {
        int maxCount = 0, minCount = 0;

        for (var cell : line) {
            if (cell == max.symbol())
                maxCount++;
            else if (cell == min.symbol())
                minCount++;
        }

        if (maxCount > 0 && minCount == 0) {
            return (int) Math.pow(10, maxCount); // Favor lines with more max symbols
        } else if (minCount > 0 && maxCount == 0) {
            return -(int) Math.pow(10, minCount); // Penalize lines with more min symbols
        }

        return 0;
    }
}
