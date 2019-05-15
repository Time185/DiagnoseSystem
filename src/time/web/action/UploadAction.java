package time.web.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class UploadAction extends ActionSupport {
	private File[] upload;
	private String[] uploadFileName;
	private String[] uploadContentType;
	// 在strtus2中配置成员 变量
	private String savePath;
	
	public File[] getUpload() {
		return upload;
	}
	public void setUpload(File[] upload) {
		this.upload = upload;
	}
	public String[] getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String[] uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String[] getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String[] uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
	public String upload() throws IOException {
		ServletActionContext.getRequest().setCharacterEncoding("UTF-8");
		// 获取要上传文件的数组
		File[] files = getUpload();
		if(files != null && files.length > 0) {
			for(int i = 0; i < files.length ; i++) {
				System.out.println(this.getSavePath() + "\\" + getUploadFileName()[i]);
				FileOutputStream fos = new FileOutputStream(this.getSavePath() + "\\" + getUploadFileName()[i]);
				// 建立上传文件输入流
				FileInputStream fis = new FileInputStream(files[i]);
				byte[] buffer = new byte[1024];
				int len = 0;
				while((len = fis.read(buffer)) > 0) {
					fos.write(buffer);
				}
				fos.close();
				fis.close();
			}
		}
	
		return SUCCESS;
	}
	
}
