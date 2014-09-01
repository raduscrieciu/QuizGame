package quizgame.framework;

import java.io.Serializable;
import java.util.List;

public abstract class Question implements Serializable{

	private static final long serialVersionUID = 1L;	
	
	private String questionText;
	private int rating;
	private boolean completed;
	private List<Hint> hints;

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public List<Hint> getHints() {
		return hints;
	}

	public void setHints(List<Hint> hints) {
		this.hints = hints;
	}
	
	
}
