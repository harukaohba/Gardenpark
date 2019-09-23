package bcmpTest;

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

public class BcmpTest_graph extends Frame implements ActionListener,WindowListener{

	public BcmpTest_graph(BcmpTest_sim bsim,int time, int[] node_index, int classnum,int num,int N) {
		// TODO Auto-generated constructor stub
		addWindowListener(this);
		String title = "";
		if(classnum < num) {
			title = "Closed Queue(class:all)";
		}else {
			title = "ClosedQueue(class:"+num+")";
		}
		this.setTitle(title);
		
		ArrayList<Integer> timequeue[] = bsim.getTimequeue();
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		double allqueue = 0.0;
		for(int i = 0; i < time; i++) {
			if(classnum < num) {//all
				for(int j = 0; j < node_index.length/2; j++) {
					allqueue = 0.0;
					for(int k = 0; k < classnum; k++)allqueue += timequeue[j + k*N].get(i);
					data.addValue(allqueue, node_index[j]+"", i+"");
				}
			}else {
				for(int j = 0; j < node_index.length/2; j++)data.addValue(timequeue[j+N*(num-1)].get(i), node_index[j]+"", i+"");
				/*
				data.addValue(timequeue[1].get(i), node_index[1]+"", i+"");
				data.addValue(timequeue[2].get(i), node_index[2]+"", i+"");
				data.addValue(timequeue[3].get(i), node_index[3]+"", i+"");
				data.addValue(timequeue[4].get(i), node_index[4]+"", i+"");
				data.addValue(timequeue[5].get(i), node_index[5]+"", i+"");
				 */
			}
			
		}
		
		JFreeChart chart = ChartFactory.createLineChart(title,"Time","Customer",data,PlotOrientation.VERTICAL,true,false,false);
		ChartPanel cpanel = new ChartPanel(chart);
	    add(cpanel, BorderLayout.CENTER);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
