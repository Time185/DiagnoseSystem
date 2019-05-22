package time.web.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


import com.itextpdf.text.DocumentException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import time.domain.User;
import time.service.DiagnoseService;
import time.serviceImp.DiagnoseServiceImp;

public class DiagnoseAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private String path;
	// pdf模板
	private String templatePDF;
	// 获取标注图片
	private String image;
	// 算法输入路径
	private String jpgDir;
	// 算法输出路径
	private String resultDir;
	
	public String getJpgDir() {
		return jpgDir;
	}
	public void setJpgDir(String jpgDir) {
		this.jpgDir = jpgDir;
	}
	public String getResultDir() {
		return resultDir;
	}
	public void setResultDir(String resultDir) {
		this.resultDir = resultDir;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTemplatePDF() {
		return templatePDF;
	}
	public void setTemplatePDF(String templatePDF) {
		this.templatePDF = templatePDF;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}	
	
	public String diagnose() throws DocumentException, IOException {
		DiagnoseService diagnose = new DiagnoseServiceImp();
		User user = (User) request.getSession().getAttribute("user");
		// 判断后台算法是否调用成功
		String jpgDir = getJpgDir() + user.getLoginname();
		String resultDir = getResultDir() + user.getLoginname();
		String result = diagnose.diagnose(jpgDir, resultDir);
		// 选出前三个概率最高的切片路径	
		if(result != null) {
			//说明算法执行结束  读取json文件
			String[] imagePath = diagnose.getImagePath(result);
			
			diagnose.producePDF(getTemplatePDF(), result, imagePath, user);
		}
		System.out.println("辅助诊断");
		return SUCCESS;
	}
	public String getResult() {
		User user = (User) request.getSession().getAttribute("user");
		String pdfName = user.getLoginname() + "/" + user.getLoginname() + ".pdf";
		response.setContentType(ServletActionContext.getServletContext().getMimeType(pdfName));		
		response.setHeader("Content-Disposition", "attachment;filename="+ pdfName);
		DiagnoseService diagnose = new DiagnoseServiceImp();
		diagnose.getPDF(getPath() + pdfName,response);
		return null;
	}
	
	public String scanDcm() throws ServletException, IOException {	
		User user = (User) request.getSession().getAttribute("user");
		String resultDir = "/data/leichao/diagnose/result/" + user.getLoginname();
		DiagnoseService diagnose = new DiagnoseServiceImp();
		diagnose.scanDcm(resultDir,request,response);
		return null;
	}
	public String pictureTransport() throws IOException {
		User user = (User) request.getSession().getAttribute("user");
		String pictureName = request.getParameter("name");
		
		System.out.println(pictureName + "in pcitureTransport()");
		
		FileInputStream fileInputStream = new FileInputStream("/data/leichao/diagnose/result/"+ user.getLoginname()+ "/" + pictureName);
		int size = fileInputStream.available();
		byte[] data = new byte[size];
		fileInputStream.read(data);
		response.setContentType("image/*");
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();
		fileInputStream.close();
		return null;
	}
	
	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}
}
