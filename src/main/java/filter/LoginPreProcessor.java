package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter( urlPatterns = {"/todo/add", "/todo/update", "/project/add", "/project/update"} )
public class LoginPreProcessor implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			
			HttpSession session = req.getSession();
			boolean isLogin = (boolean) session.getAttribute("isLogin");
			
			chain.doFilter(request, response);
		} catch(NullPointerException e) {
			// NullPointerException이 발생했다면 ( 로그인이 되지 않은 상태라면 )
			// 로그인이 되지 않은 상태를 의미하는 시그널을 보낸다.
			HttpServletResponse res = (HttpServletResponse) response;
			
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
}






