package hu.tb.online.algorithms;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPagingAlgorithm<T> {
	public enum ALGORITHM{FIFO, LRU,LFD}

	private int pageFault = 0;
	protected ArrayList<T> cache = new ArrayList<>();
	protected int cacheSize = 3;

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public abstract void solve(List<T> input);

	protected void incrementPageFault() {
		++pageFault;
	}

	public void printCacheContent() {
		System.out.println("Cache content");
		for (T c : cache) {
			System.out.println(c);
		}
		System.out.println("");
	}

	public ArrayList<T> getCache() {
		return cache;
	}

	public String getFormattedCache(){
		return "Cache: " + cache.get(0) + " | " + cache.get(1) + " | " + cache.get(2) + " - Page fault: " + pageFault;
	}

	public int getPageFault() {
		return pageFault;
	}
}
