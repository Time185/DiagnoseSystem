package time.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import time.domain.User;



public interface DiagnoseService {
	// 产生诊断报告
	void producePDF(String templatePdf, String result, String[] imagePath, User user);

	String diagnose(String jpgDir, String resultDir);

	boolean getPDF(String string, HttpServletResponse response);

	String[] getImagePath(String result);

	void scanDcm(String resultDir, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
