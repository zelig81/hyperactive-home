package com.example.a21;

import com.example.a2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MultiplicationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_output);
		TextView name = (TextView) findViewById(R.id.name);
		TextView output = (TextView) findViewById(R.id.output);
		name.setText("Multiplication result:");
		Intent intent = getIntent();
		Bundle bund = intent.getExtras();
		int i1 = bund.getInt("first");
		int i2 = bund.getInt("second");
		output.setText(i1 * i2);
	}

}
