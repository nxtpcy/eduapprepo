package com.slhj.www.edu.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.slhj.www.edu.annotation.SysControllerLogAnnotation;
import com.slhj.www.edu.common.Response;
import com.slhj.www.edu.common.StatusType;
import com.slhj.www.edu.pojo.dto.RegisterDTO;
import com.slhj.www.edu.service.StudentService;

@Controller
@RequestMapping("/user/")
public class RegisterController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private StudentService studentService;

	@SysControllerLogAnnotation(desc = "用户注册")
	@RequestMapping(value = { "/register" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public Object register(HttpServletRequest request,
			@RequestBody RegisterDTO studentUser) {
		try {
			int status = this.studentService.addStuUser(studentUser);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			this.logger.error("调用LoginController.addStuUser出错,studentUser={}",
					new Object[] { studentUser }, e);
		}
		return new Response(StatusType.EXCEPTION.getValue(),
				StatusType.EXCEPTION.getMessage());
	}
}
