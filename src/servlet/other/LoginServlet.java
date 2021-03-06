package servlet.other;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ResultBean;
import data.LogDataHelper;
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
		String token = request.getParameter("token");
		System.out.println("acount:" + account + " token:" + token);
		String ret;
		ResultBean resultBean = new ResultBean();
		if (token != null && ! token.equals("")) {
			//检查token和account即可
			ret = UserDataHelper.checkToken(account, token);
			if (UserDataHelper.CORRECT.equals(ret)) {
				String role = UserDataHelper.getUser(account).getRole();
				resultBean.setCode(1);
				resultBean.setMsg(role);
				LogDataHelper.log(account, "token登陆","成功", request.getRemoteAddr());
			} else {
				resultBean.setCode(-1);
				resultBean.setMsg(ret);
				LogDataHelper.log(account, "token登陆","失败", request.getRemoteAddr());
			}
		} else {
			ret = UserDataHelper.checkAccount(account, password);
			if (UserDataHelper.CORRECT.equals(ret)) {
				LogDataHelper.log(account, "帐号登陆","成功", request.getRemoteAddr());
				//更新token
				String newToken = UUID.randomUUID().toString();
				UserDataHelper.updateToken(account, newToken);
				resultBean.setCode(1);
				resultBean.setMsg(newToken);
			} else {
				LogDataHelper.log(account, "帐号登陆","失败", request.getRemoteAddr());
				resultBean.setCode(-1);
				resultBean.setMsg(ret);
			}
		}

		response.getWriter().write(JSONObject.fromObject(resultBean).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
