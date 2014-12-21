package ilyag.ah72;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText					et;
	private final static int	dialog_id	= 234234;
	private int					timer;
	private Handler				handler		= new Handler();
	private boolean				isPDShown;
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
					Toast.makeText(getApplication(), "Please fill the timer field", Toast.LENGTH_LONG).show();
					return;
				}
				timer = Integer.parseInt(s);
				isPDShown = true;
				showDialog(dialog_id);
				Log.i("ilyag", "0");
				/*
				 * new Thread() {
				 * 
				 * @Override public void run() { while (true) { if (isPDShown == false) { break; } if (timer < 0) {
				 * handler.post(new Runnable() {
				 * 
				 * @Override public void run() { MainActivity.this.finish(); } }); } try { Thread.sleep(100); } catch
				 * (InterruptedException e) { Log.e("ilyag", e.getMessage()); } } } }.start();
				 */
			}
		});
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Log.i("ilyag", "1");
		if (id == dialog_id) {
			ProgressDialog pd = new ProgressDialog(getApplication());
			pd.setTitle("The application will be closed after " + timer + ":");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			Log.i("ilyag", "2");
			pd.setButton("Cancel closing", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					isPDShown = false;
				}
			});
			Log.i("ilyag", "3");
			return pd;
			
		}
		return super.onCreateDialog(id);
		
	}
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		if (id == dialog_id) {
			final ProgressDialog pd = (ProgressDialog) dialog;
			pd.setMax(timer * 10);
			pd.setProgress(timer * 10);
			new Thread() {
				@Override
				public void run() {
					for (int i = timer * 10; i > 0.1; i--) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							Log.e("ilyag", e.getMessage());
						}
						handler.post(new Runnable() {
							
							@Override
							public void run() {
								pd.incrementProgressBy(-1);
							}
						});
					}
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							MainActivity.this.finish();
						}
					});
				}
			}.start();
		}
		super.onPrepareDialog(id, dialog);
	}
}
