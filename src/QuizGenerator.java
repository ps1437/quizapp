import java.io.IOException;
import java.util.List;

import com.syscho.quiz.pojo.Question;
import com.syscho.quiz.service.QuizReader;

public class QuizGenerator {

	public static void main(String[] args) throws IOException {

		/*
		  FileOutputStream out = new FileOutputStream(new
		  File("D:\\Test.txt"));
		  
		  
		  int j =1; for (int i = 1; i < 80; i++) {
		  
		  out.write(("Q"+i).getBytes());
		  
		  out.write(("|").getBytes());
		  
		  if( j %2 ==0){
		  
		  out.write(("EASY").getBytes()); }else if( j %3 ==0){
		  
		  out.write(("MEDIUM").getBytes()); }else
		  
		  out.write(("HARD ").getBytes()); out.write(("|").getBytes());
		  out.write(("Tag"+j).getBytes());
		  
		  out.write(("\n").getBytes());
		  
		  j++; if(j >6 ){ j=1; } }
		 */

		String outputFile = null;
		QuizReader reader = QuizReader.getInstance();

		List<Question> readQuiz = reader.readQuiz("Null");

		if (args.length == 2) {
			outputFile = args[1];
		}

		int quizCount = reader.generateQuiz(readQuiz, outputFile);

		System.out.println("Quiz Count : " + quizCount);
	}

}
