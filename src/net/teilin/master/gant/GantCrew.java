package net.teilin.master.gant;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.TimePeriodValue;
import org.jfree.ui.ApplicationFrame;

public class GantCrew {
	private static final long serialVersionUID = 3488074583840465632L;
	
	public static IntervalCategoryDataset createDataset(List<Activity> activity, List<Crew> crew) throws ParseException {
		TaskSeriesCollection collection = new TaskSeriesCollection();
		for(int i=0;i<crew.size();i++) {
			String crewName = crew.get(i).getName();
			TaskSeries taskSeriesCrew = new TaskSeries(crewName);
			for(int j=0;j<activity.size();j++) {
				if(activity.get(j).getCrew().equals(crewName)) {
					int start = activity.get(j).getStart();
					//int end = start + activity.get(j).getDuration();
					int dur = activity.get(i).getDuration();
					Date[] period = makeDate(start, dur);
					Task task = new Task(activity.get(j).getName(), period[0], period[1]);
					taskSeriesCrew.add(task);
				}
			}
			collection.add(taskSeriesCrew);
		}
		
		return collection;
	}
	
	private static Date[] makeDate(int start, int dur) throws ParseException {
		Date[] back = new Date[2];
		String nullpoint = "2012-01-01";
		
		SimpleDateFormat sdf = new SimpleDateFormat("D");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(Integer.toString(start)));
		//cal.add(Calendar.DATE, start);
		back[0] = cal.getTime();
		cal.add(Calendar.DATE, dur);
		back[1] = cal.getTime();
		return back;
	}
	
	private JFreeChart createChart(final IntervalCategoryDataset dataset) {
		final JFreeChart chart = ChartFactory.createBarChart(
				"Activities sorted by crew",
				"Aktiviteter",
				"Tid",
				dataset,
				PlotOrientation.HORIZONTAL,
				true,
				false,
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
	
	public static void run(List<Activity> activity, List<Crew> crew, String fileName) throws ParseException {
		final GantCrew chartCreator = new GantCrew();
		System.out.println("... Creating Dataset");
		IntervalCategoryDataset dataset = createDataset(activity, crew);
		
		System.out.println("... Creating chart");
		JFreeChart chart = chartCreator.createChart(dataset);
		
		fileName = "chartcrew/" + fileName;
		
		System.out.println("... Saving chart");
		chartCreator.saveChart(chart, fileName);
		
		System.out.println("... Chart is succesfully created and saved");
	}
}