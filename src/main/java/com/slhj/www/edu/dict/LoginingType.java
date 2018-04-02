package com.slhj.www.edu.dict;

/**
 * 用户登陆类型
 * @author wanghang
 *
 */
public enum LoginingType {

	NORMAL(0, "正常"),
	FORBIT(1, "禁止登陆"),
	DELETED(2, "已删除");
	
	private int value;
	private String desc;
	
	private LoginingType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public static LoginingType getLoginingTypeByValue(int value) {
		LoginingType[] types = values();
		for(LoginingType type : types) {
			if(type.getValue() == value) {
				return type;
			}
		}
		return null;
	}
}
