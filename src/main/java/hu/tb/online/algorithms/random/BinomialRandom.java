package hu.tb.online.algorithms.random;

public class BinomialRandom extends AbstractRandom {
	@Override

	public int nextRandom() {
		return generate(10, 0.2);
	}

	/**
	 * Generate next random number with Binomial distribution
	 */
	private int generate(int n, double p) {
		double log_q = Math.log(1.0 - p);
		int x = 0;
		double sum = 0;
		for (; ; ) {
			sum += Math.log(Math.random()) / (n - x);
			if (sum < log_q) {
				return x;
			}
			x++;
		}
	}
}
