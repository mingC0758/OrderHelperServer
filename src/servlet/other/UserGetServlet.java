package servlet.other;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import data.UserDataHelper;

/**
 * 获取用户信息
 * @author mingC
 * @date 2018/5/14
 */
public class UserGetServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		System.out.println("查询用户");
		String type = request.getParameter("type");
		if (type.equals("single")) {
			//查询单个用户
			User user = UserDataHelper.getUser(request.getParameter("account"));
			System.out.println(user);
			response.getWriter().write(JSONObject.fromObject(user).toString());
		} else if (type.equals("list")) {
			//查询所有用户
			List<User> userList = UserDataHelper.getUserList();
			response.getWriter().write(JSONArray.fromObject(userList).toString());
		}
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
