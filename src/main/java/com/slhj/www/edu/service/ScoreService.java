package com.slhj.www.edu.service;

import com.slhj.www.edu.common.QueryBase;

public interface ScoreService {

	//可查一个学生的所有成绩或该学生某一套试卷的成绩（分页）
	public void selectByStuIdOrPaperIdByPage(QueryBase queryBase);
	
	//查询所有成绩记录（带分页）
	public void selectAllByPage(QueryBase queryBase);

}