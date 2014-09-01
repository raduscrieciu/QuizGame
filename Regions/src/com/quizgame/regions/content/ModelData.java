package com.quizgame.regions.content;

import java.util.ArrayList;
import java.util.List;

import quizgame.framework.Achievement;
import quizgame.framework.GameLevels;
import quizgame.framework.HangmanQuestion;
import quizgame.framework.Hint;
import quizgame.framework.Level;
import quizgame.framework.MapQuestion;
import quizgame.framework.MultipleChoiceQuestion;
import quizgame.framework.Question;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.quizgame.regions.HintActivity;
import com.quizgame.regions.R;

/**
 * 
 * Model Data
 * EGL Generated Class
 * @author Radu
 *
 */
public abstract class ModelData{

	public static final int LEVEL_SELECT_BUTTON_BACKGROUND=R.drawable.button;
	public static final String TYPEFACE="Arista2.0.ttf";
	public static void populateQuestions(Context context){
		GameLevels.load(context);
         
        if(GameLevels.getNumberOfLevels()==0){
        
        Question question;
        List<Hint> hints;
        Level level;
        
        
  			level=new Level("Level One");
  				
  				question = new MapQuestion(
  				   "Which city in Yorkshire is known as the City of Steel?",
					88,
					22,
					R.drawable.map);
					
				hints=new ArrayList<Hint>();
				hints.add(new Hint("sheffield1.html"));
				hints.add(new Hint("sheffield2.html"));
				question.setHints(hints);
				level.addQuestion(question);
					
				question = new MultipleChoiceQuestion(
				    "What is the population of Yorkshire according to the 2011 census?",
					"5.3 million",
					"4.8 million",
					"4.2 million");
				
				hints=new ArrayList<Hint>();
				hints.add(new Hint("Less than 6 million."));
				question.setHints(hints);
				level.addQuestion(question);
				
  				question = new HangmanQuestion(
  				    "Name the best selling Leeds-born author of the book Talking Heads?",
					"Pam Ayres");
  				
  				hints=new ArrayList<Hint>();
				hints.add(new Hint("pam.html"));
				question.setHints(hints);
				level.addQuestion(question);
				
  				question = new HangmanQuestion(
  				    "Which river runs through the heart of the historic city of York?",
					"River Ure");
  				
  				hints=new ArrayList<Hint>();
				hints.add(new Hint("ure1.html"));
				hints.add(new Hint("ure2.html"));
				question.setHints(hints);
				level.addQuestion(question);
				
  			
  			level.setAchievement(new Achievement("My First Level", //title
  									"Complete all questions in Level One.", //description
  									"voucher1.html", //reward file name
  									false, //completed
  									false)); //redeemed
  									
  			GameLevels.addLevel(level);
        
  			level=new Level("Level Two");
  				
  				question = new MapQuestion(
  				   "Which West Yorkshire town is the birthplace of former Prime Minister Harlold Wilson?",
					23,
					35,
					R.drawable.map);
					
				hints=new ArrayList<Hint>();
				hints.add(new Hint("huddersfield.html"));
				question.setHints(hints);
				level.addQuestion(question);
					
				question = new MultipleChoiceQuestion(
				    "How many national parks are within Yorkshire's county borders?",
					"Two",
					"Three",
					"Five");
				
				hints=new ArrayList<Hint>();
				hints.add(new Hint("More than two."));
				question.setHints(hints);
				level.addQuestion(question);
				
  			
  			level.setAchievement(new Achievement("My 2nd Achievement", //title
  									"Complete Level Two", //description
  									"voucher2.html", //reward file name
  									false, //completed
  									false)); //redeemed
  									
  			GameLevels.addLevel(level);
        
  			level=new Level("My Level");
				question = new MultipleChoiceQuestion(
				    "aaaaaaa",
					"aasasa",
					"bbbbbbbbb",
					"ccccccccccc");
				
				hints=new ArrayList<Hint>();
				question.setHints(hints);
				level.addQuestion(question);
				
  				question = new HangmanQuestion(
  				    "type in correct.",
					"correct");
  				
  				hints=new ArrayList<Hint>();
				question.setHints(hints);
				level.addQuestion(question);
				
  			
  			level.setAchievement(new Achievement("asgsfg", //title
  									"safvdf", //description
  									"asgyhu", //reward file name
  									false, //completed
  									false)); //redeemed
  									
  			GameLevels.addLevel(level);
		 GameLevels.save(context);
        }else{
           Log.i("Quiz Game", "Generated content successfully loaded.");
        }
	}
	
	
	public static void populateHints(Question question, LinearLayout hintsLayout, final Context context){
		hintsLayout.removeAllViews();
		
		Button hintButton;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
	            LayoutParams.WRAP_CONTENT);
		params.weight=1;
		params.setMargins(5, 5, 5, 5);
		int index=0;
		
		for(final Hint hint: question.getHints()){
			index++;
			hintButton=new Button(context);
			hintButton.setText("Hint "+index);
			hintButton.setBackgroundResource(R.drawable.button);
			hintButton.setTextColor(context.getResources().getColor(R.color.white));
			hintButton.setLayoutParams(params);
			
			hintButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, HintActivity.class);
					intent.putExtra("hint", hint);  // pass your values and retrieve them in the other Activity using keyName
					context.startActivity(intent);
				}
			});
			
			hintsLayout.addView(hintButton);
		}
	}
}