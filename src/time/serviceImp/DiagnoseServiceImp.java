package time.serviceImp;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import time.domain.User;
import time.service.DiagnoseService;
import time.utils.ImageStitching;
import time.utils.ReadJson;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


public class DiagnoseServiceImp implements DiagnoseService {

	@Override
	public void producePDF(String templatePath, String result, String pdfImage, User user) {		
		PdfReader reader;
		FileOutputStream out;
		ByteArrayOutputStream bos;
		PdfStamper stamper;
		String newPDFPath = result + "/" + user.getLoginname() + ".pdf"; 
		try {
			out = new FileOutputStream(newPDFPath);
			reader = new PdfReader(templatePath);
			bos = new ByteArrayOutputStream();
			stamper = new PdfStamper(reader, bos);
			AcroFields form = stamper.getAcroFields();
			//form.addSubstitutionFont(BaseFont.createFont("STSong-light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED));
			String[] str = {user.getName(),String.valueOf(user.getAge()),"20190426","待完善","待完善"};
			int i = 0;
			Iterator<String> it =  form.getFields().keySet().iterator();
			
			while(it.hasNext()) {
				String name = it.next().toString();
				if(name.equals("fill_4")) {
					int pageNo = form.getFieldPositions(name).get(0).page;
					Rectangle sign = form.getFieldPositions(name).get(0).position;	
					float x = sign.getLeft();
					float y = sign.getBottom();
					// 读图片
					Image image = Image.getInstance( pdfImage);
					// 获取操作界面
					PdfContentByte under = stamper.getOverContent(pageNo);
					// 根据域的大小缩放图片
					image.scaleToFit(sign.getWidth(),sign.getHeight());
					// 添加图片
					image.setAbsolutePosition(x, y);
					under.addImage(image);
				}else {
					
					form.setField(name, str[i++]);
					System.out.println("添加字段");
				}
				
			}
			// 如果为false的话 ，那么生成的PDF文件还可以在进行编译，一定设置为true
			stamper.setFormFlattening(true);
			stamper.close();
			
			Document doc = new Document();
			PdfCopy copy = new PdfCopy(doc,out);
			doc.open();
			PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
			copy.addPage(importPage);
			doc.close();
		}catch(IOException e) {
			e.printStackTrace();
		}catch(DocumentException e) {
			e.printStackTrace();
		}
        
	}
	// 调用服务器算法  
	@Override
	public String diagnose(String jpgDir,String resultDir) {
		// TODO Auto-generated method stub
		File result = new File(resultDir);
		if(!result.exists())
			result.mkdirs();
		System.out.println("jpgDir" + jpgDir);
		System.out.println("resultDir" + resultDir);
		String arg = "sh /data/leichao/test.sh" + " " + jpgDir + " " + resultDir;
 	
		
		BufferedReader br = null;
		Process proc;
		try {
			proc = Runtime.getRuntime().exec(arg);
			
			proc.waitFor();
			br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
		    StringBuilder sb = new StringBuilder();
	        while((line = br.readLine()) != null){
	             sb.append(line + "\n");
	         }

	        System.out.println(sb.toString());
			System.out.println("调用算法结束");
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		return resultDir;
		
	}
	@Override
	public boolean getPDF(String string, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			InputStream in = new FileInputStream(string);
			OutputStream out = response.getOutputStream();
			int b;
			while((b=in.read())!= -1)
			{
				out.write(b);
			}
			
			in.close();
			out.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public String[] getImagePath(String result) {
		// TODO Auto-generated method stub
		String[] imagePath = new String[4];
		try {
			String jsonArray = ReadJson.ReadFile(result + "/pred_result.json");
			JSONArray jsonArray1=JSONArray.fromObject(jsonArraySort(jsonArray));////按value排序后的json
			for(int i = 0; i < jsonArray1.size() ; i++){
        		JSONObject itemcoord = jsonArray1.getJSONObject(i);
        		        		
        	}
			for(int i = 0; i < 4 ; i++){
        		JSONObject itemcoord = jsonArray1.getJSONObject(i);
        		System.out.println(itemcoord.get("picture")); 
        		if(itemcoord.get("picture") != null) {
        			
        			imagePath[i] = (String) itemcoord.get("picture");
        		}else{
        			
        			imagePath[i] = null;
        		}
			}	
        	}catch(JsonIOException e) {
        		e.printStackTrace();
        	}catch(JsonSyntaxException e) {
	            e.printStackTrace();
	        }
		return imagePath;
	}
	
	public String jsonArraySort(String jsonArrStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		JSONArray sortedJsonArray = new JSONArray();
		ArrayList<JSONObject> jsonValues = new ArrayList<JSONObject>();
		for (int i = 0; i < jsonArr.size(); i++) {
			jsonValues.add(jsonArr.getJSONObject(i));
		}
		Collections.sort(jsonValues, new Comparator<JSONObject>() {
			private  String KEY_NAME = "value";

			@Override
			public int compare(JSONObject a, JSONObject b) {
				String valA = new String();
				String valB = new String();
				try {
					// // 这里是a、b需要处理的业务，需要根据你的规则进行修改。
					String aStr = a.getString(KEY_NAME);
					valA = aStr.replaceAll("-", "");
					String bStr = b.getString(KEY_NAME);
					valB = bStr.replaceAll("-", "");
				} catch (JSONException e) {
				}
				return -valA.compareTo(valB);			
			}
		});
		
		for (int i = 0; i < jsonArr.size(); i++) {
			sortedJsonArray.add(jsonValues.get(i));
		}
		return sortedJsonArray.toString();
	}
	@Override
	public void scanDcm(String resultDir ,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=utf-8");
		System.out.println("进入picture()");
		
		//String path ="C:\\Users\\XAYY\\Desktop\\1";  

		
		
		File file = new File(resultDir);
		
		ArrayList<String> urlList = new ArrayList<>();
		
		if (file.exists()) {
			File[] pictureList = file.listFiles();
			if(pictureList.length ==0) {
				System.out.println("错误，文件夹内没有文件");
			}
			else {
				for (int i = 0; i < pictureList.length; i++) {
					String pictureName = pictureList[i].getName();	
					System.out.println("pictureName = "+ pictureName);
					String extension = "";
					int j = pictureName.lastIndexOf('.');
					if (j > 0) {
					    extension = pictureName.substring(j+1);
					    System.out.println("extension = "+ extension);
						if("jpg".equals(extension) || "png".equals(extension)){
							System.out.println(pictureName);
							//	String url = "images/gallery/" + pictureName;
							urlList.add(pictureName);
						}
					}else {
						System.out.println("不能得到文件的扩展名");
					}
				}
			}
		}	
		System.out.println("picture()中urlList.size() = " + urlList.size());
		request.setAttribute("url", urlList);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("../diagnose/picture.jsp");
		requestDispatcher.forward(request, response);
		
	}
	@Override
	public String getpdfImage(String[] imagePath, String result, String loginname) {
		// TODO Auto-generated method stub
		BufferedImage image1 = null;
		BufferedImage image2 = null;
		BufferedImage imageAfter1 = null;
		BufferedImage imageAfter2 = null;
		String savePath = result + "/" + loginname + ".jpg";
		try {
			image1 = ImageStitching.getBufferedImage(result + "/" + loginname + "/" + imagePath[0]);
			image2 = ImageStitching.getBufferedImage(result + "/" + loginname + "/" + imagePath[1]);			
			imageAfter1 = ImageStitching.mergeImage(image1, image2, false);
			
			image1 = ImageStitching.getBufferedImage(result + "/" + loginname + "/" + imagePath[2]);
			image2 = ImageStitching.getBufferedImage(result + "/" + loginname + "/" + imagePath[3]);
			imageAfter2 = ImageStitching.mergeImage(image1, image2, false);
			
			imageAfter1 = ImageStitching.mergeImage(imageAfter1, imageAfter2, true);
			ImageStitching.generateSaveFile(imageAfter1, savePath);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return savePath;
		
	}

}
