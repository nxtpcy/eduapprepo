package com.slhj.www.edu.dao;

import java.util.List;
import java.util.Map;

import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.pojo.Score;

public interface ScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Score record);

    int insertSelective(Score record);

    Score selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Score record);

    int updateByPrimaryKey(Score record);
    
    List<Score> selectByStuIdOrPaperIdByPage(QueryBase queryBase);
    
    Score selectByStuIdAndPaperId(Map<String, Object> map);
    
    Long size(Map<String, Object> map);
    
    List<Score> selectAllByPage(QueryBase queryBase);
}