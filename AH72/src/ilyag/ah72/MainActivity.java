package ilyag.ah72;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	EditText					et;
	private final static int	dialog_id	= 234234234;
	private int					timer;
	Button						b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b = (Button) findViewById(R.id.button1);
		et = (EditText) findViewById(R.id.editText1);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String s = et.getText().toString();
				if ("".equals(s)) {
					timer = 0;
				} else {
					timer = Integer.parseInt(s);
				}
				showDialog(dialog_id);
			}
		});
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == dialog_id) {
			Builder builder = new AlertDialog.Builder(getApplication());
			builder.setTitle("The application will be closed after " + timer + ":");
			builder.setCancelable(false);
			builder.setNegativeButton(R.string.tCancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			return builder.create();
			
		}
		return super.onCreateDialog(id);
		
	}
}
