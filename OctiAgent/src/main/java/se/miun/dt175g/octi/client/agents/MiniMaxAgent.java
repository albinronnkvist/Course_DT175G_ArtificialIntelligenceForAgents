package se.miun.dt175g.octi.client.agents;

import se.miun.dt175g.octi.client.miniMaxUtils.UtilityActionPair;
import se.miun.dt175g.octi.client.miniMaxUtils.evaluators.Evaluator;
import se.miun.dt175g.octi.client.utils.TimeLimitChecker;
import se.miun.dt175g.octi.core.Agent;
import se.miun.dt175g.octi.core.Node;
import se.miun.dt175g.octi.core.OctiAction;
import se.miun.dt175g.octi.core.OctiState;

public class MiniMaxAgent extends Agent {

    private final long bufferMillis;

    public MiniMaxAgent(long bufferMillis) {
        this.bufferMillis = bufferMillis;
    }

    @Override
    public OctiAction getNextMove(OctiState state) {
        OctiAction bestAction = null;
        var timeLimitChecker = new TimeLimitChecker(super.timeLimit, bufferMillis);

        for (int depth = 1; depth <= Integer.MAX_VALUE; depth++) {
            if (timeLimitChecker.isTimeUp()) {
                break;
            }

            var currentAction = maxValue(Node.root(state), 
                timeLimitChecker, depth, Integer.MIN_VALUE, Integer.MAX_VALUE)
                .action();

            if(currentAction != null) {
                bestAction = currentAction;
            }
        }

        return bestAction;
    }

    public void setTimeLimit(long timeLimitMillis) {
        super.timeLimit = timeLimitMillis;
    }

    private UtilityActionPair maxValue(Node<OctiState, OctiAction> node, 
        TimeLimitChecker timeLimitChecker, int depth, int alpha, int beta) {
        if (timeLimitChecker.isTimeUp()) {
            return new UtilityActionPair(Integer.MAX_VALUE, null);
        }

        if(isCutOff(node, depth)) {
            return new UtilityActionPair(Evaluator.evaluate(node.state, super.player), null);
        }

        int bestUtility = Integer.MIN_VALUE;
        OctiAction bestAction = null;

        for(var childNode : Agent.generateChildNodes(node)) {
            var minResult = minValue(childNode, timeLimitChecker, depth - 1, alpha, beta);

            if(minResult.utility() > bestUtility) {
                bestUtility = minResult.utility();
                bestAction = childNode.action;
                alpha = Math.max(alpha, bestUtility);
            }

            if(bestUtility >= beta) {
                break;
            }
        }

        return new UtilityActionPair(bestUtility, bestAction);
    }

    private UtilityActionPair minValue(Node<OctiState, OctiAction> node, 
        TimeLimitChecker timeLimitChecker, int depth, int alpha, int beta) {
        if (timeLimitChecker.isTimeUp()) {
            return new UtilityActionPair(Integer.MIN_VALUE, null);
        }

        if(isCutOff(node, depth)) {
            return new UtilityActionPair(Evaluator.evaluate(node.state, super.player), null);
        }

        int bestUtility = Integer.MAX_VALUE;
        OctiAction bestAction = null;

        for(var childNode : Agent.generateChildNodes(node)) {
            var maxResult = maxValue(childNode, timeLimitChecker, depth - 1, alpha, beta);

            if(maxResult.utility() < bestUtility) {
                bestUtility = maxResult.utility();
                bestAction = childNode.action;
                beta = Math.min(beta, bestUtility);
            }

            if(bestUtility <= alpha) {
                break;
            }
        }

        return new UtilityActionPair(bestUtility, bestAction);
    }

    private boolean isCutOff(Node<OctiState, OctiAction> node, int depth) {
        return node.state.isTerminal() || depth == 0;
    }
}
