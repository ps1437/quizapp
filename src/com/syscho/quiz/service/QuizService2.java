package com.syscho.quiz.service;

public class QuizService2 {

	private QuizService2 quizService = null;

	private QuizService2() {

	}

	public QuizService2 getInstance() {

		if (null == quizService) {
			quizService = new QuizService2();
		}
		return quizService;

	}

}
