package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bean.PurchasePlan;
import bean.Requirement;
import util.Util;

/**
 * @author mingC
 * @date 2018/3/26
 */
public class PlanDataHelper extends BaseDataHelper{

	public static void main(String[] args) {
		setPlanAudited(12);
	}

	//添加计划，将审核过的需求作为需求列表
	public static boolean addPlan(int eateryCode, String name, String address, String phone) {
		Connection connection = getConnection();
		try {
			//找到最大id
			ResultSet resultSet = connection.createStatement().executeQuery("SELECT max(id) FROM plan");
			int id = 0;
			if (resultSet.next()) {
				id = resultSet.getInt(1);
			}
			id++;
			PreparedStatement statement = connection.prepareStatement("INSERT INTO plan(id, eateryCode, state, receiverName, receiverPhone, receiverAddress, submit_time, submit_user,eateryName) VALUES (?,?,?,?,?,?,?,?,?)");
			statement.setInt(1, id);
			statement.setInt(2, eateryCode);
			statement.setString(3, "审核中");
			statement.setString(4, name);
			statement.setString(5, phone);
			statement.setString(6, address);
			statement.setString(7, Util.getDateTimePretty());
			statement.setString(8, "张三");
			statement.setString(9, "第三食堂");
			statement.executeUpdate();

			ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT id FROM need WHERE state = '已审核' AND eateryCode = " + eateryCode);
			while (resultSet1.next()) {
				PreparedStatement statement1 = connection.prepareStatement("INSERT INTO plan_need(plan_id, need_id) VALUES (?,?)");
				statement1.setInt(1, id);
				statement1.setInt(2, resultSet1.getInt(1));
				statement1.executeUpdate();
			}

			PreparedStatement statement2 = connection.prepareStatement("UPDATE need SET state = ? WHERE eateryCode = ? AND state = ?");
			statement2.setString(1, "已生成计划");
			statement2.setInt(2, eateryCode);
			statement2.setString(3, "已审核");
			statement2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 获取未审核的计划列表
	 * @param eateryCode 指定计划的编号
	 * @return
	 */
	public static List<PurchasePlan> getUnauditedPlanList(int eateryCode) {
		List<PurchasePlan> planList = new LinkedList<>();
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM plan WHERE eateryCode = ? AND state = ?");
			statement.setInt(1, eateryCode);
			statement.setString(2, "审核中");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				PurchasePlan purchasePlan = new PurchasePlan();
				purchasePlan.setEateryCode(eateryCode);
				purchasePlan.setPlanCode(resultSet.getInt("id"));
				purchasePlan.setReceiveAddress(resultSet.getString("receiverAddress"));
				purchasePlan.setReceiverName(resultSet.getString("receiverName"));
				purchasePlan.setReceiverPhone(resultSet.getString("receiverPhone"));
				purchasePlan.setStatus(resultSet.getString("state"));
				purchasePlan.setSubmitTime(resultSet.getString("submit_time"));
				//继续查找需求列表
				List<Requirement> requirementList = new LinkedList<>();
				PreparedStatement reqStatement = connection.prepareStatement("SELECT * FROM (plan_need,need,variety) WHERE plan_need.plan_id = ? AND plan_need.need_id = need.id AND need.varietyCode=variety.id");
				reqStatement.setInt(1, purchasePlan.getPlanCode());
				ResultSet reqResultSet = reqStatement.executeQuery();
				while (reqResultSet.next()) {
					Requirement requirement = NeedDataHelper.getRequirement(reqResultSet);
					requirementList.add(requirement);
				}
				purchasePlan.setRequirementList(requirementList);
				planList.add(purchasePlan);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return planList;
	}

	/**
	 * @deprecated 需求的resulset有bug
	 * 将计划状态设为“通过”，并切割成订单
	 * @param planCode
	 * @return
	 */
	public static boolean setPlanAudited(int planCode) {
		int count = 0;
		try {
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement("UPDATE plan SET state='通过' WHERE id=?");
			statement.setInt(1, planCode);
			count = statement.executeUpdate();
			//切割订单
			//1.获取计划的所有需求
			PreparedStatement needStat = connection.prepareStatement("SELECT * FROM (plan_need, need, variety) WHERE plan_need.plan_id=? AND plan_need.need_id=need.id AND variety.id=need.varietyCode");
			needStat.setInt(1, planCode);
			ResultSet resultSet = needStat.executeQuery();
			//2.根据需求品种的供应商分类
			Map<Integer, List<Requirement>> venderMap = new HashMap<>();
			while (resultSet.next()) {
				Requirement requirement = NeedDataHelper.getRequirement(resultSet);
				int venderCode = resultSet.getInt("vender_code");
				if (! venderMap.containsKey(venderCode)) {
					venderMap.put(venderCode, new LinkedList<>());
				}
				venderMap.get(venderCode).add(requirement);
			}
			//3.按每一个供应商创建一个订单
			for (Integer venderCode : venderMap.keySet()) {
				ResultSet rsMax = connection.createStatement().executeQuery("SELECT max(id) FROM purchase_order");
				int maxId = 1;
				if (rsMax.next()) {
					maxId = rsMax.getInt(1) + 1;
				}
				PreparedStatement insertStat = connection.prepareStatement("INSERT INTO purchase_order(id,planId, state, submit_time, venderCode, submit_user) VALUES (?,?,?,?,?,?)");
				insertStat.setInt(1, maxId);
				insertStat.setInt(2, planCode);
				insertStat.setString(3, "待发货");
				insertStat.setString(4, Util.getDateTimePretty());
				insertStat.setInt(5, venderCode);
				insertStat.setString(6, "李四");
				insertStat.executeUpdate();
				//4.创建订单对应的需求
				for (Requirement requirement : venderMap.get(venderCode)) {
					PreparedStatement needInsertStat = connection.prepareStatement("INSERT INTO order_need(order_id, need_id) VALUES (?,?)");
					needInsertStat.setInt(1, maxId);
					needInsertStat.setInt(2, requirement.getNeedId());
					needInsertStat.executeUpdate();
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (count == 1) {
			return true;
		}
		return false;
	}

	public static List<PurchasePlan> getPlanList(int eateryCode) {
		List<PurchasePlan> planList = new LinkedList<>();
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM plan WHERE eateryCode=?");
			statement.setInt(1, eateryCode);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				PurchasePlan purchasePlan = new PurchasePlan();
				purchasePlan.setEateryCode(eateryCode);
				purchasePlan.setPlanCode(resultSet.getInt("id"));
				purchasePlan.setReceiveAddress(resultSet.getString("receiverAddress"));
				purchasePlan.setReceiverName(resultSet.getString("receiverName"));
				purchasePlan.setReceiverPhone(resultSet.getString("receiverPhone"));
				purchasePlan.setStatus(resultSet.getString("state"));
				purchasePlan.setSubmitTime(resultSet.getString("submit_time"));
				//继续查找需求列表
				List<Requirement> requirementList = new LinkedList<>();
				PreparedStatement reqStatement = connection.prepareStatement("SELECT * FROM (plan_need,need) WHERE plan_need.plan_id = ? AND plan_need.need_id = need.id");
				reqStatement.setInt(1, purchasePlan.getPlanCode());
				ResultSet reqResultSet = reqStatement.executeQuery();
				while (reqResultSet.next()) {
					Requirement requirement = new Requirement();
					requirement.setAmount(reqResultSet.getInt("amount"));
					requirement.setEateryCode(reqResultSet.getInt("eateryCode"));
					requirement.setNeedId(reqResultSet.getInt("id"));
					requirement.setPrice(reqResultSet.getDouble("price"));
					requirement.setSpecification(reqResultSet.getString("specification"));
					requirement.setVarietyCode(reqResultSet.getInt("varietyCode"));
					requirement.setVarietyName(reqResultSet.getString("varietyName"));
					requirementList.add(requirement);
				}
				purchasePlan.setRequirementList(requirementList);
				planList.add(purchasePlan);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return planList;
	}
}
