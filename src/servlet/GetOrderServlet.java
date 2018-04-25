package servlet;

import net.sf.json.JSONArray;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.OrderDataHelper;

/**
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
		System.out.println("获取所有订单post," + request.getRemoteAddr());
		String type = request.getParameter("type");  //0代表发货显示的，1代表收货显示的
		JSONArray object = null;
		if (type == null) {
			object = JSONArray.fromObject(OrderDataHelper.getHistoryOrders());
		} else if ("0".equals(type)){
			object = JSONArray.fromObject(OrderDataHelper.getDispatchedOrder());
		} else if ("1".equals(type)) {
			object = JSONArray.fromObject(OrderDataHelper.getUndispatchOrder());
		}
		if (object == null) {
			response.sendError(400, "参数错误！");
		}
		System.out.println(object.toString());
		response.getWriter().write(object.toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
