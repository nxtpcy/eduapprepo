package com.slhj.www.edu.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slhj.www.edu.common.StatusType;
import com.slhj.www.edu.dao.AdminUserMapper;
import com.slhj.www.edu.pojo.AdminUser;
import com.slhj.www.edu.pojo.dto.PasswordDTO;
import com.slhj.www.edu.service.AdminstratorService;
import com.slhj.www.edu.utils.DAOResultUtil;

@Service("AdminstratorService")
public class AdminstratorServiceImpl implements AdminstratorService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminUserMapper adminUserMapper;

	@Override
	public int login(AdminUser adminUser) {
		// TODO Auto-generated method stub
		AdminUser tempUser = null;
		try {
			tempUser = this.adminUserMapper.selectByLogin(adminUser);
			if (tempUser != null) {
				this.logger.info("这是测试用例");
				return StatusType.SUCCESS.getValue();
			}
			return StatusType.PASSWD_NOT_MATCH.getValue();
		} catch (Throwable e) {
			this.logger.error(
					"调用AdminUserServiceImpl.login出错,传入参数user={},查询到user={}",
					new Object[] { adminUser, tempUser }, e);
		}
		return StatusType.EXCEPTION.getValue();
	}

	@Override
	public AdminUser getAdminUser(AdminUser adminUser) {
		// TODO Auto-generated method stub
		AdminUser tempUser = null;
		try {
			tempUser = this.adminUserMapper.selectByLogin(adminUser);
		} catch (Throwable e) {
			this.logger.error(
					"调用AdminUserServiceImpl.getUser出错,传入参数user={},查询到user={}",
					new Object[] { adminUser, tempUser }, e);
		}
		return tempUser;
	}

	@Override
	public int updatePassword(PasswordDTO passwordDTO) {
		// TODO Auto-generated method stub
		if ((passwordDTO == null) || (passwordDTO.getOriginPassword() == null)
				|| (passwordDTO.getNewPassword() == null))
			return StatusType.OBJECT_NULL.getValue();
		int rows = 0;
		try {
			String originPassword = passwordDTO.getOriginPassword();
			String newPassword = passwordDTO.getNewPassword();
			String repetitionPassword = passwordDTO.getRepetitionPassword();
			if (newPassword.equals(repetitionPassword)) {
				String username = passwordDTO.getManagerName();
				AdminUser user = this.adminUserMapper.selectByManagerName(username);
				if (user != null)
					if (user.getPassword().equals(originPassword)) {
						user.setManagerName(username);
						user.setPassword(newPassword);
						rows = this.adminUserMapper.updatePwdByManagerName(user);
					} else {
						return StatusType.PASSWD_NOT_MATCH.getValue();
					}
			}
		} catch (Throwable e) {
			this.logger.error("调用AdminUserServiceImpl.updatePassword出错,passwordDTO={}",
					new Object[] { passwordDTO }, e);
			return StatusType.EXCEPTION.getValue();
		}
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getValue();
	}

	@Override
	public AdminUser getAdminUserByManagerName(String managerName) {
		// TODO Auto-generated method stub
		return this.adminUserMapper.selectByManagerName(managerName);
	}

	@Override
	public boolean checkRoleWhenLogin(String loginName, Byte roleByte) {
		// TODO Auto-generated method stub
		AdminUser currentUser = this.adminUserMapper.selectByManagerName(loginName);
		if(currentUser != null)
			return true;
		return false;
	}

	@Override
	public int updateAdminUserInfo(AdminUser adminUser) {
		// TODO Auto-generated method stub
		int rows = this.adminUserMapper.updateByPrimaryKeySelective(adminUser);
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getValue();
	}

}
