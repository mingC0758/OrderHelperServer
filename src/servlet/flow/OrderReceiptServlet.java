package servlet.flow;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.PurchaseOrder;
import bean.ResultBean;
import data.OrderDataHelper;
import util.Util;

/**
 * 收货接口
 * @author mingC
 * @date 2018/4/22
 */
public class OrderReceiptServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		System.out.println(request.getRemoteAddr() + "收货：");

		Gson gson = new Gson();
		PurchaseOrder order = gson.fromJson(request.getReader(), PurchaseOrder.class);
		System.out.println(order);
		boolean result = OrderDataHelper.confirmReceipt(order);
		if (!result) {
			ResultBean bean = new ResultBean(-1, "数据库更新失败");
			response.getWriter().write(JSONObject.fromObject(bean).toString());
		} else {
			ResultBean bean = new ResultBean(1, "ok");
			response.getWriter().write(JSONObject.fromObject(bean).toString());
			//设置收货时间
			OrderDataHelper.setOrderReceiptTime(order.getOrderCode(), Util.getDateTimePretty());
		}
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
