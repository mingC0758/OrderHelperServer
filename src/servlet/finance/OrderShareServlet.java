package servlet.finance;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.PurchaseOrder;
import bean.Requirement;
import data.OrderDataHelper;

/**
 * 订单分享接口
 * @author mingC
 * @date 2018/6/23
 */
public class OrderShareServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		int orderId = Integer.parseInt(request.getParameter("orderId"));
		PurchaseOrder order = OrderDataHelper.getSingleOrder(orderId);
		if (order == null) {
			response.getWriter().write("order not found");
			return;
		}
		String html = "";
		html += " <style>\n" + "        table,table tr th, table tr td { border:1px solid #0094ff; }\n" + "        table { text-align: center; border-collapse: collapse; padding:10px;}   \n" + "    </style>";

		html += "<table><tr>";
		html += "<td>订单号</td><td>";
		html += order.getOrderCode();
		html += "</td><td>下单时间</td>";
		html += "<td>" + order.getSubmitTime() + "</td></tr>";
		html += "<tr><td>供应商</td><td>" + order.getVenderName() + "</td>";
		html += "<td>合计</td><td>" + OrderDataHelper.calTotalPrice(order) + "</td></tr>";

		html += "<table><tr><td>产品名称</td><td>规格</td><td>单价</td><td>数量</td><td>小计</td></tr>";
		for (Requirement req : order.getPurchaseList()) {
			html += "<tr><td>" + req.getVarietyName() + "</td>";
			html += "<td>" + req.getSpecification() + "</td>";
			html += "<td>" + req.getPrice() + "</td>";
			html += "<td>" + req.getAmount() + "</td>";
			html += "<td>" + req.getAmount() * req.getPrice() + "</td></tr>";
		}
		html += "</table>";
		response.getWriter().write(html);
	}
}
