package com.syscho.quiz.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.syscho.quiz.pojo.Question;

public class QuizReader {

	private QuizReader() {

	}

	private static QuizReader quizReader = null;

	public static QuizReader getInstance() {

		if (null == quizReader) {
			return new QuizReader();
		}
		return quizReader;
	}

	/*
	 * Sample Data
	 *  Q1|HARD |Tag1
	 *   Q2|EASY|Tag2 Q3|MEDIUM|Tag3 Q4|EASY|Tag4
	 * Q5|HARD |Tag5 Q6|EASY|Tag6 Q7|HARD |Tag1 Q8|EASY|Tag2 Q9|MEDIUM|Tag3
	 * Q10|EASY|Tag4 Q11|HARD |Tag5 Q12|EASY|Tag6 Q13|HARD |Tag1 Q14|EASY|Tag2
	 * Q15|MEDIUM|Tag3 Q16|EASY|Tag4 Q17|HARD |Tag5
	 * 
	 * 
	 * readQuiz - holding the above data in Question object with
	 * question,quesLevel ,quesTag property
	 */
	private final int QUIZ_SIZE = 10;
	private final int TAG_SIZE = 6;

	public List<Question> readQuiz(String fileName) {

		List<Question> list = null;
		Scanner input = null;
		try {
			input = new Scanner(new File("D:\\Test.txt"));

			list = new ArrayList<Question>();

			while (input.hasNextLine()) {

				StringTokenizer token = new StringTokenizer(input.nextLine(), "|");

				if (token.countTokens() == 3)
					list.add(new Question(token.nextToken().toUpperCase(), token.nextToken().toUpperCase(),
							token.nextToken().toUpperCase()));
			}
			System.out.println(list.size());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			input.close();
		}
		return list;

	}

	public int generateQuiz(List<Question> readQuiz, String outputFile) {
		int quizCount = 0;

		FileOutputStream out = null;
		Question quiz[] = null;
		List<String> tags = null;
		try {

			if (outputFile != null) {
				out = new FileOutputStream(outputFile);
			} else
				out = new FileOutputStream("D:\\output.txt");

			Random rand = new Random();
			QuizReader reader = new QuizReader();

			for (int index = 0; index < readQuiz.size(); index++) {

				quiz = new Question[10];
				tags = new ArrayList<String>();

				for (int i = 0; i < QUIZ_SIZE; i++) {

					int quizIndex = rand.nextInt(readQuiz.size());
					Question question = readQuiz.get(quizIndex);

					if (!tags.contains(question.getQuesTag())) {

						tags.add(question.getQuesTag());
					}

					if (i == (QUIZ_SIZE - (TAG_SIZE - tags.size()))) {

						Question generateQues = reader.generateQues(reader, rand, readQuiz, tags);
						quiz[i] = generateQues;

					} else {
						quiz[i] = question;
						if (outputFile != null)
							out.write("   \n  ".getBytes());
						out.write(question.getQuestion().getBytes());
						out.write("     ".getBytes());
						out.write(question.getQuesTag().getBytes());
						out.write("     ".getBytes());
						out.write(question.getQuesLevel().getBytes());
						out.write("\n".getBytes());
						readQuiz.remove(quizIndex);
					}
				}
				out.write("\n--------------------------------------------------------------------------------"
						.getBytes());
				if (quiz.length == 10) {
					System.out.println("tags" + tags);
					quizCount = quizCount + 1;
				}

			}

		} catch (FileNotFoundException exp) {

			exp.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
		return quizCount;
	}

	public Question generateQues(QuizReader reader, Random rand, List<Question> readQuiz, List<String> tags) {

		int quizIndex = rand.nextInt(readQuiz.size());
		Question question = readQuiz.get(quizIndex);

		if (!tags.contains(question.getQuesTag())) {
			tags.add(question.getQuesTag());
			readQuiz.remove(quizIndex);
		} else
			reader.generateQues(reader, rand, readQuiz, tags);

		return question;
	}
}
