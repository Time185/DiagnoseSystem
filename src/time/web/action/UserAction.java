 package time.web.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.corba.se.pept.transport.Connection;

import time.domain.User;
import time.service.UserService;
import time.serviceImp.UserServiceImp;
import time.utils.HibernateUtils;

public class UserAction extends ActionSupport{
	// 提供一个user对象
	private User user;
	// 一定要提供get方法， 因为拦截器需要完成数据的封装，通过get获得同一个对象，将数据封装到同一个对象中
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// 注册功能
	public String regist() throws IllegalAccessException, InvocationTargetException{		
		UserService userService = new UserServiceImp();
		if(userService.registe(user) != 0) {
			System.out.println("注册成功！");
			return SUCCESS;
		}else {
			System.out.println("注册失败！");
		}		
		return ERROR;		
	}
	
	// 登录功能
	public String login() throws IOException, ServletException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=utf-8");
		String loginname = request.getParameter("loginname");
		String password = request.getParameter("password");		
		System.out.println(loginname);

		int status = Integer.parseInt(request.getParameter("status"));
		if(loginname == null || password ==null || request.getParameter("status") ==null) {
			request.setAttribute("errorMsg0","登录信息不能为空");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login/login.jsp");
			requestDispatcher.forward(request, response);
		}
		
		
		UserService userService = new UserServiceImp();
		User user = userService.login(loginname,password);
		if(user.getLoginname() != null && user.getNum() <= 2) {
			return SUCCESS;
		}else if(user.getNum() > 2) {
			request.setAttribute("errorMsg","当前账户使用次数已到，请重新注册");
		}else {
			request.setAttribute("errorMsg","登录失败,密码或用户类型错误");		
		}	
		return ERROR;
		
	
	}
	// 注册校验
	public String check() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		String loginname = request.getParameter("loginname");
		System.out.println(loginname + "-------------");
		UserService user =new UserServiceImp();
		List<User> list = user.checkLoginname(loginname);
		
		if(list.isEmpty()&&!loginname.isEmpty()) {
		
			response.getWriter().write("当前用户名可用");
		}else if(loginname.isEmpty()){
		
			response.getWriter().write("用户名不能为空");
		}else {
			response.getWriter().write("用户名重名");
		}
		
		return null;
	}
	
}
