package com.slhj.www.edu.shiro.authentication;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义身份验证Token
 * @author wanghang
 *
 */
public class UsernamePwdRoleToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;
	private Byte role;
	
	public UsernamePwdRoleToken(String username, String password, Byte role) {
		super(username, password);
		this.role = role;
	}
	
	public Byte getRole() {
		return this.role;
	}
	
	public void setRole(Byte role) {
		this.role = role;
	}
}
