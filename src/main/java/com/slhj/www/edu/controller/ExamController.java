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
				Session session = currentUser.getSession();
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
	@RequestMapping(value = "/submitPaper", method = RequestMethod.POST)
	@ResponseBody
	public Object submitPaper(HttpServletRequest request,
			HttpServletResponse response, @RequestBody List<ExercisesDTO> stuAnswers, Session session) {
		
		
		
		try {
			int status = exercisesService.judgeAndUpdateScore(stuAnswers, session);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(
					"调用ExamController.submitPaper出错,list={},error={}",
					new Object[] { stuAnswers, e });
			return new Response(StatusType.EXCEPTION.getValue(),
					StatusType.EXCEPTION.getMessage());
		}
	}
	
}
