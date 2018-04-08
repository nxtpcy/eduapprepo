package com.slhj.www.edu.pojo.dto;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;


/**
 * StudentUserDTO数据传输类
 * @author wanghang
 */
public class StudentUserDTO implements Serializable{

	private static final long serialVersionUID = 5507144317104101115L;
	
	private Integer id;
	private String stuId; // 学号
	private String stuName; // 学生姓名
	private Integer age; // 学生年龄
	private String instId; // 学生所在部门编号
	
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

}
