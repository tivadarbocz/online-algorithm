package hu.tb.online.algorithms;

import java.util.List;

public class FIFO<T> extends AbstractPagingAlgorithm<T> {
	@Override
	public void solve(List<T> input) {
		for(int i = 0; i < input.size(); ++i){
			if(cache.size() < cacheSize){
				cache.add(input.get(i));
			}
			else{
				if(!cache.contains(input.get(i))){
					System.out.println("Page fault at element: " + input.get(i));
					cache.set(0, cache.get(1));
					cache.set(1, cache.get(2));
					cache.set(2, input.get(i));
					System.out.println("New cache content: " + input.get(i));
					printCacheContent();
					incrementPageFault();
				}
			}
		}
	}
}
