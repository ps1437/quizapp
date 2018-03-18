package com.syscho.quiz.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.syscho.quiz.pojo.Question;
import com.syscho.quiz.util.StringUtil;

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

	private final String MEDIUM_LEVEL = "MEDIUM";

	private final String EASY_LEVEL = "EASY";
	private final String HARD_LEVEL = "HARD";
	private int noOfQues = 0;

	private boolean TAGNOTFOUND = true;

	/**
	 * @param filename
	 * @Method use to read the input file and store the data into 3 list based o
	 *  Question level (HARD,MEDIUM,EASY)
	 * @return List of Question
	 */

	@SuppressWarnings("hiding")
	public Map<String, List<Question>> readQuiz(String fileName) {

		Map<String, List<Question>> listMap = null;

		List<Question> easyQues = new ArrayList<Question>();

		List<Question> midQues = new ArrayList<Question>();

		List<Question> hardQues = new ArrayList<Question>();

		Scanner input = null;
		try {
			input = new Scanner(new File(fileName));

			listMap = new HashMap<String, List<Question>>();

			while (input.hasNextLine()) {

				StringTokenizer token = new StringTokenizer(input.nextLine(), "|");

				if (token.countTokens() == 3) {

					noOfQues = noOfQues + 1;

					Question question = new Question(StringUtil.notNullTrim(token.nextToken()),
							StringUtil.notNullTrim(token.nextToken()), StringUtil.notNullTrim(token.nextToken()));

					if (question.getQuesLevel().equalsIgnoreCase(EASY_LEVEL)) {
						easyQues.add(question);

					} else if (question.getQuesLevel().equalsIgnoreCase(MEDIUM_LEVEL)) {
						midQues.add(question);
					} else if (question.getQuesLevel().equalsIgnoreCase(HARD_LEVEL)) {
						hardQues.add(question);
					}

				}

			}


			
			listMap.put(EASY_LEVEL, easyQues);
			listMap.put(MEDIUM_LEVEL, midQues);
			listMap.put(HARD_LEVEL, hardQues);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			input.close();
		}
		return listMap;

	}

	/**
	 * @param readQuiz , outputFile
	 * @Method Use to create the set of question (10 ) per quiz
	 * @return int (quiz count)
	 */
	
	public int generateQuiz(Map<String, List<Question>> readQuiz, String outputFile) {
		int quizCount = 0;

		FileOutputStream out = null;
		Question quiz[] = null;
		List<String> tagsList = null;

		try {
			List<Question> easyQuesList = readQuiz.get(EASY_LEVEL);
			List<Question> midQuesList = readQuiz.get(MEDIUM_LEVEL);
			List<Question> hardQuesList = readQuiz.get(HARD_LEVEL);

			if (outputFile != null) {
				File file = new File(outputFile);
				if (!file.exists()) {
					file.createNewFile();
				}
				out = new FileOutputStream(outputFile);
			}

			Random rand = new Random();

			QuizReader quizReaderobj = QuizReader.getInstance();
			
			if (noOfQues < 10) {
				System.out.println("Input file is not having sufficient data ");
				return 0;
			}
			for (int index = 0; index < noOfQues; index++) {

				
				if (noOfQues > 9 && (easyQuesList.size() != 0 && midQuesList.size() != 0 && hardQuesList.size() != 0)) {

					int counter = 0;
					quiz = new Question[10];
					tagsList = new ArrayList<String>();

					for (int i = 0; i < QUIZ_SIZE; i++) {

						TAGNOTFOUND = true;
						if (easyQuesList.size() != 0 && (i == 0 || i == 2)) {

							quiz[i] = quizReaderobj.addQuestion(rand, easyQuesList);
							tagsList = quizReaderobj.addTag(tagsList, quiz[i]);
							noOfQues = noOfQues - 1;
						} else

						if (midQuesList.size() != 0 && (i == 3 || i == 5)) {
							quiz[i] = quizReaderobj.addQuestion(rand, midQuesList);
							tagsList = quizReaderobj.addTag(tagsList, quiz[i]);
							noOfQues = noOfQues - 1;

						} else

						if (hardQuesList.size() != 0 && (i == 1 || i == 4)) {
							quiz[i] = quizReaderobj.addQuestion(rand, hardQuesList);

							tagsList = quizReaderobj.addTag(tagsList, quiz[i]);
							noOfQues = noOfQues - 1;
						} else {

							if ((TAGNOTFOUND && easyQuesList.size() != 0) || (TAGNOTFOUND && easyQuesList.size() != 0
									&& easyQuesList.size() >= midQuesList.size()
									&& easyQuesList.size() >= hardQuesList.size())) {
								System.out.println("easyQuesList  " + tagsList);
								if (tagsList.size() < 6) {
									quiz[i] = quizReaderobj.addMissingTagQes(rand, easyQuesList, tagsList, counter);
								} else {
									quiz[i] = quizReaderobj.addQuestion(rand, easyQuesList);
								}
								if (quiz[i] == null) {

									TAGNOTFOUND = true;
								} else {
									TAGNOTFOUND = false;
									noOfQues = noOfQues - 1;
								}
							}

							if ((TAGNOTFOUND && midQuesList.size() != 0) || (TAGNOTFOUND && midQuesList.size() != 0
									&& midQuesList.size() >= hardQuesList.size()
									&& midQuesList.size() >= easyQuesList.size())) {
								if (tagsList.size() < 6) {
									quiz[i] = quizReaderobj.addMissingTagQes(rand, midQuesList, tagsList, counter);
								} else {
									quiz[i] = quizReaderobj.addQuestion(rand, midQuesList);
								}

								if (quiz[i] == null) {
									TAGNOTFOUND = true;
								} else {
									TAGNOTFOUND = false;
									noOfQues = noOfQues - 1;
								}
							}

							if ((TAGNOTFOUND && hardQuesList.size() != 0) || ((TAGNOTFOUND && hardQuesList.size() != 0)
									&& hardQuesList.size() >= midQuesList.size()
									&& hardQuesList.size() >= easyQuesList.size())) {

								if (tagsList.size() < 6) {
									quiz[i] = quizReaderobj.addMissingTagQes(rand, hardQuesList, tagsList, counter);
								} else {
									quiz[i] = quizReaderobj.addQuestion(rand, hardQuesList);
								}
								if (quiz[i] == null) {
									TAGNOTFOUND = true;
								} else {
									TAGNOTFOUND = false;
									noOfQues = noOfQues - 1;
								}
							}

						}

						if (out != null && quiz[i] != null) {
							out.write(quiz[i].getQuestion().getBytes());
							out.write("     ".getBytes());
							out.write(quiz[i].getQuesTag().getBytes());
							out.write("     ".getBytes());
							out.write(quiz[i].getQuesLevel().getBytes());
							out.write("\n".getBytes());

						}
						System.out.println(" noOfQues : " + noOfQues);

					}
					if (null != out) {
						out.write("\n--------------------------------------------------------------------------------"
								.getBytes());

					}
					if (quiz.length == 10) {

						quizCount = quizCount + 1;
					}
					System.out.println(" tags " + tagsList);

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

	
	private List<String> addTag(List<String> tagsList, Question question) {
		String quesTag = question.getQuesTag();
		if (!tagsList.contains(quesTag)) {
			tagsList.add(quesTag);
		}
		return tagsList;

	}

	private Question addQuestion(Random rand, List<Question> list) {

		int quizIndex = rand.nextInt(list.size());
		Question question = list.get(quizIndex);
	
		list.remove(quizIndex);
		return question;
		

	}

	private Question addMissingTagQes(Random rand, List<Question> list, List<String> tagsList, int counter) {

		int quizIndex = rand.nextInt(list.size());
		Question question = list.get(quizIndex);
		String quesTag = question.getQuesTag();
		counter = counter + 1;
		if (counter != list.size()) {
			if (!tagsList.contains(quesTag)) {
				tagsList.add(quesTag);
				list.remove(quizIndex);
				TAGNOTFOUND = false;
				return question;
			} else {

				return QuizReader.getInstance().addMissingTagQes(rand, list, tagsList, counter);

			}
		}
		TAGNOTFOUND = true;
		return null;

	}

}
