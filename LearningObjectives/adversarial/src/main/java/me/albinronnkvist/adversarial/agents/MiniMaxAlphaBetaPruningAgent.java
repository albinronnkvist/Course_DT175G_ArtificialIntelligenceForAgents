package me.albinronnkvist.adversarial.agents;

import me.albinronnkvist.adversarial.Action;
import me.albinronnkvist.adversarial.BoardState;
import me.albinronnkvist.adversarial.Player;
import me.albinronnkvist.adversarial.exceptions.InvalidMoveException;
import me.albinronnkvist.adversarial.utils.GameMoveHelper;
import me.albinronnkvist.adversarial.utils.GameTerminalHelper;
import me.albinronnkvist.adversarial.utils.UtilityMoveTuple;

// For the first move in tic-tac-toe, this reduced MaxValue calls from 258265 to 8401 and MinValue calls from 291681 to 9896
public class MiniMaxAlphaBetaPruningAgent implements Agent {

    private final BoardState root;
    private final Player max;
    private final Player min;
    private final Player currentPlayer;

    public MiniMaxAlphaBetaPruningAgent(BoardState boardState, Player max, Player min, Player currentPlayer) {
        this.root = boardState.clone();
        this.max = max;
        this.min = min;
        this.currentPlayer = currentPlayer;
    }

    @Override
    public Action getMove() throws InvalidMoveException {
        var result = currentPlayer.equals(max) 
            ? maxValue(root, Integer.MIN_VALUE, Integer.MAX_VALUE) 
            : minValue(root, Integer.MIN_VALUE, Integer.MAX_VALUE);

        return result.move();
    }

    private UtilityMoveTuple<Integer, Action> maxValue(BoardState state, int alpha, int beta) throws InvalidMoveException {
        if (GameTerminalHelper.isTerminal(state)) {
            return new UtilityMoveTuple<>(GameTerminalHelper.utility(state, max, min), null);
        }

        int value = Integer.MIN_VALUE;
        Action bestAction = null;

        for (var action : GameMoveHelper.actions(state)) {
            var resultState = GameMoveHelper.result(state, action, max.symbol());
            var minResult = minValue(resultState, alpha, beta);

            if (minResult.utility() > value) {
                value = minResult.utility();
                bestAction = action;
                alpha = Math.max(alpha, value); // Update best alternative for max
            }

            // Prune remaining actions
            if (value >= beta) {
                break;
            }
        }

        return new UtilityMoveTuple<>(value, bestAction);
    }

    private UtilityMoveTuple<Integer, Action> minValue(BoardState state, int alpha, int beta) throws InvalidMoveException {
        if (GameTerminalHelper.isTerminal(state)) {
            return new UtilityMoveTuple<>(GameTerminalHelper.utility(state, max, min), null);
        }

        int value = Integer.MAX_VALUE;
        Action bestAction = null;

        for (var action : GameMoveHelper.actions(state)) {
            var resultState = GameMoveHelper.result(state, action, min.symbol());
            var maxResult = maxValue(resultState, alpha, beta);

            if (maxResult.utility() < value) {
                value = maxResult.utility();
                bestAction = action;
                beta = Math.min(beta, value); // Update best alternative for min
            }

            // Prune remaining actions
            if(value <= alpha) {
                break;
            }
        }

        return new UtilityMoveTuple<>(value, bestAction);
    }
}
