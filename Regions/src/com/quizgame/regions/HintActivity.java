package com.quizgame.regions;

import quizgame.framework.Hint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class HintActivity extends Activity {

	private TextView hintText;
	private WebView webView;
	private Hint hint;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hint);
		
		hintText=(TextView) findViewById(R.id.hint_text);
		webView=(WebView) findViewById(R.id.hint_webView);
		
		hint=(Hint)getIntent().getExtras().getSerializable("hint");
		
		
		if(hint.getContent().endsWith("htm") || hint.getContent().endsWith("html")){
			webView.loadUrl("file:///android_asset/"+hint.getContent());
			hintText.setVisibility(View.GONE);
		}else{
			hintText.setText(hint.getContent());
			webView.setVisibility(View.GONE);
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
}
