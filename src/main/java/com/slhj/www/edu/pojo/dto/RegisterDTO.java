package com.slhj.www.edu.pojo.dto;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.slhj.www.edu.pojo.StudentUser;

public class RegisterDTO extends StudentUser implements Serializable{

	private static final long serialVersionUID = 8147552069949646644L;

	private String stuId; // 学号
	private String stuName; // 学生姓名
	private Integer age; // 学生年龄
	private String instId; // 学生所在部门编号
	private String password;
	
	
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStutName() {
		return stuName;
	}
	public void setStutName(String stutName) {
		this.stuName = stutName;
	}
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
