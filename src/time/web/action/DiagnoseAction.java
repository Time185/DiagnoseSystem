package time.web.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.itextpdf.text.DocumentException;
import com.opensymphony.xwork2.ActionSupport;

import time.service.DiagnoseService;
import time.serviceImp.DiagnoseServiceImp;

public class DiagnoseAction extends ActionSupport{
	private String path;
	private String templatePDF;
	private String diagnoseResult;
	private String image;
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
	public String getDiagnoseResult() {
		return diagnoseResult;
	}
	public void setDiagnoseResult(String diagnoseResult) {
		this.diagnoseResult = diagnoseResult;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public String diagnose() throws DocumentException, IOException {
		DiagnoseService diagnose = new DiagnoseServiceImp();
		// 判断后台算法是否调用成功
		//boolean flag = diagnose.diagnose();
		//if(flag)
			
			diagnose.producePDF(getTemplatePDF(), getDiagnoseResult(), getImage());
		System.out.println("辅助诊断");
		return SUCCESS;
	}
	public String getResult() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String pdfName = request.getParameter("result");
		System.out.println(pdfName);
		response.setContentType(ServletActionContext.getServletContext().getMimeType(pdfName));		
		response.setHeader("Content-Disposition", "attachment;filename="+ pdfName);
		DiagnoseService diagnose = new DiagnoseServiceImp();
		diagnose.getPDF("F:/" + pdfName,response);
		return null;
	}
	
	public String scanDcm() {
		System.out.println("切片浏览");
		return null;
	}
}
