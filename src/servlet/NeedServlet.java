package servlet;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Requirement;
import bean.ResultBean;
import data.NeedDataHelper;

/**
 * @author mingC
 * @date 2018/3/23
 */
public class NeedServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		System.out.println("添加需求：" + request.getRemoteAddr());
		//提交需求
		//设置utf8编码
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

//		int eateryCode = Integer.parseInt(request.getParameter("eateryCode"));
//		int varietyId = Integer.parseInt(request.getParameter("varietyId"));
//		String varietyName = request.getParameter("varietyName");
//		int amount = Integer.parseInt(request.getParameter("amount"));
//		String specification = request.getParameter("specification");
//		Double price = Double.parseDouble(request.getParameter("price"));
		Gson gson = new Gson();
		Requirement requirement = gson.fromJson(request.getReader(), Requirement.class);
		//		Requirement requirement = new Requirement(eateryCode, varietyName, specification, varietyId, amount, price);
		boolean res = NeedDataHelper.insertNeed(requirement);
		if (res) {
			JSONObject object = JSONObject.fromObject(new ResultBean(1, "insert ok"));
			response.getWriter().write(object.toString());
		} else {
			JSONObject object = JSONObject.fromObject(new ResultBean(-1, "insert error"));
			response.getWriter().write(object.toString());
		}
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {
		JSONObject object = JSONObject.fromObject(new ResultBean(-1, "insert error"));
		response.getWriter().write(object.toString());
	}
}
