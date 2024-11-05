package se.miun.dt175g.guesser;

public interface Guesser {
	/**
	 * Find a secret number by proposing guesses to an Oracle.
	 * 
	 * @param max    the (inclusive) maximum for the secret number (min is always
	 *               1).
	 * @param oracle knows everything and will help you learn the secret.
	 * @return the secret number
	 */
	public int findNumber(int max, Oracle oracle);
}
