package servlet;

import net.sf.json.JSONArray;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ProductBean;
import bean.Variety;
import data.VarietyDataHelper;

/**
 * @author mingC
 * @date 2018/3/23
 */
public class VarietyServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {
		System.out.println("访问Variety：" + request.getRemoteAddr());
		//数据查询
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		String type = request.getParameter("type");
		if (type == null || type.equals("")) {
			System.out.println("type为空");
			return;
		}
		if (type.equals("productBeans")) {
			List<ProductBean> list = VarietyDataHelper.getProductBeanList();
			if (list != null) {
				JSONArray object = JSONArray.fromObject(list);
				response.getWriter().write(object.toString());
			} else {
				response.sendError(500, "MySQL出错");
			}
		} else if (type.equals("varieties")) {
			List<Variety> list = VarietyDataHelper.getVarietyList();
			if (list != null) {
				JSONArray object = JSONArray.fromObject(list);
				response.getWriter().write(object.toString());
			} else {
				response.sendError(500, "MySQL出错");
			}
		}
	}

}
