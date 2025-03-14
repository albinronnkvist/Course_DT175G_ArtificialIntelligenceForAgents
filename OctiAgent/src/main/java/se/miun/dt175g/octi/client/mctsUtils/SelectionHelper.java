package se.miun.dt175g.octi.client.mctsUtils;

import se.miun.dt175g.octi.client.utils.TimeLimitChecker;
import se.miun.dt175g.octi.core.Node;
import se.miun.dt175g.octi.core.OctiAction;
import se.miun.dt175g.octi.core.OctiState;

public final class SelectionHelper {
    private SelectionHelper() {
    }

    public static Node<OctiState, OctiAction> select(Node<OctiState, OctiAction> node,
            double c, TimeLimitChecker timeLimitChecker) {
        while (!node.isLeaf() && !node.hasUnexploredActions() && !timeLimitChecker.isTimeUp()) {
            node = getBestChildByUCT(node, c);
        }

        return node;
    }

    public static Node<OctiState, OctiAction> getBestChildByUCT(Node<OctiState, OctiAction> parent, double c) {
        Node<OctiState, OctiAction> bestChild = null;
        double bestValue = Double.NEGATIVE_INFINITY;

        for (var child : parent.getChildren()) {
            if (child.getVisits() == 0) {
                return child;
            }

            var uctValue = (child.getTotalReward() / child.getVisits())
                    + c * Math.sqrt(Math.log(parent.getVisits()) / child.getVisits());

            if (uctValue > bestValue) {
                bestValue = uctValue;
                bestChild = child;
            }
        }

        return bestChild;
    }
}
