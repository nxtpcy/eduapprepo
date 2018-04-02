package com.slhj.www.edu.dao;

import com.slhj.www.edu.pojo.AdminUser;

public interface AdminUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);
    
    AdminUser selectByLogin(AdminUser record);
    
    int updatePwdByManagerName(AdminUser record);
    
    AdminUser selectByManagerName(String managerName);
    
    int deleteByManagerName(String managerName);
}