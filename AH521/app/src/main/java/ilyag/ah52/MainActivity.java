package ilyag.ah52;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText	et;
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.square) {
			String s = et.getText().toString();
			if ("".equals(s)) {
				return false;
			}
			Integer i = Integer.parseInt(s);
			Toast.makeText(getApplicationContext(), String.valueOf(Math.sqrt(i)), Toast.LENGTH_LONG).show();
		} else if (id == R.id.powerTwo) {
			String s = et.getText().toString();
			if ("".equals(s)) {
				return false;
			}
			Integer i = Integer.parseInt(s);
			Toast.makeText(getApplicationContext(), String.valueOf(Math.pow(i, 2)), Toast.LENGTH_LONG).show();
		} else {
			return false;
		}
		return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et = (EditText) findViewById(R.id.editText);
		registerForContextMenu(et);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v == et) {
			getMenuInflater().inflate(R.menu.menu, menu);
		}
	}
}
