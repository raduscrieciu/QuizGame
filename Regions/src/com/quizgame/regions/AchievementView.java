package com.quizgame.regions;

import quizgame.framework.Achievement;
import quizgame.framework.GameLevels;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AchievementView extends LinearLayout { 
	private TextView titleText; 
	private TextView descriptionText;
	private Button redeemButton;

	private Context context;



	public AchievementView(Context context) { 
		super(context);
		this.context= context;
		LayoutInflater inflater = (LayoutInflater) 
				context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		inflater.inflate(R.layout.achievements_list_item, this, true); 

		titleText = (TextView) findViewById(R.id.achievement_titleText); 
		descriptionText = (TextView) findViewById(R.id.achievement_descriptionText);

		redeemButton=(Button) findViewById(R.id.achievementButton);
	} 


	public void setAchievement(final Achievement achievement) { 
		titleText.setText(achievement.getTitle());
		descriptionText.setText(achievement.getDescription());

		redeemButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {

				if(achievement.isRedemed()==false){
					redeemButton.setText("View Reward");
					GameLevels.getAchievement(achievement).setRedemed(true);
					Toast.makeText(context, "Achievement has been redeemed.",
							Toast.LENGTH_LONG).show();
					displayAchievement(achievement);
					GameLevels.save(context);
				}
				else if(achievement.isRedemed()){
					displayAchievement(achievement);
				}

			}
		});

		if(achievement.isCompleted()){
			redeemButton.setText("Redeem Reward");

			if(achievement.isRedemed()){
				redeemButton.setText("View Reward");
			}
		}
		else if(achievement.isCompleted()==false){
			redeemButton.setText("Incomplete");
			redeemButton.setEnabled(false);
		}

	}

	private void displayAchievement(Achievement achievement){
		AlertDialog.Builder alertadd = new AlertDialog.Builder(
				context);
		LayoutInflater factory = LayoutInflater.from(context);
		final View view = factory.inflate(R.layout.achievement_dialog, null);
		alertadd.setView(view);

		if(achievement.getReward().endsWith("html") || achievement.getReward().endsWith("htm")){
			WebView rewardWebView=(WebView) view.findViewById(R.id.achievementDialog_webView);
			rewardWebView.loadUrl("file:///android_asset/"+achievement.getReward());
			rewardWebView.setVisibility(View.VISIBLE);
		}else{
			TextView rewardText=(TextView) view.findViewById(R.id.achievementDialog_text);
			rewardText.setText(achievement.getReward());
			rewardText.setVisibility(View.VISIBLE);
		}
		
		alertadd.setNeutralButton("Return", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dlg, int sumthin) {
			}
		});

		alertadd.show();
	}
}
