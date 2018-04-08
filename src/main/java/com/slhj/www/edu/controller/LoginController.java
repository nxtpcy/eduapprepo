package com.slhj.www.edu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.slhj.www.edu.annotation.SysControllerLogAnnotation;
import com.slhj.www.edu.common.Response;
import com.slhj.www.edu.common.StatusType;
import com.slhj.www.edu.dict.UserType;
import com.slhj.www.edu.pojo.dto.PasswordDTO;
import com.slhj.www.edu.service.AdminstratorService;
import com.slhj.www.edu.service.StudentService;
import com.slhj.www.edu.shiro.authentication.UsernamePwdRoleToken;

@Controller
@RequestMapping("/user/")
public class LoginController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminstratorService adminService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private SessionDAO sessionDAO;

	@SysControllerLogAnnotation(desc = "登录")
	@RequestMapping(value = { "/shiroLogin" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public Object shiroLogin(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String loginName,
			@RequestParam String password, @RequestParam Byte role,
			@RequestParam(required = false) String imageValue) {

		//检查图片验证码
		String kaptchaExpected = (String) request.getSession().getAttribute(
				"KAPTCHA_SESSION_KEY");
		/*if ((imageValue == null) || (!imageValue.equals(kaptchaExpected))) {
			return new Response(StatusType.KAPTCHA_ERROR.getValue(),
					StatusType.KAPTCHA_ERROR.getMessage());
		}*/

		StatusType status = StatusType.ERROR;

		// 检查用户角色是否为管理员
		if ((role.byteValue() == UserType.ADMINISTRATOR.getValue())
				&& (!this.adminService.checkRoleWhenLogin(loginName, role))) {
			return new Response(StatusType.ROLE_INVALID.getValue(), StatusType
					.value(StatusType.ROLE_INVALID.getValue())
					.getMessage());
		}

		// 检查用户角色是否为学生
		if ((role.byteValue() == UserType.STUDENT.getValue())
				&& (!this.studentService.checkRoleWhenLogin(loginName, role))) {
			return new Response(StatusType.ROLE_INVALID.getValue(), StatusType
					.value(StatusType.ROLE_INVALID.getValue())
					.getMessage());
		}

		// 登录验证
		UsernamePwdRoleToken token = new UsernamePwdRoleToken(loginName,
				password, role);
		try {
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.login(token);
			if (currentUser.isAuthenticated()) {
				this.logger.info(loginName + " login success");
				if(role.byteValue() == UserType.STUDENT.getValue()) {
					status = StatusType.STUDENTSUCCESS;
				} else if(role.byteValue() == UserType.ADMINISTRATOR.getValue()) {
					status = StatusType.ADMINSUCCESS;
				}
				return new Response(status.getValue(), status.getMessage());
			}
		} catch (UnknownAccountException e) {
			this.logger.error("对用户<{}.{}>进行登录验证..验证未通过,未知账户", new Object[] {
					loginName, password }, e);
			token.clear();
			status = StatusType.USER_IS_NULL;
		} catch (IncorrectCredentialsException e) {
			this.logger.error("对用户<{}.{}>进行登录验证..验证未通过,错误的凭证", new Object[] {
					loginName, password }, e);
			token.clear();
			status = StatusType.PASSWD_NOT_MATCH;
		} catch (AuthenticationException e) {
			this.logger.error("对用户<{}.{}>验证错误", new Object[] { loginName,
					password }, e);
			status = StatusType.UNAUTHORIZED;
			token.clear();
		}
		return new Response(status.getValue(), status.getMessage());
	}

	@SysControllerLogAnnotation(desc = "修改管理员密码")
	@RequestMapping(value = { "/updatePwd" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public Object changePwd(HttpServletRequest request,
			@RequestBody PasswordDTO passwordDTO) {
		try {
			int status = this.adminService.updatePassword(passwordDTO);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			this.logger.error(
					"调用LoginController.updatePassword出错,passwordDTO={}",
					new Object[] { passwordDTO }, e);
		}
		return new Response(StatusType.EXCEPTION.getValue(),
				StatusType.EXCEPTION.getMessage());
	}

	/**@SysControllerLogAnnotation(desc = "管理员重置密码")
	@RequiresRoles({ "0" })
	@RequestMapping(value = { "/user/resetPwd" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public Object resetPwd(HttpServletRequest request,
			@RequestBody PasswordDTO passwordDTO) {
		try {
			int status = this.adminService.resetPassword(passwordDTO);
			String message = StatusType.getStatusTypeByValue(status).getDesc();
			return new Response(status, message);
		} catch (Exception e) {
			this.logger.error(
					"调用LoginController.resetPassword出错,passwordDTO={}",
					new Object[] { passwordDTO }, e);
		}
		return new Response(StatusType.EXCEPTION.getValue(),
				StatusType.EXCEPTION.getDesc());
	}**/

	@SysControllerLogAnnotation(desc = "登录注销")
	@RequestMapping(value = { "/shiroLogout" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public Object logout(HttpServletRequest request) {
		int status = StatusType.SUCCESS.getValue();
		Object currentUser = request.getSession().getAttribute("currentUser");
		try {
			Subject subject = SecurityUtils.getSubject();
			subject.logout();
			if (currentUser != null) {
				this.logger.info(currentUser + " logout success");
			}
			return new Response(status, StatusType.value(status).getMessage());
		} catch (Exception e) {
			this.logger.error("{} logout failed", currentUser, e);
			status = StatusType.ERROR.getValue();
		}
		return new Response(status, StatusType.value(status).getMessage());
	}

}
