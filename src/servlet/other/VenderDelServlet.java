package servlet.other;

import net.sf.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ResultBean;
import data.VenderDataHelper;

/**
 * 删除供应商
 * @author mingC
 * @date 2018/5/18
 */
public class VenderDelServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		int venderId = Integer.parseInt(request.getParameter("venderId"));
		String ret = VenderDataHelper.delVender(venderId);
		ResultBean resultBean;
		if (ret.equals(VenderDataHelper.RET_OK)) {
			resultBean = new ResultBean(1, "ok");
		} else {
			resultBean = new ResultBean(-1, ret);
		}
		response.getWriter().write(JSONObject.fromObject(resultBean).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
