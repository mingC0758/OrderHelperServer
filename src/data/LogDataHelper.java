package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.Util;

/**
 * @author mingC
 * @date 2018/7/5
 */
public class LogDataHelper extends BaseDataHelper{
	public static void log(String account, String operation, String result, String ip) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO log(log_time, account, operation, result,ip) VALUES (?,?,?,?,?)");
			statement.setString(1, Util.getDateTimePretty());
			statement.setString(2, account);
			statement.setString(3, operation);
			statement.setString(4, result);
			statement.setString(5, ip);
			int i = statement.executeUpdate();
			if (i == 1) {
				System.out.println(Util.getDateTimePretty() + "日志记录成功！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
}
