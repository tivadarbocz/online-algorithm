package hu.tb.online.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import hu.tb.online.algorithms.AbstractPagingAlgorithm;
import hu.tb.online.algorithms.FIFO;
import hu.tb.online.algorithms.LFD;
import hu.tb.online.algorithms.LRU;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class
AlgorithmOutput extends VerticalLayout {

	public enum OUTPUT_RESET {YES, NO}

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
		if (pageFaultMap == null) {
			pageFaultMap = new HashMap<>();
		}
		switch (alg) {
		case LFD:
			button = new Button("LFD");
			button.addClickListener((Button.ClickListener) event -> {
				LFD<Integer> lfd = new LFD<>();
				lfd.solve(list);
				if (pageFaultMap.get("LFD") == null) {
					pageFaultMap.put("LFD", lfd.getPageFault());
				} else {
					pageFaultMap.put("LFD", pageFaultMap.get("LFD") + lfd.getPageFault());
				}
				output.setValue(lfd.getFormattedCache());
			});
			break;
		case LRU:
			button = new Button("LRU");
			button.addClickListener((Button.ClickListener) event -> {
				LRU<Integer> lru = new LRU<>();
				lru.solve(list);
				if (pageFaultMap.get("LRU") == null) {
					pageFaultMap.put("LRU", lru.getPageFault());
				} else {
					pageFaultMap.put("LRU", pageFaultMap.get("LRU") + lru.getPageFault());
				}
				output.setValue(lru.getFormattedCache());
			});
			break;
		case FIFO:
			button = new Button("FIFO");
			button.addClickListener((Button.ClickListener) event -> {
				FIFO<Integer> fifo = new FIFO<>();
				fifo.solve(list);
				if (pageFaultMap.get("FIFO") == null) {
					pageFaultMap.put("FIFO", fifo.getPageFault());
				} else {
					pageFaultMap.put("FIFO", pageFaultMap.get("FIFO") + fifo.getPageFault());
				}

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

	public Button getButton() {
		return button;
	}

	public Label getOutput() {
		return output;
	}

	public static void resetPageFaultMap() {
		pageFaultMap = new HashMap<>();
	}
}

