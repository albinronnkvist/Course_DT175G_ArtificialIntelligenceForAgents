package se.miun.dt175g.guesser;

/**
 * Implements a linear guessing algorithm. 
 * Complete this implementation so that findNumber returns the secret number in time Θ(n).
 */
public class LinearGuesser implements Guesser {
    @Override
	public int findNumber(int max, Oracle oracle) {
		for (int n = 1; n <= max; n++) {
			if (oracle.test(n).equals(Oracle.Answer.MATCH)) {
				return n;
			}
		}

		return -1;
	}
}
