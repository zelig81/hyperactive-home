package ilyag.a32;

import java.util.HashMap;
import java.util.Map.Entry;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_show);
		HashMap<String, Integer> map = MyIO.load(this);
		StringBuilder sb = new StringBuilder();
		sb.append("count of items in purchase list:").append(map.size());
		for (Entry<String, Integer> e : map.entrySet()) {
			sb.append("\nproduct: ").append(e.getKey()).append(" / count: ").append(e.getValue());
		}
		TextView tv = (TextView) findViewById(R.id.textview1);
		tv.setText(sb.toString());
	}
	
}
