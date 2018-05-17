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

		Gson gson = new Gson();
		User user = gson.fromJson(request.getReader(), User.class);
		boolean result = false;
		if (user.getId() != 0 && UserDataHelper.userExists(user)) {
			result = UserDataHelper.updateUser(user);
		} else {
			result = UserDataHelper.insertUser(user);
		}
		ResultBean resultBean = new ResultBean();
		if (result) {
			resultBean.setCode(1);
			resultBean.setMsg("ok");
		} else {
			resultBean.setCode(-1);
			resultBean.setMsg("err");
		}
		response.getWriter().write(JSONObject.fromObject(resultBean).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
