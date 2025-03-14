package se.miun.dt175g.octi.client.miniMaxUtils.evaluators;

import java.util.HashSet;
import java.util.Set;

import se.miun.dt175g.octi.client.utils.StateHelper;
import se.miun.dt175g.octi.core.Direction;
import se.miun.dt175g.octi.core.OctiState;
import se.miun.dt175g.octi.core.Player;
import se.miun.dt175g.octi.core.Point;

public class PositionalProngEvaluator {
    private static final int OPPOSING_PRONGS_BONUS = 400;
    private static final int OPPONENT_BASE_DIRECTION_PRONGS_BONUS = 200;

    public static int evaluate(OctiState state, Player player) {
        int totalValue = 0;

        totalValue += calculateOpposingProngsBonus(state, player);
        totalValue += calculateOpponentBaseDirectionProngsBonus(state, player);

        return totalValue;
    }

    private static int calculateOpposingProngsBonus(OctiState state, Player player) {
        int totalOpposingProngs = 0;
        var pods = StateHelper.getPodsForPlayer(state, player);

        for (var pod : pods) {
            for (var direction : Direction.values()) {
                if (pod.hasProng(direction) && pod.hasProng(Direction.getOpposite(direction))) {
                    totalOpposingProngs++;
                }
            }
        }

        return (totalOpposingProngs / 2) * OPPOSING_PRONGS_BONUS;
    }

    private static int calculateOpponentBaseDirectionProngsBonus(OctiState state, Player player) {
        int totalOpponentBaseDirectionProngs = 0;
        var pods = StateHelper.getPodsForPlayer(state, player);
        var opponentBasePositions = StateHelper.getOpponentBasePositions(state, player);

        for (var pod : pods) {
            var podPosition = state.getBoard().getPositionFromPod(pod);

            var directionsToOpponentBase = getDirectionsToOpponentBase(podPosition, opponentBasePositions);

            for (var direction : Direction.values()) {
                if (pod.hasProng(direction) && directionsToOpponentBase.contains(direction)) {
                    totalOpponentBaseDirectionProngs++;
                }
            }
        }

        return totalOpponentBaseDirectionProngs * OPPONENT_BASE_DIRECTION_PRONGS_BONUS;
    }

    private static Set<Direction> getDirectionsToOpponentBase(Point podPosition, Point[] opponentBasePositions) {
        Set<Direction> directions = new HashSet<>();

        for (var opponentBasePosition : opponentBasePositions) {
            var direction = calculateDirection(podPosition, opponentBasePosition);
            if (direction != null) {
                directions.add(direction);
            }
        }

        return directions;
    }
    
    private static Direction calculateDirection(Point from, Point to) {
        int horizontalDifference = to.x() - from.x();
        int verticalDifference = to.y() - from.y();
    
        if (horizontalDifference == 0 && verticalDifference > 0) return Direction.FRONT;
        if (horizontalDifference == 0 && verticalDifference < 0) return Direction.BACK;
        if (horizontalDifference > 0 && verticalDifference == 0) return Direction.RIGHT;
        if (horizontalDifference < 0 && verticalDifference == 0) return Direction.LEFT;
        if (horizontalDifference > 0 && verticalDifference > 0) return Direction.FRONT_RIGHT;
        if (horizontalDifference > 0 && verticalDifference < 0) return Direction.BACK_RIGHT;
        if (horizontalDifference < 0 && verticalDifference > 0) return Direction.FRONT_LEFT;
        if (horizontalDifference < 0 && verticalDifference < 0) return Direction.BACK_LEFT;
    
        return null;
    }
}
