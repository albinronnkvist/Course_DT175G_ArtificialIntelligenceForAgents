package se.miun.dt175g.octi.client.mctsUtils;

import java.util.List;
import java.util.Random;
import se.miun.dt175g.octi.client.mctsUtils.evaluators.Evaluator;
import se.miun.dt175g.octi.client.utils.TimeLimitChecker;
import se.miun.dt175g.octi.core.Node;
import se.miun.dt175g.octi.core.OctiAction;
import se.miun.dt175g.octi.core.OctiState;
import se.miun.dt175g.octi.core.Player;

public final class SimulationHelper {
    private static final Random RANDOM = new Random();

    private SimulationHelper() {
    }

    public static double simulate(Node<OctiState, OctiAction> node, 
        Player player, double randomMoveProbability, TimeLimitChecker timeLimitChecker) {
        OctiState tempState = (OctiState) node.state.clone();

        while (!tempState.isTerminal() && !tempState.getLegalActions().isEmpty() && !timeLimitChecker.isTimeUp()) {
            var legalActions = tempState.getLegalActions();
            var bestAction = chooseActionWithRandomness(tempState, legalActions, randomMoveProbability, timeLimitChecker);
            tempState = tempState.performAction(bestAction);
        }

        return Evaluator.evaluate(tempState, player);
    }

    private static OctiAction chooseActionWithRandomness(OctiState state,
        List<OctiAction> legalActions, double randomMoveProbability, TimeLimitChecker timeLimitChecker) {

        return RANDOM.nextDouble() < randomMoveProbability
                ? legalActions.get(RANDOM.nextInt(legalActions.size()))
                : selectBestAction(state, legalActions, timeLimitChecker);
    }

    private static OctiAction selectBestAction(OctiState state, List<OctiAction> actions, TimeLimitChecker timeLimitChecker) {
        OctiAction bestAction = null;
        double bestValue = Double.NEGATIVE_INFINITY;

        for (OctiAction action : actions) {
            if (timeLimitChecker.isTimeUp()) {
                break;
            }

            var nextState = state.performAction(action);
            if(nextState.isTerminal() && nextState.getWinner() == state.getCurrentPlayer().getPlayerId()) {
                return action;
            }

            double value = Evaluator.evaluate(nextState, state.getCurrentPlayer());

            if (value > bestValue) {
                bestValue = value;
                bestAction = action;
            }
        }
        
        return bestAction;
    }
}
