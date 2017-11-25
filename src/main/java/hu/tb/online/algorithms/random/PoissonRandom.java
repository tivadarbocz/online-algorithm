package hu.tb.online.algorithms.random;

public class PoissonRandom extends AbstractRandom {
	@Override
	public int nextRandom() {
		return generate(10);
	}

	/**
	 * Generate next random number with Poisson distribution
	 */
	private int generate(double lambda) {
		double l = Math.exp(-lambda);
		double p = 1.0;
		int k = 0;

		do {
			k++;
			p *= Math.random();
		} while (p > l);
		return k - 1;
	}
}
