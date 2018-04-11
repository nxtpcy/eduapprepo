package com.slhj.www.edu.service;

import java.util.List;

import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.pojo.Article;



public interface ArticleService {

	// 增加文章
	int add(Article article);

	// 批量增加文章
	String batchAdd(List<Article> articles);

	// 修改文章
	int update(Article article);

	// 删除文章
	int delete(Article article);

	// 查询文章(按类型查询全部)
	void selectByTypeAll(QueryBase queryBase);
	
	// 查询文章(按类型查询前几条)
	void selectByTypeFirst6(QueryBase queryBase);
	
	// 按类型和标题（模糊查询）查询文章
	public void selectByTypeAndTitle(QueryBase queryBase);

}