package servlet;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Product;

/**
 * @author mingC
 * @date 2018/1/31
 */
public class GetProductsServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		//获取数据
		List<Product> productList = new ArrayList<>(20);
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
			String id = request.getParameter("id");
			if (id != null && id.equals("1"))
			{
				int r = random.nextInt(pro2.length - 1);
				int num = random.nextInt(200);
				productList.add(new Product(pro2[r], num, "公斤"));
			} else {
				int r = random.nextInt(pro1.length - 1);
				int num = random.nextInt(200);
				productList.add(new Product(pro1[r], num, "公斤"));
			}
		}

		Map<String, List> map = new HashMap<>();
		map.put("productList", productList);
		JSONObject object = JSONObject.fromObject(map);
		response.getWriter().write(object.toString());
	}

	static String pro2[] = {"苹果", "香蕉", "西瓜", "提子", "雪梨", "青苹果", "橙", "沙糖桔", "木瓜"};
	static String pro1[] = {"茄子", "牛肉", "西洋菜", "面粉", "鸡蛋", "红萝卜", "土豆", "鸡肉", "猪肉", "青菜", "大米", "小米"};
}
