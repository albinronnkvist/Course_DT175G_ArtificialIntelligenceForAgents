package se.miun.dt175g.octi.client.miniMaxUtils.evaluators;

import se.miun.dt175g.octi.client.utils.StateHelper;
import se.miun.dt175g.octi.core.OctiState;
import se.miun.dt175g.octi.core.Player;

public class MaterialEvaluator {
    private static final int POD_BONUS = 1000;
    private static final int PRONG_BONUS = 100;

    public static int evaluate(OctiState state, Player player) {
        int totalValue = 0;

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
