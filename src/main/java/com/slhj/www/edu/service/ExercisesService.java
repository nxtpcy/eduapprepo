package com.slhj.www.edu.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.session.Session;

import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.pojo.Exercises;
import com.slhj.www.edu.pojo.dto.ExercisesDTO;



public interface ExercisesService {

	// 增加题目
	public int add(Exercises exercises);

	// 更新题目信息（加入对应学号及学生答案）
	public int update(Exercises exercises);

	// 删除题目
	public int delete(Exercises exercises);

	//按paperId查出对应试卷上的全部题目
	public void selectByPaperId(QueryBase queryBase, Session session);

	// 查询全部试卷对应的试卷号paperId的集合 
	public void selectPaperIdsByPage(QueryBase queryBase);
	
	//分页查询所有题目 
	public void selectExercisesByPage(QueryBase queryBase);
	
	//判分方法：插入错题并同时更新学生得分。 
	public int judgeAndUpdateScore(List<ExercisesDTO> stuAnswers, Session session);

}