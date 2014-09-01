package com.quizgame.regions;

import com.quizgame.regions.content.ModelData;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends ActionBarActivity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		Button startGame=(Button) findViewById(R.id.startButton);
		startGame.setOnClickListener(this);
		Button achievements=(Button) findViewById(R.id.achievementsButton);
		achievements.setOnClickListener(this);
		Button settings=(Button) findViewById(R.id.settingsButton);
		settings.setOnClickListener(this);

		//Set the title font
		TextView titleText=(TextView) findViewById(R.id.titleText);
		if(!ModelData.TYPEFACE.equals("")){
			try{
				titleText.setTypeface(Typeface.createFromAsset(getAssets(),
						"fonts/"+ModelData.TYPEFACE));
				titleText.setTextColor(Color.WHITE);
			}catch(Exception e){
				e.printStackTrace();
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

	@Override
	public void onClick(View v) {

		switch(v.getId()){
		case R.id.startButton:
			startActivity( new Intent(this, MainActivity.class));
			break;

		case R.id.achievementsButton:
			startActivity( new Intent(this, AchievementsActivity.class));
			break;

		case R.id.settingsButton:
			startActivity( new Intent(this, UserSettingsActivity.class));
			break;
		}

	}
}
