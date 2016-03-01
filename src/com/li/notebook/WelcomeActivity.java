package com.li.notebook;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class WelcomeActivity extends Activity {

	private Handler handler = new Handler();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				finish();
				startActivity(new Intent().setClass(WelcomeActivity.this,
						MainActivity.class));
			}
		}, 1);
	}
}
