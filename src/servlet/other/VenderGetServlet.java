package servlet.other;

import net.sf.json.JSONArray;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Vender;
import data.VenderDataHelper;

/**
 * @author mingC
 * @date 2018/5/17
 */
public class VenderGetServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		List<Vender> list = VenderDataHelper.getVenderList(request.getParameter("eateryName"));
		response.getWriter().write(JSONArray.fromObject(list).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
