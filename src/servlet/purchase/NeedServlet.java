package servlet.purchase;

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

import bean.Requirement;
import bean.ResultBean;
import data.NeedDataHelper;

/**
 * 需求提交
 *
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
		response.setHeader("content-type", "text/html;charset=UTF-8");

		String type = request.getParameter("type");
		if (null == type || "".equals(type)) {
			//单个需求上传
			Gson gson = new Gson();
			Requirement requirement = gson.fromJson(request.getReader(), Requirement.class);
			boolean res = NeedDataHelper.insertNeed(requirement);
			if (res) {
				JSONObject object = JSONObject.fromObject(new ResultBean(1, "insert ok"));
				response.getWriter().write(object.toString());
			} else {
				JSONObject object = JSONObject.fromObject(new ResultBean(-1, "insert error"));
				response.getWriter().write(object.toString());
			}
		} else {
			//需求列表上传
			Gson gson = new Gson();
			Type listType = new TypeToken<ArrayList<Requirement>>() {}.getType();
			List<Requirement> requirementList = gson.fromJson(request.getReader(), listType);
			for (Requirement requirement : requirementList) {
				boolean res = NeedDataHelper.insertNeed(requirement);
				if (!res) {
					JSONObject object = JSONObject.fromObject(new ResultBean(-1, "insert error"));
					response.getWriter().write(object.toString());
					return;
				}
			}
			JSONObject object = JSONObject.fromObject(new ResultBean(1, "insert ok"));
			response.getWriter().write(object.toString());
		}
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {
		JSONObject object = JSONObject.fromObject(new ResultBean(-1, "insert error"));
		response.getWriter().write(object.toString());
	}
}
