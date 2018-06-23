package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import bean.Vender;
import util.Util;

/**
 * @author mingC
 * @date 2018/5/17
 */
public class VenderDataHelper extends BaseDataHelper{
	public static final String RET_OK = "[ok]";
	public static final String RET_Duplicate = "[Duplicate entry]"; //编码重复

	public static String delVender(int venderId) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM vender WHERE id=?");
			statement.setInt(1, venderId);
			if (1 == statement.executeUpdate()) {
				return RET_OK;
			}
			return "delete count != 1";
		} catch (Exception e) {
			e.printStackTrace();
			return "no such vender!";
		} finally {
			closeConnection(connection);
		}
	}

	/**
	 * 插入供应商记录
	 * @return
	 */
	public static String insertVender(Vender vender) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO vender(code, name, create_time, eateryName) VALUES (?, ?, ?, ?)");
			statement.setString(1, vender.getCode());
			statement.setString(2, vender.getName());
			statement.setString(3, Util.getDateTimePretty());
			statement.setString(4, vender.getEateryName());
			if (1 == statement.executeUpdate()) {
				return RET_OK;
			}
			return "insert count != 1";
		} catch (SQLException e) {
			System.out.println("VenderDataHelper:" + e.getMessage());
			return RET_Duplicate;
		} finally {
			closeConnection(connection);
		}
	}

	/**
	 * 获取食堂的对应供应商列表
	 * @return
	 */
	public static List<Vender> getVenderList(String eateryName) {
		List<Vender> list = new LinkedList<>();
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM vender WHERE eateryName=?");
			statement.setString(1, eateryName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Vender vender = new Vender();
				vender.setId(resultSet.getInt("id"));
				vender.setCode(resultSet.getString("code"));
				vender.setName(resultSet.getString("name"));
				vender.setEateryName(resultSet.getString("eateryName"));
				list.add(vender);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return list;
	}
}
