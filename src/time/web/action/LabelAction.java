package time.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import time.domain.User;

public class LabelAction extends ActionSupport {
	private static String filezhenduan;
	private static String dcmDir;
	
	
	public String getDcmDir() {
		return dcmDir;
	}

	public void setDcmDir(String dcmDir) {
		this.dcmDir = dcmDir;
	}

	public String getFilezhenduan() {
		return filezhenduan;
	}

	public void setFilezhenduan(String filezhenduan) {
		this.filezhenduan = filezhenduan;
	}

	public String zhenduan() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		
		String name = (String)request.getParameter("name");
		// 需要标记文件的路径
		System.out.println("name=" + name);		
		
		String[] names = name.split("\\."); //分割字符串
		String dcmname = names[0]+"."+names[1];		
		String[] s1 = dcmname.split("_");
		dcmname = s1[1] + "_" + s1[2]  + "_" + s1[3] +  "_" +  s1[4];
		System.out.println(dcmname);  //得到.dcm文件名
		
	    //filezhenduan = "F:\\diagnose\\zhenduan";// 创建诊断文件夹 
        File myPath = new File( filezhenduan ); 
        if ( !myPath.exists()){//若此目录不存在，则创建之  
            myPath.mkdir();  
            System.out.println("创建诊断文件夹路径为："+ filezhenduan);  
        }
       
        String labelPath = getFileListame(dcmname);
        String[] s = labelPath.split("/");
        
        String labelDcmFileName = s[s.length-1];
        System.out.println("labelDcmFileName   " + labelDcmFileName);
        ServletActionContext.getRequest().getSession().setAttribute("labelDcmFileName",labelDcmFileName);
     
        labelPath = labelPath.replace(labelDcmFileName, "");
        ServletActionContext.getRequest().getSession().setAttribute("resParentPath",labelPath);
        System.out.println("labelPath" + labelPath);
        res.sendRedirect("/SH/DWV/index.jsp");
		return null;		
	}
	
	//查找dcm文件
	public static String getFileListame(String strPath) throws Exception {
		//String fileuser = "456/";// 创建用户文件夹 
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		String fileuser = filezhenduan + user.getLoginname() + "/";
        File myPath1 = new File( fileuser );  
        if ( !myPath1.exists()){//若此目录不存在，则创建之  
            myPath1.mkdir();  
            System.out.println("创建用户文件夹路径为："+ fileuser);  
        }
        String dcmDirWithUser = (String)ServletActionContext.getRequest().getSession().getAttribute("dcmDirWithUser");
        System.out.println("dcmDirWithUser   " + dcmDirWithUser);
        String[] dcmfilenameArray = dcmDirWithUser.split("\\.");
        System.out.println("dcmfilenameArray   " + dcmfilenameArray );
        String dcmfilename = dcmfilenameArray[0]; 
        String userDcmDir = dcmDir + dcmfilename + "/";
        System.out.println("userDcmDir   " + userDcmDir);
		File dir = new File(userDcmDir);
		File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
		if (files != null) {
			System.out.println("不为空");
			for (int i = 0; i < files.length; i++) {
				if(files[i].getName().equals(strPath)) {
					System.out.println("源文件路径    "+files[i].getAbsolutePath());
					copyFile(files[i].getAbsolutePath(), fileuser);
				}						
			}
		}
		return fileuser+strPath;
	}	

	//文件复制
	public static void copyFile(String srcPath, String targetPath) throws Exception {
		File srcFile = new File(srcPath);
		File target = new File(targetPath);
		if (!srcFile.exists()) {
			throw new Exception("文件不存在！");
		}
		if (!srcFile.isFile()) {
			throw new Exception("不是文件！");
		}
		//判断目标路径是否是目录
		if (!target.isDirectory()) {
			throw new Exception("文件路径不存在！");
		}

		// 获取源文件的文件名
		String fileName = srcPath.substring(srcPath.lastIndexOf("/") + 1);
		//TODO:判断是否存在相同的文件名的文件
		@SuppressWarnings("unused")
		File[] listFiles = target.listFiles();
		//for (File file : listFiles) {
		//	if(fileName.equals(file.getName())){
		//		fileName += "_1";
		//	}
		//}
		//String newFileName = "F:/diagnose/zhenduan/"+ targetPath + fileName;
		String newFileName = targetPath + fileName;
		File targetFile = new File(newFileName);
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(targetFile);
			//从in中批量读取字节，放入到buf这个字节数组中，
			// 从第0个位置开始放，最多放buf.length个 返回的是读到的字节的个数
			byte[] buf = new byte[8 * 1024];
			int len = 0;
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(in != null){
					 in.close();
				}
			}catch(Exception e){
					System.out.println("关闭输入流错误！");
			}
			try{
				if(out != null){
					out.close();
				}
			}catch(Exception e){
				System.out.println("关闭输出流错误！");
			}
		}
		System.out.println("目标文件路径     "+newFileName);
	}
}
