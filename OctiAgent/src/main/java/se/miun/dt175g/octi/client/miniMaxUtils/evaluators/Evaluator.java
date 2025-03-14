package se.miun.dt175g.octi.client.miniMaxUtils.evaluators;

import se.miun.dt175g.octi.core.OctiState;
import se.miun.dt175g.octi.core.Player;

public class Evaluator {
    private static final double MATERIAL_WEIGHT = 0.2;
    private static final double POSITIONAL_POD_WEIGHT = 0.7;
    private static final double POSITIONAL_PRONG_WEIGHT = 0.1;
    private static final double POSITIONAL_POD_TEAMWORK_WEIGHT = 0.5;

    public static int evaluate(OctiState state, Player player) {
        if(state.isTerminal()) {
            return terminalStateUtility(state, player);
        }

        return nonTerminalStateHeuristicEstimate(state, player);
    }

    private static int terminalStateUtility(OctiState state, Player player) {
        if(state.hasWinner() == null) {
            return 0;
        }

        return state.getWinner() == player.getPlayerId() ? Integer.MAX_VALUE : Integer.MIN_VALUE;
    }

    private static int nonTerminalStateHeuristicEstimate(OctiState state, Player player) {
        int materialScore = MaterialEvaluator.evaluate(state, player);
        int positionalPodScore = PositionalPodEvaluator.evaluate(state, player);
        int positionalProngScore = PositionalProngEvaluator.evaluate(state, player);
        int positionalPodTeamworkScore = PositionalPodTeamworkEvaluator.evaluate(state, player);

        return (int) (MATERIAL_WEIGHT * materialScore) 
            + (int) (POSITIONAL_POD_WEIGHT * positionalPodScore)
            + (int) (POSITIONAL_PRONG_WEIGHT * positionalProngScore)
            + (int) (POSITIONAL_POD_TEAMWORK_WEIGHT * positionalPodTeamworkScore);
    }
}
