package se.miun.dt175g.octi.client;

import se.miun.dt175g.octi.client.agents.MctsAgent;
import se.miun.dt175g.octi.core.communicator.PlayerSetup;
import se.miun.dt175g.octi.core.communicator.PlayerSetupParser;


public class Main {
	public static void main(String[] args) {
		PlayerSetup setup = new PlayerSetupParser().parse(args);
		GameClient gc = new GameClient(setup, new MctsAgent(10));
		gc.play();
	}
}
