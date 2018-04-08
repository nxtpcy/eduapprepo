package com.slhj.www.edu.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.common.StatusType;
import com.slhj.www.edu.dao.StudentUserMapper;
import com.slhj.www.edu.pojo.StudentUser;
import com.slhj.www.edu.pojo.dto.PasswordDTO;
import com.slhj.www.edu.pojo.dto.RegisterDTO;
import com.slhj.www.edu.pojo.dto.StudentUserDTO;
import com.slhj.www.edu.service.StudentService;
import com.slhj.www.edu.utils.DAOResultUtil;

@Service("StudentService")
public class StudentServiceImpl implements StudentService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private StudentUserMapper studentUserMapper;

	public int login(StudentUser studentUser) {
		// TODO Auto-generated method stub
		StudentUser tempUser = null; // 临时用户
		try {
			tempUser = this.studentUserMapper.getStudentUserByStuId(studentUser
					.getStuId());
			if (tempUser != null) {
				return StatusType.SUCCESS.getValue();
			}
			return StatusType.PASSWD_NOT_MATCH.getValue();
		} catch (Throwable e) {
			this.logger.error(
					"调用StudentServiceImpl.login出错,传入参数user={},查询到user={}",
					new Object[] { studentUser, tempUser }, e);
			return StatusType.EXCEPTION.getValue();
		}
	}

	/**
	 * 添加学生用户
	 */
	public int addStuUser(RegisterDTO studentUser) {
		// TODO Auto-generated method stub
		int rows = this.studentUserMapper.insertSelective(studentUser);
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getValue();
	}

	public int updateStuUser(StudentUser studentUser) {
		// TODO Auto-generated method stub
		int rows = this.studentUserMapper
				.updateByPrimaryKeySelective(studentUser);
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getValue();
	}

	/**
	 * 根据id删除用户
	 */
	public int deleteStuUserById(Integer id) {
		// TODO Auto-generated method stub
		int rows = this.studentUserMapper.deleteByPrimaryKey(id);
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getValue();
	}

	/**
	 * 根据学号删除用户
	 */
	public int deleteStuUserByStuId(String stuId) {
		// TODO Auto-generated method stub
		int rows = this.studentUserMapper.deleteUserByStuId(stuId);
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getValue();
	}

	/**
	 * 根据学号得到学生对象
	 */
	public StudentUser getStuUserByStuId(String studentId) {
		// TODO Auto-generated method stub
		StudentUser studentUser = this.studentUserMapper
				.getStudentUserByStuId(studentId);
		return studentUser;
	}

	/**
	 * 更新密码
	 */
	public int updatePassword(PasswordDTO passwordDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 登录时检查用户
	 */
	public boolean checkRoleWhenLogin(String loginName, Byte role) {
		// TODO Auto-generated method stub
		StudentUser currentUser = this.studentUserMapper
				.getStudentUserByStuId(loginName);
		if (currentUser != null) {
			return true;
		} else {
			this.logger.warn("cannot get object id:{} from student", loginName);
		}
		return false;
	}

	/**
	 * 遍历学生信息
	 */
	public QueryBase query(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		QueryBase queryBase = new QueryBase();
		if ((paramMap.get("page") != null) && (paramMap.get("rows") != null)) {
			long page = Long.parseLong(paramMap.get("page").toString());
			long rows = Long.parseLong(paramMap.get("rows").toString());
			queryBase.setPageSize(Long.valueOf(rows));
			queryBase.setCurrentPage(Long.valueOf(page));
		}
		queryBase.setParameters(paramMap);
		Long rows = this.studentUserMapper.queryRows(queryBase);
		List<StudentUserDTO> results = this.studentUserMapper.query(queryBase);
		queryBase.setResults(results);
		queryBase.setTotalRow(rows);
		return queryBase;
	}

	@Override
	public StudentUserDTO getStuUserDTOByStuId(String studentId) {
		// TODO Auto-generated method stub
		StudentUserDTO studentUserDTO = this.studentUserMapper
				.getStudentUserDTOByStuId(studentId);
		return studentUserDTO;
	}

	@Override
	public int updateStuUserbyDTO(StudentUserDTO studentUserDTO) {
		// TODO Auto-generated method stub
		int rows = this.studentUserMapper.updateByStuIdSelective(studentUserDTO);
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getValue();
	}

	@Override
	public int batchDeleteStudent(Integer[] ids) {
		// TODO Auto-generated method stub
		for (int i = 0; i < ids.length; i++) {
			int id = ids[i];
			if(!(DAOResultUtil.getAddUpDateRemoveResult(studentUserMapper.deleteByPrimaryKey(id), 0).getValue() == 1)) {
				return StatusType.ERROR.getValue();
			}
		}
		return StatusType.SUCCESS.getValue();
	}

}
