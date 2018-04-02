package com.slhj.www.edu.service;


import com.slhj.www.edu.pojo.AdminUser;
import com.slhj.www.edu.pojo.dto.PasswordDTO;


public interface AdminstratorService {
	
	public int login(AdminUser adminUser);

	public AdminUser getAdminUser(AdminUser adminUser); // 得到管理员对象

	public int updatePassword(PasswordDTO passwordDTO); // 修改密码

	public AdminUser getAdminUserByManagerName(String loginName); // 根据登录名得到管理员对象

	public boolean checkRoleWhenLogin(String loginName, Byte roleByte); // 当登录时检查用户角色

	public int updateAdminUserInfo(AdminUser adminUser); //更新管理员用户信息
}
