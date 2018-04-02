package com.slhj.www.edu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.common.Response;
import com.slhj.www.edu.common.StatusType;
import com.slhj.www.edu.pojo.dto.ExercisesDTO;
import com.slhj.www.edu.service.ExercisesService;
import com.slhj.www.edu.service.ScoreService;

@Controller
@RequestMapping(value = "/score")
public class ScoreController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ScoreService scoreService;

	/**
	 * 按条件查询成绩列表(带分页)
	 * 查询条件可以只传学号，也可学号和试卷号都传
	 * 
	 */
	@RequestMapping(value = "/scoreList", method = RequestMethod.POST)
	@ResponseBody
	public Object scoreList(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
		try {
			QueryBase queryBase = new QueryBase();
			queryBase.addParameter("stuId", map.get("stuId"));
			queryBase.addParameter("paperId", map.get("paperId"));
			queryBase.setPageSize(Long.parseLong(map.get("rows").toString()));
			queryBase
					.setCurrentPage(Long.parseLong(map.get("page").toString()));
			scoreService.selectByStuIdOrPaperIdByPage(queryBase);
			
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("total", queryBase.getTotalRow());
			result.put("result", queryBase.getResults());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用ScoreController.scoreList出错,map={},error={}",
					new Object[] { map, e });
			return new Response(StatusType.EXCEPTION.getValue(),
					StatusType.EXCEPTION.getMessage());
		}
		
	}
	
	
	//查询所有成绩记录（要按学号和试卷号排序，并带分页功能）
	@RequestMapping(value = "/allScoreList", method = RequestMethod.POST)
	@ResponseBody
	public Object allScoreList(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
		try {
			QueryBase queryBase = new QueryBase();
			
			queryBase.setPageSize(Long.parseLong(map.get("rows").toString()));
			queryBase
					.setCurrentPage(Long.parseLong(map.get("page").toString()));
			scoreService.selectAllByPage(queryBase);
			
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("total", queryBase.getTotalRow());
			result.put("result", queryBase.getResults());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用ScoreController.allScoreList出错,map={},error={}",
					new Object[] { map, e });
			return new Response(StatusType.EXCEPTION.getValue(),
					StatusType.EXCEPTION.getMessage());
		}
		
	}
	
	
}