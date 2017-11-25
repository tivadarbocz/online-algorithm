package hu.tb.online.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import hu.tb.online.algorithms.paging.AbstractPagingAlgorithm;
import hu.tb.online.algorithms.paging.PagingAlgorithmFactory;
import hu.tb.online.algorithms.random.AbstractRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class
AlgorithmOutput extends VerticalLayout {

	private Label title;
	public static List<Integer> list;
	private Label output;
	public static HashMap<String, Integer> pageFaultMap;
	private AbstractPagingAlgorithm<Integer> algorithm;
	private AbstractPagingAlgorithm.ALGORITHM alg;

	public AlgorithmOutput(AbstractPagingAlgorithm.ALGORITHM alg) {
		this.alg = alg;
		init();
		setSizeFull();
		addComponent(title);
		if (output != null) {
			addComponent(output);
			setComponentAlignment(output, Alignment.TOP_CENTER);
		}
		setComponentAlignment(title, Alignment.TOP_CENTER);

	}

	private void init() {
		output = new Label();
		if (pageFaultMap == null) {
			pageFaultMap = new HashMap<>();
		}
		switch (alg) {
		case LFD:
			title = new Label("LFD");
			break;
		case LRU:
			title = new Label("LRU");
			break;
		case FIFO:
			title = new Label("FIFO");
			break;
		}

	}

	public static void initInput(Distribution distribution) {
		list = new ArrayList<>();
		AbstractRandom random = distribution.getRandom();
		for (int i = 1; i < 50000; ++i) {
			//list.add(random.nextInt(5 - 0 + 1) + 0);
			list.add(random.nextRandom());
		}
		/*list.add(1);
		list.add(2);
		list.add(4);
		list.add(3);
		list.add(4);
		list.add(2);
		list.add(5);*/
/*
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(2);
		list.add(5);
		list.add(1);
		list.add(2);*/
	}

	public Label getTitle() {
		return title;
	}

	public Label getOutput() {
		return output;
	}

	public static void resetPageFaultMap() {
		pageFaultMap = new HashMap<>();
	}

	public void run() {

		PagingAlgorithmFactory pagingAlgorithmFactory = new PagingAlgorithmFactory();
		algorithm = pagingAlgorithmFactory.create(alg);
		algorithm.solve(list);
		if (pageFaultMap.get(alg.name()) == null) {
			pageFaultMap.put(alg.name(), algorithm.getPageFault());
		} else {
			pageFaultMap.put(alg.name(), pageFaultMap.get(alg.name()) + algorithm.getPageFault());
		}
		output.setValue(algorithm.getFormattedCache());
	}
}

