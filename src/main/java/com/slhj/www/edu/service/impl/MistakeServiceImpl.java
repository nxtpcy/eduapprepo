package com.slhj.www.edu.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.dao.ExercisesMapper;
import com.slhj.www.edu.dao.MistakeMapper;
import com.slhj.www.edu.dao.ScoreMapper;
import com.slhj.www.edu.pojo.Exercises;
import com.slhj.www.edu.pojo.Mistake;
import com.slhj.www.edu.pojo.Score;
import com.slhj.www.edu.pojo.dto.MistakeDTO;
import com.slhj.www.edu.service.MistakeService;
import com.slhj.www.edu.service.ScoreService;

@Service("mistakeService")
public class MistakeServiceImpl implements MistakeService {
	
	@Autowired
	private MistakeMapper mistakeMapper; 
	
	
	@Autowired
	private ExercisesMapper exercisesMapper; 
	
	
	//可查一个学生的所有错题或该学生某一套试卷的错题（分页）
	@Override
	public void selectByStuIdOrPaperIdByPage(QueryBase queryBase) {
		
		queryBase.setTotalRow(mistakeMapper.size(queryBase
				.getParameters()));// 获取查询到的总记录数
		List<Mistake> mistakeList = mistakeMapper.selectByStuIdOrPaperIdByPage(queryBase);
		List<MistakeDTO> mistakeDTOList = new ArrayList<MistakeDTO>();
		for (Mistake mistake : mistakeList) {
			Exercises exercises = exercisesMapper.selectByQuestionId(mistake.getQuestionId());
			if (exercises != null) {
				MistakeDTO mistakeDTO = new MistakeDTO(mistake.getStuId(), mistake.getPaperId(), mistake.getQuestionId(), exercises);
				mistakeDTOList.add(mistakeDTO);
			}
			
		}
				
		queryBase.setResults(mistakeDTOList);// 设置需要返回的多条错题记录 
	}




	//查询全部错题记录（要排序，带分页）
	@Override
	public void selectAllByPage(QueryBase queryBase) {
		queryBase.setTotalRow(mistakeMapper.size(queryBase
				.getParameters()));// 获取查询到的总记录数
		List<Mistake> mistakeList = mistakeMapper.selectAllByPage(queryBase);
		List<MistakeDTO> mistakeDTOList = new ArrayList<MistakeDTO>();
		for (Mistake mistake : mistakeList) {
			Exercises exercises = exercisesMapper.selectByQuestionId(mistake.getQuestionId());
			if (exercises != null) {
				MistakeDTO mistakeDTO = new MistakeDTO(mistake.getStuId(), mistake.getPaperId(), mistake.getQuestionId(), exercises);
				mistakeDTOList.add(mistakeDTO);
			}
			
		}		
		queryBase.setResults(mistakeDTOList);// 设置需要返回的多条错题记录 
	}
	
}