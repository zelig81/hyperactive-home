package ilyag.a32;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class AddActivity extends Activity {
	EditText					product, count;
	HashMap<String, Integer>	map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_add);
		this.product = (EditText) this.findViewById(R.id.product_name);
		this.count = (EditText) this.findViewById(R.id.count);
		this.map = MyIO.load(this);
		Button b = (Button) this.findViewById(R.id.addB);
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = AddActivity.this.product.getText().toString();
				String sCount = AddActivity.this.count.getText().toString();
				Toast t;
				if ("".equals(name) || "".equals(sCount)) {
					t =
							Toast.makeText(AddActivity.this.getApplicationContext(),
									"please pay attention to put something in both fields",
									Toast.LENGTH_LONG);
					t.show();
				} else {
					AddActivity.this.map.put(name, Integer.parseInt(sCount));
					t =
							Toast.makeText(AddActivity.this.getApplicationContext(), "list counts "
									+ AddActivity.this.map.size() + " of products", Toast.LENGTH_LONG);
					t.show();
				}
				AddActivity.this.product.setText("");
				AddActivity.this.count.setText("");
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		MyIO.save(this, this.map);
	}
}
