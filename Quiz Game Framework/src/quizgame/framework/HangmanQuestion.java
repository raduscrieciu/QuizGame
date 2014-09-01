package quizgame.framework;

public class HangmanQuestion extends Question{

	private static final long serialVersionUID = 4521782304322750777L;
	
	private String answer;
	
	public HangmanQuestion(String questionText, String answer){
		this.setQuestionText(questionText);
		this.setAnswer(answer);
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
