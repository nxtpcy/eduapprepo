package com.slhj.www.edu.pojo;

import java.io.Serializable;
import com.alibaba.fastjson.JSON;

/**
 * 管理员用户类
 * 
 * @author wanghang
 * 
 */
public class AdminUser implements Serializable {

	private static final long serialVersionUID = -1619638525095690026L;

	private Integer id;
	private String managerName; // 管理员登录账号的用户名
	private String password; // 密码
	private String deptId; // 管理员所在部门编号
	private String telphone; // 管理员手机
	private String email; // 管理员邮箱
	private Integer age;
	private String workId; // 管理员工号

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId == null ? null : workId.trim();
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName == null ? null : managerName.trim();
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId == null ? null : deptId.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone == null ? null : telphone.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
