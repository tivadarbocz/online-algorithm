package hu.tb.online.algorithms;

import hu.tb.online.Util;

import java.util.HashMap;
import java.util.List;

public class LFD<T> extends AbstractPagingAlgorithm<T> {

	@Override
	public void solve(List<T> input) {
		for (int i = 0; i < input.size(); ++i) {
			if (cache.size() < cacheSize) {
				cache.add(input.get(i));
			} else {

				if (!cache.contains(input.get(i))) {
					HashMap<T, Integer> forward = new HashMap<>();
					for (int j = 0; j < cacheSize; ++j) {
						forward.put(cache.get(j), Integer.MAX_VALUE);
					}

					int distance = 0;

					for (int j = i; j < input.size(); ++j) {
						for (int k = 0; k < cacheSize; ++k) {
							if (k == 0 && input.get(j).equals(cache.get(k)) && forward.get(cache.get(k)).equals(Integer.MAX_VALUE)) {
								forward.put(input.get(j), distance);
							} else if (k == 1 && input.get(j).equals(cache.get(k)) && forward.get(cache.get(k)).equals(Integer.MAX_VALUE)) {
								forward.put(input.get(j), distance);
							} else if (k == 2 && input.get(j).equals(cache.get(k)) && forward.get(cache.get(k)).equals(Integer.MAX_VALUE)) {
								forward.put(input.get(j), distance);
							}
						}
						++distance;
						if (allElementFound(forward)) {
							break;
						}
					}

					if (anyElementFound(forward)) {
						T farthest = (T) Util.getKeyFromValue(forward, Util.max(forward.get(cache.get(0)), forward.get(cache.get(1)), forward.get(cache.get(2))));
						cache.set(cache.indexOf(farthest), input.get(i));
					}
					incrementPageFault();
				}
			}
		}
	}

	private boolean allElementFound(HashMap<T, Integer> forward) {
		return forward.get(cache.get(0)) < Integer.MAX_VALUE && forward.get(cache.get(1)) < Integer.MAX_VALUE && forward.get(cache.get(2)) < Integer.MAX_VALUE;
	}

	private boolean anyElementFound(HashMap<T, Integer> forward) {
		return forward.get(cache.get(0)) < Integer.MAX_VALUE || forward.get(cache.get(1)) < Integer.MAX_VALUE || forward.get(cache.get(2)) < Integer.MAX_VALUE;
	}
}
