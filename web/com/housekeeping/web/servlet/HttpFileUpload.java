package com.housekeeping.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.housekeeping.utils.JsonUtils;
import com.housekeeping.web.common.JsonResponse;

/**
 * Servlet implementation class FileUpload
 */
@WebServlet(description = "文件上传", urlPatterns = { "/FileUpload" })
public class HttpFileUpload extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

	// 内存缓存大小
	private static final int yourMaxMemorySize = 1024 * 1024 * 30;

	// 上传最大文件大小
	private static final long yourMaxRequestSize = 1024 * 1024 * 30;

	// 文件上传目录
	private static final String dir = "upload";

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		PrintWriter out = response.getWriter();
		JsonResponse jsonResponse = new JsonResponse();
		List<String> fileNames = new ArrayList<String>();
		try {
			// 1. 检查请求是否含有上传文件
			if (!ServletFileUpload.isMultipartContent(request)) {
				throw new RuntimeException("只能处理multipart/form-data");
			}

			// 2. 设置保存上传文件的目录
			String uploadDir = getServletContext().getRealPath("/upload");
			if (uploadDir == null) {
				throw new RuntimeException("无法访问存储目录");
			}
			File fUploadDir = new File(uploadDir);
			if (!fUploadDir.exists()) {
				if (!fUploadDir.mkdir()) {
					throw new RuntimeException("无法创建存储目录");
				}
			}

			// 3. 设置临时文件目录
			String tempDirectory = getServletContext().getRealPath(dir);
			if (tempDirectory == null) {
				throw new RuntimeException("无法访问临时存储目录");
			}
			File fTempDirectory = new File(uploadDir);
			if (!fTempDirectory.exists()) {
				if (!fUploadDir.mkdir()) {
					throw new RuntimeException("无法创建临时存储目录");
				}
			}

			// 4. 解析上传数据
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置缓存和缓存目录
			factory.setSizeThreshold(yourMaxMemorySize);
			factory.setRepository(fTempDirectory);

			// 新建一个文件上传句柄
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 设置最大上传大小
			upload.setSizeMax(yourMaxRequestSize);
			//如果这个文件真的很大，你可能会希望向用户报告到底传了多少到服务端，让用户了解上传的过程
			upload.setProgressListener(new FileUploadProgressListener());

			List<?> items = null;
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e1) {
				e1.printStackTrace();
				throw new RuntimeException("解析请求数据失败");
			}

			//一旦解析完成，你需要进一步处理item的列表。
			Iterator<?> iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				//如果是表单数据
				if (item.isFormField()) {
					// processFormField(item);
				}//如果是文件数据
				else {
					try {
						int dotIndex = item.getName().lastIndexOf(".");
						String suffix = "";
						if (dotIndex != -1) {
							suffix = "." + item.getName().substring(dotIndex + 1);
						}
						String fileName = format.format(new Date()) + (int) ((Math.random() * 9 + 1) * 100000) + suffix;
						fileNames.add(fileName);
						File uploadedFile = new File(uploadDir, fileName);
						item.write(uploadedFile);
						item.delete();
					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException("文件上传失败");
					}
				}
			}
			String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/" + dir;
			jsonResponse.setSuccess(true);
			jsonResponse.setParameter("uploadDir", uploadDir);
			jsonResponse.setParameter("url", path);
			jsonResponse.setData("files", fileNames);
			jsonResponse.setMessage("文件上传成功");

		} catch (RuntimeException e) {
			jsonResponse.setSuccess(false);
			jsonResponse.setMessage(e.getMessage());
		}
		String str = JsonUtils.toJson(jsonResponse, false);
		System.out.println(str);
		out.println("<html><body><textarea>" + str + "</textarea></body></html>");
	}

	class FileUploadProgressListener implements ProgressListener {
		@Override
		public void update(long pBytesRead, long pContentLength, int pItems) {
			System.out.println("We are currently reading item " + pItems);
			if (pContentLength == -1) {
				System.out.println("So far, " + pBytesRead + " bytes have been read.");
			} else {
				System.out.println("So far, " + pBytesRead + " of " + pContentLength + " bytes have been read.");
			}
		}
	}

	/*
	 * 将一段字符串追加到一个结果字符串中.如果结果字符串的初始内容不为空,在追加当前这段字符串之前先追加一个
	 * (,).再组合sql语句的查询语句是,经常要用到类似的方法,第一条件前没有"and"而后面的条件前都有
	 * and做连词,如果没有选择第一个条件,第二个条件就变成第一个,一次类推
	 */
	private void makeUplist(StringBuffer result, String fragment) {
		if (fragment != null) {
			if (result.length() != 0) {
				result.append(",");
			}
			result.append(fragment);
		}
	}

	/**
	 * 老方法文件上传
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unused")
	private void oldFileUp(HttpServletRequest request, HttpServletResponse response) throws IOException,
			UnsupportedEncodingException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try {

			// 设置保存上传文件的目录
			String uploadDir = getServletContext().getRealPath("/upload");
			System.out.println(uploadDir);
			if (uploadDir == null) {
				throw new RuntimeException("无法访问存储目录");
			}
			File fUploadDir = new File(uploadDir);
			if (!fUploadDir.exists()) {
				if (!fUploadDir.mkdir()) {
					throw new RuntimeException("无法创建存储目录");
				}
			}

			if (!FileUpload.isMultipartContent(request)) {
				throw new RuntimeException("只能处理multipart/form-data");
			}

			DiskFileUpload fu = new DiskFileUpload();
			// 最多上传200M数据
			fu.setSizeMax(1024 * 1024 * 200);
			// 超过1M的字段数据采用临时文件缓存
			fu.setSizeThreshold(1024 * 1024);
			// 采用默认的临时文件存储位置
			// fu.setRepositoryPath(.fu..);
			// 设置上传的普通字段的名称和文件字段的文件名所采用的字符集编码
			fu.setHeaderEncoding("utf-8");

			// 得到所有的表单字段对象集合
			List<FileItem> fileItems = null;
			try {
				fileItems = fu.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
				throw new RuntimeException("解析数据时出现如下问题:");
			}
			String fileName = "";
			// 处理每个表单字段
			Iterator<FileItem> iterator = fileItems.iterator();
			while (iterator.hasNext()) {
				FileItem fileItem = iterator.next();
				if (fileItem.isFormField()) {
					String content = fileItem.getString("utf-8");
					String fieldName = fileItem.getFieldName();
					request.setAttribute(fieldName, content);
				} else {
					try {
						String pathSrc = fileItem.getName();
						/* 如果用户没有在form表单的文件字段中选择任何文件,那么忽略对该字段的处理 */
						if (pathSrc.trim().equals("")) {
							continue;
						}
						int start = pathSrc.lastIndexOf('\\');
						// pathSrc.substring(start + 1);
						/*String*/fileName = format.format(new Date()) + (int) ((Math.random() * 9 + 1) * 100000);
						File pathDest = new File(uploadDir, fileName);

						fileItem.write(pathDest);
						String fieldName = fileItem.getFieldName();
						request.setAttribute(fieldName, fileName);

					} catch (Exception e) {
						e.printStackTrace(out);
						throw new RuntimeException("存储文件时出现如下问题:");
					}
					// 总是立即删除保存表单字段内容的临时文件
					finally {
						fileItem.delete();
					}
				}
			}

			/*
			 * 将上传文件名组合成"file1,file2"这种形式显示处理,如果没有上传任何文件
			 * ,则显示"无" ,如果只上传了第二个文件,则显示为"file2"
			 */
			StringBuffer fileList = new StringBuffer();
			String file1 = (String) request.getAttribute("file1");
			makeUplist(fileList, file1);
			String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();
			String imgurl = path + "/upload/" + fileName;
			JsonResponse jsonResponse = new JsonResponse();
			jsonResponse.setSuccess(true);
			jsonResponse.setParameter("src", imgurl);
			String str = JsonUtils.toJson(jsonResponse, false);
			System.out.println(str);
			out.println("<html><body><textarea>" + str + "</textarea></body></html>");
		} catch (RuntimeException e) {
			JsonResponse jsonResponse = new JsonResponse();
			jsonResponse.setSuccess(false);
			jsonResponse.setMessage(e.getMessage());
			String str = JsonUtils.toJson(jsonResponse, false);
			System.out.println(str);
			out.println("<html><body><textarea>" + str + "</textarea></body></html>");
		}
	}

}
