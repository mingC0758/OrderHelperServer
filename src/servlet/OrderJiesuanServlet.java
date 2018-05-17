package servlet;

import net.sf.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ResultBean;
import data.OrderDataHelper;

/**
 * 订单结算
 * @author mingC
 * @date 2018/5/11
 */
public class OrderJiesuanServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		ResultBean resultBean = new ResultBean();
		if (OrderDataHelper.jiesuanOrder(orderId)) {
			resultBean.setCode(1);
			resultBean.setMsg("ok");
		} else {
			resultBean.setCode(-1);
			resultBean.setMsg("update err");
		}
		response.getWriter().write(JSONObject.fromObject(resultBean).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
