package hu.tb.online.algorithms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LRU<T> extends AbstractPagingAlgorithm<T>{
	private Map<T, Integer> history = new HashMap<>();

	@Override
	public void solve(List<T> input) {
		for(int i = 0; i < input.size(); ++i){
			if(cache.size() < cacheSize){
				if(!cache.contains(input.get(i))){
					cache.add(input.get(i));
				}
				history.put(input.get(i),0);
			}
			else{

				if(!cache.contains(input.get(i))){
					//if(history.containsKey(input.get(i))){

						System.out.println("Page fault at element: " + input.get(i));

						/*int last = min(history.get(cache.get(0)), history.get(cache.get(1)), history.get(cache.get(2)));
						Object value = getKeyFromValue(history, last);
						cache.set(cache.indexOf(value), input.get(i));
						history.put(input.get(i), 0);
						System.out.println("New cache content: " + input.get(i));
						printCacheContent();
						for(Map.Entry<T, Integer> e : history.entrySet()){
							if(e.getKey() != input.get(i)){
								history.put(e.getKey(), e.getValue() + 1);
							}
						}*/
						cache.set(0, cache.get(1));
						cache.set(1, cache.get(2));
						cache.set(2, input.get(i));
						incrementPageFault();
				}else{
					T tmp = cache.get(0);
					cache.set(0,cache.get(2));
					cache.set(2, tmp);

					 tmp = cache.get(1);
					cache.set(1,cache.get(0));
					cache.set(0, tmp);

				}

			}
		}
	}


	public int min(final int a, final int b, final int c){
		return Math.min(a, Math.min(b, c));
	}

	public Object getKeyFromValue(Map hm, Object value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}
}
