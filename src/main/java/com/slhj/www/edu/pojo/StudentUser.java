package com.slhj.www.edu.pojo;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 学生用户类
 * 
 * @author wanghang
 */
public class StudentUser implements Serializable {
	private static final long serialVersionUID = -8810296127756245882L;
	private Integer id; // 存储在数据库中的id主键
	private String stuId; // 学号
	private String password; // 登录密码
	private String stuName; // 学生姓名
	private Integer age; // 学生年龄
	private String instId; // 学生所在部门编号
	private Integer score;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId == null ? null : stuId.trim();
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName == null ? null : stuName.trim();
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId == null ? null : instId.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}
