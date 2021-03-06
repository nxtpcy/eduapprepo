package com.slhj.www.edu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.slhj.www.edu.common.QueryBase;
import com.slhj.www.edu.common.StatusType;
import com.slhj.www.edu.dao.ExercisesMapper;
import com.slhj.www.edu.dao.MistakeMapper;
import com.slhj.www.edu.dao.ScoreMapper;
import com.slhj.www.edu.dao.StudentUserMapper;
import com.slhj.www.edu.dao.TestPaperMapper;
import com.slhj.www.edu.pojo.Exercises;
import com.slhj.www.edu.pojo.Mistake;
import com.slhj.www.edu.pojo.Score;
import com.slhj.www.edu.pojo.StudentUser;
import com.slhj.www.edu.pojo.TestPaper;
import com.slhj.www.edu.pojo.dto.ExercisesDTO;
import com.slhj.www.edu.service.ExercisesService;
import com.slhj.www.edu.utils.DAOResultUtil;
import com.alibaba.fastjson.JSON;
import com.slhj.www.edu.common.*;

@Service("exercisesService")
public class ExercisesServiceImpl implements ExercisesService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	TransactionTemplate txTemplate;
	
	//注入错题mapper 
	@Autowired 
	private MistakeMapper mistakeMapper;
	
	@Autowired
	private ScoreMapper scoreMapper; 
	
	@Autowired
	private ExercisesMapper exercisesMapper;
	@Autowired 
	private TestPaperMapper testPaperMapper;
	
	// 增加题目
	@Override
	public int add(Exercises exercises) {
		if (exercises == null || exercises.getQuestionId() == null || 
				exercises.getQuestionId().equals("") || exercises.getQuestion() == null) {
			return StatusType.OBJECT_NULL.getValue();
		}
		
		int rows = exercisesMapper.insertSelective(exercises);
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getValue();
	}

	// 更新题目信息
	@Override
	public int update(Exercises exercises) {
		if (exercises == null || exercises.getQuestionId() == null
				|| exercisesMapper.selectByQuestionId(exercises.getQuestionId()) == null) {
			return StatusType.NOT_EXISTS.getValue();
		}

		int rows = exercisesMapper.updateByPrimaryKeySelective(exercises);

		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getValue();
	}

	// 删除题目
	@Override
	public int delete(Exercises exercises) {
		if (exercises == null || exercises.getQuestionId() == null
				|| exercisesMapper.selectByQuestionId(exercises.getQuestionId()) == null) {
			return StatusType.NOT_EXISTS.getValue();
		}
		int rows = exercisesMapper.deleteByQuestionId(exercises.getQuestionId());

		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getValue();
	}

	
	//按paperId查出对应试卷上的全部题目
	@Override
	public void selectByPaperId(QueryBase queryBase, Session session) {
		//在session中放入paperId
		session.setAttribute("paperId", queryBase.getParameter("paperId"));
		
		queryBase.setTotalRow(testPaperMapper.size(queryBase
				.getParameters()));// 获取该份试卷的题目总数
		List<TestPaper> list = testPaperMapper.selectByPaperId(queryBase);
		List<Exercises> exercisesList = new ArrayList<Exercises>();
		
		for (TestPaper testPaper : list) {
			Exercises exercises = exercisesMapper.selectByQuestionId(testPaper.getQuestionId()); 
			if (exercises != null) {
				exercisesList.add(exercises);
				session.setAttribute(exercises.getQuestionId(), exercises.getCorrectAnswer());
			}
			
		}		
		queryBase.setResults(exercisesList);// 设置需要返回的该套试卷上的题目
	}
	
	// 查询全部试卷对应的试卷号paperId的集合 
	@Override
	public void selectPaperIdsByPage(QueryBase queryBase) {
		List<String> paperIds = testPaperMapper.selectPaperIdByPage(queryBase);
		queryBase.setTotalRow(testPaperMapper.selectDistinctPaperIdSize());// 设置paperId的总个数 
		
		queryBase.setResults(paperIds);// 获取需要返回的数据集
	}
	
	
	@Override
	public void selectExercisesByPage(QueryBase queryBase) {
		queryBase.setTotalRow(exercisesMapper.size());// 设置题目总数		
		List<Exercises> exercisesList = exercisesMapper.selectExercisesByPage(queryBase);
		queryBase.setResults(exercisesList);// 获取需要返回的数据集
	}
	
	//学生提交考卷时调用。（因为同时操作两张表，所以要加上事务管理 ）
	//插入错题，同时计算试卷分数，在score表中插入一行成绩，插入前要根据学号和试卷号判断分数是否存在，若存在，直接更新即可。
	@Override
	public int judgeAndUpdateScore(List<ExercisesDTO> stuAnswers, Session session) {
		if (stuAnswers == null) {
			return StatusType.OBJECT_NULL.getValue();
		}
		
		final List<ExercisesDTO> stuAnswersList = stuAnswers;
		final Session sessionRef = session;
		
		int statusCode = -1;
		try {
			statusCode = txTemplate.execute(new TransactionCallback<Integer>() {
				@Override
				public Integer doInTransaction(TransactionStatus status) {
					int mistakeNumber = 0; // 错误题目数
					StudentUser studentUser = (StudentUser) sessionRef.getAttribute("currentUser");
					String stuId = studentUser.getStuId();
					String paperId = (String)sessionRef.getAttribute("paperId");
					int correctNumber = 0;
					for (ExercisesDTO exercisesDTO : stuAnswersList) {
						//从session中取出该题的正确答案
						String correctAnswer = (String)sessionRef.getAttribute(exercisesDTO.getQuestionId());
						//学生答案
						String stuAns = exercisesDTO.getStuAnswer();
						//如果学生答案正确则答对题数加1
						if (correctAnswer != null && stuAns != null && stuAns.equalsIgnoreCase(correctAnswer)) {
							correctNumber++;
						} else {
							
							//学生答案不正确，不加分，但要插入错题
							//判断之前是否已有过该学生该套试卷相同题目的错题记录，若有，则可以不用再次插入
							QueryBase queryBase = new QueryBase();
							queryBase.addParameter("stuId", stuId);
							queryBase.addParameter("paperId", paperId);
							queryBase.addParameter("questionId", exercisesDTO.getQuestionId());
							
							Mistake mistake = mistakeMapper.selectOneExist(queryBase);
							if (mistake == null) {
								//不存在相同错题的记录，因此要插入
								mistake = new Mistake();
								mistake.setPaperId(paperId);
								mistake.setQuestionId(exercisesDTO.getQuestionId());
								mistake.setStuId(stuId);
								
								//插入一道错题
								mistakeNumber += mistakeMapper.insertSelective(mistake);
							} else {
								//已存在相同记录，则无须再插入一次了 
								mistakeNumber++;
							}
							
						} 
					}
					//计算分数
					int  examScore = correctNumber * Constants.SCORE_OF_EVERY_EXERCISES;
					
					//更新分数score，先要根据学号和paperId判断是否已经有过一条记录（该学生之前已做过这套试卷）
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("stuId", stuId);
					map.put("paperId", paperId);
					Score score = scoreMapper.selectByStuIdAndPaperId(map);
					if (score != null) {
						//说明已存在一条记录，直接更新该纪录的分数字段即可。
						score.setScore(examScore);
						scoreMapper.updateByPrimaryKeySelective(score);
					} else {
						//插入一条新的分数记录
						score = new Score();
						score.setPaperId(paperId);
						score.setScore(examScore);
						score.setStuId(stuId);
						scoreMapper.insertSelective(score);
					}
					if (mistakeNumber + correctNumber == Constants.NUMBER_OF_EXERCISES_ON_ONE_PAPER) {
						return StatusType.SUCCESS.getValue();
					} else {
						return StatusType.ERROR.getValue();
					}
					
				}
			});
		} catch (Exception e) {
			logger.error(
					"调用ExercisesServiceImpl.judgeAndUpdateScore出错,list={},e={}",
					new Object[] { stuAnswers, e});
		}
		
		return statusCode;
	}
	
}