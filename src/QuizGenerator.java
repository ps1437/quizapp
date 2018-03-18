import java.io.IOException;
import java.util.List;

import com.syscho.quiz.pojo.Question;
import com.syscho.quiz.service.QuizReader;

public class QuizGenerator {

	public static void main(String[] args) throws IOException {

		String outputFile = null;
		QuizReader reader = QuizReader.getInstance();

		if (0 == args.length) {

			System.out.println("Input file cannot empty! ");
		} else {

			List<Question> readQuiz = reader.readQuiz(args[0]);

			if (args.length == 2) {
				outputFile = args[1];
			}

			if (readQuiz.size() < 10) {

				System.out.println("Input file is not having sufficient data ");
			} else {

				int quizCount = reader.generateQuiz(readQuiz, outputFile);
				System.out.println("Quiz Count : " + quizCount);

			}

		}
	}

}
