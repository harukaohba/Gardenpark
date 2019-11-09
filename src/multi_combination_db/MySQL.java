package multi_combination_db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MySQL {

	String driver;// JDBCドライバの登録
	String server, dbname, url, user, password;// データベースの指定
	Connection con;
	Statement stmt;
	Map<String, Object> lng = new HashMap<>();

	public MySQL() {
		this.driver = "org.gjt.mm.mysql.Driver";
		this.server = "mznbcmp.mizunolab.info";
		this.dbname = "mznbcmp";
		this.url = "jdbc:mysql://" + server + "/" + dbname + "?useUnicode=true&characterEncoding=UTF-8";
		this.user = "mznbcmp";
		this.password = "-------------";
		try {
			this.con = DriverManager.getConnection(url, user, password);
			this.stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ResultSet getCombinations() {
		// TODO Auto-generated method stub
		ResultSet rs = null;
		String sql = "SELECT * FROM  `combinations` WHERE  `combination_num` is null";
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;

	}

	public void inportCombinations(int id, int combination_num, double time) {
		// TODO Auto-generated method stub
		StringBuffer buf = new StringBuffer();
		buf.append("UPDATE `combinations` SET `combination_num`=" + combination_num + ", `time`=" + time
				+ " WHERE `id`=" + id + ";");
		String sql = buf.toString();
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void inportCombinationValues(int id, int n, int[][] valuenc) {
		// TODO Auto-generated method stub
		int index = 0;
		StringBuffer buf;
		String msql = null;
		String sql;
		System.out.println("upload start");
		for (int i = 0; i < valuenc.length; i++) {
			// System.out.println((i+1)+"/"+valuenc.length);
			for (int j = 0; j < valuenc[i].length; j++) {
				if (index == 0)
					msql = " (" + id + "," + n + "," + j + "," + valuenc[i][j] + ")";
				if (index != 0)
					msql = msql + ", (" + id + "," + n + "," + j + "," + valuenc[i][j] + ")";
				index++;
				if (index == 100) {
					buf = new StringBuffer();
					buf.append("INSERT INTO `combination_values`(`combination_id`, `n`, `index`, `value`) VALUES "
							+ msql + ";");
					sql = buf.toString();
					try {
						stmt.execute(sql);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					index = 0;
					msql = "";
				}

			}
		}
		if (index != 0) {
			buf = new StringBuffer();
			buf.append(
					"INSERT INTO `combination_values`(`combination_id`, `n`, `index`, `value`) VALUES " + msql + ";");
			sql = buf.toString();
			try {
				stmt.execute(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("upload end");
	}

}
