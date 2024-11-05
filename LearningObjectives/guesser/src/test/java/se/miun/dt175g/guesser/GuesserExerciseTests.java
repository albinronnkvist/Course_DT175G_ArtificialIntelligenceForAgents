package se.miun.dt175g.guesser;

public class GuesserExerciseTests extends GuesserTest {
    @Override
	public Guesser getLinearGuesserInstance() {
		return new LinearGuesser();
	}

	@Override
	public Guesser getLogGuesserInstance() {
		return new LogGuesser();
	}

	// Only modify this method, if you want to.
	@Override
	public TestVerbosity getVerbosity() {
		return TestVerbosity.FULL;
	}
}
