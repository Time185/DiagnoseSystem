package time.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;



public interface DiagnoseService {
	// 产生诊断报告
	void producePDF(String templatePdf, String result, String image);

	boolean diagnose();

	boolean getPDF(String string, HttpServletResponse response);
}
