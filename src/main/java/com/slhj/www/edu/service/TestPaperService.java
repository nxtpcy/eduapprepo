package com.slhj.www.edu.service;

import com.slhj.www.edu.pojo.TestPaper;

public interface TestPaperService {

	// 向试卷中增加一个题目
	public int add(TestPaper testPaper);

	// 更新试卷和题目的对应关系（也即改变哪套试卷上有哪道题）
	public int update(TestPaper testPaper);

	// 删除某套试卷上的某道题目
	public int delete(TestPaper testPaper);

}