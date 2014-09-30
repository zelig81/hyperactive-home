package com.example.a21;

import com.example.a2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText et1;
	private EditText et2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button b = (Button) findViewById(R.id.button1);
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);

		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.example.calculate");
				int i1 = Integer.parseInt(et1.getText().toString());
				int i2 = Integer.parseInt(et2.getText().toString());
				intent.putExtra("first", i1);
				intent.putExtra("second", i2);
				startActivity(intent);
			}
		});
	}

}
