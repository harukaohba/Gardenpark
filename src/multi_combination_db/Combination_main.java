package multi_combination_db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class Combination_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// DB コネクトっ！
		MySQL mysql = new MySQL();
		MySQL mysqlin = new MySQL();
		// lib 準備
		Combination_lib clib;

		// 総組み合わせ数が入っていないものを抽出
		ResultSet rs = mysql.getCombinations();

		int id, c, maxclassnum;
		int combination_num = 0;
		long time = 0;
		try {
			while (rs.next()) {
				combination_num = 0;
				time = 0;
				id = rs.getInt("id");
				maxclassnum = rs.getInt("n") / rs.getInt("c");// クラス最大数
				System.out.println();
				System.out.println();
				System.out.println(
						"id:" + id + "  c=" + rs.getInt("c") + "  n=" + rs.getInt("n") + "  [制約]クラス最大数=" + maxclassnum);
				System.out.println("Start Calculation");
				c = rs.getInt("c");
				// n を動かしていく
				for (int n = 1; n <= rs.getInt("n"); n++) {
					System.out.println();
					System.out.println(
							"id:" + id + "Start   c=" + rs.getInt("c") + "  n=" + n + "  [制約]クラス最大数=" + maxclassnum);

					long startTime = System.currentTimeMillis();// 処理前の時刻を取得

					clib = new Combination_lib(n, c);
					clib.GetRecursive(n, 0, -1);
					int value[][] = clib.getValue();
					// System.out.println("個数N = " +n);
					// System.out.println("クラス数C = " +c);
					// System.out.println("重複組合せ:個数" +value.length);
					// System.out.println("重複組合せ:" +Arrays.deepToString(value));
					int valuenc[][] = clib.getValueNc(value, maxclassnum);

					long endTime = System.currentTimeMillis();// 処理後の時刻を取得
					time += endTime - startTime;

					// System.out.println("制約(最大数):" +maxclassnum);
					System.out.println("重複組合せ(制約付き):個数" + valuenc.length);
					combination_num += valuenc.length;
					// System.out.println("重複組合せ(制約付き):" +Arrays.deepToString(valuenc));

					// DB inport
					mysqlin.inportCombinationValues(id, n, valuenc);
				}
				// DB inport
				System.out.println("重複組合せ(制約付き)合計数" + combination_num);
				System.out.println("重複組合せ(制約付き)計算:  " + time + "ms");
				mysqlin.inportCombinations(id, combination_num, time * 0.001);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
