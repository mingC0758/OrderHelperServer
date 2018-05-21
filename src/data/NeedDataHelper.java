package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import bean.Requirement;
import bean.WriteOff;
import util.Util;

/**
 * @author mingC
 * @date 2018/3/25
 */
public class NeedDataHelper extends BaseDataHelper {

	/**
	 * 添加需求（未审核）同时合并相同档口的相同产品需求
	 */
	public static boolean insertNeed(Requirement requirement) {
		Connection connection = getConnection();
		try {
			//先判断该档口的该产品是否存在，若存在则增加数量即可
			PreparedStatement statCheck = connection.prepareStatement(
					"SELECT * FROM need WHERE state='审核中' AND  store_name=? AND varietyCode=?");
			statCheck.setString(1, requirement.getStoreName());
			statCheck.setInt(2, requirement.getVarietyCode());
			ResultSet rsCheck = statCheck.executeQuery();
			if (rsCheck.next()) {
				System.out.println("需求已经存在：" + requirement.getVarietyName());
				int amount = rsCheck.getInt("amount");
				amount += requirement.getAmount();
				int id = rsCheck.getInt("id");
				//更新数量
				PreparedStatement statUpdate = connection.prepareStatement(
						"UPDATE need SET amount=? WHERE id=?");
				statUpdate.setInt(1, amount);
				statUpdate.setInt(2, id);
				statUpdate.executeUpdate();
			} else {
				//不存在相同，插入新需求
				System.out.println("需求不存在，新插入：" + requirement.getVarietyName());
				PreparedStatement statement = connection.prepareStatement(
						"INSERT INTO need(eateryName, varietyName, specification, varietyCode, amount, price, state, submit_time, actual_amount, store_name) VALUES (?,?,?,?,?,?,?,?,?,?)");
				statement.setString(1, requirement.getEateryName());
				statement.setString(2, requirement.getVarietyName());
				statement.setString(3, requirement.getSpecification());
				statement.setInt(4, requirement.getVarietyCode());
				statement.setInt(5, requirement.getAmount());
				statement.setDouble(6, requirement.getPrice());
				statement.setString(7, "审核中");
				statement.setString(8, Util.getDateTimePretty());
				statement.setInt(9, requirement.getAmount());
				statement.setString(10, requirement.getStoreName());
				statement.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection(connection);
		}
		return true;
	}

	/**
	 * 获取食堂所有需求
	 *
	 * @param eateryCode
	 * @return
	 */
	public static List<Requirement> getNeedList(String eateryName) {
		List<Requirement> list = new ArrayList<>();
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM need,variety WHERE need.varietyCode=variety.id AND eateryName = ?");
			statement.setString(1, eateryName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Requirement requirement = getRequirement(resultSet);
				list.add(requirement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeConnection(connection);
		}
		return list;
	}

	/**
	 * 获取某状态下的需求
	 *
	 * @param state
	 * @return
	 */
	public static List<Requirement> getNeedList(String eateryName, String state) {
		List<Requirement> list = new ArrayList<>();
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM need,variety WHERE need.varietyCode=variety.id and state = ? AND eateryName = ?");
			statement.setString(1, state);
			statement.setString(2, eateryName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Requirement requirement = getRequirement(resultSet);
				list.add(requirement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeConnection(connection);
		}
		return list;
	}

	public static Requirement getRequirement(ResultSet resultSet) throws SQLException {
		Requirement requirement = new Requirement();
		requirement.setNeedId(resultSet.getInt("id"));
		requirement.setAmount(resultSet.getInt("amount"));
		requirement.setPrice(resultSet.getDouble("price"));
		requirement.setEateryName(resultSet.getString("eateryName"));
		requirement.setSpecification(resultSet.getString("specification"));
		requirement.setVarietyCode(resultSet.getInt("varietyCode"));
		requirement.setVarietyName(resultSet.getString("varietyName"));
		requirement.setSubmitTime(resultSet.getString("submit_time"));
		requirement.setImg(resultSet.getString("pic_url"));
		requirement.setActualAmount(resultSet.getInt("actual_amount"));
		requirement.setStoreName(resultSet.getString("store_name"));
		requirement.setWriteOffs(getWriteOffList(requirement.getNeedId()));
		requirement.setState(resultSet.getString("state"));
		return requirement;
	}

	public static List<String> getNeedNotePics(int needId) {
		List<String> list = new LinkedList<>();
		Connection connection = getConnection();
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement("SELECT * FROM order_note_pic WHERE order_id=?");
			stat.setInt(1, needId);
			ResultSet resultSet = stat.executeQuery();
			while (resultSet.next()) {
				list.add(resultSet.getString("pic_url"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return list;
	}

	/**
	 * 将需求状态设为：已审核
	 *
	 * @return
	 */
	public static boolean setNeedAudited(List<Requirement> requirements) {
		//传入的为0则直接返回成功
		if (requirements.size() == 0) {
			return true;
		}
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < requirements.size(); i++) {
				builder.append(String.valueOf(requirements.get(i).getNeedId()));
				if (i != requirements.size() - 1) {
					builder.append(',');
				}
			}
			String param = builder.toString();
			System.out.println("拼接结果：" + param);
			statement = connection.prepareStatement(
					"UPDATE need SET state = '已审核' WHERE id IN (" + param + ")");
			int row = statement.executeUpdate();
			System.out.println("影响：" + row);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection(connection);
		}
		return true;
	}

	/**
	 * 查找特定订单下特定产品的冲销记录列表
	 *
	 * @param needId
	 * @return
	 */
	public static List<WriteOff> getWriteOffList(int needId) {
		List<WriteOff> list = new LinkedList<>();
		Connection connection = getConnection();
		try {
			PreparedStatement stat = connection.prepareStatement(
					"SELECT * FROM write_off WHERE need_id=?");
			stat.setInt(1, needId);
			ResultSet resultSet = stat.executeQuery();
			while (resultSet.next()) {
				WriteOff writeOff = new WriteOff();
				writeOff.setAmount(resultSet.getInt("amount"));
				writeOff.setId(resultSet.getInt("id"));
				writeOff.setReasonText(resultSet.getString("reason"));
				writeOff.setSubmitTime(resultSet.getString("submit_time"));
				writeOff.setSubmitUser(resultSet.getString("submit_user"));
				writeOff.setType(resultSet.getString("type"));
				writeOff.setSignPic(resultSet.getString("sign_pic"));
				list.add(writeOff);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}

		return list;
	}

	/**
	 * 插入特定订单下特定产品的冲销记录
	 *
	 * @param orderId
	 * @param needId
	 * @param writeOff
	 * @return
	 */
	public static boolean insertWriteOff(int needId, WriteOff writeOff) {
		Connection connection = getConnection();
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(
					"INSERT INTO write_off(need_id, amount, submit_time, reason, submit_user, type, sign_pic) VALUES (?,?,?,?,?,?,?)");
			stat.setInt(1, needId);
			stat.setInt(2, writeOff.getAmount());
			stat.setString(3, writeOff.getSubmitTime());
			stat.setString(4, writeOff.getReasonText());
			stat.setString(5, writeOff.getSubmitUser());
			stat.setString(6, writeOff.getType());
			stat.setString(7, writeOff.getSignPic());
			int count = stat.executeUpdate();
			if (count == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return false;
	}

	public static boolean updateNeed(int needId, int actualAmount) {
		Connection connection = getConnection();
		try {
			//更新实收数量
			PreparedStatement statNeed = connection.prepareStatement(
					"UPDATE need SET actual_amount=? WHERE id=?");
			statNeed.setInt(1, actualAmount);
			statNeed.setInt(2, needId);
			statNeed.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return false;
	}

}
