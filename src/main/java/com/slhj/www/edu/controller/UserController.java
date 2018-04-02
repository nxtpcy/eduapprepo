package com.slhj.www.edu.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.slhj.www.edu.annotation.SysControllerLogAnnotation;
import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.common.Response;
import com.slhj.www.edu.common.StatusType;
import com.slhj.www.edu.service.StudentService;

@Controller
@RequestMapping("/user/")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private StudentService studentService;
	
	@SysControllerLogAnnotation(desc = "查询所有学生信息")
	@RequestMapping(value = "listUserInfo", method = RequestMethod.POST)
	@ResponseBody
	//@RequiresRoles(value = "0") 暂时取消权限
	public Object listUser(HttpServletRequest request, @RequestParam Map<String, Object> paramMap) {
		try {
			QueryBase queryBase = studentService.query(paramMap);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("rows", queryBase.getResults());
			result.put("total", queryBase.getTotalRow());
			return result;
		} catch (Exception e) {
			logger.error("调用userController.listUser出错,para={}", paramMap, e);
			return new Response(StatusType.EXCEPTION.getValue(), StatusType.EXCEPTION.getMessage());
		}
	}
}
