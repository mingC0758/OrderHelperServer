package servlet.purchase;

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
 * 需求获取
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

		String name = request.getParameter("name");
		String role = request.getParameter("role");
		String type = request.getParameter("type");
		if (type == null) {
			return;
		}

		//0代表未审核的需求，1代表所有需求
		List<Requirement> list;
		if (type.equals("unaudited")) {
			list = NeedDataHelper.getUnauditedNeedList(name);
		} else if (type.equals("all")){
			list = NeedDataHelper.getNeedList(name, role);
		} else {
			System.out.println("type参数错误");
			return;
		}
		response.getWriter().write(JSONArray.fromObject(list).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
