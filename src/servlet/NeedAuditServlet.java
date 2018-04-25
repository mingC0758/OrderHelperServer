package servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Requirement;
import bean.ResultBean;
import data.NeedDataHelper;

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

		Type listType = new TypeToken<ArrayList<Requirement>>(){}.getType();
		Gson gson = new Gson();
		ArrayList<Requirement> list = gson.fromJson(request.getReader(), listType);
		System.out.println("需求审核:" + request.getRemoteAddr() + ",参数：" + list);

		boolean res = NeedDataHelper.setNeedAudited(list);
		ResultBean bean;
		if (res) {
			bean = new ResultBean(1, "ok");
		} else {
			bean = new ResultBean(-1, "audit error");
		}
		response.getWriter().write(JSONObject.fromObject(bean).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
