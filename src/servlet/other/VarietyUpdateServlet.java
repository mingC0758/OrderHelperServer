package servlet.other;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ResultBean;
import bean.Variety;
import data.VarietyDataHelper;

/**
 * 更新（新增）品种
 * @author mingC
 * @date 2018/5/23
 */
public class VarietyUpdateServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		System.out.println("提交产品更新..");
		String type = request.getParameter("type");
		Gson gson = new Gson();
		Variety variety = gson.fromJson(request.getReader(), Variety.class);
		if (type.equals("new")) {
			String result = VarietyDataHelper.insertVariety(variety);
			ResultBean resultBean;
			if (result.equals(VarietyDataHelper.RET_OK)) {
				resultBean = new ResultBean(1, "ok");
			} else {
				resultBean = new ResultBean(-1, result);
			}
			response.getWriter().write(JSONObject.fromObject(resultBean).toString());
		} else if (type.equals("update")){
			String result = VarietyDataHelper.updateVariety(variety);
			ResultBean resultBean;
			if (result.equals(VarietyDataHelper.RET_OK)) {
				resultBean = new ResultBean(1, "ok");
			} else {
				resultBean = new ResultBean(-1, result);
			}
			response.getWriter().write(JSONObject.fromObject(resultBean).toString());
		} else if(type.equals("del")) {
			//删除产品
			String result = VarietyDataHelper.delVariety(variety);
			ResultBean resultBean;
			if (result.equals(VarietyDataHelper.RET_OK)) {
				resultBean = new ResultBean(1, "ok");
			} else {
				resultBean = new ResultBean(-1, result);
			}
			response.getWriter().write(JSONObject.fromObject(resultBean).toString());
		} else {
			response.sendError(500, "params error");
		}
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
