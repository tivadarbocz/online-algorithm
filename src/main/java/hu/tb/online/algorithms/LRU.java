package hu.tb.online.algorithms;

import java.util.List;

public class LRU<T> extends AbstractPagingAlgorithm<T> {

	@Override
	public void solve(List<T> input) {
		for (int i = 0; i < input.size(); ++i) {
			if (cache.size() < cacheSize) {
				if (!cache.contains(input.get(i))) {
					cache.add(input.get(i));
				}
			} else {

				if (!cache.contains(input.get(i))) {
					cache.set(0, cache.get(1));
					cache.set(1, cache.get(2));
					cache.set(2, input.get(i));
					incrementPageFault();
				} else {
					int indexOfInput = cache.indexOf(input.get(i));

					for (int j = 0; j < 2; ++j) {
						if (indexOfInput == j) {
							T tmp = cache.get(1 - j);
							cache.set(0, cache.get(j));
							cache.set(1, cache.get(2));
							cache.set(2, tmp);
						}
					}

				}
			}
		}
	}
}
