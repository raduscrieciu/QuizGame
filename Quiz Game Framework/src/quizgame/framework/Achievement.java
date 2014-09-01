package quizgame.framework;

import java.io.Serializable;

public class Achievement implements Serializable{
	
	private static final long serialVersionUID = 3774156912271439933L;
	
	private String title;
	private String description;
	private String reward;
	private boolean completed;
	private boolean redemed;
	
	public Achievement (String title, String description, String reward, boolean completed, boolean redemeed){
		this.title=title;
		this.reward=reward;
		this.description=description;
		this.completed=completed;
		this.redemed=redemeed;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isRedemed() {
		return redemed;
	}

	public void setRedemed(boolean redemed) {
		this.redemed = redemed;
	}
}
