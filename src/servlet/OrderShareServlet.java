package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Requirement;
import data.BaseDataHelper;
import data.NeedDataHelper;

/**
 * 订单分享接口
 * @author mingC
 * @date 2018/6/23
 */
public class OrderShareServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		String msg = "订单信息如下：\n产品名\t采购价\t数量\t提交时间";
		try {
			ResultSet rs = BaseDataHelper.getConnection().createStatement().executeQuery("SELECT * FROM need");
			while (rs.next()) {
				Requirement req = NeedDataHelper.rsToRequirement(rs);
				msg += req.getVarietyName() + "\t";
				msg += req.getPrice() + "\t";
				msg += req.getAmount() + "\t";
				msg += req.getSubmitTime() + "\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.getWriter().write(msg);
	}
}
