package time.serviceImp;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Normalizer.Form;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

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

import time.service.DiagnoseService;

public class DiagnoseServiceImp implements DiagnoseService {

	@Override
	public void producePDF(String templatePath, String newPDFPath, String imagePath) {		
		PdfReader reader;
		FileOutputStream out;
		ByteArrayOutputStream bos;
		PdfStamper stamper;
		try {
			out = new FileOutputStream(newPDFPath);
			reader = new PdfReader(templatePath);
			bos = new ByteArrayOutputStream();
			stamper = new PdfStamper(reader, bos);
			AcroFields form = stamper.getAcroFields();
			//form.addSubstitutionFont(BaseFont.createFont("STSong-light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED));
			String[] str = {"李四","24","20190426","正常","健康"};
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
					Image image = Image.getInstance(imagePath);
					// 获取操作界面
					PdfContentByte under = stamper.getOverContent(pageNo);
					// 根据域的大小缩放图片
					image.scaleToFit(sign.getWidth(),sign.getHeight());
					// 添加图片
					image.setAbsolutePosition(x, y);
					under.addImage(image);
				}else {
					
					form.setField(name, str[i++]);
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
	public boolean diagnose() {
		// TODO Auto-generated method stub
		String message = "sudo /data/tanjiale/py-faster-rcnn/tools/demo.py --net resnet101";
		try {
			Process proc = Runtime.getRuntime().exec(message);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
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
	

}
