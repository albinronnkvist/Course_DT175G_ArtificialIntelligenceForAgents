package se.miun.dt175g.octi.client.mctsUtils;

import se.miun.dt175g.octi.core.Node;
import se.miun.dt175g.octi.core.OctiAction;
import se.miun.dt175g.octi.core.OctiState;

public final class ExpansionHelper {
    private ExpansionHelper() {}

    public static Node<OctiState, OctiAction> expand(Node<OctiState, OctiAction> node) {
        if (node.hasUnexploredActions()) {
            return node.generateNextChildNode();
        }
        
        return node;
    }
}
