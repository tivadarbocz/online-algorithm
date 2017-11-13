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
import com.vaadin.ui.VerticalLayout;

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
				.text("Chart.js Pie Chart (Data Refresh)")
				.and()
				.animation()
				//.animateScale(true)
				.animateRotate(true)
				.and()
				.done();

		chart = new ChartJs(config);
		chart.setJsLoggingEnabled(true);
		//chart.addClickListener((a, b) -> DemoUtils.notification(a, b, config.data().getDatasets().get(a)));
		refreshChartData(chart);
		chart.setHeight("50%");
		chart.setWidth("50%");

		Button refreshButton = new Button("Rerun algorithms", VaadinIcons.REFRESH);
		refreshButton.addClickListener(
				(Button.ClickListener) event -> {
					refreshChartData(chart);

				});

		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.addComponent(refreshButton);
		layout.addComponent(chart);
		layout.setComponentAlignment(refreshButton, Alignment.TOP_CENTER);
		layout.setComponentAlignment(chart, Alignment.MIDDLE_CENTER);
		layout.setExpandRatio(chart, 1);
		return layout;
	}

	protected void refreshChartData(ChartJs chart) {
		MyVaadinUI.runAllAlgorithm();
		generateRandomData(chart.getConfig());
		chart.refreshData();
	}

	private void generateRandomData(ChartConfig chartConfig) {
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

			double sum = (double)AlgorithmOutput.pageFaultMap.get("FIFO") + (double)AlgorithmOutput.pageFaultMap.get("LRU") + (double)AlgorithmOutput.pageFaultMap.get("LFD");
			DecimalFormat df = new DecimalFormat("##.###%");
			for(String s : labels){
				if( s.contains("FIFO")){
					labels.set(labels.indexOf(s),  "FIFO " + (df.format(AlgorithmOutput.pageFaultMap.get("FIFO") / sum)));
				}
				else if( s.contains("LRU")){
					labels.set(labels.indexOf(s),  "LRU " + (df.format(AlgorithmOutput.pageFaultMap.get("LRU") / sum)));
				}
				else if( s.contains("LFD")){
					labels.set(labels.indexOf(s),   "LFD " + (df.format(AlgorithmOutput.pageFaultMap.get("LFD") / sum)));
				}

			}
			data.add((double)AlgorithmOutput.pageFaultMap.get("FIFO"));
			colors.add(ColorUtils.randomColor(0.7));
			data.add((double)AlgorithmOutput.pageFaultMap.get("LRU"));
			colors.add(ColorUtils.randomColor(0.7));
			data.add((double)AlgorithmOutput.pageFaultMap.get("LFD"));
			colors.add(ColorUtils.randomColor(0.7));



			lds.backgroundColor(colors.toArray(new String[colors.size()]));
			lds.dataAsList(data);
		}
	}
}