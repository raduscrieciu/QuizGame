package com.quizgame.regions;

import quizgame.framework.GameLevels;
import quizgame.framework.MapQuestion;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quizgame.regions.content.ModelData;

public class MapActivity extends ActionBarActivity{

	private MapView mapView;
	private LinearLayout mapContainer; 
	private int width, height;
	private TextView questionText;
	private int levelIndex, questionIndex;
	
	private MapQuestion question;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		
		mapContainer=(LinearLayout) findViewById(R.id.mapContainer);
		
		questionText=(TextView) findViewById(R.id.questionText_map);
		
		levelIndex=getIntent().getIntExtra("level", 0);
		questionIndex=getIntent().getIntExtra("question", 0);
		
		question=(MapQuestion) GameLevels.getLevels().get(levelIndex).getQuestions().get(questionIndex);
		
		populate(question);
		ModelData.populateHints(question, (LinearLayout) findViewById(R.id.map_hintLayout), MapActivity.this);
	}
	
	public void correctAnswer(){
		Toast.makeText(this, "Awesome!", Toast.LENGTH_LONG).show();
		
		GameLevels.getLevels().get(levelIndex).getQuestions().get(questionIndex).setCompleted(true);
		GameLevels.getLevels().get(levelIndex).getQuestions().get(questionIndex).setRating(3);
		GameLevels.save(this);
		
		GameLevels.getLevels().get(levelIndex).checkLevelAchievement(this);
		
		Intent intent = new Intent(); 
		intent.putExtra("completed", true); 
		setResult(RESULT_OK, intent); 
		finish(); 
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
	
	private void populate (MapQuestion question){
		mapView = new MapView(this, question.getX(), question.getY(), question.getMapImageResourceId(),question.isCompleted());
		mapContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
		    @Override
		    public void onGlobalLayout() {
		        // Ensure you call it only once :
		    	mapContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
		    	width = mapContainer.getWidth();
		    	height = mapContainer.getHeight();
		    	
		    	int size=0;
		    	if(width>height){
		    		size=height;
		    	}
		    	else{
		    		size=width;
		    	}
		    	
		    	mapView.setLayoutParams(new LayoutParams(size, size));
				mapContainer.addView(mapView);
		    }
		});
		
		
		questionText.setText(question.getQuestionText());
	}
}
