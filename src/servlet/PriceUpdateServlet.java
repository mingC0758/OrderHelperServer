package servlet;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.PriceBean;
import bean.ResultBean;
import data.PriceDataHelper;

/**
 * @author mingC
 * @date 2018/6/6
 */
public class PriceUpdateServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		String type = request.getParameter("type");
		boolean result = false;
		if (type.equals("add")) {
			PriceBean priceBean = new Gson().fromJson(request.getReader(), PriceBean.class);
			System.out.println("增加：" + priceBean);
			if (PriceDataHelper.isPriceExist(priceBean)) {
				System.out.println("价格已存在");
				result = true;
			} else {
				result = PriceDataHelper.addPrice(priceBean);
			}
		} else if (type.equals("del")) {
			result = PriceDataHelper.delPrice(Integer.parseInt(request.getParameter("priceId")));
		}
		if (result) {
			response.getWriter().write(JSONObject.fromObject(new ResultBean(1, "ok")).toString());
		} else {
			response.getWriter().write(JSONObject.fromObject(new ResultBean(-1, "err")).toString());
		}
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
