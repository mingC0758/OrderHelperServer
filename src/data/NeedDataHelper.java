package data;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.plaf.nimbus.State;

import bean.Requirement;
import bean.WriteOff;
import util.Util;

/**
 * @author mingC
 * @date 2018/3/25
 */
public class NeedDataHelper extends BaseDataHelper{

	/**
	 * 添加需求（未审核）
	 */
	public static boolean insertNeed(Requirement requirement) {
		int count;
		try {
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement("INSERT INTO need(eateryCode, varietyName, specification, varietyCode, amount, price, state, submit_time, actual_amount, store_name) VALUES (?,?,?,?,?,?,?,?,?,?)");
		statement.setInt(1, requirement.getEateryCode());
		statement.setString(2, requirement.getVarietyName());
		statement.setString(3, requirement.getSpecification());
		statement.setInt(4, requirement.getVarietyCode());
		statement.setInt(5, requirement.getAmount());
		statement.setDouble(6, requirement.getPrice());
		statement.setString(7, "审核中");
		statement.setString(8, Util.getDateTimePretty());
		statement.setInt(9, requirement.getAmount());
		statement.setString(10, "档口B");
		count = statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (count == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取某状态下的需求
	 * @param eateryCode
	 * @param state
	 * @return
	 */
	public static List<Requirement> getUnauditedNeed(int eateryCode, String state) {
		List<Requirement> list = new ArrayList<>();
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM need,variety WHERE need.varietyCode=variety.id and state = ? AND eateryCode = ?");
			statement.setString(1, state);
			statement.setInt(2, eateryCode);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Requirement requirement = getRequirement(resultSet);
				list.add(requirement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public static Requirement getRequirement(ResultSet resultSet) throws SQLException {
		Requirement requirement = new Requirement();
		requirement.setNeedId(resultSet.getInt("id"));
		requirement.setAmount(resultSet.getInt("amount"));
		requirement.setPrice(resultSet.getDouble("price"));
		requirement.setEateryCode(resultSet.getInt("eateryCode"));
		requirement.setSpecification(resultSet.getString("specification"));
		requirement.setVarietyCode(resultSet.getInt("varietyCode"));
		requirement.setVarietyName(resultSet.getString("varietyName"));
		requirement.setSubmitTime(resultSet.getString("submit_time"));
		requirement.setImg(resultSet.getString("pic_url"));
		requirement.setActualAmount(resultSet.getInt("actual_amount"));
		requirement.setStoreName(resultSet.getString("store_name"));
		requirement.setWriteOffs(getWriteOffList(requirement.getNeedId()));
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
		}
		return list;
	}

	/**
	 * 将需求状态设为：已审核
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
				if (i != requirements.size()-1) {
					builder.append(',');
				}
			}
			String param = builder.toString();
			System.out.println("拼接结果：" + param);
			statement = connection.prepareStatement("UPDATE need SET state = '已审核' WHERE id IN (" + param + ")");
			int row = statement.executeUpdate();
			System.out.println("影响：" + row);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 查找特定订单下特定产品的冲销记录列表
	 * @param orderId
	 * @param needId
	 * @return
	 */
	public static List<WriteOff> getWriteOffList(int needId) {
		List<WriteOff> list = new LinkedList<>();
		Connection connection = getConnection();
		try {
			PreparedStatement stat = connection.prepareStatement("SELECT * FROM write_off WHERE need_id=?");
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
				list.add(writeOff);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 插入特定订单下特定产品的冲销记录
	 * @param orderId
	 * @param needId
	 * @param writeOff
	 * @return
	 */
	public static boolean insertWriteOff(int needId, WriteOff writeOff) {
		Connection connection = getConnection();
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement("INSERT INTO write_off(need_id, amount, submit_time, reason, submit_user, type) VALUES (?,?,?,?,?,?)");
			stat.setInt(1, needId);
			stat.setInt(2, writeOff.getAmount());
			stat.setString(3, writeOff.getSubmitTime());
			stat.setString(4, writeOff.getReasonText());
			stat.setString(5, writeOff.getSubmitUser());
			stat.setString(6, writeOff.getType());
			int count = stat.executeUpdate();
			if (count == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean updateNeed(int needId, int actualAmount) {
		Connection connection = getConnection();
		try {
			//更新实收数量
			PreparedStatement statNeed = connection.prepareStatement("UPDATE need SET actual_amount=? WHERE id=?");
			statNeed.setInt(1, actualAmount);
			statNeed.setInt(2, needId);
			statNeed.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}



	public static void main(String[] args) {
		List<String> urls = new LinkedList<>();
		urls.add("need=95 图片Url1");
		urls.add("need=95 图片Url2");
		urls.add("need=95 图片Url3");
	}
}
