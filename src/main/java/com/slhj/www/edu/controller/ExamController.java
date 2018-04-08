package com.slhj.www.edu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.common.Response;
import com.slhj.www.edu.common.StatusType;
import com.slhj.www.edu.pojo.dto.ExercisesDTO;
import com.slhj.www.edu.service.ExercisesService;

@Controller
@RequestMapping(value = "/exam")
public class ExamController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ExercisesService exercisesService;

	/**
	 * 查询所有试卷列表(分页)
	 * @return
	 */
	@RequestMapping(value = "/paperList", method = RequestMethod.POST)
	@ResponseBody
	public Object paperList(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
		try {
			QueryBase queryBase = new QueryBase();
			queryBase.setPageSize(Long.parseLong(map.get("rows").toString()));
			queryBase
					.setCurrentPage(Long.parseLong(map.get("page").toString()));
			exercisesService.selectPaperIdsByPage(queryBase);
			
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("total", queryBase.getTotalRow());
			result.put("result", queryBase.getResults());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用ExamController.paperList出错,map={},error={}",
					new Object[] { map, e });
			return new Response(StatusType.EXCEPTION.getValue(),
					StatusType.EXCEPTION.getMessage());
		}
		
	}
	
	/**
	 * 点击了某一套试卷准备做题时
	 * @return
	 */
	@RequestMapping(value = "/exam", method = RequestMethod.POST)
	@ResponseBody
	public Object exam(HttpServletRequest request,
			HttpServletResponse response, @RequestBody Map<String, Object> map) {
		try {
			Subject currentUser = SecurityUtils.getSubject();
			if (currentUser != null) {
				Session session = currentUser.getSession(false);
				if (session != null) {
					QueryBase queryBase = new QueryBase();
					queryBase.addParameter("paperId", map.get("paperId"));
					exercisesService.selectByPaperId(queryBase, session);
					
					HashMap<String, Object> result = new HashMap<String, Object>();
					result.put("total", queryBase.getTotalRow());
					result.put("result", queryBase.getResults());
					return result;
				}
			} 
			return new Response(StatusType.UNAUTHORIZED.getValue(),
					StatusType.UNAUTHORIZED.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用ExamController.exam出错,map={},error={}",
					new Object[] { map, e });
			return new Response(StatusType.EXCEPTION.getValue(),
					StatusType.EXCEPTION.getMessage());
		}
		
	}
	
	//提交试卷并更新分数，插入错题
	/*前端传入json串，通过@RequestBody注解注入controller的入参stuAnswers
	[
		{"questionId": "008", "stuAnswer": "a"},
		{"questionId": "009", "stuAnswer": "c"},
		{"questionId": "010", "stuAnswer": "b"},
		{"questionId": "011", "stuAnswer": "a"},
		{"questionId": "012", "stuAnswer": "c"}
	]
	*/
	@RequestMapping(value = "/submitPaper", method = RequestMethod.POST)
	@ResponseBody
	public Object submitPaper(HttpServletRequest request,
			HttpServletResponse response, @RequestBody String stuAnswers) {
		int status = StatusType.EXAM_TIMEOUT.getValue();
		String message = StatusType.EXAM_TIMEOUT.getMessage();
		
		try {
			Subject currentUser = SecurityUtils.getSubject();
			if (currentUser != null) {
				Session session = currentUser.getSession(false);
				if (session != null) {
					
					List<ExercisesDTO> stuAnswersList = JSON.parseObject(stuAnswers, new TypeReference<List<ExercisesDTO>>(){});
					status = exercisesService.judgeAndUpdateScore(stuAnswersList, session);
					message = StatusType.value(status).getMessage();
				} 
				return new Response(status, message);
				
			}
			
			status = StatusType.UNAUTHORIZED.getValue();
			message = StatusType.UNAUTHORIZED.getMessage(); 
			return new Response(status, message);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(
					"调用ExamController.submitPaper出错,string={},error={}",
					new Object[] { stuAnswers, e });
			return new Response(StatusType.EXCEPTION.getValue(),
					StatusType.EXCEPTION.getMessage());
		}
	}
	
}