package com.example.a2_2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_welcome);
		Bundle bundle = getIntent().getExtras();
		String nickname = bundle.getString("nickname");
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(nickname);
	}

}
