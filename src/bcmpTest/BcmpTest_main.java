package bcmpTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class BcmpTest_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//これはテスト用：閉鎖型クラスつき待ち行列 
		int time = 50000;
		int N = 12;
		int classnum = 2;
		double p[][] = new double[N*classnum][N*classnum];
		double mu[] = new double[N*classnum];
		int node_index[] = new int[N*classnum];
		String path = "csv/transition_class.csv";
		csvread(path,p,mu,node_index);
		
		//理論値
		System.out.println("理論値 : 使用拠点 " + N +"(クラス数:"+classnum+")"+ Arrays.toString(node_index));
		System.out.println("理論値 : 推移確率 " + Arrays.deepToString(p));
		System.out.println("理論値 : サービス率 " + Arrays.toString(mu));
		
		//simulation：これ間違いらしい
		int people = 100;
		BcmpTest_sim bsim =  new BcmpTest_sim(N,time,p,classnum,mu,people);
		double simulation[][] = bsim.getSimulation();
		double evaluation[][] = bsim.getEvaluation();
		double result[][] = new double[N*classnum][5];
		for(int i = 0; i < result.length; i++) {
			for(int j = 0; j < result[0].length; j++) {
				if(j < 2 ) result[i][j] = simulation[j][i];
				else if (j >= 2) result[i][j] = evaluation[j-2][i];
			}
		}
		System.out.println("Simulation : (平均系内人数, 平均待ち人数) = "+Arrays.deepToString(simulation));
		System.out.println("Simulation : (系内時間,系内時間分散,最大待ち人数) = "+Arrays.deepToString(evaluation));
		System.out.println("Simulation : (平均系内人数, 平均待ち人数,系内時間,系内時間分散,最大待ち人数) = "+Arrays.deepToString(result));
		System.out.println("Simulation : (MaxLengthTime) = "+Arrays.toString(bsim.getMaxlengthtime()));
		System.out.println("Simulation : (時間割合) = "+Arrays.deepToString(bsim.getTimerate()));
		System.out.println("Simulation : (同時時間割合) = "+Arrays.deepToString(bsim.getTimerate2()));
		double[] timeratecap = bsim.getTimeratecap();
		System.out.println("Simulation : (Cap越え時間割合) = "+Arrays.toString(timeratecap));
		System.out.println("Simulation : (相関係数行列) = "+Arrays.deepToString(bsim.getCorrelation()));
		
		
		BcmpTest_graph graph;
		for(int num = 0; num < classnum+1; num++) {
			graph = new BcmpTest_graph(bsim,time,node_index,classnum,num+1,N);
			graph.setBounds(5,5,755,455);
			graph.setVisible(true);
		}		
		
	}

	private static void csvread(String path, double[][] p, double[] mu, int[] node_index) {
		// TODO Auto-generated method stub
		//CSVから取り込み
				try {
				      File f = new File(path);
				      BufferedReader br = new BufferedReader(new FileReader(f));
				 
				      int K = p.length;
				      String[][] data = new String[K][K+3];
				      String line = br.readLine();
				      for (int row = 0; line != null; row++) {
				        data[row] = line.split(",", 0);
				        line = br.readLine();
				      }
				      br.close();

				      // CSVから読み込んだ配列の中身を表示
				      for(int row = 0; row < data.length; row++) {
				        for(int col = 0; col < data[0].length; col++) {
				        		if( col < data[0].length -3 ) {
				        			p[row][col] = Double.parseDouble(data[row][col]);
				        		}
				        		//else if (col == data[0].length -3) lambda[row] = Double.parseDouble(data[row][col]);
				        		else if (col == data[0].length -2) mu[row] = Double.parseDouble(data[row][col]);
				        		else if (col == data[0].length -1) node_index[row] = Integer.parseInt(data[row][col]);
				        }
				      } 

				    } catch (IOException e) {
				      System.out.println(e);
				    }
				//CSVから取り込みここまで
	}

}
