package com.example.a21;

import com.example.a2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DivisionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_output);
		TextView name = (TextView) findViewById(R.id.name);
		TextView output = (TextView) findViewById(R.id.output);
		name.setText("Division result:");
		Intent intent = getIntent();
		int i1 = intent.getIntExtra("first", 0);
		int i2 = intent.getIntExtra("second", 0);
		if (i2 != 0)
			output.setText(i1 / i2 +"");
		else
			output.setText("could not devide by null");
	}

}
