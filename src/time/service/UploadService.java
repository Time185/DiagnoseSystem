package time.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UploadService {

	String upload(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException;

	String unzip(String uploadName, String unzipDir);

	void copy(String string, String string2);

	void dcm2jpg(String string, String string2);

	void delete(String path);

}
