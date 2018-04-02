package com.slhj.www.edu.shiro.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slhj.www.edu.dict.UserType;
import com.slhj.www.edu.pojo.AdminUser;
import com.slhj.www.edu.pojo.StudentUser;
import com.slhj.www.edu.service.AdminstratorService;
import com.slhj.www.edu.service.StudentService;
import com.slhj.www.edu.shiro.authentication.UsernamePwdRoleToken;

/**
 * 自定义shiro Realm
 * 
 * @author wanghang
 * 
 */
public class UserRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory
			.getLogger(UserRealm.class);
	
	@Autowired
	private AdminstratorService adminService;
	
	@Autowired
	private StudentService studentService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		try {
			Set<String> roles = new HashSet<String>();
			logger.debug("roles={}", roles);
			authorizationInfo.setRoles(roles);
		} catch (UnauthorizedException e) {
			// TODO: handle exception
			logger.error("UnauthorizedException error", e);
		} catch (AuthorizationException e) {
			logger.error("AuthorizationException error", e);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {

		UsernamePwdRoleToken authenticationToken = (UsernamePwdRoleToken) token;
		int role = authenticationToken.getRole().byteValue();
		if (role == UserType.STUDENT.getValue()) {
			StudentUser user = this.studentService
					.getStuUserByStuId(authenticationToken.getUsername());
			if (user != null) {
				SimpleAuthenticationInfo authcInfo = new SimpleAuthenticationInfo(
						user.getStuId(), user.getPassword(),
						"StudentUserAuthenticationInfo");
				setSession("currentUser", user);
				return authcInfo;
			}
		}

		if (role == UserType.ADMINISTRATOR.getValue()) {
			AdminUser user = this.adminService
					.getAdminUserByManagerName(authenticationToken.getUsername());
			if (user != null) {
				SimpleAuthenticationInfo authcInfo = new SimpleAuthenticationInfo(
						user.getManagerName(), user.getPassword(),
						"AdminUserAuthenticationInfo");
				setSession("currentUser", user);
				return authcInfo;
			}
		}
		return null;
	}

	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser != null) {
			Session session = currentUser.getSession();
			if (session != null) {
				session.setAttribute(key, value);
			}
		}
	}

	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}
}
