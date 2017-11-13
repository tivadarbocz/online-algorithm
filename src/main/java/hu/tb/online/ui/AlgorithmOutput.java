package hu.tb.online.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import hu.tb.online.algorithms.AbstractPagingAlgorithm;
import hu.tb.online.algorithms.FIFO;
import hu.tb.online.algorithms.LRU;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AlgorithmOutput extends VerticalLayout {

	private Button button;
	public static List<Integer> list;
	private Label output;
	public static HashMap<String, Integer> pageFaultMap;


	public AlgorithmOutput(AbstractPagingAlgorithm.ALGORITHM alg) {

		init(alg);
		setSizeFull();
		addComponent(button);
		if(output != null){
			addComponent(output);
			setComponentAlignment(output, Alignment.TOP_CENTER);
		}
		setComponentAlignment(button, Alignment.TOP_CENTER);

	}

	private void init(AbstractPagingAlgorithm.ALGORITHM alg) {
		output = new Label();
		pageFaultMap = new HashMap<>();
		initInput();
		switch (alg) {
		case LFD:
			button = new Button("LFD");
			button.addClickListener((Button.ClickListener) event -> {
				//TODO implement
				LRU<Integer> lru2 = new LRU<>();
				lru2.setCacheSize(3);
				lru2.solve(list);
				lru2.printCacheContent();
				pageFaultMap.put("LFD", lru2.getPageFault());
				output.setValue(lru2.getFormattedCache());
			});
			break;
		case LRU:
			button = new Button("LRU");
			button.addClickListener((Button.ClickListener) event -> {
				LRU<Integer> lru = new LRU<>();
				lru.setCacheSize(3);
				lru.solve(list);
				lru.printCacheContent();
				pageFaultMap.put("LRU",lru.getPageFault());
				output.setValue(lru.getFormattedCache());
			});
			break;
		case FIFO:
			button = new Button("FIFO");
			button.addClickListener((Button.ClickListener) event -> {
				FIFO<Integer> fifo = new FIFO<>();
				fifo.setCacheSize(3);
				fifo.solve(list);
				fifo.printCacheContent();
				pageFaultMap.put("FIFO", fifo.getPageFault());
				output.setValue(fifo.getFormattedCache());
			});
			break;
		}

	}

	public static void initInput() {
		list = new ArrayList<>();
		Random random = new Random();
		for(int i = 1; i < 50000; ++i){
			list.add(random.nextInt(5 - 0 + 1) + 0);
		}
		/*list.add(1);
		list.add(2);
		list.add(4);
		list.add(3);
		list.add(4);
		list.add(2);
		list.add(5);*/

		/*list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(2);
		list.add(5);
		list.add(1);
		list.add(2);*/
	}

	public Button getButton() {
		return button;
	}
}

