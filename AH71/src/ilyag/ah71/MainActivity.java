package ilyag.ah71;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button		b1, b2, b3, b4, b5;
	Random		r;
	Button[]	buttons;
	Boolean[]	bool	= new Boolean[] { true, true, true, true, true };
	
	protected int changeGravity(int index) {
		bool[index] = !bool[index];
		return (bool[index] == true) ? Gravity.TOP : Gravity.BOTTOM;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		b3 = (Button) findViewById(R.id.button3);
		b4 = (Button) findViewById(R.id.button4);
		b5 = (Button) findViewById(R.id.button5);
		buttons = new Button[] { b1, b2, b3, b4, b5 };
		r = new Random();
		View.OnClickListener myLis = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				int index = -1;
				for (int i = 0; i < 5; i++) {
					if (v == (buttons[i])) {
						index = i;
						break;
					}
				}
				if (index == -1) {
					return;
				}
				params.gravity = changeGravity(index);
				params.weight = 1;
				v.setLayoutParams(params);
				int color = Color.argb(r.nextInt(0xff), r.nextInt(0xff), r.nextInt(0xff), r.nextInt(0xff));
				Toast.makeText(getApplicationContext(), "color " + color, Toast.LENGTH_LONG).show();
				((Button) v).setBackgroundColor(color);
				
			}
		};
		b1.setOnClickListener(myLis);
		b2.setOnClickListener(myLis);
		b3.setOnClickListener(myLis);
		b4.setOnClickListener(myLis);
		b5.setOnClickListener(myLis);
		
	}
}
