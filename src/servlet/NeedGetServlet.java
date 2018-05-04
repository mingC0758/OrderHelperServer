package servlet;

import net.sf.json.JSONArray;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Requirement;
import data.NeedDataHelper;

/**
 * @author mingC
 * @date 2018/3/25
 */
public class NeedGetServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		int eateryCode = Integer.parseInt(request.getParameter("eateryCode"));
		int type = Integer.parseInt(request.getParameter("type"));
		System.out.println("需求获取：" + request.getRemoteAddr() + "，参数eateryCode:" + eateryCode + ",type=" + type);

		//0代表未审核的需求，1代表所有需求
		List<Requirement> list;
		if (type == 0) {
			list = NeedDataHelper.getNeedList(eateryCode, "审核中");
		} else {
			list = NeedDataHelper.getNeedList(eateryCode);
		}
		response.getWriter().write(JSONArray.fromObject(list).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
