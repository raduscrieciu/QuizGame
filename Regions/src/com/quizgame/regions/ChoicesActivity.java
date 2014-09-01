package com.quizgame.regions;

import quizgame.framework.GameLevels;
import quizgame.framework.MultipleChoiceQuestion;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quizgame.regions.content.ModelData;

public class ChoicesActivity extends ActionBarActivity implements OnClickListener{

	private TextView questionText;
	private Button choice1, choice2, choice3;
	private MultipleChoiceQuestion question;
	private int levelIndex, questionIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choices);

		questionText=(TextView) findViewById(R.id.questionText_choices);

		choice1=(Button) findViewById(R.id.answerButton1);
		choice1.setOnClickListener(this);
		choice2=(Button) findViewById(R.id.answerButton2);
		choice2.setOnClickListener(this);
		choice3=(Button) findViewById(R.id.answerButton3);
		choice3.setOnClickListener(this);

		levelIndex=getIntent().getIntExtra("level", 0);
		questionIndex=getIntent().getIntExtra("question", 0);
		
		question=(MultipleChoiceQuestion) GameLevels.getLevels().get(levelIndex)
				.getQuestions().get(questionIndex);
		
		populate(question);
		ModelData.populateHints(question, (LinearLayout) findViewById(R.id.choices_hintLayout), ChoicesActivity.this);
	}

	private void populate(MultipleChoiceQuestion question){

		questionText.setText(question.getQuestionText());
		
		choice1.setText(question.getShuffledAnswers().get(0));
		choice2.setText(question.getShuffledAnswers().get(1));
		choice3.setText(question.getShuffledAnswers().get(2));
		
		if(question.isCompleted()){
			choice1.setEnabled(false);
			choice2.setEnabled(false);
			choice3.setEnabled(false);
			if(choice1.getText().toString().contains(question.getCorrectAnswer())){
				choice1.setBackgroundResource(R.drawable.button_green);
			}else if(choice2.getText().toString().contains(question.getCorrectAnswer())){
				choice2.setBackgroundResource(R.drawable.button_green);
			}else if(choice3.getText().toString().contains(question.getCorrectAnswer())){
				choice3.setBackgroundResource(R.drawable.button_green);
			}
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void checkAnswer(Button answer){
		if(answer.getText().toString().contains(question.getCorrectAnswer())){
			Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_LONG).show();
			GameLevels.getLevels().get(levelIndex).getQuestions().get(questionIndex).setRating(3);
			GameLevels.getLevels().get(levelIndex).getQuestions().get(questionIndex).setCompleted(true);
			GameLevels.save(getApplicationContext());
			
			choice1.setEnabled(false);
			choice2.setEnabled(false);
			choice3.setEnabled(false);
			
			answer.setBackgroundResource(R.drawable.button_green);
			
			GameLevels.getLevels().get(levelIndex).checkLevelAchievement(this);
			
			Intent intent = new Intent(); 
			intent.putExtra("completed", true); 
			setResult(RESULT_OK, intent); 
			
			finish(); 
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.answerButton1:
			checkAnswer(choice1);
			break;

		case R.id.answerButton2:
			checkAnswer(choice2);
			break;

		case R.id.answerButton3:
			checkAnswer(choice3);
			break;
		}

	}

}
