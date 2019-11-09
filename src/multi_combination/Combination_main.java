package multi_combination;

import java.util.Arrays;

public class Combination_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//c種類のものから重複を許してn個選ぶ時の組合せ
		//cHn = c+n-1Cn
		//2H5 = 6C5 = 6通り(5,0),(4,1),(3,2),(2,3),(1,4),(0,5)
		int n = 50, c = 2; 
		int nc[] = {50,50,50};//各クラスの最大値
		
		Combination_lib clib = new Combination_lib(n,c);
		clib.GetRecursive(n,0, -1);
		int value[][] = clib.getValue();
		System.out.println("個数N = " +n);
		System.out.println("クラス数C = " +c);
		System.out.println("重複組合せ:個数" +value.length);
		System.out.println("重複組合せ:" +Arrays.deepToString(value));
		int valuenc[][] = clib.getValueNc(value, nc);
		System.out.println("制約(最大数):" +Arrays.toString(nc));
		System.out.println("重複組合せ(制約付き):個数" +valuenc.length);
		System.out.println("重複組合せ(制約付き):" +Arrays.deepToString(valuenc));
		
		int N = n + c -1;
		int R = n;
		double sum1 = 0.0;
		double sum2 = 0.0;
		for(int i = N - R + 1; i <= N; i++) {
			sum1 += Math.log(i);
		}
		for(int i = 2; i <= R; i++) {
			sum2 += Math.log(i);
		}
		double S = Math.exp(sum1 - sum2);
		System.out.println(Math.round(S));
		
		
	}

}
