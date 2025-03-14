package se.miun.dt175g.octi.client.agents;

import java.util.Random;

import se.miun.dt175g.octi.core.Agent;
import se.miun.dt175g.octi.core.OctiAction;
import se.miun.dt175g.octi.core.OctiState;

public class RandomAgent extends Agent {
	
	@Override
	public OctiAction getNextMove(OctiState state) {
		Random rand = new Random();
		var legalActions = state.getLegalActions();
		return legalActions.get(rand.nextInt(legalActions.size()));
	}

}

