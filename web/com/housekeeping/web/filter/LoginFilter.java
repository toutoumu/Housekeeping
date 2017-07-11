package com.housekeeping.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		// request.setCharacterEncoding("utf-8");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String path = req.getContextPath();
		String indexPath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path
				+ "/modules/login/index.jsp";
		if (req.getRequestURI().endsWith("index.jsp")) {
			chain.doFilter(request, response);
			return;
		}

		Object loginuser = req.getSession().getAttribute("manager");
		if (loginuser == null) {
			//request.getRequestDispatcher(indexPath).forward(request, response);
			res.sendRedirect(indexPath);
			//chain.doFilter(request, response);
			return;
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
