package com.slhj.www.edu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.slhj.www.edu.exception.UserLoginException;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	  {
	    if (!SecurityUtils.getSubject().isAuthenticated()) {
	       throw new UserLoginException();
	     }
	     return true;
	   }
}
