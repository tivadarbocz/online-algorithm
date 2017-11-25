package hu.tb.online.algorithms.paging;

public class PagingAlgorithmFactory {

	public AbstractPagingAlgorithm create(AbstractPagingAlgorithm.ALGORITHM algorithm) {
		switch (algorithm) {
		case FIFO:
			return new FIFO<>();
		case LRU:
			return new LRU<>();
		case LFD:
			return new LFD<>();
		default:
			return new LFD<>();
		}

	}
}
