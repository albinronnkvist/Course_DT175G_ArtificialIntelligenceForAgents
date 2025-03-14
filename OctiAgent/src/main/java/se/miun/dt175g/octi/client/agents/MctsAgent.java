package se.miun.dt175g.octi.client.agents;

import se.miun.dt175g.octi.client.mctsUtils.BackpropagationHelper;
import se.miun.dt175g.octi.client.mctsUtils.ExpansionHelper;
import se.miun.dt175g.octi.client.mctsUtils.MoveSelectionHelper;
import se.miun.dt175g.octi.client.mctsUtils.SelectionHelper;
import se.miun.dt175g.octi.client.mctsUtils.SimulationHelper;
import se.miun.dt175g.octi.client.utils.TimeLimitChecker;
import se.miun.dt175g.octi.core.Agent;
import se.miun.dt175g.octi.core.Node;
import se.miun.dt175g.octi.core.OctiAction;
import se.miun.dt175g.octi.core.OctiState;

public class MctsAgent extends Agent {
    private static final double RANDOM_MOVE_PROBABILITY = 0.2;
    private static final double C = Math.sqrt(2);
    private final long bufferMillis;

    public MctsAgent(long bufferMillis) {
        this.bufferMillis = bufferMillis;
    }

    @Override
    public OctiAction getNextMove(OctiState state) {
        var timeLimitChecker = new TimeLimitChecker(super.timeLimit, bufferMillis);

        var clonedState = (OctiState) state.clone();
        for (var action : clonedState.getLegalActions()) {
            var nextState = clonedState.performAction(action);
            if (nextState.isTerminal() && nextState.getWinner() == super.player.getPlayerId()) {
                return action;
            }
        }

        var rootNode = Node.root(state);
        while (!timeLimitChecker.isTimeUp()) {
            var leafNode = SelectionHelper.select(rootNode, C, timeLimitChecker);
            var nodeToSimulate = ExpansionHelper.expand(leafNode);
            var simulationResult = SimulationHelper.simulate(nodeToSimulate, super.player, RANDOM_MOVE_PROBABILITY, timeLimitChecker);
            BackpropagationHelper.backPropagate(nodeToSimulate, simulationResult, super.player, timeLimitChecker);
        }

        return MoveSelectionHelper.getBestMoveBasedOnVisits(rootNode, timeLimitChecker);
    }

    public void setTimeLimit(long timeLimitMillis) {
        super.timeLimit = timeLimitMillis;
    }
}
