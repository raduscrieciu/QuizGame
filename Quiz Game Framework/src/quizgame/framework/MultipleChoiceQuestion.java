package quizgame.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MultipleChoiceQuestion extends Question{

	private static final long serialVersionUID = 7010833648191534459L;

	private String correctAnswer;
	private String wrongAnswer1, wrongAnswer2;
	private List<String> shuffledAnswers=new ArrayList<String>();
	
	public MultipleChoiceQuestion (String questionText, String correctAnswer, String wrongAnswer1, String wrongAnswer2){
		this.setQuestionText(questionText);
		this.setCorrectAnswer(correctAnswer);
		this.setWrongAnswer1(wrongAnswer1);
		this.setWrongAnswer2(wrongAnswer2);
		
		shuffledAnswers.add(correctAnswer);
		shuffledAnswers.add(wrongAnswer1);
		shuffledAnswers.add(wrongAnswer2);
		
		long seed = System.nanoTime();
		Collections.shuffle(shuffledAnswers, new Random(seed));
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String getWrongAnswer1() {
		return wrongAnswer1;
	}

	public void setWrongAnswer1(String wrongAnswer1) {
		this.wrongAnswer1 = wrongAnswer1;
	}

	public String getWrongAnswer2() {
		return wrongAnswer2;
	}

	public void setWrongAnswer2(String wrongAnswer2) {
		this.wrongAnswer2 = wrongAnswer2;
	}

	public List<String> getShuffledAnswers() {
		
		return shuffledAnswers;
	}

	public void setShuffledAnswers(List<String> shuffledAnswers) {
		this.shuffledAnswers = shuffledAnswers;
	}
}
