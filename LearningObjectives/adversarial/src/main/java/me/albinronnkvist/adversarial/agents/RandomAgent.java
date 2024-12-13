package me.albinronnkvist.adversarial.agents;

import java.util.Random;

import me.albinronnkvist.adversarial.Action;
import me.albinronnkvist.adversarial.BoardState;
import me.albinronnkvist.adversarial.utils.GameMoveHelper;

public class RandomAgent implements Agent {

    private BoardState board;
    public RandomAgent(BoardState board) {
        this.board = board;
    }

    @Override
    public Action getMove() {
        var legalActions = GameMoveHelper.actions(board);

        return legalActions.get(new Random().nextInt(legalActions.size()));
    }

}
