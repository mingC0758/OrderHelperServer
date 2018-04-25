package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author mingC
 * @date 2018/3/23
 */
public class BaseDataHelper {
	private static final String URL = "jdbc:mysql://localhost:3306/orderHelper?characterEncoding=utf8&useSSL=false";
	private static final String USERNAME = "root";
	private static final String PWD = "momingqi";

	static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PWD);
			conn.setAutoCommit(true);
		} catch (ClassNotFoundException | SQLException e) {
			//连接失败
			e.printStackTrace();
			System.out.println("连接MySQL数据库失败！");
		}
		return conn;
	}
}
