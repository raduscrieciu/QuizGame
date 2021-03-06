package com.quizgame.regions;

import quizgame.framework.GameLevels;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;

import com.quizgame.regions.content.ModelData;

public class MainActivity extends ActionBarActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ModelData.populateQuestions(this);


		// Create the adapter that will return a fragment for each of the
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
		mViewPager.setAdapter(mSectionsPagerAdapter);
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
			startActivityForResult(new Intent(this, UserSettingsActivity.class), 1001);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int index) {

			LevelsFragment levelFragment = new LevelsFragment();
			levelFragment.setLevelIndex(index);
			return levelFragment;
		}

		@Override
		public int getCount() {
			// get item count - equal to number of levels
			return GameLevels.getNumberOfLevels();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return GameLevels.getLevels().get(position).getName();
		}
	}


	public static class LevelsFragment extends Fragment {

		private LinearLayout containerLayout;
		private int orientationIntex=2;
		private int levelIndex;
		private Button button;
		private RatingBar ratingBar;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			int currentOrientation = getResources().getConfiguration().orientation;
			if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
				orientationIntex=3;
			}else {
				orientationIntex=2; 
			}


			View rootView = inflater.inflate(R.layout.fragment_levels, container, false);

			containerLayout=(LinearLayout) rootView.findViewById(R.id.questionsContainerLayout);

			populate();

			return rootView;
		}
		
		private void populate(){
			LinearLayout questionRow=new LinearLayout(getActivity());
			questionRow.setOrientation(LinearLayout.HORIZONTAL);
			questionRow.setGravity(Gravity.CENTER);
			questionRow.setWeightSum(2);

			LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			p.weight = 1;
			p.setMargins(5, 5, 5, 5);

			LinearLayout questionContainer;
			

			for(int i=0; i<GameLevels.getLevels().get(levelIndex).getQuestions().size(); i++){

				questionContainer=new LinearLayout(getActivity());
				questionContainer.setOrientation(LinearLayout.VERTICAL);
				questionContainer.setGravity(Gravity.CENTER);
				questionContainer.setLayoutParams(p);

				button=new Button(getActivity());
				ratingBar=new RatingBar(getActivity());
				ratingBar.setRating(GameLevels.getLevels().get(levelIndex).getQuestions().get(i).getRating());

				button.setText("Question "+(i+1));
				button.setTextColor(getResources().getColor(R.color.white));
				button.setBackgroundResource(ModelData.LEVEL_SELECT_BUTTON_BACKGROUND);
				final int j=i;
				button.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if(GameLevels.getLevels().get(levelIndex).getQuestions().get(j).getClass().toString().contains("Choice")){
							Intent i=new Intent(getActivity(), ChoicesActivity.class);
							i.putExtra("question", j);
							i.putExtra("level", levelIndex);
							startActivityForResult(i,1000);
						}
						else if (GameLevels.getLevels().get(levelIndex).getQuestions().get(j).getClass().toString().contains("Hangman")){
							Intent i=new Intent(getActivity(), HangmanActivity.class);
							i.putExtra("question", j);
							i.putExtra("level", levelIndex);
							startActivityForResult(i,1000);
						}
						else if (GameLevels.getLevels().get(levelIndex).getQuestions().get(j).getClass().toString().contains("Map")){
							Intent i=new Intent(getActivity(), MapActivity.class);
							i.putExtra("question", j);
							i.putExtra("level", levelIndex);
							startActivityForResult(i,1000);
						}
					}

				});

				questionContainer.addView(button);
				questionContainer.addView(ratingBar);

				android.view.ViewGroup.LayoutParams btnParams=button.getLayoutParams();
				btnParams.width=LayoutParams.WRAP_CONTENT;
				button.setLayoutParams(btnParams);

				android.view.ViewGroup.LayoutParams params=ratingBar.getLayoutParams();
				params.width=LayoutParams.WRAP_CONTENT;
				ratingBar.setLayoutParams(params);
				ratingBar.setNumStars(3);
				ratingBar.setMax(3);
				ratingBar.setEnabled(false);

				if(i%orientationIntex==0){
					questionRow=new LinearLayout(getActivity());
					questionRow.setOrientation(LinearLayout.HORIZONTAL);
					questionRow.setGravity(Gravity.CENTER);
					questionRow.setWeightSum(orientationIntex);

					questionRow.addView(questionContainer);

					containerLayout.addView(questionRow);

				}else{
					questionRow.addView(questionContainer);
				}
			}
		}
		
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			// super.onActivityResult();
			
			if( requestCode == 1000  && 
					resultCode == Activity.RESULT_OK) {
				containerLayout.removeAllViews();
				populate();
		    }
		}

		public void setLevelIndex(int levelIndex){
			this.levelIndex=levelIndex;
		}

		public int getLevelIndex(){
			return this.levelIndex;
		}

	}

}
