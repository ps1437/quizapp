package com.syscho.quiz.service;

public class QuizService {

	private QuizService quizService = null;

	private QuizService() {

	}

	public QuizService getInstance() {

		if (null == quizService) {
			quizService = new QuizService();
		}
		return quizService;

	}

}
