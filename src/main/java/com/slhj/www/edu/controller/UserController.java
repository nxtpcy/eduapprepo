package com.slhj.www.edu.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.slhj.www.edu.annotation.SysControllerLogAnnotation;
import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.common.Response;
import com.slhj.www.edu.common.StatusType;
import com.slhj.www.edu.pojo.dto.StudentUserDTO;
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
	// @RequiresRoles(value = "0") 暂时取消权限
	public Object listUser(HttpServletRequest request,
			@RequestParam Map<String, Object> paramMap) {
		try {
			QueryBase queryBase = studentService.query(paramMap);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("rows", queryBase.getResults());
			result.put("total", queryBase.getTotalRow());
			return result;
		} catch (Exception e) {
			logger.error("调用userController.listUser出错,para={}", paramMap, e);
			return new Response(StatusType.EXCEPTION.getValue(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@SysControllerLogAnnotation(desc = "查询单条学生信息")
	@RequestMapping(value = "simpleUserInfo", method = RequestMethod.POST)
	@ResponseBody
	// @RequiresRoles(value = "0") 暂时取消权限
	public Object simpleUser(HttpServletRequest request,
			@RequestParam String stuId) {
		try {
			StudentUserDTO studentUserDTO = studentService
					.getStuUserDTOByStuId(stuId);
			return studentUserDTO;
		} catch (Exception e) {
			logger.error("调用userController.simpleUser出错,para={}", stuId, e);
			return new Response(StatusType.EXCEPTION.getValue(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@SysControllerLogAnnotation(desc = "更新学生信息")
	@RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
	@ResponseBody
	//@RequiresRoles(value = "0") 暂时取消权限
	public Object updateUser(HttpServletRequest request,
			@RequestBody StudentUserDTO stuUserDTO) {
		try {
			int status = studentService.updateStuUserbyDTO(stuUserDTO);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			logger.error("调用userController.updateUser出错,para={}", stuUserDTO, e);
			return new Response(StatusType.EXCEPTION.getValue(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	/**
	 * 根据前端传递的学号集合，批量删除学生信息
	 * 
	 * @author wanghang
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	// @RequiresRoles("0")
	@SysControllerLogAnnotation(desc = "批量删除学生信息")
	@RequestMapping(value = "deleteUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteUsers(HttpServletRequest request,
			HttpServletResponse response, @RequestBody Map<String, Object> map) {
		try {
			String stuIds = (String) map.get("stuIds");
			if (stuIds == null) {
				return new Response(StatusType.PARAMETER_IS_NULL.getValue(),
						StatusType.PARAMETER_IS_NULL.getMessage());
			}
			// 将stuId分割
			String[] ids = stuIds.split(",");
			Integer[] idsInt = new Integer[ids.length];
			for(int i = 0; i < ids.length; i++) {
				idsInt[i] = Integer.valueOf(ids[i]);
			}
			int info = studentService.batchDeleteStudent(idsInt);
			if (info == StatusType.ERROR.getValue()) {
				return new Response(StatusType.ERROR.getValue(), "删除失败：" + StatusType.ERROR.getMessage());
			} else {
				return new Response(StatusType.SUCCESS.getValue(), StatusType.SUCCESS.getMessage());
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(
					"调用UserController.deleteUsers出错,map={}",
					new Object[] { map }, e);
			return new Response(StatusType.EXCEPTION.getValue(),
					StatusType.EXCEPTION.getMessage());
		}
	}

}
