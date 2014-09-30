package com.example.a1_2;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity {
	EditText et;
	LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button b = (Button) findViewById(R.id.button1);
		ll = (LinearLayout) findViewById(R.id.LinearLayout1);
		et = (EditText) findViewById(R.id.editText1);
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String s = et.getText().toString();
				int in = Integer.parseInt(s);
				// for (int i = 0; i < 9 && i < in; i++) {
				TextView tv = new TextView(MainActivity.this);
				tv.setText(in);
				LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
				tv.setBottom(R.id.button1);
				ll.addView(tv, 0, lp);
				// }
			}
		});
	}

}
