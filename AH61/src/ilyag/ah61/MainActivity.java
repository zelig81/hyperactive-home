package ilyag.ah61;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ToggleButton;
import android.widget.Spinner;
import android.widget.AutoCompleteTextView;
import android.widget.Adapter;

public class MainActivity extends Activity {
	EditText				et;
	Button					b;
	TextView				tv;
	ToggleButton			tb;
	Spinner					s;
	AutoCompleteTextView	actv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		et = (EditText) findViewById(R.id.editText1);
		b = (Button) findViewById(R.id.button1);
		tv = (TextView) findViewById(R.id.textView1);
		tb = (ToggleButton) findViewById(R.id.toggleButton1);
		s = (Spinner) findViewById(R.id.spinner1);
		actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		final String[] list = new String[] { "Fsdfsdf", "Asdfsdf", "Bsdfsdf" };
		ArrayAdapter<String> a =
				new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		actv.setAdapter(a);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tv.setText("Info: " + et.getText().toString() + "/"
						+ (tb.isChecked() ? "male/" : "female/") + "from "
						+ actv.getText().toString() + " school/learn "
						+ s.getSelectedItem().toString() + " course");
			}
		});
	}
}
