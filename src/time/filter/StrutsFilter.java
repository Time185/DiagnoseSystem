package time.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

/**
 * Servlet Filter implementation class StrutsFilter
 */
@WebFilter("/StrutsFilter")
public class StrutsFilter extends StrutsPrepareAndExecuteFilter implements Filter {
       
    /**
     * @see StrutsPrepareAndExecuteFilter#StrutsPrepareAndExecuteFilter()
     */
    public StrutsFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		HttpServletRequest httprequest = (HttpServletRequest) request;
		String url = httprequest.getRequestURI();
		
		// 如果请求的是servlet   直接转发到sevlet   不执行struts2的核心过滤器
		if(url.contains("Servlet")) {
			
			chain.doFilter(request, response);
		}else {
			// 请求的为action   交给核心处理器来处理
			super.doFilter(request, response,chain);
		}
		// pass the request along the filter chain
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */ 
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		super.init(fConfig);
	}

}
