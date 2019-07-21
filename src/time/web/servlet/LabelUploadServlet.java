package time.web.servlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


@WebServlet("/LabelUploadServlet")
public class LabelUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // �ϴ��ļ��洢Ŀ¼
    @SuppressWarnings("unused")
	private static final String UPLOAD_DIRECTORY = "2";

    // �ϴ�����-��λ�ֽ�
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

    /**
     * �ϴ����ݼ������ļ�
     */
    public static void copyFile(File src,File dest) throws IOException{
		File newFile = new File(dest,src.getName());
		if(!newFile.exists()){
			newFile.mkdirs();
		}
		File[] files = src.listFiles();
		for (File file : files) {

			if(file.isFile()){
				FileInputStream fis = new FileInputStream(file);
				FileOutputStream fos = new FileOutputStream(new File(newFile,file.getName()));
				byte[] b = new byte[1024];
				int len;
				while((len  = fis.read(b)) !=-1){
					fos.write(b, 0, len);
				}
				fos.close();
				fis.close();
			}else if(file.isDirectory()){
				copyFile(file, newFile);
			}
		}
	}
    @SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
    	//System.out.println("--------------- uploadservlet");
    	
        // 1.�ж��Ƿ�Ϊ��ý���ϴ�
        if (!ServletFileUpload.isMultipartContent(request)) {
            // ���������ֹͣ
            PrintWriter writer = response.getWriter();
            writer.println("Error: ������д��:enctype=multipart/form-data");
            writer.flush();
            return;
        }

        //2.��ʼ�����ϴ�����-����fileItem����
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // �����ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼��
        factory.setSizeThreshold(MEMORY_THRESHOLD);

        // ������ʱ�洢Ŀ¼
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        //�����ļ��ϴ��������
        ServletFileUpload upload = new ServletFileUpload(factory);

        // ��������ϴ��ļ�����ֵ
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // �����������ֵ (�����ļ��ͱ�����)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // ���Ĵ���
        upload.setHeaderEncoding("UTF-8"); 

        // ������ʱ·�����洢�ϴ����ļ�
        // ���·����Ե�ǰӦ�õ�Ŀ¼
   
    
        HttpSession session = request.getSession();       
        String uploadPath = (String) session.getAttribute("resParentPath");
     
       /* String uploadPath = resParentPath;*/
        System.out.println(uploadPath+"------uploadPath");
        
        
        // ���Ŀ¼�������򴴽�                             
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        /*String[] copyPath=new String[300];
        int itemLength=0;*/
        try {

            // ���������������ȡ�ļ�����
            List<FileItem> formItems = upload.parseRequest(request);
            //����Items
            if (formItems != null && formItems.size() > 0) {
            	
                // ����������

                for (FileItem item : formItems) {
                    // �����ڱ��е��ֶ�
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        //��ȡ�ļ������ڷ�������·��
                        //fileName="ll.png";
                        String filePath = uploadPath + File.separator + fileName;
                        //copyPath[itemLength]=filePath;
                        //itemLength++;                        
                        //���·���Ѿ�������ͼƬ���ƣ��ŵ�file�����б��档
                        File storeFile = new File(filePath);
                        // �ڿ���̨����ļ����ϴ�·��
                        // �����ļ���Ӳ��
                        item.write(storeFile);
                        request.setAttribute("message",
                            "�ļ��ϴ��ɹ�!"
                            + "<br>���ڣ�"+filePath);
                   
                    }
                }
            }
            
        } catch (Exception ex) {
            request.setAttribute("message", "������Ϣ: " + ex.getMessage());
        }

        // ��ת�� message.jsp
        getServletContext().getRequestDispatcher("/message.jsp").forward(
                request, response);
    }
    
}