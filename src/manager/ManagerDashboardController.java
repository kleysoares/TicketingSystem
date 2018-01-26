package manager;

/*
 * barGraph references (adapted to this class):
 * 
 * TutorialsPoint. 2017. JFreeChart Bar Chart. [ONLINE] Available at: https://www.tutorialspoint.com/jfreechart/jfreechart_bar_chart.htm. [Accessed 17 November 2017].
 * Java2x. 2017. JFreeChart: Bar Chart Demo 1. [ONLINE] Available at: http://www.java2s.com/Code/Java/Chart/JFreeChartBarChartDemo1.htm. [Accessed 18 November 2017.
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.data.category.*;

public class ManagerDashboardController implements ActionListener {
	JFreeChart barGraph;
	ChartPanel chart;
	
	private ManagerDashboard controller;

	public ManagerDashboardController(ManagerDashboard controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("refresh"))
			refreshInfo();
		else
			if(e.getActionCommand().equals("tl"))
				new TicketLifetime();
			else
				if(e.getActionCommand().equals("exit")) {
					int option = JOptionPane.showConfirmDialog(controller, "Are you sure you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
					if(option == JOptionPane.YES_OPTION)
						System.exit(0);
				}
	}
	
	public ChartPanel barGraph() {
		barGraph = ChartFactory.createBarChart("Ticketing System Reports", "", "", graphData(), PlotOrientation.HORIZONTAL, true, true, false);
		chart = new ChartPanel(barGraph);
		chart.setRefreshBuffer(true);
		chart.setPreferredSize(new Dimension(1100, 500));

		// get a reference to the plot for further customisation...
		CategoryPlot plot = barGraph.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.white);

		// set the range axis to display integers only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// disable bar outlines...
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);

		//gradient colour for open tickets
		GradientPaint open = new GradientPaint(0.0f, 0.0f, Color.red, 0.0f, 0.0f, new Color(0, 0, 64));
		//gradient colour for closed tickets
		GradientPaint closed = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f, 0.0f, new Color(0, 64, 0));

		renderer.setSeriesPaint(0, open);
		renderer.setSeriesPaint(1, closed);

		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));

		return chart;
	}

	public CategoryDataset graphData() {
		String james = "JAMES";
		String johan = "JOHAN";
		String john = "JOHN";
		String tt = "Total Tickets";
		
		String ot = "Open Tickets";        
		String ct = "Closed Tickets";        
		
		DefaultCategoryDataset data = new DefaultCategoryDataset( );  

		data.setValue(OpenTicketsData.jamesOpenTickets(), ot, james);        
		data.setValue(ClosedTicketsData.jamesClosedTickets(), ct, james);

		data.setValue(OpenTicketsData.johanOpenTickets(), ot, johan);        
		data.setValue(ClosedTicketsData.johanClosedTickets(), ct, johan);        

		data.setValue(OpenTicketsData.johnOpenTickets(), ot, john);        
		data.setValue(ClosedTicketsData.johnClosedTickets(), ct, john);
		
		data.setValue(OpenTicketsData.totalOpenTickets(), ot, tt);
		data.setValue(ClosedTicketsData.totalClosedTickets(), ct, tt);

		return data; 
	}
	
	public void refreshInfo() {
		controller.remove(controller.content);
		controller.remove(controller.info);
		controller.revalidate();
		controller.repaint();
		
		controller.add(controller.graphInfo());
		controller.add(controller.usefulInfo());
		controller.revalidate();
		controller.repaint(); 
	}
}
