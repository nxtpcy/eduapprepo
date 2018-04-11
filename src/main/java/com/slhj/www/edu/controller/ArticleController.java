package com.slhj.www.edu.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.common.Response;
import com.slhj.www.edu.common.StatusType;
import com.slhj.www.edu.pojo.Article;
import com.slhj.www.edu.service.ArticleService;


@Controller
@RequestMapping("/article/")
public class ArticleController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	//文章service
	@Autowired
	private ArticleService articleService;

	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody //方法返回值的Java对象可通过HttpMessageConverter转换为HttpOutputMessage，进而转为一个response返回给客户端 （body里是json对象的形式 ）
	public Object add(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Article article) {
		try {
			int status = articleService.add(article);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(
					"调用ArticleController.add出错,article={},error={}",
					new Object[] { article, e });
			return new Response(StatusType.EXCEPTION.getValue(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Object update(HttpServletRequest request,
			HttpServletResponse response, @RequestBody Article article) {
		try {
			int status = articleService.update(article);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(
					"调用ArticleController.update出错,article={},error={}",
					new Object[] { article, e });
			return new Response(StatusType.EXCEPTION.getValue(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(HttpServletRequest request,
			HttpServletResponse response, @RequestBody Article article) {
		try {
			int status = articleService.delete(article);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用ArticleController.delete出错,article={},error={}",
					new Object[] { article, e });
			return new Response(StatusType.EXCEPTION.getValue(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	//按类别查询前六条
	@RequestMapping(value = "selectByTypeFirstN", method = RequestMethod.POST)
	@ResponseBody
	public Object selectByTypeFirstN(HttpServletRequest request,
			HttpServletResponse response, @RequestBody Map map) {
		try {
			QueryBase querybase = new QueryBase();
			
			
			
			querybase.addParameter("artType", map.get("artType"));
			querybase.setPageSize(Long.parseLong(map.get("rows").toString()));
			querybase
					.setCurrentPage(Long.parseLong(map.get("page").toString()));
			articleService.selectByTypeFirst6(querybase);
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("total", querybase.getTotalRow());
			result.put("result", querybase.getResults());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用ArticleController.selectByTypeFirst6出错,map={},error={}",
					new Object[] { map, e });
			return new Response(StatusType.EXCEPTION.getValue(),
					StatusType.EXCEPTION.getMessage());
		}
	}
	
	//按类别查询所有
	@RequestMapping(value = "selectByTypeAll", method = RequestMethod.POST)
	@ResponseBody
	public Object selectByTypeAll(HttpServletRequest request,
			HttpServletResponse response, @RequestBody Map map) {
		try {
			QueryBase querybase = new QueryBase();
			
			
			
			querybase.addParameter("artType", map.get("artType"));
			
			articleService.selectByTypeAll(querybase);
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("total", querybase.getTotalRow());
			result.put("result", querybase.getResults());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用ArticleController.selectByTypeAll出错,map={},error={}",
					new Object[] { map, e });
			return new Response(StatusType.EXCEPTION.getValue(),
					StatusType.EXCEPTION.getMessage());
		}
	}
	
	// 按类型和标题（模糊查询）查询文章
	@RequestMapping(value = "selectByTypeAndTitle", method = RequestMethod.POST)
	@ResponseBody
	public Object selectByTypeAndTitle(HttpServletRequest request,
			HttpServletResponse response, @RequestBody Map map) {
		try {
			QueryBase querybase = new QueryBase();
			
			
			
			querybase.addParameter("artType", map.get("artType"));
			querybase.addParameter("title", map.get("title"));
			querybase.setPageSize(Long.parseLong(map.get("rows").toString()));
			querybase
					.setCurrentPage(Long.parseLong(map.get("page").toString()));
			
			articleService.selectByTypeAndTitle(querybase);
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("total", querybase.getTotalRow());
			result.put("result", querybase.getResults());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用ArticleController.selectByTypeAndTitle出错,map={},error={}",
					new Object[] { map, e });
			return new Response(StatusType.EXCEPTION.getValue(),
					StatusType.EXCEPTION.getMessage());
		}
	}
	

}
