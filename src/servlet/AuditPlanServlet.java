package servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ResultBean;
import data.PlanDataHelper;

/**
 * @author mingC
 * @date 2018/4/3
 */
public class AuditPlanServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		System.out.println("计划审核.." + request.getRemoteAddr());

		Type listType = new TypeToken<ArrayList<Integer>>(){}.getType();
		Gson gson = new Gson();
		List<Integer> list = gson.fromJson(request.getReader(), listType);
		//计划编号
		for (Integer integer : list) {
			System.out.println("计划审核：" + integer);
			PlanDataHelper.setPlanAudited(integer);
		}
		//将计划由“审核”状态改为“通过”

		ResultBean resultBean = new ResultBean(1, "ok");
		response.getWriter().write(JSONObject.fromObject(resultBean).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
