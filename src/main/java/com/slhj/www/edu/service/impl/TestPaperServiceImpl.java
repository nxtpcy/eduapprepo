package com.slhj.www.edu.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.slhj.www.edu.common.StatusType;
import com.slhj.www.edu.dao.TestPaperMapper;
import com.slhj.www.edu.pojo.TestPaper;
import com.slhj.www.edu.service.TestPaperService;
import com.slhj.www.edu.utils.DAOResultUtil;


@Service("testPaperService")
public class TestPaperServiceImpl implements TestPaperService {

	@Autowired
	private TestPaperMapper testPaperMapper;

	// 向试卷中增加一个题目
	@Override
	public int add(TestPaper testPaper) {
		if (testPaper == null || testPaper.getPaperId() == null || testPaper.getQuestionId() == null) {
			return StatusType.OBJECT_NULL.getValue();
		}
		
		int rows = testPaperMapper.insertSelective(testPaper);
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getValue();
	}

	// 更新试卷和题目的对应关系（也即改变哪套试卷上有哪道题）
	@Override
	public int update(TestPaper testPaper) {
		if (testPaper == null || testPaper.getId() == null
				|| testPaperMapper.selectByPrimaryKey(testPaper.getId()) == null) {
			return StatusType.NOT_EXISTS.getValue();
		}

		int rows = testPaperMapper.updateByPrimaryKeySelective(testPaper);

		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getValue();
	}

	// 删除某套试卷上的某道题目
	@Override
	public int delete(TestPaper testPaper) {
		if (testPaper == null || testPaper.getId() == null
				|| testPaperMapper.selectByPrimaryKey(testPaper.getId()) == null) {
			return StatusType.NOT_EXISTS.getValue();
		}
		int rows = testPaperMapper.deleteByPrimaryKey(testPaper.getId());

		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getValue();
	}
	
}