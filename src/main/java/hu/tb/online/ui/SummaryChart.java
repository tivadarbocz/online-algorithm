package hu.tb.online.ui;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.ChartConfig;
import com.byteowls.vaadin.chartjs.config.PieChartConfig;
import com.byteowls.vaadin.chartjs.data.Dataset;
import com.byteowls.vaadin.chartjs.data.PieDataset;
import com.byteowls.vaadin.chartjs.utils.ColorUtils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;
import hu.tb.online.algorithms.random.BinomialRandom;
import hu.tb.online.algorithms.random.PoissonRandom;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SummaryChart {
	private ChartJs chart;

	public VerticalLayout getSummaryChart() {
		PieChartConfig config = new PieChartConfig();
		config
				.data()
				.labels("FIFO", "LRU", "LFD")
				.addDataset(new PieDataset().label("Dataset 1"))
				.and();

		config.
				options()
				//                .maintainAspectRatio(true)
				.responsive(true)
				.title()
				.display(true)
				.text("Number of page fault")
				.and()
				.animation()
				//.animateScale(true)
				.animateRotate(true)
				.and()
				.done();

		chart = new ChartJs(config);
		chart.setJsLoggingEnabled(true);
		//chart.addClickListener((a, b) -> DemoUtils.notification(a, b, config.data().getDatasets().get(a)));
		//setChartDummyData(chart);
		chart.setHeight("50%");
		chart.setWidth("50%");

		Button refreshButton = new Button("Run algorithms", VaadinIcons.REFRESH);
		ComboBox<Distribution> comboBoxDistribution = new ComboBox<>("Choose distribution");
		initDistibutionComboBox(comboBoxDistribution);
		refreshButton.addClickListener(
				(Button.ClickListener) event -> {
					refreshChartData(chart, comboBoxDistribution.getSelectedItem().get());

				});

		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.addComponents(refreshButton, comboBoxDistribution);
		layout.addComponent(chart);
		layout.setComponentAlignment(refreshButton, Alignment.TOP_CENTER);
		layout.setComponentAlignment(comboBoxDistribution, Alignment.TOP_CENTER);
		layout.setComponentAlignment(chart, Alignment.MIDDLE_CENTER);
		layout.setExpandRatio(chart, 1);

		return layout;
	}

	private void setChartDummyData(ChartJs chart) {
		PieChartConfig config = (PieChartConfig) chart.getConfig();
		List<String> labels = config.data().getLabels();
		for (Dataset<?, ?> ds : config.data().getDatasets()) {
			PieDataset lds = (PieDataset) ds;
			List<Double> data = new ArrayList<>();
			List<String> colors = new ArrayList<>();
			for (int i = 0; i < labels.size(); i++) {
				data.add((double) (Math.round(Math.random() * 100)));
				colors.add(ColorUtils.randomColor(0.7));
			}
			lds.backgroundColor(colors.toArray(new String[colors.size()]));
			lds.dataAsList(data);
		}
		chart.refreshData();
	}

	private void refreshChartData(ChartJs chart, Distribution distribution) {
		AlgorithmOutput.resetPageFaultMap();
		for (int i = 0; i < 1000; ++i) {
			MyVaadinUI.runAllAlgorithm(distribution);
			generateData(chart.getConfig());
			chart.refreshData();
		}
	}

	private void initDistibutionComboBox(ComboBox<Distribution> comboBoxDistribution) {
		Distribution dBinomial = new Distribution(1, "Binomial distribution", new BinomialRandom());
		Distribution dPoisson = new Distribution(1, "Poisson distribution", new PoissonRandom());
		comboBoxDistribution.setItems(dBinomial, dPoisson);
		comboBoxDistribution.setItemCaptionGenerator(Distribution::getName);
		comboBoxDistribution.setEmptySelectionAllowed(false);
		comboBoxDistribution.setSelectedItem(dBinomial);
	}


	private synchronized void generateData(ChartConfig chartConfig) {
		PieChartConfig config = (PieChartConfig) chartConfig;
		List<String> labels = config.data().getLabels();
		for (Dataset<?, ?> ds : config.data().getDatasets()) {
			PieDataset lds = (PieDataset) ds;
			List<Double> data = new ArrayList<>();
			List<String> colors = new ArrayList<>();
			/*for (int i = 0; i < labels.size(); i++) {
				data.add((double) (Math.round(Math.random() * 100)));
				colors.add(ColorUtils.randomColor(0.7));
			}*/

			double sum = (double) AlgorithmOutput.pageFaultMap.get("FIFO") + (double) AlgorithmOutput.pageFaultMap.get("LRU") + (double) AlgorithmOutput.pageFaultMap.get("LFD");
			DecimalFormat df = new DecimalFormat("##.####%");
			for (String s : labels) {
				if (s.contains("FIFO")) {
					MyVaadinUI.fifo.getOutput().setValue(String.valueOf(AlgorithmOutput.pageFaultMap.get("FIFO")));
					labels.set(labels.indexOf(s), "FIFO " + (df.format(AlgorithmOutput.pageFaultMap.get("FIFO") / sum)));
				} else if (s.contains("LRU")) {
					MyVaadinUI.lru.getOutput().setValue(String.valueOf(AlgorithmOutput.pageFaultMap.get("LRU")));
					labels.set(labels.indexOf(s), "LRU " + (df.format(AlgorithmOutput.pageFaultMap.get("LRU") / sum)));
				} else if (s.contains("LFD")) {
					MyVaadinUI.lfd.getOutput().setValue(String.valueOf(AlgorithmOutput.pageFaultMap.get("LFD")));
					labels.set(labels.indexOf(s), "LFD " + (df.format(AlgorithmOutput.pageFaultMap.get("LFD") / sum)));
				}

			}
			data.add((double) AlgorithmOutput.pageFaultMap.get("FIFO"));
			colors.add(ColorUtils.randomColor(0.7));
			data.add((double) AlgorithmOutput.pageFaultMap.get("LRU"));
			colors.add(ColorUtils.randomColor(0.7));
			data.add((double) AlgorithmOutput.pageFaultMap.get("LFD"));
			colors.add(ColorUtils.randomColor(0.7));

			lds.backgroundColor(colors.toArray(new String[colors.size()]));
			lds.dataAsList(data);
		}
	}
}