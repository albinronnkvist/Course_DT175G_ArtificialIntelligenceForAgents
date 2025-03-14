package se.miun.dt175g.octi.client.miniMaxUtils.evaluators;

import se.miun.dt175g.octi.client.utils.StateHelper;
import se.miun.dt175g.octi.core.OctiState;
import se.miun.dt175g.octi.core.Player;
import se.miun.dt175g.octi.core.Point;

public class PositionalPodTeamworkEvaluator {
    private static final int POD_BORDERING_FRIEND_BONUS = 500;

    public static int evaluate(OctiState state, Player player) {
        int totalValue = 0;

        totalValue += calculatePodBorderingFriendBonus(state, player);

        return totalValue;
    }

    private static int calculatePodBorderingFriendBonus(OctiState state, Player player) {
        int totalBonus = 0;

        var pods = StateHelper.getPodsForPlayer(state, player);
        var podPositions = pods.stream()
            .map(pod -> state.getBoard().getPositionFromPod(pod))
            .toList();

        for (var podPosition : podPositions) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    boolean isCurrentPod = dx == 0 && dy == 0;
                    if (isCurrentPod) continue;

                    int adjacentX = podPosition.x() + dx;
                    int adjacentY = podPosition.y() + dy;

                    if (podPositions.contains(new Point(adjacentX, adjacentY))) {
                        totalBonus += POD_BORDERING_FRIEND_BONUS;
                    }
                }
            }
        }

        return totalBonus;
    }
}
