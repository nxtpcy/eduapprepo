package com.slhj.www.edu.pojo.dto;

import com.slhj.www.edu.pojo.Exercises;

public class MistakeDTO extends Exercises {
	
	private String stuId;
	private String paperId;
	private String questionId;
	private String question;
	private String selectionA;

    private String selectionB;

    private String selectionC;

    private String selectionD;

    private String correctAnswer;

    
    
	public MistakeDTO(String stuId, String paperId, String questionId, Exercises exercises) {
		super();
		this.stuId = stuId;
		this.paperId = paperId;
		this.questionId = questionId;
		this.question = exercises.getQuestion();
		this.selectionA = exercises.getSelectionA();
		this.selectionB = exercises.getSelectionB();
		this.selectionC = exercises.getSelectionC();
		this.selectionD = exercises.getSelectionD();
		this.correctAnswer = exercises.getCorrectAnswer();
	}

	public MistakeDTO() {}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getSelectionA() {
		return selectionA;
	}

	public void setSelectionA(String selectionA) {
		this.selectionA = selectionA;
	}

	public String getSelectionB() {
		return selectionB;
	}

	public void setSelectionB(String selectionB) {
		this.selectionB = selectionB;
	}

	public String getSelectionC() {
		return selectionC;
	}

	public void setSelectionC(String selectionC) {
		this.selectionC = selectionC;
	}

	public String getSelectionD() {
		return selectionD;
	}

	public void setSelectionD(String selectionD) {
		this.selectionD = selectionD;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
    
    
}
