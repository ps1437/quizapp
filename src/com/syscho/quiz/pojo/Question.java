package com.syscho.quiz.pojo;



public class Question  {

	private String question;

	private String quesLevel;

	private String quesTag;

	public String getQuestion() {
		return question;
	}

	public Question(String question,  String quesLevel,String quesTag) {
		super();
		this.question = question;
		this.quesLevel = quesLevel;
		this.quesTag = quesTag;
	}
	
	
	public Question() {
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuesLevel() {
		return quesLevel;
	}

	public void setQuesLevel(String quesLevel) {
		this.quesLevel = quesLevel;
	}

	public String getQuesTag() {
		return quesTag;
	}

	public void setQuesTag(String quesTag) {
		this.quesTag = quesTag;
	}

}
