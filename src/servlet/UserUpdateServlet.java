package servlet;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ResultBean;
import bean.User;
import data.UserDataHelper;

/**
 * 如果User.id已经存在则视为更新，否则新增
 * @author mingC
 * @date 2018/5/16
 */
public class UserUpdateServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		String type = request.getParameter("type");
		Gson gson = new Gson();
		User user = gson.fromJson(request.getReader(), User.class);
		String result;
		if ("add".equals(type)) {
			result = UserDataHelper.insertUser(user);
			System.out.println("添加用户：" + user.getName());
		} else if ("del".equals(type)) {
			result = UserDataHelper.delUser(user);
			System.out.println("删除用户：" + user.getName());
		} else {
			result = "Request params error";
		}
		ResultBean resultBean = new ResultBean();
		if (result.equals(UserDataHelper.CORRECT)) {
			resultBean.setCode(1);
			resultBean.setMsg("ok");
		} else {
			resultBean.setCode(-1);
			resultBean.setMsg(result);
		}
		response.getWriter().write(JSONObject.fromObject(resultBean).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
