package me.albinronnkvist.adversarial.agents;

import me.albinronnkvist.adversarial.Action;
import me.albinronnkvist.adversarial.BoardState;
import me.albinronnkvist.adversarial.Player;
import me.albinronnkvist.adversarial.exceptions.InvalidMoveException;
import me.albinronnkvist.adversarial.utils.GameMoveHelper;
import me.albinronnkvist.adversarial.utils.GameTerminalHelper;
import me.albinronnkvist.adversarial.utils.UtilityMoveTuple;

public class MiniMaxAgent implements Agent {

    private final BoardState root;
    private final Player max;
    private final Player min;
    private final Player currentPlayer;

    public MiniMaxAgent(BoardState boardState, Player max, Player min, Player currentPlayer) {
        this.root = boardState.clone();
        this.max = max;
        this.min = min;
        this.currentPlayer = currentPlayer;
    }

    @Override
    public Action getMove() throws InvalidMoveException {
        var result = currentPlayer.equals(max) ? maxValue(root) : minValue(root);
        return result.move();
    }

    private UtilityMoveTuple<Integer, Action> maxValue(BoardState state) throws InvalidMoveException {
        if (GameTerminalHelper.isTerminal(state)) {
            return new UtilityMoveTuple<>(GameTerminalHelper.utility(state, max, min), null);
        }

        int value = Integer.MIN_VALUE;
        Action bestAction = null;

        for (var action : GameMoveHelper.actions(state)) {
            var resultState = GameMoveHelper.result(state, action, max.symbol());
            var minResult = minValue(resultState);
            
            if(minResult.utility() > value) {
                value = minResult.utility();
                bestAction = action;
            }
        }

        return new UtilityMoveTuple<>(value, bestAction);
    }

    private UtilityMoveTuple<Integer, Action> minValue(BoardState state) throws InvalidMoveException {
        if (GameTerminalHelper.isTerminal(state)) {
            return new UtilityMoveTuple<>(GameTerminalHelper.utility(state, max, min), null);
        }

        int value = Integer.MAX_VALUE;
        Action bestAction = null;

        for (var action : GameMoveHelper.actions(state)) {
            var resultState = GameMoveHelper.result(state, action, min.symbol());
            var maxResult = maxValue(resultState);
            
            if(maxResult.utility() < value) {
                value = maxResult.utility();
                bestAction = action;
            }
        }
        
        return new UtilityMoveTuple<>(value, bestAction);
    }
}
