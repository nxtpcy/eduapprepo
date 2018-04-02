package com.slhj.www.edu.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.dao.ScoreMapper;
import com.slhj.www.edu.pojo.Score;
import com.slhj.www.edu.service.ScoreService;

@Service("scoreService")
public class ScoreServiceImpl implements ScoreService {
	
	@Autowired
	private ScoreMapper scoreMapper; 
	
	
	
	
	
	//可查一个学生的所有成绩或该学生某一套试卷的成绩（分页）
	@Override
	public void selectByStuIdOrPaperIdByPage(QueryBase queryBase) {
		
		queryBase.setTotalRow(scoreMapper.size(queryBase
				.getParameters()));// 获取查询到的总记录数
		List<Score> scoreList = scoreMapper.selectByStuIdOrPaperIdByPage(queryBase);
				
		queryBase.setResults(scoreList);// 设置需要返回的多条成绩记录 
	}





	@Override
	public void selectAllByPage(QueryBase queryBase) {
		queryBase.setTotalRow(scoreMapper.size(queryBase
				.getParameters()));// 获取查询到的总记录数
		List<Score> scoreList = scoreMapper.selectAllByPage(queryBase);
				
		queryBase.setResults(scoreList);// 设置需要返回的多条成绩记录 
		
	}
	
}