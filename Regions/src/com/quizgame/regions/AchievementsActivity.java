package com.quizgame.regions;

import quizgame.framework.GameLevels;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class AchievementsActivity extends ActionBarActivity {

	private AchievementsListViewAdaptor achievementsListViewAdapter;
	private ListView achievementsListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_achievements);
		
		achievementsListView=(ListView) findViewById(R.id.achievementsListView);
		
		
		GameLevels.load(this);
		
		updateAchievementsList();
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
	
	public void updateAchievementsList(){
		
		achievementsListViewAdapter = new AchievementsListViewAdaptor(this,GameLevels.getAchievements()); 
		achievementsListView.setAdapter(achievementsListViewAdapter); 
		registerForContextMenu(achievementsListView); 
	}
}
