package ilyag.a32;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		Button a = (Button) findViewById(R.id.buttonA);
		Button s = (Button) findViewById(R.id.buttonS);
		Button r = (Button) findViewById(R.id.buttonR);
		a.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AddActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		
		s.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ShowActivity.class);
				MainActivity.this.startActivity(intent);
				
			}
		});
		
		r.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyIO.save(MainActivity.this, new HashMap<String, Integer>());
			}
		});
	}
}
