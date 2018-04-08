package com.slhj.www.edu.dao;

import java.util.List;
import java.util.Map;

import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.pojo.Exercises;

public interface ExercisesMapper {
    int deleteByPrimaryKey(Integer id);
    
    int deleteByQuestionId(String questionId);

    int insert(Exercises record);

    int insertSelective(Exercises record);

    Exercises selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Exercises record);

    int updateByPrimaryKey(Exercises record);
    
    Exercises selectByQuestionId(String questionId);
    
    List<Exercises> selectExercisesByPage(QueryBase queryBase);
    
    Long size();
    
}