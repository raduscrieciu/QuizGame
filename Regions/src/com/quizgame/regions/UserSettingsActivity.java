package com.quizgame.regions;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class UserSettingsActivity extends PreferenceActivity {


	final Context context = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.layout.settings);

		final Preference resetButton = (Preference) findPreference("reset_progress");
		resetButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference arg0) { 
				try{
					deleteFile("gameData");
					deleteFile("gameAchievements");
					Toast.makeText(UserSettingsActivity.this, "User progress has been reset.", Toast.LENGTH_SHORT).show();
					
				}catch(Exception e){
					Toast.makeText(UserSettingsActivity.this, "Unable to reset user progress.", Toast.LENGTH_SHORT).show();
				}
				return true;
			}
		});

	}
}

