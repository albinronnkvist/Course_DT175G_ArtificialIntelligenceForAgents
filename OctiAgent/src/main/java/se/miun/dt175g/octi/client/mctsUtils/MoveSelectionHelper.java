package se.miun.dt175g.octi.client.mctsUtils;

import java.util.Comparator;

import se.miun.dt175g.octi.client.utils.TimeLimitChecker;
import se.miun.dt175g.octi.core.Node;
import se.miun.dt175g.octi.core.OctiAction;
import se.miun.dt175g.octi.core.OctiState;

public final class MoveSelectionHelper {
    private MoveSelectionHelper() {
    }

    public static OctiAction getBestMoveBasedOnAverageReward(Node<OctiState, OctiAction> rootNode, TimeLimitChecker timeLimitChecker) {
        return rootNode.getChildren().stream()
            .max((n1, n2) -> n1.compareTo(n2))
            .map(child -> child.action)
            .orElse(null);
    }

    public static OctiAction getBestMoveBasedOnVisits(Node<OctiState, OctiAction> rootNode, TimeLimitChecker timeLimitChecker) {
        return rootNode.getChildren().stream()
            .max(Comparator.comparingInt(Node::getVisits))
            .map(child -> child.action)
            .orElse(null);
    }
}
