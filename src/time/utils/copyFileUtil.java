package time.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class copyFileUtil {

	public static void main(String[] args) throws IOException {
		//String path = "D:\\upload\\qmy";
		//traverseFolder(path);
	}
	
	public static void copyFile(String srcPathStr, String desPathStr) {
        //1.获取源文件的名称
        String newFileName = srcPathStr.substring(srcPathStr.lastIndexOf("\\")+1); //目标文件地址
        //System.out.println(newFileName);
        desPathStr = desPathStr + File.separator + newFileName; //源文件地址
        //System.out.println(desPathStr);

        try{
            //2.创建输入输出流对象
            FileInputStream fis = new FileInputStream(srcPathStr);
            @SuppressWarnings("resource")
			FileOutputStream fos = new FileOutputStream(desPathStr);                

            //创建搬运工具
            byte datas[] = new byte[1024*8];
            //创建长度
            int len = 0;
            //循环读取数据
            while((len = fis.read(datas))!=-1){
                fos.write(datas,0,len);
            }
            //3.释放资源
            fis.close();
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
} 