package servlet;

import net.sf.json.JSONArray;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.PlanDataHelper;

/**
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
		System.out.println("获取所有计划," + request.getRemoteAddr());

		int eateryCode = Integer.parseInt(request.getParameter("eateryCode"));
		JSONArray object = JSONArray.fromObject(PlanDataHelper.getPlanList(eateryCode));
		response.getWriter().write(object.toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

		int eateryCode = Integer.parseInt(request.getParameter("eateryCode"));
		JSONArray object = JSONArray.fromObject(PlanDataHelper.getPlanList(eateryCode));
		System.out.println(object.toString());
		response.getWriter().write(object.toString());
	}
}
