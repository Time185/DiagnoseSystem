package time.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadJson {

	public static String ReadFile(String path){
	    String laststr="";
	    File file=new File(path);// 打开文件
	    BufferedReader reader=null;
	    try{
	      FileInputStream in = new FileInputStream(file);
	      reader=new BufferedReader(new InputStreamReader(in,"UTF-8"));//读取文件  
	      String tempString=null;
	      while((tempString=reader.readLine())!=null){
	        laststr=laststr+tempString;
	      }
	      reader.close();
	    }catch(IOException e){
	      e.printStackTrace();
	    }finally{
	      if(reader!=null){
	        try{
	          reader.close();
	        }catch(IOException el){
	        } 
	      } 
	    }
	    return laststr;
	  }
}