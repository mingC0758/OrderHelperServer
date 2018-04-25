package servlet;

import net.sf.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ResultBean;
import data.PlanDataHelper;

/**
 * @author mingC
 * @date 2018/3/26
 */
public class AddPlanServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		System.out.println("计划提交，" + request.getRemoteAddr());
		int eateryCode = Integer.parseInt(request.getParameter("eateryCode"));
		String receiverName = request.getParameter("receiverName");
		String receiverAddress = request.getParameter("receiverAddress");
		String receiverPhone = request.getParameter("receiverPhone");
		PlanDataHelper.addPlan(eateryCode, receiverName, receiverAddress, receiverPhone);
//		Gson gson = new Gson();
//		PurchasePlan plan = gson.fromJson(request.getReader(), PurchasePlan.class);
//		System.out.println(request.getRemoteAddr() + "添加计划，参数：" + plan);
		ResultBean bean = new ResultBean(1, "ok");
		response.getWriter().write(JSONObject.fromObject(bean).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
