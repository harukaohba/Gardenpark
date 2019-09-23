package jacksonTest;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class JacksonTest_graph extends Frame implements ActionListener,WindowListener{

	ArrayList<Integer> queuelength[];
	
	public JacksonTest_graph(ArrayList<Integer>[] queuelength, int N) {
		// TODO Auto-generated constructor stub
		addWindowListener(this);
		this.setTitle("Jackson Network");
		
		this.queuelength = queuelength;
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		int sum;
		for(int i = 0; i < 1000; i++) {
			sum = 0;
			for(int j = 0; j < N; j++) {
				data.addValue(this.queuelength[j].get(i),j+"", i+"");
				sum += this.queuelength[j].get(i);
			}
			data.addValue(sum,"all", i+"");
		}
	    JFreeChart chart = ChartFactory.createLineChart("Jackson Network","Time","Customer",data,PlotOrientation.VERTICAL,true,false,false);
	    ChartPanel cpanel = new ChartPanel(chart);
	    add(cpanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
