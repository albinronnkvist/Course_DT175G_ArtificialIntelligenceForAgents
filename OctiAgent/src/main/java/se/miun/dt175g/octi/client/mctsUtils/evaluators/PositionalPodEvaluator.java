package se.miun.dt175g.octi.client.mctsUtils.evaluators;

import se.miun.dt175g.octi.client.mctsUtils.Normalizer;
import se.miun.dt175g.octi.client.utils.StateHelper;
import se.miun.dt175g.octi.core.OctiState;
import se.miun.dt175g.octi.core.Player;
import se.miun.dt175g.octi.core.Point;

public class PositionalPodEvaluator {
    private static final int POD_CLOSE_TO_ENEMY_BASE_BONUS = 500;
    private static final int POD_NEXT_TO_ENEMY_BASE_BONUS  = 100;
    private static final int MAX_VERTICAL_DISTANCE = 7;
    
    private static final int MAX_POSITIONAL_BONUS_PER_POD = 
        (MAX_VERTICAL_DISTANCE * POD_CLOSE_TO_ENEMY_BASE_BONUS) + POD_NEXT_TO_ENEMY_BASE_BONUS;
    private static final int MAX_PODS = 4;
    private static final int MAX_POSITIONAL_SCORE = MAX_PODS * MAX_POSITIONAL_BONUS_PER_POD;

    public static double evaluate(OctiState state, Player player) {
        double playerScore = calculatePositionalScore(state, player);
        double opponentScore = calculatePositionalScore(state, StateHelper.getOpponentPlayer(state, player));

        double relativeScore = playerScore - opponentScore;
        return Normalizer.normalize(relativeScore, MAX_POSITIONAL_SCORE);
    }

    private static double calculatePositionalScore(OctiState state, Player player) {
        double totalBonus = 0.0;

        var pods = StateHelper.getPodsForPlayer(state, player);
        var opponentBasePositions = StateHelper.getOpponentBasePositions(state, player);

        for (var pod : pods) {
            var podPosition = state.getBoard().getPositionFromPod(pod);

            int minDistance = Integer.MAX_VALUE;
            boolean isNextToEnemyBase = false;

            for (var basePos : opponentBasePositions) {
                int distance = calculateManhattanDistance(podPosition, basePos);
                if (distance == 1) {
                    isNextToEnemyBase = true;
                }
                minDistance = Math.min(minDistance, distance);
            }

            int effectiveDistance = Math.min(minDistance, MAX_VERTICAL_DISTANCE);

            if (isNextToEnemyBase) {
                totalBonus += POD_NEXT_TO_ENEMY_BASE_BONUS;
            }

            int distanceFactor = (MAX_VERTICAL_DISTANCE - effectiveDistance);
            totalBonus += distanceFactor * POD_CLOSE_TO_ENEMY_BASE_BONUS;
        }

        return totalBonus;
    }

    private static int calculateManhattanDistance(Point from, Point to) {
        return Math.abs(from.x() - to.x()) + Math.abs(from.y() - to.y());
    }
}
