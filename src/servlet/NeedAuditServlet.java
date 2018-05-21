package servlet;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.PurchasePlan;
import bean.ResultBean;
import data.NeedDataHelper;
import data.PlanDataHelper;

/**
 * @author mingC
 * @date 2018/3/26
 */
public class NeedAuditServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		Gson gson = new Gson();
		PurchasePlan plan = gson.fromJson(request.getReader(), PurchasePlan.class);
		//设需求为已审核
		boolean res = NeedDataHelper.setNeedAudited(plan.getRequirementList());
		//添加需求到计划中
		boolean res2 = PlanDataHelper.addPlan(plan.getEateryName(), plan.getReceiverName(), plan.getReceiveAddress(), plan.getReceiverPhone());
		ResultBean bean;
		if (res && res2) {
			bean = new ResultBean(1, "ok");
		} else {
			bean = new ResultBean(-1, "审核需求(生成计划)失败！");
		}
		response.getWriter().write(JSONObject.fromObject(bean).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
