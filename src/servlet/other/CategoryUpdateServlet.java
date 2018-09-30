package servlet.other;

import net.sf.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Category;
import bean.ResultBean;
import data.CategoryDataHelper;

/**
 * @author mingC
 * @date 2018/6/1
 */
public class CategoryUpdateServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		String type = request.getParameter("type");
		boolean result = false;
		if (type.equals("insert")) {
			Category category = new Category(request.getParameter("first"),
					request.getParameter("second"));
			result = CategoryDataHelper.addCategory(category);
		} else if (type.equals("delete")) {
			result = CategoryDataHelper.delCategory(Integer.parseInt(request.getParameter("id")));
		} else {
			result = false;
		}
		ResultBean resultBean;
		if (result) {
			resultBean = new ResultBean(1, "ok");
		} else {
			resultBean = new ResultBean(-1, "err");
		}
		response.getWriter().write(JSONObject.fromObject(resultBean).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
