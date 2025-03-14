package se.miun.dt175g.octi.client.mctsUtils.evaluators;

import se.miun.dt175g.octi.core.OctiState;
import se.miun.dt175g.octi.core.Player;

public class Evaluator {
    private static final double MATERIAL_WEIGHT = 0.3;
    private static final double POSITIONAL_POD_WEIGHT = 0.6;
    private static final double POSITIONAL_PRONG_WEIGHT = 0.1;

    public static double evaluate(OctiState state, Player player) {
        if(state.isTerminal()) {
            return terminalStateUtility(state, player);
        }

        return nonTerminalStateHeuristicEstimate(state, player);
    }

    private static double terminalStateUtility(OctiState state, Player player) {
        if(state.hasWinner() == null) {
            return 0;
        }

        return state.getWinner() == player.getPlayerId() ? 1 : -1;
    }

    private static double nonTerminalStateHeuristicEstimate(OctiState state, Player player) {
        var materialScore = MaterialEvaluator.evaluate(state, player);
        var positionalPodScore = PositionalPodEvaluator.evaluate(state, player);
        var positionalProngScore = PositionalProngEvaluator.evaluate(state, player);

        return materialScore * MATERIAL_WEIGHT 
            + positionalPodScore * POSITIONAL_POD_WEIGHT
            + positionalProngScore * POSITIONAL_PRONG_WEIGHT;
    }
}
