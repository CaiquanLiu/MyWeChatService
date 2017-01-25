package com.eddy;

import java.io.IOException;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter{
	private String characterEncoding;
	private boolean enable;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		characterEncoding=filterConfig.getInitParameter("characterEncoding");
		enable="true".equalsIgnoreCase(filterConfig.getInitParameter("enabled").trim());
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		if(enable || characterEncoding!=null){
			arg0.setCharacterEncoding(characterEncoding);
			arg1.setCharacterEncoding(characterEncoding);
//			System.out.println("characterEncoding= "+characterEncoding);
		}
		
		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void destroy(){
		characterEncoding=null;
	}
}
