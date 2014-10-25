package il.co.hyperactive.ilyag;

import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;

public class HelloKittyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.hello_kitty_layout);
		TextView tv = (TextView) this.findViewById(R.id.helloHello);
		tv.setText("Hello hello ki... hm no... dear [nickname]=" + this.getIntent().getStringExtra("nickname"));
	}
}
