package com.slhj.www.edu.common;

public class Status {

	public static final short SUCCESS = 0;

	public static final short ERROR = 1;

	public static final short NO_REMOVE = 2;

	public static final short NO_DELETE = 3;

	public static final short NO_UPDATE = 4;

	public static final short LOCK = 5;

	public static final short EXISTS = 6;

	public static final short NOT_EXISTS = 7;

	public static final short VERSION_NOT_MATCH = 8;

	public static final short PASSWD_NOT_MATCH = 9;

	public static final short CODE_NOT_MATCH = 10;

	public static final short BLOCKED = 11;

	public static final short VALIDATE_CODE_EMPTY = 12;

	public static final short INVALID_OPERATION = 13;

	public static final short TIMEOUT = 14;

	public static final short USER_IS_NULL = 15;

	public static final short OBJECT_NULL = 16;

	public static final short DUPLICATION_ENTRY = 17;

	public static final int SYSTEM_EXCEPTION = -1; // 系统抛出异常时

	public static final int APPLICATION_EXCEPTION = -2; // 应用逻辑出现错误时

	public static final int ROLE_NOT_MATCH = 18;// 角色不匹配

}
