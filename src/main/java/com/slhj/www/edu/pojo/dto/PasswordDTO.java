package com.slhj.www.edu.pojo.dto;

import com.alibaba.fastjson.JSON;

/**
 * 数据传输类，传输密码DTO
 * @author wanghang
 *
 */
public class PasswordDTO {

	private String managerName; //登录名
	private String originPassword; //原始密码
	private String newPassword; //新密码
	private String repetitionPassword; //重复密码
	
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getOriginPassword() {
		return originPassword;
	}
	public void setOriginPassword(String originPassword) {
		this.originPassword = originPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getRepetitionPassword() {
		return repetitionPassword;
	}
	public void setRepetitionPassword(String repetitionPassword) {
		this.repetitionPassword = repetitionPassword;
	}
	
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
