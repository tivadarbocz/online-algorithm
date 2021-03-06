package hu.tb.online.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.*;
import hu.tb.online.algorithms.paging.AbstractPagingAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

@Theme("valo")
@SpringUI
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {
	static AlgorithmOutput fifo;
	static AlgorithmOutput lru;
	static AlgorithmOutput lfd;

	@WebServlet(value = "/*", asyncSupported = true)
	public static class Servlet extends SpringVaadinServlet {
	}

	@WebListener
	public static class MyContextLoaderListener extends ContextLoaderListener {
	}

	@Configuration
	@EnableVaadin
	public static class MyConfiguration {
	}

	@Override
	protected void init(VaadinRequest request) {
		drawUI();
	}

	private void drawUI() {
		Panel panel = new Panel();
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSizeFull();
		GridLayout grid = new GridLayout(3, 3);
		grid.setSizeFull();
		grid.setColumnExpandRatio(0, 1f);
		grid.setColumnExpandRatio(1, 1f);
		grid.setColumnExpandRatio(2, 1f);

		grid.setRowExpandRatio(0, 0.1f);
		grid.setRowExpandRatio(1, 1f);
		grid.setRowExpandRatio(2, 0.3f);
		layout.addComponent(grid);
		panel.setContent(layout);
		setContent(panel);
		Label lblInput = new Label("Input: 50000 element, 1000 test case, k = 3");
		grid.addComponent(lblInput, 0,0, 2, 0);
		grid.setComponentAlignment(lblInput, Alignment.TOP_CENTER);
		fifo = new AlgorithmOutput(AbstractPagingAlgorithm.ALGORITHM.FIFO);
		lru = new AlgorithmOutput(AbstractPagingAlgorithm.ALGORITHM.LRU);
		lfd = new AlgorithmOutput(AbstractPagingAlgorithm.ALGORITHM.LFD);
		grid.addComponent(fifo, 0,1);
		grid.addComponent(lru, 1, 1);
		grid.addComponent(lfd, 2, 1);
		SummaryChart chart = new SummaryChart();

		grid.addComponent(chart.getSummaryChart(), 0, 2, 2, 2);
	}

	public static synchronized void runAllAlgorithm(Distribution distribution) {
		AlgorithmOutput.initInput(distribution);
		fifo.run();
		lru.run();
		lfd.run();

	}
}