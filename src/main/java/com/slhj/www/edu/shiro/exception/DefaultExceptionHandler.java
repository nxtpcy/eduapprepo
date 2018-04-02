package com.slhj.www.edu.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.slhj.www.edu.common.Response;
import com.slhj.www.edu.common.StatusType;
import com.slhj.www.edu.exception.UserLoginException;

@ControllerAdvice
public class DefaultExceptionHandler {
	@ExceptionHandler({ AuthorizationException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public Object processAuthorizationException() {
		return new Response(StatusType.PERMISSION_DENIED.getValue(),
				StatusType.PERMISSION_DENIED.getMessage());
	}

	@ExceptionHandler({ AuthenticationException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public Object processAuthenticationException() {
		return new Response(StatusType.UNAUTHORIZED.getValue(),
				StatusType.UNAUTHORIZED.getMessage());
	}

	@ExceptionHandler({ UserLoginException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public Object processUserLoginException() {
		return new Response(StatusType.UNAUTHORIZED.getValue(),
				StatusType.UNAUTHORIZED.getMessage());
	}
}
