package servlet.flow;

import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;

import net.sf.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ResultBean;
import data.OrderDataHelper;
import util.Constant;
import util.Util;

/**
 * 上传/获取订单安全报告
 * @author mingC
 * @date 2018/9/22
 */
public class SecureServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");

		try {
			//初始化
			SmartUpload smartUpload = new SmartUpload();
			smartUpload.initialize(this.getServletConfig(), request, response);
			smartUpload.upload("utf-8");
			//获取参数
			int orderId = Integer.parseInt(smartUpload.getRequest().getParameter("orderId"));
			//获取文件信息并保存
			Files files = smartUpload.getFiles();
			com.jspsmart.upload.File file = files.getFile(0);
			System.out.println("fileName:" + file.getFileName());
			System.out.println("fieldName:" + file.getFieldName());
			System.out.println("上传文件大小：" + Util.fileSizeConver(file.getSize()));
			//获取随机字符串
			String name = UUID.randomUUID().toString();
			//保存路径
			String savePath = this.getServletContext().getRealPath(
					"./") + File.separator + Constant.UPLOAD_PATH + File.separator + name + ".jpg";
			File file1 = new File(savePath).getParentFile();
			if (! file1.exists()) {
				file1.mkdirs();
			}
			//保存
			System.out.println(savePath);
			file.saveAs(savePath);

			System.out.println("保存成功：" + savePath);
			String webUrl = Constant.BASE_URL + Constant.UPLOAD_PATH + "/" + name + ".jpg";
			OrderDataHelper.addSecurePic(orderId, webUrl);
			ResultBean resultBean = new ResultBean(1, "ok");
			response.getWriter().write(JSONObject.fromObject(resultBean).toString());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("保存失败！");
			ResultBean resultBean = new ResultBean(-1, e.getMessage());
			response.getWriter().write(JSONObject.fromObject(resultBean).toString());
		}
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {
		//获取安全报告图片url
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		String path = OrderDataHelper.getSecurePic(orderId);
		if (path.equals("")) {
			response.sendError(404, "安全报告不存在");
			return;
		}
		ResultBean resultBean = new ResultBean(1, path);
		response.getWriter().write(JSONObject.fromObject(resultBean).toString());
	}

	@Override
	protected void doDelete(HttpServletRequest req,
	                        HttpServletResponse resp) throws ServletException, IOException {

	}
}
