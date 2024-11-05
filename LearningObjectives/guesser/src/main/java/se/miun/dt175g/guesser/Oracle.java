package se.miun.dt175g.guesser;

public interface Oracle {
	public static final int MIN = 1;

	/** An answer from an Oracle. */
	public enum Answer {
		/** Your guess was too low - the secret number is bigger. */
		TOO_LOW,
		/** Your guess is spot on. */
		MATCH,
		/** Your guess was too high - the secret number is smaller. */
		TOO_HIGH,
	}

	/** Propose a number to the Oracle and use the answer to refine your guess. */
	public Answer test(int guess);
}
