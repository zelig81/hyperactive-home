package com.example.a2_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText et1;
	EditText et2;
	EditText et3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button b = (Button) findViewById(R.id.button1);
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
		et3 = (EditText) findViewById(R.id.editText3);
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String user = et2.getText().toString();
				String password = et3.getText().toString();
				if ("lior".equals(user.toLowerCase())
						&& "11255".equals(password)) {
					Intent intent = new Intent(MainActivity.this,
							WelcomeActivity.class);
					intent.putExtra("nickname", et1.getText().toString());
					startActivity(intent);
				} else {
					Toast t = Toast
							.makeText(MainActivity.this,
									"It's not the right credentials",
									Toast.LENGTH_LONG);
					t.show();
				}
			}
		});
	}

}
