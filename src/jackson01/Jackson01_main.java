package jackson01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Jackson01_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int time = 50000;
		int N = 12;
		double p[][] = new double[N][N];
		double lambda[] = new double[N];
		double mu[] = new double[N];
		int node_index[] = new int[N];
		String path = "csv/distance_jacksonTest.csv";
		csvread(path,N,p,lambda,mu,node_index);//csvからの値取り込み
		
		//理論値
		System.out.println("理論値 : 使用拠点 " + N + Arrays.toString(node_index));
		System.out.println("理論値 : 推移確率 " + Arrays.deepToString(p));
		System.out.println("理論値 : 到着率(外部) " + Arrays.toString(lambda));
		System.out.println("理論値 : サービス率 " + Arrays.toString(mu));
		Jackson01_lib jlib = new Jackson01_lib(N,p,lambda,mu);
		
		//simulation
		Jackson01_sim jsim = new Jackson01_sim(N,time,p,lambda,mu);
		System.out.println("Simulation : 系内人数 = "+Arrays.toString(jsim.getSimulation()));
		System.out.println("Simulation : 相関係数行列 = "+Arrays.deepToString(jsim.getCorrelation()));
				
		//グラフ描画
		Jackson01_graph graph = new Jackson01_graph(jsim.getQueuelength(),N,node_index);
		graph.setBounds(5,5,1000,600);
		graph.setVisible(true);

	}

	private static void csvread(String path, int N, double[][] p, double[] lambda, double[] mu, int[] node_index) {
		// TODO Auto-generated method stub
		int row = N;
		int column = N+3;
		try {
			File f = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(f));
				 
			String[][] data = new String[row][column]; //今回は[K][K+3]
			String line = br.readLine();
			for (int i = 0; line != null; i++) {
				data[i] = line.split(",", 0);
				line = br.readLine();
			}
			br.close();
			
			// CSVから読み込んだ配列の中身を表示
		      for(int rowindex = 0; rowindex < data.length; rowindex++) {
		        for(int col = 0; col < data[0].length; col++) {
		        		if( col < data[0].length -3 ) {
		        			p[rowindex][col] = Double.parseDouble(data[rowindex][col]);
		        		}
		        		else if (col == data[0].length -3) lambda[rowindex] = Double.parseDouble(data[rowindex][col]);
		        		else if (col == data[0].length -2) mu[rowindex] = Double.parseDouble(data[rowindex][col]);
		        		else if (col == data[0].length -1) node_index[rowindex] = Integer.parseInt(data[rowindex][col]);
		        }
		      }
			
		} catch (IOException e) {
			System.out.println(e);
		}	   
	}


}
