package servlet;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ResultBean;
import data.OrderDataHelper;
import util.Constant;

/**
 * 添加订单签名
 * @author mingC
 * @date 2018/4/24
 */
public class AddOrderSignServlet extends HttpServlet {
	// 上传文件存储目录
	private static final String UPLOAD_DIRECTORY = Constant.UPLOAD_PATH;

	// 上传配置
	private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
	private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		//设置utf8编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		System.out.println("上传订单备注图片..");

		//图片相对路劲
		List<String> picPaths = new LinkedList<>();
		//订单号
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		System.out.println("订单号：" + orderId);
		// 检测是否为多媒体上传
		if (!ServletFileUpload.isMultipartContent(request)) {
			// 如果不是则停止
			PrintWriter writer = response.getWriter();
			writer.println("Error: 表单必须包含 enctype=multipart/form-data");
			writer.flush();
			return;
		}

		// 配置上传参数
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		// 设置临时存储目录
		factory.setRepository(new File(getServletContext().getRealPath("./temp")));

		ServletFileUpload upload = new ServletFileUpload(factory);

		// 设置最大文件上传值
		upload.setFileSizeMax(MAX_FILE_SIZE);

		// 设置最大请求值 (包含文件和表单数据)
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// 中文处理
		upload.setHeaderEncoding("UTF-8");

		// 构造临时路径来存储上传的文件
		// 这个路径相对当前应用的目录
		String uploadPath = getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;


		// 如果目录不存在则创建
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			// 解析请求的内容提取文件数据
			@SuppressWarnings("unchecked") List<FileItem> formItems = upload.parseRequest(request);

			if (formItems != null && formItems.size() > 0) {
				// 迭代表单数据
				for (FileItem item : formItems) {
					// 处理不在表单中的字段
					if (!item.isFormField()) {
						//文件表单字段处理
						//						String fileName = new File(item.getName()).getName();
						String fileName = UUID.randomUUID().toString() + ".jpg"; //36位随机序列号
						String filePath = uploadPath + File.separator + fileName;
						File storeFile = new File(filePath);
						// 在控制台输出文件的上传路径
						System.out.println(filePath);
						// 保存文件到硬盘
						item.write(storeFile);
						request.setAttribute("message",
								"文件上传成功!");
						//添加相对路径
						String relativePath = UPLOAD_DIRECTORY + "/" + fileName;
						picPaths.add(relativePath);
					}
				}
			}
			//保存到数据库
			for (String picPath : picPaths) {
				boolean result = OrderDataHelper.insertOrderSign(orderId, picPath);
				if (!result) {
					ResultBean bean = new ResultBean(-1, "插入签名图片失败！");
					response.getWriter().write(JSONObject.fromObject(bean).toString());
				}
			}
		} catch (Exception ex) {
			request.setAttribute("message",
					"错误信息: " + ex.getMessage());
			ResultBean bean = new ResultBean(-1, ex.getMessage());
			response.getWriter().write(JSONObject.fromObject(bean).toString());
		}
		ResultBean bean = new ResultBean(1, "ok");
		response.getWriter().write(JSONObject.fromObject(bean).toString());
	}

	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {

	}
}
