package se.miun.dt175g.octi.client;

import se.miun.dt175g.octi.client.agents.MctsAgent;
import se.miun.dt175g.octi.core.PerformanceTest;

public class ExampleTest {
	private final static int timeLimit = 200;
	public static void main(String[] args) {
		var myAgent = new MctsAgent(10);
		myAgent.setTimeLimit(timeLimit);

		PerformanceTest.test(myAgent).random(55).color("red")
				.matches(10).timeout(timeLimit).level(1).run(0.85);
	}
}
