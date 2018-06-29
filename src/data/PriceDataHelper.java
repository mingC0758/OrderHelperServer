package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import bean.PriceBean;

/**
 * @author mingC
 * @date 2018/6/5
 */
public class PriceDataHelper extends BaseDataHelper{

	/***
	 * 获取今天之后的价格表
	 * @param eateryName
	 * @return
	 */
	public static List<PriceBean> getPriceList(String eateryName) {
		List<PriceBean> list = new LinkedList<>();
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM price WHERE eateryName=?");
			statement.setString(1, eateryName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				PriceBean priceBean = new PriceBean();
				priceBean.setCreateTime(resultSet.getString("createTime"));
				priceBean.setEateryName(resultSet.getString("eateryName"));
				priceBean.setId(resultSet.getInt("id"));
				priceBean.setPrice(resultSet.getDouble("price"));
				priceBean.setType(resultSet.getString("type"));
				priceBean.setProductName(resultSet.getString("productName"));
				priceBean.setVenderName(resultSet.getString("venderName"));
				list.add(priceBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return list;
	}

	public static boolean isPriceExist(PriceBean priceBean) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM price WHERE price=? AND eateryName=? AND venderName=? AND productName=?");
			statement.setDouble(1, priceBean.getPrice());
			statement.setString(2, priceBean.getEateryName());
			statement.setString(3, priceBean.getVenderName());
			statement.setString(4, priceBean.getProductName());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean addPrice(PriceBean priceBean) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO price(productName, venderName, price, type, createTime, eateryName) VALUES (?,?,?,?,?,?)");
			statement.setString(1, priceBean.getProductName());
			statement.setString(2, priceBean.getVenderName());
			statement.setDouble(3, priceBean.getPrice());
			statement.setString(4, priceBean.getType());
			statement.setString(5, priceBean.getCreateTime());
			statement.setString(6, priceBean.getEateryName());
			if (1 == statement.executeUpdate()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return false;
	}

	public static boolean delPrice(int priceId) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(
					"DELETE FROM price WHERE id=?");
			statement.setInt(1, priceId);
			if (1 == statement.executeUpdate()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return false;
	}
}
