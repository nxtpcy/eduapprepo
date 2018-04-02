package com.slhj.www.edu.dict;

/**
 * 记录用户类型
 * @author wanghang
 *
 */
public enum UserType {
	
	ADMINISTRATOR(0, "超级管理员"),
	STUDENT(1, "学生");
	private int value;
	private String desc;
	private UserType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	/**
	 * 根据值找到相对应的用户角色对象
	 * @param value
	 * @return
	 */
	public static UserType getUserTypeByValue(int value) {
		UserType[] types = values();
		for(UserType type : types) {
			if(type.getValue() == value) {
				return type;
			}
		}
		return null;
	}
	
	/**
	 * 根据值找到相关角色的描述信息
	 * @param value
	 * @return
	 */
	public static String findDesc(int value) {
		UserType type = getUserTypeByValue(value);
		if(type != null)
			return type.getDesc();
		else 
			return "";
	}
	
}
