package se.miun.dt175g.octi.client.mctsUtils;

import se.miun.dt175g.octi.client.utils.TimeLimitChecker;
import se.miun.dt175g.octi.core.Node;
import se.miun.dt175g.octi.core.OctiAction;
import se.miun.dt175g.octi.core.OctiState;
import se.miun.dt175g.octi.core.Player;

public final class BackpropagationHelper {
    private BackpropagationHelper() {}

    public static void backPropagate(Node<OctiState, OctiAction> node, double simulationResult, Player rootPlayer, TimeLimitChecker timeLimitChecker) {
        while (node != null && !timeLimitChecker.isTimeUp()) {
            node.update(simulationResult);
            node = node.parent;
        }
    }
}
