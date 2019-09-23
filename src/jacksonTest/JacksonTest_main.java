package jacksonTest;

import java.util.Arrays;

public class JacksonTest_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//これはテスト用
		int time = 50000;
		//P153 例題9-4
		int N = 3;
		double p[][] = {{0,0,1},{0,0,0.6},{0.5,0,0}};
		double lambda[] = {2,1,0};
		double mu[] = {5,4,6};

		//理論値
		JacksonTest_lib jlib = new JacksonTest_lib(N,p,lambda,mu);
		
		//simulation
		JacksonTest_sim jsim = new JacksonTest_sim(N,time,p,lambda,mu);
		System.out.println("Simulation : 系内人数 = "+Arrays.toString(jsim.getSimulation()));
		System.out.println("Simulation : 相関係数行列 = "+Arrays.deepToString(jsim.getCorrelation()));
		
		//グラフ描画
		JacksonTest_graph graph = new JacksonTest_graph(jsim.getQueuelength(),N);
		graph.setBounds(5,5,1000,600);
		graph.setVisible(true);

	}

}
