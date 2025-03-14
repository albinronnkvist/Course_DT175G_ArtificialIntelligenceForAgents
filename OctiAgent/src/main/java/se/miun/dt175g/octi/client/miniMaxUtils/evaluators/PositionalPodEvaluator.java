package se.miun.dt175g.octi.client.miniMaxUtils.evaluators;

import se.miun.dt175g.octi.client.utils.StateHelper;
import se.miun.dt175g.octi.core.OctiState;
import se.miun.dt175g.octi.core.Player;
import se.miun.dt175g.octi.core.Point;

public class PositionalPodEvaluator {
    private static final int POD_CLOSE_TO_ENEMY_BASE_BONUS = 5000;
    private static final int POD_NEXT_TO_ENEMY_BASE_BONUS = 1000;
    private static final int MAX_VERTICAL_DISTANCE = 7;

    public static int evaluate(OctiState state, Player player) {
        int totalValue = 0;

        totalValue += calculatePodCloseToEnemyBaseBonus(state, player);

        return totalValue;
    }

    private static int calculatePodCloseToEnemyBaseBonus(OctiState state, Player player) {
        int totalBonus = 0;
    
        var pods = StateHelper.getPodsForPlayer(state, player);
        var opponentBasePositions = StateHelper.getOpponentBasePositions(state, player);
    
        for (var pod : pods) {
            var podPosition = state.getBoard().getPositionFromPod(pod);
    
            int minDistance = Integer.MAX_VALUE;
            boolean isNextToEnemyBase = false;
            for (var opponentBase : opponentBasePositions) {
                int distance = calculateManhattanDistance(podPosition, opponentBase);
                if(distance == 1) {
                    isNextToEnemyBase = true;
                }
                minDistance = Math.min(minDistance, distance);
            }
    
            int effectiveDistance = Math.min(minDistance, MAX_VERTICAL_DISTANCE);
            
            if (isNextToEnemyBase) {
                totalBonus += POD_NEXT_TO_ENEMY_BASE_BONUS;
            }
            int bonus = (MAX_VERTICAL_DISTANCE - effectiveDistance) * POD_CLOSE_TO_ENEMY_BASE_BONUS;
            totalBonus += bonus;
        }
    
        return totalBonus;
    }
    
    private static int calculateManhattanDistance(Point from, Point to) {
        return Math.abs(to.x() - from.x()) + Math.abs(to.y() - from.y());
    }
}
