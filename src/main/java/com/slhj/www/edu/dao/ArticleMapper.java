package com.slhj.www.edu.dao;

import java.util.List;
import java.util.Map;

import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.pojo.Article;

public interface ArticleMapper {
	
	int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);
	
	Article selectByTitle(String title);
	
	int updateByTitleSelective(Article article);
	
	Long size(Map parameters);
	
	List<Article> selectByPageAndSelections(QueryBase queryBase);
	
	List<Article> selectByTypeAll(QueryBase queryBase);
}
