package se.miun.dt175g.octi.client.utils;

import java.util.List;

import se.miun.dt175g.octi.core.Direction;
import se.miun.dt175g.octi.core.OctiState;
import se.miun.dt175g.octi.core.Player;
import se.miun.dt175g.octi.core.Pod;
import se.miun.dt175g.octi.core.Point;

public class StateHelper {
    public static Player getOpponentPlayer(OctiState state, Player player) {
        return state.getRedPlayer().equals(player) ? state.getBlackPlayer() : state.getRedPlayer();
    }

    public static String getPlayerColor(OctiState state, Player player) {
        return state.getRedPlayer().equals(player) ? state.getRedPlayer().getColor() : state.getBlackPlayer().getColor();
    }

    public static List<Pod> getPodsForPlayer(OctiState state, Player player) {
        return state.getBoard().getPodsForPlayer(getPlayerColor(state, player));
    }

    public static int getPodProngCount(Pod pod) {
        int totalCount = 0;

        // This would have been easier if Pod.getSockets() was accessible
        // Or if there was a Pod.getProngsCount() method
        for (var direction : Direction.values()) {
            if (pod.hasProng(direction)) {
                totalCount++;
            }
        }

        return totalCount;
    }

    public static boolean playerHasProngsLeft(OctiState state, Player player) {
        return state.getRedPlayer().equals(player) ? state.getRedProngsLeft() > 0 : state.getBlackProngsLeft() > 0;
    }

    public static Point[] getFriendlyBasePositions(OctiState state, Player player) {
        return state.getRedPlayer().equals(player) ? state.getRedBase() : state.getBlackBase();
    }

    public static Point[] getOpponentBasePositions(OctiState state, Player player) {
        return state.getRedPlayer().equals(player) ? state.getBlackBase() : state.getRedBase();
    }
}
