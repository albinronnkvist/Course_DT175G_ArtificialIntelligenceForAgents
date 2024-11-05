package se.miun.dt175g.guesser;

/**
 * Implements a logarithmic guessing algorithm. 
 * Complete this implementation so that findNumber returns the secret number in time Ο(log n).
 */
public class LogGuesser implements Guesser {

	@Override
	public int findNumber(int max, Oracle oracle) {
		int left = 1;
		int right = max;
		
		while (left <= right)
		{
			int mid = left + (right - left) / 2;

			Oracle.Answer result = oracle.test(mid);
			switch(result)
			{
				case MATCH:
					return mid;
				case TOO_LOW:
					left = mid + 1;
					continue;
				case TOO_HIGH:
					right = mid - 1;
					continue;
				default:
					return -1; // Error
			}
		}

		return -1; // Error
	}
}