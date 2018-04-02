package com.slhj.www.edu.service.impl;


import java.util.Date;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.common.StatusType;
import com.slhj.www.edu.dao.ArticleMapper;
import com.slhj.www.edu.pojo.Article;
import com.slhj.www.edu.service.ArticleService;
import com.slhj.www.edu.utils.DAOResultUtil;


@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;
	

	// 增加文章
	@Override
	public int add(Article article) {
		if (article == null || article.getContent() == null || article.getContent().equals("")) {
			return StatusType.OBJECT_NULL.getValue();
		}
		if (articleMapper.selectByTitle(article.getTitle()) != null) {
			return StatusType.EXISTS.getValue();
		}
		article.setArtTime(new Date());
		int rows = articleMapper.insertSelective(article);
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getValue();
	}

	// 修改文章信息
	@Override
	public int update(Article article) {
		if (article == null || article.getId() == null
				|| articleMapper.selectByPrimaryKey(article.getId()) == null) {
			return StatusType.NOT_EXISTS.getValue();
		}

		int rows = articleMapper.updateByPrimaryKeySelective(article);

		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getValue();
	}

	// 删除文章信息
	@Override
	public int delete(Article article) {
		if (article == null || article.getId() == null
				|| articleMapper.selectByPrimaryKey(article.getId()) == null) {
			return StatusType.NOT_EXISTS.getValue();
		}
		int rows = articleMapper.deleteByPrimaryKey(article.getId());

		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getValue();

	}

	// 查询文章信息（按类型查前六条）
	@Override
	public void selectByTypeFirst6(QueryBase queryBase) {
		queryBase.setTotalRow(articleMapper.size(queryBase
				.getParameters()));// 获取总行数
		List<Article> articles = articleMapper
				.selectByPageAndSelections(queryBase);
		queryBase.setResults(articles);// 获取需要返回的数据集
	}

	/**
	 * 批量增加文章
	 * 
	 * 
	 * @return 返回第几条数据在数据库中已经存在的信息
	 */
	@Override
	public String batchAdd(List<Article> articles) {
		
		int successNumber = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < articles.size(); i++) {
			Article article = articles.get(i);
			if (articleMapper.selectByTitle(article.getTitle()) == null) {
				successNumber++;
				article.setArtTime(new Date());
				articleMapper.insertSelective(article);
			} else {
				
				sb.append("第" + (i+1) + "篇文章在数据库中已经存在</br>");
			}
		}
		return sb.toString();
	}


	//按类型查询全部文章
	@Override
	public void selectByTypeAll(QueryBase queryBase) {
		queryBase.setTotalRow(articleMapper.size(queryBase
				.getParameters()));// 获取总行数
		List<Article> articles = articleMapper
				.selectByTypeAll(queryBase);
		queryBase.setResults(articles);// 获取需要返回的数据集
	}
}
