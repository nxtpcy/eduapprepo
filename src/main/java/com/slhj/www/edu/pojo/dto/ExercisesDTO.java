package com.slhj.www.edu.pojo.dto;

import com.slhj.www.edu.pojo.Exercises;

public class ExercisesDTO extends Exercises{

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
	
	
}
