package se.miun.dt175g.octi.client.mctsUtils;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import se.miun.dt175g.octi.client.mctsUtils.evaluators.PositionalPodEvaluator;
import se.miun.dt175g.octi.client.utils.StateHelper;
import se.miun.dt175g.octi.core.Direction;
import se.miun.dt175g.octi.core.OctiState;

public class PositionalPodEvaluatorTests {
    @Test
    public void evaluate_WithEqualPositions_ShouldReturnZero() {
        var state = OctiState.createBasicMode();
        var redPlayer = state.getRedPlayer();
        var blackPlayer = state.getBlackPlayer();

        double redScore = PositionalPodEvaluator.evaluate(state, redPlayer);
        double blackScore = PositionalPodEvaluator.evaluate(state, blackPlayer);

        assertThat(redScore).isCloseTo(0.0, within(0.001));
        assertThat(blackScore).isCloseTo(0.0, within(0.001));
    }

    @Test
    public void evaluate_WithRedAdvantage_Positional_ShouldReturnGreaterScoreForRed() {
        var state = OctiState.createBasicMode();

        var firstMoveState = state.performAction(
            state.getPlaceProngActions(StateHelper.getPodsForPlayer(state, state.getRedPlayer())).stream()
            .filter(action -> action.getDirection() == Direction.FRONT).findFirst().get());

        var secondMoveState = firstMoveState.performAction(
            firstMoveState.getPlaceProngActions(StateHelper.getPodsForPlayer(firstMoveState, firstMoveState.getBlackPlayer())).get(0));

        var lastMoveState = secondMoveState.performAction(
            secondMoveState.getMoveActions(StateHelper.getPodsForPlayer(secondMoveState, secondMoveState.getRedPlayer())).stream()
            .filter(action -> action.getDirection() == Direction.FRONT).findFirst().get());

        double redScore = PositionalPodEvaluator.evaluate(lastMoveState, lastMoveState.getRedPlayer());
        double blackScore = PositionalPodEvaluator.evaluate(lastMoveState, lastMoveState.getBlackPlayer());

        assertThat(redScore).isGreaterThan(blackScore);
    }

    @Test
    public void evaluate_WithBlackAdvantage_Positional_ShouldReturnGreaterScoreForBlack() {
        var state = OctiState.createBasicMode();

        var firstMoveState = state.performAction(
            state.getPlaceProngActions(StateHelper.getPodsForPlayer(state, state.getRedPlayer())).stream()
            .filter(action -> action.getDirection() == Direction.FRONT).findFirst().get());

        var secondMoveState = firstMoveState.performAction(
            firstMoveState.getPlaceProngActions(StateHelper.getPodsForPlayer(firstMoveState, firstMoveState.getBlackPlayer())).get(0));

        var thirdMoveState = secondMoveState.performAction(
            secondMoveState.getPlaceProngActions(StateHelper.getPodsForPlayer(secondMoveState, secondMoveState.getRedPlayer())).get(0));

        var lastMoveState = thirdMoveState.performAction(
            thirdMoveState.getMoveActions(StateHelper.getPodsForPlayer(thirdMoveState, thirdMoveState.getBlackPlayer())).stream()
            .filter(action -> action.getDirection() == Direction.FRONT).findFirst().get());

        double redScore = PositionalPodEvaluator.evaluate(lastMoveState, lastMoveState.getRedPlayer());
        double blackScore = PositionalPodEvaluator.evaluate(lastMoveState, lastMoveState.getBlackPlayer());

        assertThat(blackScore).isGreaterThan(redScore);
    }
}
