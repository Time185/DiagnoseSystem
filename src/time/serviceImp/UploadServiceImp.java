package time.serviceImp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import time.service.UploadService;
import time.utils.FileUtils;
import time.utils.copyFileUtil;

public class UploadServiceImp implements UploadService {
	private String userDir = "/data/leichao/diagnose/upload";
	

	@Override
	public String upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac); 
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		List<FileItem> fileList = null; 
		try {      
            fileList = upload.parseRequest(request);      
        } catch (FileUploadException ex) {      
            ex.printStackTrace();  
            System.out.println("deleteIf");
            return null;      
        } 
		Iterator<FileItem> it = fileList.iterator(); 
		if(it.hasNext()) {
			FileItem item =  it.next(); 
			System.out.println(item.getName());
			if(!item.isFormField()) {
				BufferedInputStream in = new BufferedInputStream(item.getInputStream()); 
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(userDir+"/"+ uuid + item.getName())));
				Streams.copy(in, out, true);
			}
			return uuid + item.getName();
		}
		return null;
	}
	// 进行解压
	@Override
	public String unzip(String zipFileName, String extPlace) {
		// TODO Auto-generated method stub
		System.setProperty("sun.zip.encoding",
				System.getProperty("sun.jnu.encoding"));
		try {
			(new File(extPlace)).mkdirs();
			File f = new File(zipFileName);
			ZipFile zipFile = new ZipFile(zipFileName, "GBK"); // 处理中文文件名乱码的问题
			if ((!f.exists()) && (f.length() <= 0)) {
				throw new Exception("要解压的文件不存在!");
			}
			String strPath, gbkPath, strtemp;
			File tempFile = new File(extPlace);
			strPath = tempFile.getAbsolutePath();
			Enumeration<?> e = zipFile.getEntries();
			while (e.hasMoreElements()) {
				ZipEntry zipEnt = (ZipEntry) e.nextElement();
				gbkPath = zipEnt.getName();
				if (zipEnt.isDirectory()) {
					strtemp = strPath + File.separator + gbkPath;
					File dir = new File(strtemp);
					dir.mkdirs();
					continue;
				} else { // 读写文件
					InputStream is = zipFile.getInputStream(zipEnt);
					BufferedInputStream bis = new BufferedInputStream(is);
					gbkPath = zipEnt.getName();
					strtemp = strPath + File.separator + gbkPath;// 建目录
					String strsubdir = gbkPath;
					for (int i = 0; i < strsubdir.length(); i++) {
						if (strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {
							String temp = strPath + File.separator
									+ strsubdir.substring(0, i);
							File subdir = new File(temp);
							if (!subdir.exists())
								subdir.mkdir();
						}
					}
					FileOutputStream fos = new FileOutputStream(strtemp);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					int c;
					while ((c = bis.read()) != -1) {
						bos.write((byte) c);
					}
					bos.close();
					fos.close();
				}
			}
			return tempFile.getName();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public void copy(String uploadPath, String inputPath) {
		// TODO Auto-generated method stub
		copyFileUtil copy = new copyFileUtil();
		File copyFile = new File(inputPath);
		if(!copyFile.exists()){
	    	copyFile.mkdirs();
	    }
		
		//遍历所有dicom文件并复制
		int fileNum = 0, folderNum = 0;  
		File file = new File(uploadPath);
		if (file.exists()) {  
			LinkedList<File> list = new LinkedList<File>();  
			File[] files = file.listFiles();  
			for (File file2 : files) {  
				if (file2.isDirectory()) {    
					list.add(file2);  
					fileNum++;  
				} else {    
					folderNum++; 
					 copy.copyFile((file2.getAbsolutePath()),inputPath);					 
				}  
			} 
			File temp_file;  
			while (!list.isEmpty()) {  
				temp_file = list.removeFirst();  
				files = temp_file.listFiles();  
				for (File file2 : files) {  
					if (file2.isDirectory()) {  
						//System.out.println("文件夹:" + file2.getAbsolutePath());  
						list.add(file2);  
						fileNum++;  
					} else {  
						//System.out.println("文件:" + file2.getAbsolutePath());  
						folderNum++;  
						copy.copyFile((file2.getAbsolutePath()),inputPath);
					}  
				}  
			}
		}
		System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
	}

	@Override
	public void dcm2jpg(String dcmDir, String jpgDir) {
		// TODO Auto-generated method stub
		File jpgdir = new File(jpgDir);
		if(!jpgdir.exists()) {
			jpgdir.mkdirs();
		}
		try {
			/*String[] arg = new String[] {"python","D:\\Program Files\\python37\\Dicom2Jpg\\demo.py",
					dcmDir,
					jpgDir};*/
			System.out.println("dcmDir=" + dcmDir);
			System.out.println("jpsDir=" + jpgDir);
			String[] arg = new String[] {"python3","/data/leichao/diagnose/pythonCode/dcm2jpg.py",
					dcmDir,
					jpgDir};
		
			Process proc = Runtime.getRuntime().exec(arg);
			proc.waitFor();
			System.out.println("转换完成");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("dcm2jpg ERROR");
		}
	}
	@Override
	public void delete(String path) {
		// TODO Auto-generated method stub
		FileUtils.delete(path);
	}
}
