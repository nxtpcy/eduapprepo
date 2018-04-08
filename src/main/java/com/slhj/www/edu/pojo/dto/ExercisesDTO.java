package com.slhj.www.edu.pojo.dto;

import java.io.Serializable;


public class ExercisesDTO implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3784662070729620039L;
	
	private String questionId;
	private String stuAnswer;
	
	
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getStuAnswer() {
		return stuAnswer;
	}
	public void setStuAnswer(String stuAnswer) {
		this.stuAnswer = stuAnswer;
	}
	public ExercisesDTO(String questionId, String stuAnswer) {
		this.questionId = questionId;
		this.stuAnswer = stuAnswer;
	}
	public ExercisesDTO() {
		
	}
		
}