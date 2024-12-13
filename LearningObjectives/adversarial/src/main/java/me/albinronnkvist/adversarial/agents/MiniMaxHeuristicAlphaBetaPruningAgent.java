package me.albinronnkvist.adversarial.agents;

import me.albinronnkvist.adversarial.Action;
import me.albinronnkvist.adversarial.BoardState;
import me.albinronnkvist.adversarial.Player;
import me.albinronnkvist.adversarial.exceptions.InvalidMoveException;
import me.albinronnkvist.adversarial.utils.GameMoveHelper;
import me.albinronnkvist.adversarial.utils.GameTerminalHelper;
import me.albinronnkvist.adversarial.utils.UtilityMoveTuple;

public class MiniMaxHeuristicAlphaBetaPruningAgent implements Agent {
    private final BoardState root;
    private final Player max;
    private final Player min;
    private final Player currentPlayer;
    private final int maxDepth;

    public MiniMaxHeuristicAlphaBetaPruningAgent(BoardState boardState, 
        Player max, Player min, Player currentPlayer, int maxDepth) {
        this.root = boardState.clone();
        this.max = max;
        this.min = min;
        this.currentPlayer = currentPlayer;
        this.maxDepth = maxDepth;
    }

    @Override
    public Action getMove() throws InvalidMoveException {
        var result = currentPlayer.equals(max) 
            ? maxValue(root, Integer.MIN_VALUE, Integer.MAX_VALUE, maxDepth) 
            : minValue(root, Integer.MIN_VALUE, Integer.MAX_VALUE, maxDepth);

        return result.move();
    }

    private UtilityMoveTuple<Integer, Action> maxValue(BoardState state, int alpha, int beta, int depth) throws InvalidMoveException {
        if (GameTerminalHelper.isCutoff(state, depth)) { // Replace terminal test with cutoff test
            return new UtilityMoveTuple<>(GameMoveHelper.evaluate(state, max, min), null); // Replace utility with heuristic evaluation
        }

        int value = Integer.MIN_VALUE;
        Action bestAction = null;

        for (var action : GameMoveHelper.actions(state)) {
            var resultState = GameMoveHelper.result(state, action, max.symbol());
            var minResult = minValue(resultState, alpha, beta, depth - 1);

            if (minResult.utility() > value) {
                value = minResult.utility();
                bestAction = action;
                alpha = Math.max(alpha, value);
            }

            if (value >= beta) {
                break;
            }
        }

        return new UtilityMoveTuple<>(value, bestAction);
    }

    private UtilityMoveTuple<Integer, Action> minValue(BoardState state, int alpha, int beta, int depth) throws InvalidMoveException {
        if (GameTerminalHelper.isCutoff(state, depth)) {
            return new UtilityMoveTuple<>(GameMoveHelper.evaluate(state, max, min), null);
        }

        int value = Integer.MAX_VALUE;
        Action bestAction = null;

        for (var action : GameMoveHelper.actions(state)) {
            var resultState = GameMoveHelper.result(state, action, min.symbol());
            var maxResult = maxValue(resultState, alpha, beta, depth - 1);

            if (maxResult.utility() < value) {
                value = maxResult.utility();
                bestAction = action;
                beta = Math.min(beta, value);
            }

            if (value <= alpha) {
                break;
            }
        }

        return new UtilityMoveTuple<>(value, bestAction);
    }
}
