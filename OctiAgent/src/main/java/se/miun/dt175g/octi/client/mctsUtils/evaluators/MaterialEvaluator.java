package se.miun.dt175g.octi.client.mctsUtils.evaluators;

import se.miun.dt175g.octi.client.mctsUtils.Normalizer;
import se.miun.dt175g.octi.client.utils.StateHelper;
import se.miun.dt175g.octi.core.OctiState;
import se.miun.dt175g.octi.core.Player;

public class MaterialEvaluator {
    private static final int POD_BONUS = 1000;
    private static final int PRONG_BONUS = 100;

    private static final int MAX_PODS = 4;
    private static final int MAX_PRONGS_PER_POD = 8;
    private static final int MAX_POD_BONUS = MAX_PODS * POD_BONUS;
    private static final int MAX_PRONG_BONUS = MAX_PODS * MAX_PRONGS_PER_POD * PRONG_BONUS;
    private static final double MAX_MATERIAL_SCORE = MAX_POD_BONUS + MAX_PRONG_BONUS;

    public static double evaluate(OctiState state, Player player) {
        double playerScore = calculateMaterialScore(state, player);
        double enemyScore = calculateMaterialScore(state, StateHelper.getOpponentPlayer(state, player));

        double relativeScore = playerScore - enemyScore;

        return Normalizer.normalize(relativeScore, MAX_MATERIAL_SCORE);
    }

    private static double calculateMaterialScore(OctiState state, Player player) {
        double totalValue = 0;

        totalValue += calculatePodsBonus(state, player);
        totalValue += calculateProngsBonus(state, player);

        return totalValue;
    }

    private static int calculatePodsBonus(OctiState state, Player player) {
        var pods = StateHelper.getPodsForPlayer(state, player);
        return pods.size() * POD_BONUS;
    }

    private static int calculateProngsBonus(OctiState state, Player player) {
        int totalPlacedProngs = 0;
        var pods = StateHelper.getPodsForPlayer(state, player);

        for (var pod : pods) {
            totalPlacedProngs += StateHelper.getPodProngCount(pod);
        }

        return totalPlacedProngs * PRONG_BONUS;
    }
}
