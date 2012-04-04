package net.teilin.master.gant;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.teilin.master.validator.Activity;
import net.teilin.master.validator.Crew;
import net.teilin.master.validator.Location;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class GantActivity {
	private static final long serialVersionUID = 3488074583840465632L;
	
	public static IntervalCategoryDataset createDataset(List<Activity> activity) {
		TaskSeries actTask = new TaskSeries("Aktiviteter");
		
		for(int i=0;i<activity.size();i++) {
			int start = activity.get(i).getStart();
			int end = start + activity.get(i).getDuration();
			Task task = new Task(activity.get(i).getName(),makeDate(start,0,2011),makeDate(end,0,2011));
			actTask.add(task);
		}
		
		final TaskSeriesCollection collection = new TaskSeriesCollection();
		
		collection.add(actTask);
		
		return collection;
	}
	
	private static Date makeDate(final int day, final int month, final int year) {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		final Date result = calendar.getTime();
		return result;
	}
	
	private JFreeChart createChart(final IntervalCategoryDataset dataset) {
		final JFreeChart chart = ChartFactory.createGanttChart(
				"GANT skjema",
				"Aktiviteter",
				"Tid",
				dataset,
				true,
				true,
				false);
		return chart;
	}
	
	public void saveChart(JFreeChart chart, String fileLocation) {
		String fileName = fileLocation;
		try {
			ChartUtilities.saveChartAsJPEG(new File(fileName), chart, 800, 600);
		} catch(IOException e) {
			e.printStackTrace();
			System.err.println("Error saving gant chart");
		}
	}
	
	public static void run(List<Activity> activity, String fileName) {
		final GantActivity chartCreator = new GantActivity();
		System.out.println("... Creating Dataset");
		IntervalCategoryDataset dataset = createDataset(activity);
		
		System.out.println("... Creating chart");
		JFreeChart chart = chartCreator.createChart(dataset);
		
		fileName = "gant/" + fileName;
		
		System.out.println("... Saving chart");
		chartCreator.saveChart(chart, fileName);
		
		System.out.println("... Chart is succesfully created and saved");
	}
}
