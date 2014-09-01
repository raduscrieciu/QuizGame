package quizgame.framework;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.Toast;

public class Level implements Serializable{

	private static final long serialVersionUID = -7151023864372444223L;

	private String name;
	private List<Question> questions = new ArrayList<Question>();
	private Achievement achievement;
	
	public Level (String name){
		this.setName(name);
	}
	
	public Level (String name, List<Question> questions){
		this.setName(name);
		this.setQuestions(questions);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	public void addQuestion(Question question){
		questions.add(question);
	}
	
	public void removeQuestion(Question question){
		questions.remove(question);
	}

	public Achievement getAchievement() {
		return achievement;
	}

	public void setAchievement(Achievement achievement) {
		this.achievement = achievement;
	}
	
	public void checkLevelAchievement(Context context){

		for(int i=0; i<getQuestions().size(); i++){
			if(getQuestions().get(i).isCompleted()==false){
				break;
			}else if(i==getQuestions().size()-1){
				achievement.setCompleted(true);	
				GameLevels.save(context);
				
				Toast.makeText(context, "Congratulations, you have unlocked Achievement: "+achievement.getTitle()+" !",
						   Toast.LENGTH_LONG).show();
			}
		}
	}
}
