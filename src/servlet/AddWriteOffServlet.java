package servlet;

import com.google.gson.Gson;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ResultBean;
import bean.WriteOff;
import data.NeedDataHelper;
import util.Constant;
import util.Util;

/**
 * 添加冲销记录
 * @author mingC
 * @date 2018/4/23
 */
public class AddWriteOffServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");

		try {
			//初始化
			SmartUpload smartUpload = new SmartUpload();
			smartUpload.initialize(this.getServletConfig(), request, response);
			smartUpload.upload("utf-8");
			//获取writeOff数据的json字符串
			String json = smartUpload.getRequest().getParameter("writeOff");
			System.out.println("json:" + json);
			//将json转成对象
			WriteOff writeOff = new Gson().fromJson(json, WriteOff.class);
			//获取文件信息并保存
			Files files = smartUpload.getFiles();
			for (int i = 0; i < files.getCount(); i++) {
				com.jspsmart.upload.File file = files.getFile(i);
				System.out.println("fileName:" + file.getFileName());
				System.out.println("fieldName:" + file.getFieldName());
				System.out.println("上传文件大小：" + Util.fileSizeConver(file.getSize()));
				//获取随机字符串
				String name = UUID.randomUUID().toString();
				//保存路径
				String savePath = this.getServletContext().getRealPath(Constant.UPLOAD_PATH) + "/" + name + ".jpg";
				//保存
				file.saveAs(savePath);
				System.out.println("保存成功：" + savePath);
				writeOff.setSignPic(savePath);
			}
			boolean result = NeedDataHelper.insertWriteOff(writeOff.getNeedId(), writeOff);
			if (result) {
				ResultBean resultBean = new ResultBean(1, "ok");
				response.getWriter().write(JSONObject.fromObject(resultBean).toString());
			} else {
				ResultBean resultBean = new ResultBean(-1, "插入数据库失败！");
				response.getWriter().write(JSONObject.fromObject(resultBean).toString());
			}

		} catch (SmartUploadException e) {
			e.printStackTrace();
			System.out.println("保存失败！");
			ResultBean resultBean = new ResultBean(-1, e.getMessage());
			response.getWriter().write(JSONObject.fromObject(resultBean).toString());
		}

//		Gson gson = new Gson();
//		WriteOff writeOff = gson.fromJson(request.getReader(), WriteOff.class);
//		System.out.println(request.getRemoteAddr() + "添加冲销记录:" + writeOff);
//		boolean result = NeedDataHelper.insertWriteOff(writeOff.getNeedId(), writeOff);
//		int code;
//		String msg;
//		if (result) {
//			code = 1;
//			msg = "ok";
//		} else {
//			code = -1;
//			msg = "插入冲销记录失败！";
//		}
//		response.getWriter().write(JSONObject.fromObject(new ResultBean(code, msg)).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
