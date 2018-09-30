package servlet.purchase;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.PriceBean;
import bean.PurchasePlan;
import bean.Requirement;
import bean.ResultBean;
import data.BaseDataHelper;
import data.NeedDataHelper;
import data.PlanDataHelper;
import data.PriceDataHelper;
import util.Util;

/**
 * 功能：通过采购需求、由采购需求生成采购计划、由采购计划、根据供应商名称生成采购订单
 * type:
 *      audit: 通过，并生成计划、切割成订单
 *      noPass: 不通过
 * @author mingC
 * @date 2018/3/26
 */
public class NeedAuditServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		System.out.println("需求审核");
		String type = request.getParameter("type");
		if (type == null || type.equals("")) {
			System.out.println("type为空");
			return;
		}
		if (type.equals("audit")) {
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
				bean = new ResultBean(-1, "审核需求失败！");
			}
			response.getWriter().write(JSONObject.fromObject(bean).toString());
			//保存需求产品价格作为历史价格
			for (Requirement requirement : plan.getRequirementList()) {
				PriceBean priceBean = new PriceBean();
				priceBean.setCreateTime(Util.getDateTimePretty());
				priceBean.setEateryName(plan.getEateryName());
				priceBean.setPrice(requirement.getPrice());
				priceBean.setProductName(requirement.getVarietyName());
				priceBean.setType("history");
				priceBean.setVenderName(requirement.getVenderName());
				PriceDataHelper.addPrice(priceBean);
			}

		} else if (type.equals("noPass")) {
			Gson gson = new Gson();
			Requirement requirement = gson.fromJson(request.getReader(), Requirement.class);
			String ret = NeedDataHelper.setNeedNotPassed(requirement);
			ResultBean resultBean;
			if (ret.equals(BaseDataHelper.RET_OK)) {
				resultBean = new ResultBean(1, "ok");
			} else {
				resultBean = new ResultBean(-1, ret);
			}
			response.getWriter().write(JSONObject.fromObject(resultBean).toString());
		}

	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
