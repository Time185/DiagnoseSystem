package demo;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class checkAction extends ActionSupport{
	
	public String check() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String loginname = request.getParameter("loginname");
		System.out.println(loginname);
		return null;
	}
}
