package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import bean.PurchaseOrder;
import bean.Requirement;
import util.Constant;

/**
 * @author mingC
 * @date 2018/4/8
 */
public class OrderDataHelper extends BaseDataHelper{

	public static void main(String[] args) {
		if (updateOrderState(1, "已发货")) {
			System.out.println("ok");
		}
	}

	/**
	 * 结算订单
	 * @param orderId
	 * @return
	 */
	public static boolean jiesuanOrder(int orderId) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE purchase_order SET state='已结算' WHERE id=?");
			statement.setInt(1, orderId);
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

	/**
	 * 获取历史订单（已结算）
	 * @return 采购订单列表
	 */
	public static List<PurchaseOrder> getHistoryOrders() {
		List<PurchaseOrder> orderList = new LinkedList<>();
		Connection connection = getConnection();
		try {
			PreparedStatement orderStat = connection.prepareStatement("SELECT * FROM (purchase_order,plan) WHERE purchase_order.planId=plan.id AND purchase_order.state='已结算' ORDER BY purchase_order.id DESC");
			ResultSet orderRs = orderStat.executeQuery();
			orderList = RsToOrderList(orderRs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return orderList;
	}

	/**
	 * 获取已收货订单（已收货）
	 * @return 采购订单列表
	 */
	public static List<PurchaseOrder> getReceivedOrders() {
		List<PurchaseOrder> orderList = new LinkedList<>();
		Connection connection = getConnection();
		try {
			PreparedStatement orderStat = connection.prepareStatement("SELECT * FROM (purchase_order,plan) WHERE purchase_order.planId=plan.id AND purchase_order.state='已收货' ORDER BY purchase_order.id DESC");
			ResultSet orderRs = orderStat.executeQuery();
			orderList = RsToOrderList(orderRs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return orderList;
	}

	public static List<PurchaseOrder> getUndispatchOrder() {
		List<PurchaseOrder> orderList = new LinkedList<>();
		Connection connection = getConnection();
		try {
			PreparedStatement orderStat = connection.prepareStatement("SELECT * FROM (purchase_order,plan) WHERE purchase_order.planId=plan.id AND purchase_order.state='待发货' ORDER BY purchase_order.id DESC");
			ResultSet orderRs = orderStat.executeQuery();
			orderList = RsToOrderList(orderRs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return orderList;
	}

	public static List<PurchaseOrder> getDispatchedOrder() {
		List<PurchaseOrder> orderList = new LinkedList<>();
		Connection connection = getConnection();
		try {
			PreparedStatement orderStat = connection.prepareStatement("SELECT * FROM (purchase_order,plan) WHERE purchase_order.planId=plan.id AND purchase_order.state='已发货' ORDER BY purchase_order.id DESC");
			ResultSet orderRs = orderStat.executeQuery();
			orderList = RsToOrderList(orderRs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return orderList;
	}

	/**
	 * 获取订单号对应的唯一订单
	 * @param orderId
	 * @return
	 */
	public static PurchaseOrder getSingleOrder(int orderId) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM purchase_order, plan WHERE purchase_order.planId=plan.id AND purchase_order.id=?");
			statement.setInt(1, orderId);
			ResultSet resultSet = statement.executeQuery();
			return RsToOrderList(resultSet).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return null;
	}

	public static List<PurchaseOrder> RsToOrderList(ResultSet orderRs) throws SQLException {
		Connection connection = getConnection();
		List<PurchaseOrder> orderList = new LinkedList<>();
		while (orderRs.next()) {
			PurchaseOrder order = new PurchaseOrder();
			order.setOrderCode(orderRs.getInt("purchase_order.id"));
			order.setPlanCode(orderRs.getInt("planId"));
			order.setReceiveAddress(orderRs.getString("receiverAddress"));
			order.setReceiverName(orderRs.getString("receiverName"));
			order.setReceiverPhone(orderRs.getString("receiverPhone"));
			order.setStatus(orderRs.getString("purchase_order.state"));
			order.setSubmitTime(orderRs.getString("purchase_order.submit_time"));
			order.setSubmitUser(orderRs.getString("purchase_order.submit_user"));
			order.setEateryName(orderRs.getString("eateryName"));
			order.setNoteText(orderRs.getString("note_text"));
			order.setSignPicUrl(orderRs.getString("sign_url"));
			//获取备注图片
			PreparedStatement statNotePic = connection.prepareStatement("SELECT * FROM order_note_pic WHERE order_id=?");
			statNotePic.setInt(1, order.getOrderCode());
			ResultSet rsPic = statNotePic.executeQuery();
			List<String> urls = new LinkedList<>();
			while (rsPic.next()) {
				String url = rsPic.getString("pic_url");
				urls.add(url);
			}
			order.setNotePicUrls(urls);
			//获取订单对应的采购需求
			PreparedStatement needStat = connection.prepareStatement("SELECT * FROM need,variety WHERE need.varietyCode=variety.id AND need.id IN (SELECT need_id FROM order_need WHERE order_id=?)");
			needStat.setInt(1, order.getOrderCode());
			ResultSet needRs = needStat.executeQuery();
			List<Requirement> requirementList = new LinkedList<>();
			while (needRs.next()) {
				Requirement requirement = NeedDataHelper.getRequirement(needRs);
				requirementList.add(requirement);
			}
			order.setPurchaseList(requirementList);
			orderList.add(order);
		}
		connection.close();
		return orderList;
	}

	/**
	 * 更新订单状态
	 * @param orderId 要更新的订单id
	 * @param newState 新状态
	 * @return true则成功
	 */
	public static boolean updateOrderState(int orderId, String newState) {
		int count = 0;
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE purchase_order SET state=? WHERE id=?");
			statement.setString(1, newState);
			statement.setInt(2, orderId);
			count = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection(connection);
		}
		if (count == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 确认收货（同时更新该订单中产品的实收数目、备注文字和备注图片）
	 * @return
	 */
	public static boolean confirmReceipt(PurchaseOrder order) {
		Connection connection = getConnection();
		updateOrderState(order.getOrderCode(), "已收货");
		//更新订单备注文字和备注图片、和签收图片
		try {
			PreparedStatement statOrder = connection.prepareStatement("UPDATE purchase_order SET note_text=? WHERE id=?");
			statOrder.setString(1, order.getNoteText());
			statOrder.setInt(2, order.getOrderCode());
			if (1 != statOrder.executeUpdate()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		//更新产品实收数目
		boolean result = false;
		for (Requirement requirement : order.getPurchaseList()) {
			result = NeedDataHelper.updateNeed(requirement.getNeedId(), requirement.getActualAmount());
			if (!result) {
				//有一个失败就是失败
				return false;
			}
		}
		return true;
	}

	/**
	 * 插入订单图片
	 * @param orderId
	 * @param picUrl
	 * @return
	 */
	public static boolean insertOrderPics(int orderId, String picUrl) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO order_note_pic(order_id, pic_url) VALUES (?,?)");
			statement.setInt(1, orderId);
			statement.setString(2, Constant.BASE_URL + picUrl);
			return 1 == statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return false;
	}

	public static boolean insertOrderSign(int orderId, String signUrl) {
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE purchase_order SET sign_url=? WHERE id=?");
			statement.setInt(2, orderId);
			statement.setString(1, Constant.BASE_URL + signUrl);
			return 1 == statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return false;
	}
}
