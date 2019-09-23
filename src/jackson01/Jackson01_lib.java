package jackson01;

import java.util.Arrays;

public class Jackson01_lib {

	private int N;
	private double p[][];
	private double[] lambda, mu;
	private double[] alpha, rho, L, R;
	
	public Jackson01_lib(int N, double[][] p, double[] lambda, double[] mu) {
		// TODO Auto-generated constructor stub
		this.N = N;
		this.p = p;
		this.lambda = lambda;
		this.mu = mu;
		alpha = new double[N];
		rho = new double[N];
		L = new double[N];
		calAlpha();//到着率
		calrho();//rho
		calL();//平均系内人数
	}

	private void calL() {
		// TODO Auto-generated method stub
		for(int i = 0; i < N; i++) L[i] = rho[i] / (1 - rho[i]);
		System.out.println("理論値 : 平均系内人数 " + Arrays.toString(L));
	}

	private void calrho() {
		// TODO Auto-generated method stub
		for(int i = 0; i < N; i++) rho[i] = alpha[i] / mu[i];
		System.out.println("理論値 : rho " + Arrays.toString(rho));
	}

	//到着率αを求めます
	private void calAlpha() {
		// TODO Auto-generated method stub
		//[p^t-E]α = -λ の　[p^t-E]
		double pt[][] = new double[N][N];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				pt[i][j] = p[j][i];
				if(i == j)pt[i][j]-=1;
			}
		}
		//[p^t-E]α = -λ の　-lambda
		double mlambda[] = new double[N];
		for(int i = 0; i < N; i++) mlambda[i] = -lambda[i];
		//calcGauss
		alpha = calcGauss(pt,mlambda);
		System.out.println("理論値 : 到着率 " + Arrays.toString(alpha));
		
	}
	
	private double[] calcGauss(double[][] a, double[] b) {
		// TODO Auto-generated method stub
		int p;
		int N = b.length;
		double pmax, s;
		double w[] = new double[N];
		/* 前進消去（ピボット選択）*/
		for(int k = 0; k < N-1; k++){  /* 第ｋステップ */
		      p = k;
		      pmax = Math.abs( a[k][k] );
		      for(int i = k+1; i < N; i++){  /* ピボット選択 */
		         if(Math.abs( a[i][k] ) > pmax){
		            p = i;
		            pmax = Math.abs( a[i][k] );
		         }
		      }

		      if(p != k){  /* 第ｋ行と第ｐ行の交換　*/
		         for(int i = k; i < N; i++){
		            /* 係数行列　*/
		            s = a[k][i];
		            a[k][i] = a[p][i];
		            a[p][i] = s;
		         }
		         /* 既知ベクトル */
		         s = b[k];
		         b[k] = b[p];
		         b[p] = s;
		      }
		/* 前進消去 */
		      for(int i = k +1; i < N; i++){ /* 第ｉ行 */
		         w[i] = a[i][k] / a[k][k];
		         a[i][k] = 0.0;
		         /* 第ｋ行を-a[i][k]/a[k][k]倍して、第ｉ行に加える */
		         for(int j = k + 1; j < N; j++){
		            a[i][j] = a[i][j] - a[k][j] * w[i];
		         }
		         b[i] = b[i] - b[k] * w[i];
		      }
		   }
		/* 後退代入 */
		      for(int i = N - 1; i >= 0; i--){
		         for(int j = i + 1; j < N; j++){
		            b[i] = b[i] - a[i][j] * b[j];
		            a[i][j] = 0.0;
		         }
		         b[i] = b[i] / a[i][i];
		         a[i][i] = 1.0;
		      }
		
		return b;
	}


}
