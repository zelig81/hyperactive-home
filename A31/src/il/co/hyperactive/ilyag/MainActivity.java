package il.co.hyperactive.ilyag;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	EditText			nickname, user, password;
	static String		pref_name	= "my_pref_name";
	SharedPreferences	sp;
	boolean				passed		= false;
	String				sUser, sPassword, sNickname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main_layout);
		Button b = (Button) this.findViewById(R.id.button1);
		this.user = (EditText) this.findViewById(R.id.username);
		this.password = (EditText) this.findViewById(R.id.password);
		this.nickname = (EditText) this.findViewById(R.id.nickname);
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity.this.sUser = MainActivity.this.user.getText().toString();
				MainActivity.this.sPassword = MainActivity.this.password.getText().toString();
				MainActivity.this.sNickname = MainActivity.this.nickname.getText().toString();
				if ("AdminLior".equals(MainActivity.this.sUser) && "AdminLior".equals(MainActivity.this.sPassword)) {
					MainActivity.this.passed = true;
					Intent intent = new Intent(MainActivity.this, HelloKittyActivity.class);
					intent.putExtra("nickname", MainActivity.this.sNickname);
					MainActivity.this.startActivity(intent);
				} else {
					MainActivity.this.passed = false;
					Toast t =
							Toast.makeText(MainActivity.this.getApplicationContext(),
									"You typed wrong username/password my dear " + MainActivity.this.sNickname
											+ ". its were " + MainActivity.this.sUser + "/"
											+ MainActivity.this.sPassword,
									Toast.LENGTH_LONG);
					t.show();
				}

			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.sp = this.getApplicationContext().getSharedPreferences(pref_name, MODE_PRIVATE);
		if (this.passed == true) {
			MainActivity.this.sp.edit().putBoolean("passed", true);
			MainActivity.this.sp.edit().putString("nickname", this.sNickname);
			MainActivity.this.sp.edit().putString("user", this.sUser);
			MainActivity.this.sp.edit().putString("password", this.sPassword);
			MainActivity.this.sp.edit().commit();
		} else {
			this.sp.edit().putBoolean("passed", false);
			this.sp.edit().commit();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e("onResume before check", "test");
		this.sp = this.getApplicationContext().getSharedPreferences(pref_name, MODE_PRIVATE);
		if (this.sp.contains("user")) {
			Log.e("onResume user", this.sp.getString("user", ""));
		}

		if (this.sp.contains("passed") && this.sp.getBoolean("passed", false) == true) {
			Log.e("onResume after check", "test");
			this.user = (EditText) this.findViewById(R.id.username);
			this.user.setText(this.sp.getString("user", ""));
			this.nickname = (EditText) this.findViewById(R.id.nickname);
			this.nickname.setText(this.sp.getString("nickname", ""));
			this.password = (EditText) this.findViewById(R.id.password);
			this.password.setText(this.sp.getString("password", ""));
			this.passed = true;

		}
	}

}
