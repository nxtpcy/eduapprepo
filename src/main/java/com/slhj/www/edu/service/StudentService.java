package com.slhj.www.edu.service;


import java.util.Map;

import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.pojo.StudentUser;
import com.slhj.www.edu.pojo.dto.PasswordDTO;
import com.slhj.www.edu.pojo.dto.RegisterDTO;
import com.slhj.www.edu.pojo.dto.StudentInfoDTO;
import com.slhj.www.edu.pojo.dto.StudentUserDTO;




public interface StudentService {

	public abstract int login(StudentUser studentUser);

	public abstract int addStuUser(RegisterDTO studentUser); // 添加学生用户

	public abstract int updateStuUser(StudentUser studentUser); // 更新学生用户

	public abstract int deleteStuUserById(Integer id); // 根据数据库主键Id删除用户

	public abstract int deleteStuUserByStuId(String stuId); // 根据学生学号删除用户

	public abstract StudentUser getStuUserByStuId(String studentId); // 根据学生学号得到StudentUser对象
	
	public abstract StudentUserDTO getStuUserDTOByStuId(String studentId); // 根据学生学号得到StudentUser对象

	public abstract int updatePassword(PasswordDTO passwordDTO); // 修改密码
	
	public abstract boolean checkRoleWhenLogin(String loginName, Byte role); //当登录时检查用户角色
	
	public abstract QueryBase query(Map<String, Object> paramMap);
	
	public abstract int updateStuUserbyDTO(StudentUserDTO studentUserDTO); // 更新学生用户
	
	public abstract	int batchDeleteStudent(Integer[] ids); // 批量删除学生信息
	
	public abstract StudentInfoDTO getStuInfoByStuId(String stuId);   // 根据学号查询学生基本信息（姓名学院等）
	
}
