package com.quizgame.regions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import quizgame.framework.AutoResizeTextView;
import quizgame.framework.GameLevels;
import quizgame.framework.HangmanQuestion;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.quizgame.regions.content.ModelData;

public class HangmanActivity extends ActionBarActivity implements OnClickListener{

	private LinearLayout textLayout;
	//private LinearLayout questionLayout;
	private TextView questionText;
	private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16;
	private Button[] buttonArray;
	private Button addLetter, removeLetter;

	List<TextView> answerList=new ArrayList<TextView>();
	List<Integer> whiteSpaceIndexes=new ArrayList<Integer>();
	private static String[] alphabet = { "A", "B", "C", "D", "E", "F", "G",
		"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
		"U", "V", "W", "X", "Y", "Z" };
	private List<String> extraLetters=new ArrayList<String>();

	private String name;
	private int levelIndex, questionIndex;
	private HangmanQuestion question;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hangman);

		textLayout=(LinearLayout) findViewById(R.id.textLayout);
		questionText=(TextView) findViewById(R.id.questionText_hangman);
		btn1=(Button) findViewById(R.id.startButton);
		btn1.setOnClickListener(this);
		btn2=(Button) findViewById(R.id.settingsButton);
		btn2.setOnClickListener(this);
		btn3=(Button) findViewById(R.id.aboutButton);
		btn3.setOnClickListener(this);
		btn4=(Button) findViewById(R.id.achievementsButton);
		btn4.setOnClickListener(this);
		btn5=(Button) findViewById(R.id.button5);
		btn5.setOnClickListener(this);
		btn6=(Button) findViewById(R.id.button6);
		btn6.setOnClickListener(this);
		btn7=(Button) findViewById(R.id.button7);
		btn7.setOnClickListener(this);
		btn8=(Button) findViewById(R.id.button8);
		btn8.setOnClickListener(this);
		btn9=(Button) findViewById(R.id.button9);
		btn9.setOnClickListener(this);
		btn10=(Button) findViewById(R.id.button10);
		btn10.setOnClickListener(this);
		btn11=(Button) findViewById(R.id.button11);
		btn11.setOnClickListener(this);
		btn12=(Button) findViewById(R.id.button12);
		btn12.setOnClickListener(this);
		btn13=(Button) findViewById(R.id.button13);
		btn13.setOnClickListener(this);
		btn14=(Button) findViewById(R.id.button14);
		btn14.setOnClickListener(this);
		btn15=(Button) findViewById(R.id.button15);
		btn15.setOnClickListener(this);
		btn16=(Button) findViewById(R.id.button16);
		btn16.setOnClickListener(this);

		addLetter=(Button) findViewById(R.id.addLetter);
		addLetter.setOnClickListener(this);
		addLetter.setText(Html.fromHtml("<b>A<font color=\"green\"> +</font></b>"));
		removeLetter=(Button) findViewById(R.id.removeLetter);
		removeLetter.setOnClickListener(this);
		removeLetter.setText(Html.fromHtml("<b>A<font color=\"red\"> -</font></b>"));

		levelIndex=getIntent().getIntExtra("level", 0);
		questionIndex=getIntent().getIntExtra("question", 0);

		question=(HangmanQuestion) GameLevels.getLevels().get(levelIndex).getQuestions().get(questionIndex);
		newLevel(question);
		ModelData.populateHints(question, (LinearLayout) findViewById(R.id.hangman_hintLayout), HangmanActivity.this);

		if(question.isCompleted()){
			addLetter.setEnabled(false);
			removeLetter.setEnabled(false);
			String q=question.getAnswer().replaceAll("\\s+", " ");
			for(int i=0; i<q.length(); i++){
				addLetter();
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
			startActivity( new Intent(this, UserSettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void newLevel(HangmanQuestion question){
		questionText.setText(question.getQuestionText());
		name=question.getAnswer().toUpperCase(Locale.UK);


		answerList.clear();
		whiteSpaceIndexes.clear();
		extraLetters.clear();
		textLayout.removeAllViews();


		buttonArray=new Button[]{btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16};
		for(int i=0; i<buttonArray.length; i++){
			buttonArray[i].setVisibility(0);
			if(question.isCompleted()){
				buttonArray[i].setEnabled(false);
			}

		}

		char[] c=name.toCharArray();

		for(int i=0; i<c.length; i++){
			if(c[i]==(" ".toCharArray()[0])){
				whiteSpaceIndexes.add(i);
			}
		}

		char nameChars[]=name.replaceAll(" ", "").toCharArray();
		String[] buttonChars=new String[16];
		Collections.shuffle(Arrays.asList(alphabet));
		Collections.shuffle(Arrays.asList(nameChars));

		for(int i=0; i<nameChars.length; i++){
			buttonChars[i]=Character.toString(nameChars[i]);
		}
		for(int i=0; i<(buttonChars.length-nameChars.length); i++){
			buttonChars[nameChars.length+i]=alphabet[i];
			extraLetters.add(alphabet[i]);
		}
		Collections.shuffle(Arrays.asList(buttonChars));


		for(int i=0; i<buttonChars.length;i++){
			buttonArray[i].setText(buttonChars[i]);
		}

		String str=parseString(name.replaceAll(" ", "@"));

		if(str.contains("###")){
	//		multirow=true;
			String data[]=str.split("###");
			final LinearLayout containerLayout=new LinearLayout(this);
			containerLayout.setOrientation(LinearLayout.VERTICAL);
			containerLayout.setGravity(Gravity.CENTER);
			for(int j=0; j<data.length; j++){
				char[] charArray = data[j].toCharArray();
				final LinearLayout l=new LinearLayout(this);

				for(int i=0; i<charArray.length; i++){

					final AutoResizeTextView valueTV = new AutoResizeTextView(this);
					valueTV.setGravity(Gravity.CENTER);
					valueTV.setText(" ");
					if(Character.toString(charArray[i]).equals("@")){
						valueTV.setText("@");
						valueTV.setVisibility(4);
					}

					ViewTreeObserver vto = btn1.getViewTreeObserver();  
					vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {  
						@Override  
						public void onGlobalLayout() {  
							btn1.getViewTreeObserver().removeOnGlobalLayoutListener(this);  
							int width  = (int) (btn1.getMeasuredWidth()*(0.629)); 
							valueTV.setLayoutParams(new LayoutParams(
									width,
									width));
						}  
					}); 



					valueTV.setTypeface(Typeface.DEFAULT_BOLD);
					valueTV.setBackgroundResource(R.drawable.label);
					if(!question.isCompleted()){
						valueTV.setClickable(true);

						valueTV.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								for(int i=0; i<buttonArray.length;i++){
									if(buttonArray[i].getText().equals(valueTV.getText()) && buttonArray[i].getVisibility()==4){
										buttonArray[i].setVisibility(0);
										break;
									}
								}
								valueTV.setText(" ");
							}

						});
					}
					answerList.add(valueTV);
					l.addView(valueTV);
				}
				containerLayout.addView(l);
			}
			textLayout.addView(containerLayout);
		}else{
			char[] charArray = str.toCharArray();
			for(int i=0; i<charArray.length; i++){
				final AutoResizeTextView valueTV = new AutoResizeTextView(this);
				valueTV.setGravity(Gravity.CENTER);
				valueTV.setText(" ");
				if(Character.toString(charArray[i]).equals("@")){
					valueTV.setText("@");
					valueTV.setVisibility(4);
				}
				ViewTreeObserver vto = btn1.getViewTreeObserver();  
				vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {  
					@Override  
					public void onGlobalLayout() {  
						btn1.getViewTreeObserver().removeOnGlobalLayoutListener(this);  
						int width  = (int) (btn1.getMeasuredWidth()*(0.8)); 
						if(name.length()>=10){
							width=(int) (btn1.getMeasuredWidth()*(0.65));
						}
						valueTV.setLayoutParams(new LayoutParams(
								width,
								width));
					}  
				}); 
				valueTV.setTypeface(Typeface.DEFAULT_BOLD);
				valueTV.setBackgroundResource(R.drawable.label);
				if(!question.isCompleted()){
					valueTV.setClickable(true);

					valueTV.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							for(int i=0; i<buttonArray.length;i++){
								if(buttonArray[i].getText().equals(valueTV.getText()) && buttonArray[i].getVisibility()==4){
									buttonArray[i].setVisibility(0);
									break;
								}
							}
							valueTV.setText(" ");

						}
					});
				}
				answerList.add(valueTV);
				textLayout.addView(valueTV);
			}
		}
	}

	public static int getFontSize (Activity activity, float var) { 

		DisplayMetrics dMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dMetrics);

		// lets try to get them back a font size realtive to the pixel width of the screen
		final float WIDE = activity.getResources().getDisplayMetrics().heightPixels;
		int valueWide = (int)(WIDE / 32.0f / (dMetrics.scaledDensity));
		return (int) ((float)(valueWide*var));
	}

	private String parseString(String input) {
		StringTokenizer tok = new StringTokenizer(input, "@");
		StringBuilder output = new StringBuilder(input.length());
		int lineLen = 0;
		while (tok.hasMoreTokens()) {
			String word = tok.nextToken()+"@";
			if (lineLen + word.length() > 12) {
				output.append("###");
				lineLen = 0;
			}
			output.append(word);
			lineLen += word.length();
		}

		return output.toString().substring(0, output.length() - 1);
	}

	private void buttonTap(Button btn){
		for(int i=0; i<answerList.size(); i++){
			if(answerList.get(i).getText().equals(" ")){
				if(whiteSpaceIndexes.size()==0){
					answerList.get(i).setText(btn.getText().toString());
					btn.setVisibility(4);
					break;
				} 
				else{
					for(int j=0; j<whiteSpaceIndexes.size(); j++){
						if(i!=whiteSpaceIndexes.get(j)){
							answerList.get(i).setText(btn.getText().toString());
							btn.setVisibility(4);
							break;
						}					
					}
					break;
				}
			}
		}
		checkAnswer();
	}

	private void checkAnswer(){
		int counter=0;
		for(int i=0; i<answerList.size(); i++){
			if(answerList.get(i).getText().toString().equals(Character.toString((name.replaceAll(" ", "@")).charAt(i)))){
				counter++;
			}
		}
		if(counter==answerList.size()){
			//	playSound();

			Toast.makeText(getApplicationContext(),"Correct !", Toast.LENGTH_SHORT).show();

			GameLevels.getLevels().get(levelIndex).getQuestions().get(questionIndex).setCompleted(true);
			GameLevels.getLevels().get(levelIndex).getQuestions().get(questionIndex).setRating(3);
			GameLevels.save(this);
			
			GameLevels.getLevels().get(levelIndex).checkLevelAchievement(this);

			Intent intent = new Intent(); 
			intent.putExtra("completed", true); 
			setResult(RESULT_OK, intent); 
			finish(); 

		}
	}

	private void addLetter(){
		for(int i=0; i<answerList.size(); i++){
			if(answerList.get(i).getText().equals(" ")){
				if(whiteSpaceIndexes.size()==0){
					answerList.get(i).setText(Character.toString(name.charAt(i)));
					for(int b=0; b<buttonArray.length; b++){
						if(buttonArray[b].getText().equals(answerList.get(i).getText()) && buttonArray[b].getVisibility()==0
								&& (!buttonArray[b].getText().equals(extraLetters.get(0)))){
							buttonArray[b].setVisibility(4);
							break;
						}
					}

					break;
				} 
				else{
					for(int j=0; j<whiteSpaceIndexes.size(); j++){
						if(i!=whiteSpaceIndexes.get(j)){
							answerList.get(i).setText(Character.toString(name.charAt(i)));
							for(int b=0; b<buttonArray.length; b++){
								if(buttonArray[b].getText().equals(answerList.get(i).getText()) && buttonArray[b].getVisibility()==0
										&& (!buttonArray[b].getText().equals(extraLetters.get(0)))){
									buttonArray[b].setVisibility(4);
									break;
								}
							}
							break;
						}					
					}
					break;
				}
			}
			addLetter.setEnabled(false);
			if(!question.isCompleted()){
				checkAnswer();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){

		case R.id.startButton:
			buttonTap(btn1);
			break;
		case R.id.settingsButton:
			buttonTap(btn2);
			break;
		case R.id.aboutButton:
			buttonTap(btn3);
			break;
		case R.id.achievementsButton:
			buttonTap(btn4);
			break;
		case R.id.button5:
			buttonTap(btn5);
			break;
		case R.id.button6:
			buttonTap(btn6);
			break;
		case R.id.button7:
			buttonTap(btn7);
			break;
		case R.id.button8:
			buttonTap(btn8);
			break;
		case R.id.button9:
			buttonTap(btn9);
			break;
		case R.id.button10:
			buttonTap(btn10);
			break;
		case R.id.button11:
			buttonTap(btn11);
			break;
		case R.id.button12:
			buttonTap(btn12);
			break;
		case R.id.button13:
			buttonTap(btn13);
			break;
		case R.id.button14:
			buttonTap(btn14);
			break;
		case R.id.button15:
			buttonTap(btn15);
			break;
		case R.id.button16:
			buttonTap(btn16);
			break;

		case R.id.removeLetter:
			for(int i=0; i<buttonArray.length; i++){
				if(buttonArray[i].getVisibility()==0 && buttonArray[i].getText().equals(extraLetters.get(0))){
					buttonArray[i].setVisibility(4);
					removeLetter.setEnabled(false);
					break;
				}

			}
			break;
		case R.id.addLetter:
			addLetter();
			break;
		}
	}
}