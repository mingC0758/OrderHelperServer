package servlet;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ResultBean;
import bean.Vender;
import data.VenderDataHelper;

/**
 * 添加供应商
 * @author mingC
 * @date 2018/5/18
 */
public class VenderAddServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		Gson gson = new Gson();
		Vender vender = gson.fromJson(request.getReader(), Vender.class);
		String ret = VenderDataHelper.insertVender(vender);
		ResultBean resultBean;
		if (ret.equals(VenderDataHelper.RET_OK)) {
			resultBean = new ResultBean(1, "ok");
		} else if(ret.equals(VenderDataHelper.RET_Duplicate)){
			resultBean = new ResultBean(-1, "编码或名称已经存在！");
		} else {
			resultBean = new ResultBean(-1, ret);
		}
		response.getWriter().write(JSONObject.fromObject(resultBean).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
