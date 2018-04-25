package servlet;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ResultBean;
import bean.WriteOff;
import data.NeedDataHelper;

/**
 * 添加冲销记录
 * @author mingC
 * @date 2018/4/23
 */
public class AddWriteOffServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		Gson gson = new Gson();
		WriteOff writeOff = gson.fromJson(request.getReader(), WriteOff.class);
		System.out.println(request.getRemoteAddr() + "添加冲销记录:" + writeOff);
		boolean result = NeedDataHelper.insertWriteOff(writeOff.getNeedId(), writeOff);
		int code;
		String msg;
		if (result) {
			code = 1;
			msg = "ok";
		} else {
			code = -1;
			msg = "插入冲销记录失败！";
		}
		response.getWriter().write(JSONObject.fromObject(new ResultBean(code, msg)).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
