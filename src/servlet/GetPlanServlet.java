package servlet;

import net.sf.json.JSONArray;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.PlanDataHelper;

/**
 * 获取计划，角色不同返回结果不同：
 *  1、食堂：返回该食堂已提交的计划
 *  2、中心：返回所有计划
 * @author mingC
 * @date 2018/4/9
 */
public class GetPlanServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		System.out.println("获取计划," + request.getRemoteAddr());

		String role = request.getParameter("role");
		String eateryName = request.getParameter("eateryName");
		if (role.equals("中心") || role.equals("食堂")) {
			JSONArray object = JSONArray.fromObject(PlanDataHelper.getPlanList(eateryName, role));
			System.out.println(object.toString());
			response.getWriter().write(object.toString());
		} else {
			System.out.println("参数错误！role=" + role + " eateryName=" + eateryName);
			response.sendError(500, "param err");
		}
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
