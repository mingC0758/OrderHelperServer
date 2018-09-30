package servlet.flow;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.OrderDataHelper;

/**
 * url参数type：
 *      history:历史订单
 *      dispatched:已发货订单
 *      undispatched:未发货订单
 *      received:已收货订单
 *      single:获取单个订单,orderId:订单号
 * @author mingC
 * @date 2018/4/7
 */
public class GetOrderServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		System.out.println("获取订单post," + request.getRemoteAddr());
		String type = request.getParameter("type");
		/****获取单个订单*****/
		if ("single".equals(type)) {
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			response.getWriter().write(JSONObject.fromObject(OrderDataHelper.getSingleOrder(orderId)).toString());
			return;
		}
		/****获取订单列表***/
		JSONArray object = null;
		if ("history".equals(type)) {
			object = JSONArray.fromObject(OrderDataHelper.getHistoryOrders());
		} else if ("dispatched".equals(type)){
			object = JSONArray.fromObject(OrderDataHelper.getDispatchedOrder());
		} else if ("undispatched".equals(type)) {
			object = JSONArray.fromObject(OrderDataHelper.getUndispatchOrder());
		} else if ("received".equals(type)) {
			object = JSONArray.fromObject(OrderDataHelper.getReceivedOrders());
		}
		if (object == null) {
			response.sendError(400, "参数错误！");
		}
		response.getWriter().write(object.toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
