package com.slhj.www.edu.dao;

import java.util.List;
import java.util.Map;

import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.pojo.Mistake;

public interface MistakeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Mistake record);

    int insertSelective(Mistake record);

    Mistake selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Mistake record);

    int updateByPrimaryKey(Mistake record);
    
    Long size(Map<String, Object> map);
    
    List<Mistake> selectByStuIdOrPaperIdByPage(QueryBase queryBase);
    
    List<Mistake> selectAllByPage(QueryBase queryBase);
    
    Mistake selectOneExist(QueryBase queryBase);
    
}