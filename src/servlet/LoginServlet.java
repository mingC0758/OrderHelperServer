package servlet;

import net.sf.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ResultBean;
import data.UserDataHelper;

/**
 * 登陆
 * @author mingC
 * @date 2018/5/14
 */
public class LoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		String account = request.getParameter("account");
		String password = request.getParameter("password");
		ResultBean resultBean = new ResultBean();
		String ret = UserDataHelper.checkAccount(account, password);
		if (UserDataHelper.CORRECT.equals(ret)) {
			resultBean.setCode(1);
			resultBean.setMsg("ok");
		} else {
			resultBean.setCode(-1);
			resultBean.setMsg(ret);
		}
		response.getWriter().write(JSONObject.fromObject(resultBean).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
