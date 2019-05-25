package time.web.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import time.domain.User;
import time.service.UploadService;
import time.serviceImp.UploadServiceImp;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	//private String unzipDir = "D:\\upload\\";
	private String unzipDir = "/data/leichao/diagnose/upload/";
	//private String dcmDir = "D:\\dcmDir\\";
	private String dcmDir = "/data/leichao/diagnose/dcmDir/";
	//private String inputDir = "D:\\jpgDir\\";
	private String inputDir = "/data/leichao/diagnose/jpgDir/";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 实现上传压缩包
		response.setContentType("text/html; charset=UTF-8");
		User user = (User) request.getSession().getAttribute("user");
		request.getSession().setAttribute("user", user);
		UploadService uploadService = new UploadServiceImp();
		String uploadName = uploadService.upload(request,response);		
		// 进行解压
		String unzipFile = uploadService.unzip(unzipDir + uploadName, unzipDir + uploadName.substring(0, uploadName.lastIndexOf(".")));		
		// 对文件进行整理  将所有的dcm文件放到同一个目录下面
		uploadService.copy(unzipDir + unzipFile, dcmDir + unzipFile);
		//转换之前删除上次遗留的文件夹
		uploadService.delete(inputDir + user.getLoginname());
		// 将dcm文件转换为jpg文件
		uploadService.dcm2jpg(dcmDir + unzipFile, inputDir + user.getLoginname());
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
