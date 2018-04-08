package com.slhj.www.edu.dao;

import java.util.List;

import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.pojo.StudentUser;
import com.slhj.www.edu.pojo.dto.RegisterDTO;
import com.slhj.www.edu.pojo.dto.StudentUserDTO;

public interface StudentUserMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(StudentUser record);

    int insertSelective(RegisterDTO record);

    StudentUserDTO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentUser record);
    
    int deleteUserByStuId(String stuId);

    int updateByPrimaryKey(StudentUser record);
    
    List<StudentUserDTO> query(QueryBase paramQueryBase);
    
    Long queryRows(QueryBase paramQueryBase);
    
    StudentUser getStudentUserByStuId(String stuId);
    
    StudentUserDTO getStudentUserDTOByStuId(String stuId);
    
    int updateByStuIdSelective(StudentUserDTO record);
}