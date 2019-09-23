package jacksonTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class JacksonTest_sim {

	private int N, time;
	private double p[][], lambda[], mu[], alpha[];
	Random rnd = new Random();
	ArrayList<Integer> queuelength[]; 
	ArrayList<Integer> timequeue[];
	private double correlation[][];
	
	public JacksonTest_sim(int n, int time, double[][] p, double[] lambda, double[] mu) {
		N = n;
		this.time = time;
		this.p = p;
		this.lambda = lambda;
		this.mu = mu;
		queuelength = new ArrayList[N];
		for(int i = 0; i < queuelength.length; i++) queuelength[i] = new ArrayList<Integer>();
		alpha = new double[N];
		timequeue = new ArrayList[N];
		for(int i = 0; i < timequeue.length; i++) timequeue[i] = new ArrayList<Integer>();
		correlation = new double[N][N];
	}
	
	public double[] getSimulation() {
		double elapse = 0;
		double arrival[] = new double[N];
		double service[] = new double[N];
		double total_queue[] = new double[N]; //延べ系内人数
		int queue[] = new int[N]; //現在の系内人数(サービス中も含む)
		double min_arrival, min_service;
		int arrival_index, service_index;
		double sum_p,dep_p;
		int dep_index;
		//シミュレーション前処理
		for(int i = 0; i < N; i++) {
			arrival[i] = this.getExponential(lambda[i]);
			service[i] = arrival[i] + this.getExponential(mu[i]);
		}
//		System.out.println("Simulation : 開始前Arrival = "+Arrays.toString(arrival));
//		System.out.println("Simulation : 開始前Service = "+Arrays.toString(service));
		//シミュレーション実施
		while(elapse < time) {
			//最小値の算出(次のイベント)
			min_arrival = 100;
			min_service = 100;
			arrival_index = N; 
			service_index = N;
			for(int i = 0; i < N; i++) {
				if(min_arrival > arrival[i]) {
					min_arrival = arrival[i];
					arrival_index = i;
				}
				if(min_service > service[i]) {
					min_service = service[i];
					service_index = i;
				}
			}
			if( min_arrival < min_service ) { //外部or内部到着が発生
//				System.out.println("Arrival");
				for(int i = 0; i < N; i++) {
					total_queue[i] += queue[i] * min_arrival; //延べ系内人数
					service[i] -= min_arrival;
					arrival[i] -= min_arrival;
				}
				queue[arrival_index]++; //現在の系内人数
				arrival[arrival_index] = this.getExponential(lambda[arrival_index]);
				elapse += min_arrival;
//				System.out.println("Index = "+ arrival_index);
				for(int i = 0; i < N; i++) queuelength[i].add(queue[i]);
				alpha[arrival_index] ++;
			}
			else if(min_arrival >= min_service ){ //退去が発生
//				System.out.println("Departure");
				for(int i = 0; i < N; i++) {
					total_queue[i] += queue[i] * min_service; //延べ系内人数
					arrival[i] -= min_service;
					service[i] -= min_service;
				}
				queue[service_index]--; //現在の系内人数
				if(queue[service_index] > 0) service[service_index] = this.getExponential(mu[service_index]);
				else service[service_index] = arrival[service_index] + this.getExponential(mu[service_index]);
				
				elapse += min_service;
//				System.out.println("Index = "+ service_index);
				for(int i = 0; i < N; i++) queuelength[i].add(queue[i]);
				//退去する客の行き先決定
				sum_p = 0;
				dep_p = rnd.nextDouble();
				dep_index = N;
				for(int i = 0; i < N; i++) {
					sum_p += p[service_index][i];
					if(dep_p < sum_p) {
						dep_index = i;
						break;
					}
				}
				if(dep_index != N) {
					arrival[dep_index] = 0; //行き先は到着が発生, Nの時は外部へ退去
					service[dep_index] = this.getExponential(mu[dep_index]); //サービス時間を再設定
				}
			}
			//イベント時間での待ち人数を登録
			for(int i = 0; i < N; i++) timequeue[i].add(queue[i]);
			
//			System.out.println("Elapse = "+elapse);
//			System.out.println("Simulation : 系内人数 = "+Arrays.toString(queue));
//			System.out.println("Simulation : Arrival = "+Arrays.toString(arrival));
//			System.out.println("Simulation : Service = "+Arrays.toString(service));
		}
		for(int i = 0; i < N; i++) alpha[i] /= time;
//		System.out.println("Simulation : Alpha = "+Arrays.toString(alpha));
		
		for(int i = 0; i < N; i++) total_queue[i] = total_queue[i] / time;
		return total_queue;
	}
	
	public double getExponential(double param) {
		if(param == 0) return 100;
		else return - Math.log(1 - rnd.nextDouble()) / param;
	}
	
	public double[][] getCorrelation(){//相関係数行列の作成
		//ArrayListから配列へ変換
		double elapsequeue [][] = new double[timequeue.length][timequeue[0].size()];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < timequeue[0].size(); j++) {
				elapsequeue[i][j] = timequeue[i].get(j);
			}
		}
		//相関係数行列作成
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				//correlation[i][j] = new PearsonsCorrelation().correlation(elapsequeue[i], elapsequeue[j]);
				correlation[i][j] = getCorrelationCoefficient(elapsequeue[i], elapsequeue[j]);
			}
		}
		return correlation;
	}

	private double getCorrelationCoefficient(double[] dx, double[] dy) {
		// TODO Auto-generated method stub
	    //相関係数を求めるために用意する一時的な変数
	    double XAve = 0; //観測値のx 成分の平均値
	    double YAve = 0; //観測値のy 成分の平均値
	    double XVari = 0; //x の分散
	    double YVari = 0; //y の分散
	    double XYVari = 0; //xy の共分散

	    XAve = getAverage(dx);
	    YAve = getAverage(dy);

	    XVari = getVariance(dx,dx,XAve,XAve);
	    YVari = getVariance(dy,dy,YAve,YAve);
	    XYVari = getVariance(dx,dy,XAve,YAve);

	    return XYVari / (Math.sqrt(XVari * YVari));
	}


	private double getVariance(double[] dx, double[] dy, double xAve, double yAve) {
		// TODO Auto-generated method stub
		double tempvalXY = 0.0;
		for(int i =0 ; i < dx.length; i++) {
			 tempvalXY += (dx[i] - xAve) * (dy[i] - yAve);
		}
		return tempvalXY;
	}

	private double getAverage(double[] dx) {
		// TODO Auto-generated method stub
		double sum = 0;
		for(int i =0 ; i < dx.length; i++) {
			sum += dx[i];
		}
		return sum/dx.length;
	}

	public ArrayList<Integer>[] getQueuelength() {
		return queuelength;
	}
}
