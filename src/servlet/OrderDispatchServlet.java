package servlet;

import net.sf.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ResultBean;
import data.OrderDataHelper;
import util.Util;

/**
 * 发货接口
 * @author mingC
 * @date 2018/4/8
 */
public class OrderDispatchServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		System.out.println("收发货：id=" + request.getParameter("orderId") + ",type=" + request.getParameter("type"));
		try {
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			int type = Integer.parseInt(request.getParameter("type"));

			boolean result = false;
			if (type == 1) {
				result = OrderDataHelper.updateOrderState(orderId, "已发货");
			}
			ResultBean resultBean = new ResultBean(-1, "发货失败");
			if (result) {
				resultBean.setCode(1);
				resultBean.setMsg("ok");
				OrderDataHelper.setOrderDispatchTime(orderId, Util.getDateTimePretty());
			}
			response.getWriter().write(JSONObject.fromObject(resultBean).toString());
		} catch (NumberFormatException e) {
			ResultBean resultBean = new ResultBean(-1, "参数错误");
			response.getWriter().write(JSONObject.fromObject(resultBean).toString());
		}
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
