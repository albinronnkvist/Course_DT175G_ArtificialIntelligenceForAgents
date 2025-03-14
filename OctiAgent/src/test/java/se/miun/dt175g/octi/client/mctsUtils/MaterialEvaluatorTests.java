package se.miun.dt175g.octi.client.mctsUtils;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import se.miun.dt175g.octi.client.mctsUtils.evaluators.MaterialEvaluator;
import se.miun.dt175g.octi.client.utils.StateHelper;
import se.miun.dt175g.octi.core.Direction;
import se.miun.dt175g.octi.core.JumpActionElement;
import se.miun.dt175g.octi.core.OctiState;

public class MaterialEvaluatorTests {
    @Test
    public void evaluate_WithEqualScores_ShouldReturnZero() {
        var state = OctiState.createBasicMode();
        var redPlayer = state.getRedPlayer();
        var blackPlayer = state.getBlackPlayer();

        double redScore = MaterialEvaluator.evaluate(state, redPlayer);
        double blackScore = MaterialEvaluator.evaluate(state, blackPlayer);

        assertThat(redScore).isCloseTo(0.0, within(0.001));
        assertThat(blackScore).isCloseTo(0.0, within(0.001));
    }

    @Test
    public void evaluate_WithRedAdvantage_PodAmount_ShouldReturnGreaterScoreForRed() {
        var state = OctiState.createBasicMode();

        var firstMoveState = state.performAction(
            state.getPlaceProngActions(StateHelper.getPodsForPlayer(state, state.getRedPlayer())).stream()
            .filter(action -> action.getDirection() == Direction.FRONT).findFirst().get());

        var secondMoveState = firstMoveState.performAction(
            firstMoveState.getPlaceProngActions(StateHelper.getPodsForPlayer(firstMoveState, firstMoveState.getBlackPlayer())).stream()
                .filter(action -> action.getDirection() == Direction.FRONT).findFirst().get());

        var thirdMoveState = secondMoveState.performAction(
            secondMoveState.getMoveActions(StateHelper.getPodsForPlayer(secondMoveState, secondMoveState.getRedPlayer())).stream()
            .filter(action -> action.getDirection() == Direction.FRONT).findFirst().get());

        var fourthMoveState = thirdMoveState.performAction(
            thirdMoveState.getMoveActions(StateHelper.getPodsForPlayer(thirdMoveState, thirdMoveState.getBlackPlayer())).stream()
            .filter(action -> action.getDirection() == Direction.FRONT).findFirst().get());

        var fifthMoveState = fourthMoveState.performAction(
            fourthMoveState.getMoveActions(StateHelper.getPodsForPlayer(fourthMoveState, fourthMoveState.getRedPlayer())).stream()
            .filter(action -> action.getDirection() == Direction.FRONT).findFirst().get());

        var sixthMoveState = fifthMoveState.performAction(
            fifthMoveState.getPlaceProngActions(StateHelper.getPodsForPlayer(fifthMoveState, fifthMoveState.getBlackPlayer())).get(0));

        var lastMoveState = sixthMoveState.performAction(
            sixthMoveState.getJumpActions(StateHelper.getPodsForPlayer(sixthMoveState, sixthMoveState.getRedPlayer())).stream()
            .filter(action -> action.getJumpActionElements().stream().
                anyMatch(JumpActionElement::isCapturePod)).findFirst().get());

        double redScore = MaterialEvaluator.evaluate(lastMoveState, lastMoveState.getRedPlayer());
        double blackScore = MaterialEvaluator.evaluate(lastMoveState, lastMoveState.getBlackPlayer());

        assertThat(redScore).isGreaterThan(blackScore);
    }

    @Test
    public void evaluate_WithRedAdvantage_ProngAmount_ShouldReturnGreaterScoreForRed() {
        var state = OctiState.createBasicMode();
        var redPlayer = state.getRedPlayer();
        var blackPlayer = state.getBlackPlayer();

        var redPods = StateHelper.getPodsForPlayer(state, redPlayer);
        var placeProngActions = state.getPlaceProngActions(redPods);
        var newState = state.performAction(placeProngActions.get(0));

        double redScore = MaterialEvaluator.evaluate(newState, redPlayer);
        double blackScore = MaterialEvaluator.evaluate(newState, blackPlayer);

        assertThat(redScore).isGreaterThan(blackScore);
    }

    @Test
    public void evaluate_WithBlackAdvantage_PodAmount_ShouldReturnGreaterScoreForBlack() {
        var state = OctiState.createBasicMode();

        var firstMoveState = state.performAction(
            state.getPlaceProngActions(StateHelper.getPodsForPlayer(state, state.getRedPlayer())).stream()
            .filter(action -> action.getDirection() == Direction.FRONT).findFirst().get());

        var secondMoveState = firstMoveState.performAction(
            firstMoveState.getPlaceProngActions(StateHelper.getPodsForPlayer(firstMoveState, firstMoveState.getBlackPlayer())).stream()
                .filter(action -> action.getDirection() == Direction.FRONT).findFirst().get());

        var thirdMoveState = secondMoveState.performAction(
            secondMoveState.getMoveActions(StateHelper.getPodsForPlayer(secondMoveState, secondMoveState.getRedPlayer())).stream()
            .filter(action -> action.getDirection() == Direction.FRONT).findFirst().get());

        var fourthMoveState = thirdMoveState.performAction(
            thirdMoveState.getMoveActions(StateHelper.getPodsForPlayer(thirdMoveState, thirdMoveState.getBlackPlayer())).stream()
            .filter(action -> action.getDirection() == Direction.FRONT).findFirst().get());

        var fifthMoveState = fourthMoveState.performAction(
            fourthMoveState.getMoveActions(StateHelper.getPodsForPlayer(fourthMoveState, fourthMoveState.getRedPlayer())).stream()
            .filter(action -> action.getDirection() == Direction.FRONT).findFirst().get());
        
        var lastMoveState = fifthMoveState.performAction(
            fifthMoveState.getJumpActions(StateHelper.getPodsForPlayer(fifthMoveState, fifthMoveState.getBlackPlayer())).stream()
            .filter(action -> action.getJumpActionElements().stream().
                anyMatch(JumpActionElement::isCapturePod)).findFirst().get());

        double redScore = MaterialEvaluator.evaluate(lastMoveState, lastMoveState.getRedPlayer());
        double blackScore = MaterialEvaluator.evaluate(lastMoveState, lastMoveState.getBlackPlayer());

        assertThat(blackScore).isGreaterThan(redScore);
    }

    @Test
    public void evaluate_WithBlackAdvantage_ProngAmount_ShouldReturnGreaterScoreForBlack() {
        var state = OctiState.createBasicMode();
        var redPlayer = state.getRedPlayer();
        var blackPlayer = state.getBlackPlayer();

        var blackPods = StateHelper.getPodsForPlayer(state, blackPlayer);
        var placeProngActions = state.getPlaceProngActions(blackPods);
        var newState = state.performAction(placeProngActions.get(0));

        double redScore = MaterialEvaluator.evaluate(newState, redPlayer);
        double blackScore = MaterialEvaluator.evaluate(newState, blackPlayer);

        assertThat(blackScore).isGreaterThan(redScore);
    }

}
