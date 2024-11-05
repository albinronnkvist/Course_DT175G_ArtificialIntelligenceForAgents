package se.miun.dt175g.guesser;

/**
 * Implements a linear guessing algorithm. Complete this implementation so that
 * findNumber returns the secret number in time O(n).
 *
 * If you want to be precise, then LinearGuesser should run in time Θ(n), but if
 * you don't know the difference between Ο, Θ, and Ω, then ignore this part. ;)
 */
public class LinearGuesser implements Guesser {
    @Override
	public int findNumber(int max, Oracle oracle) {
		/* Just an example that does not satisfy either of the complexity requirements.
		 * Feel free to remove */
		
		/*
		final Random rng = new Random(123);
		while (true) {
			int guess = rng.nextInt(max) + 1;
			if (oracle.test(guess).equals(Oracle.Answer.MATCH)) {
				return guess;
			}
		}
		*/
		return -1; // TODO your code here
	}
}
