package com.example.a2_2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_welcome);
		Log.i("1", "1");
		String nickname = getIntent().getStringExtra("nickname");
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText("Welcome brother " + nickname);
	}

}
