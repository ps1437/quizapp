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

/**
 * @author Soni
 *
 */
public class QuizReader {

	private QuizReader() {

	}

	private static QuizReader quizReader = null;

	/**
	 * @return QuizReader instance
	 */
	public static QuizReader getInstance() {

		if (null == quizReader) {
			return new QuizReader();
		}
		return quizReader;
	}

	private final int QUIZ_SIZE = 10;
	private final int TAG_SIZE = 6;

	/**
	 * @param filename
	 * @Method use to read the input file
	 * @return List of Question
	 */

	@SuppressWarnings("hiding")
	public List<Question> readQuiz(String fileName) {

		List<Question> list = null;
		Scanner input = null;
		try {
			input = new Scanner(new File(fileName));

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
				File file = new File(outputFile);
				if (!file.exists()) {
					file.createNewFile();
				}
				out = new FileOutputStream(outputFile);
			} /*
				 * else { File file = new File("D:\\output.txt"); if
				 * (!file.exists()) { file.createNewFile(); } out = new
				 * FileOutputStream(file);
				 * 
				 * }
				 */
			Random rand = new Random();
			QuizReader reader = new QuizReader();

			for (int index = 0; index < readQuiz.size(); index++) {

				quiz = new Question[10];
				tags = new ArrayList<String>();

				if (readQuiz.size() > 9) {

					for (int i = 0; i < QUIZ_SIZE; i++) {

						int quizIndex = rand.nextInt(readQuiz.size());

						Question question = readQuiz.get(quizIndex);

						if (!tags.contains(question.getQuesTag())) {

							tags.add(question.getQuesTag());
						}

						if (i == (QUIZ_SIZE - (TAG_SIZE - tags.size()))) {

							question = reader.generateQues(reader, rand, readQuiz, tags);
							quiz[i] = question;

						} else {
							quiz[i] = question;
							readQuiz.remove(quizIndex);
						}
						if (out != null) {
							out.write(question.getQuestion().getBytes());
							out.write("     ".getBytes());
							out.write(question.getQuesTag().getBytes());
							out.write("     ".getBytes());
							out.write(question.getQuesLevel().getBytes());
							out.write("\n".getBytes());

						}

					}
					if (null != out) {
						out.write("\n--------------------------------------------------------------------------------"
								.getBytes());
					}
					if (quiz.length == 10) {

						quizCount = quizCount + 1;
					}

				}
			}
		} catch (FileNotFoundException exp) {

			exp.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
		return quizCount;
	}

	public Question generateQues(QuizReader reader, Random rands, List<Question> readQuiz, List<String> tags) {

		int quizIndex = rands.nextInt(readQuiz.size());
		Question question = readQuiz.get(quizIndex);
		if (!tags.contains(question.getQuesTag())) {
			tags.add(question.getQuesTag());
			readQuiz.remove(quizIndex);
			return question;
		} else {
			return reader.generateQues(reader, rands, readQuiz, tags);
		}
	}
}
