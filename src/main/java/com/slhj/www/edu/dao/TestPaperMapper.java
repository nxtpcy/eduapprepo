package com.slhj.www.edu.dao;

import java.util.List;
import java.util.Map;

import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.pojo.TestPaper;

public interface TestPaperMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestPaper record);

    int insertSelective(TestPaper record);

    TestPaper selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestPaper record);

    int updateByPrimaryKey(TestPaper record);
    
    List<String> selectPaperIdByPage(QueryBase queryBase);
    
    Long size(Map<String, Object> map);
    
    List<TestPaper> selectByPaperId(QueryBase queryBase);
    
    Long selectDistinctPaperIdSize();
    
    
}