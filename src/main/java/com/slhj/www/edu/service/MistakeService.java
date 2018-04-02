package com.slhj.www.edu.service;

import com.slhj.www.edu.common.QueryBase;

public interface MistakeService {

	//可查一个学生的所有错题或该学生某套试卷的错题（带分页）
	public void selectByStuIdOrPaperIdByPage(QueryBase queryBase);
	
	//查询所有错题记录（带分页）
	public void selectAllByPage(QueryBase queryBase);

}