package ilyag.ah62;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import static org.xmlpull.v1.XmlPullParser.*;

import android.app.Activity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;

public class MainActivity extends Activity {
	public class Tasker extends AsyncTask<URL, Integer, String> {
		
		@Override
		protected String doInBackground(URL... params) {
			URL url = params[0];
			StringBuilder output = new StringBuilder("");
			int num = 0;
			try {
				XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();
				XmlPullParser xpp = xppf.newPullParser();
				InputStream is = url.openStream();
				xpp.setInput(is, null);
				int et = xpp.getEventType();
				int status = 0;
				while (et != XmlPullParser.END_DOCUMENT) {
					if (et == START_TAG) {
						if ("item".equals(xpp.getName())) {
							status = 1;
						}
						if (status == 1 && "title".equals(xpp.getName())) {
							status = 2;
						}
					}
					if (et == END_TAG) {
						if ("item".equals(xpp.getName())) {
							status = 0;
						}
						if (status == 2 && "title".equals(xpp.getName())) {
							status = 1;
						}
					}
					if (et == TEXT) {
						if (status == 2) {
							num++;
							output.append("\n").append(xpp.getText().toString());
						}
					}
					
					et = xpp.next();
				}
				publishProgress(num);
			} catch (XmlPullParserException e) {
				Log.e("ilyag", e.getMessage());
			} catch (IOException e) {
				Log.e("ilyag", e.getMessage());
			}
			
			return output.toString();
		}
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			tv.setText(result);
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Toast.makeText(getApplicationContext(), "starting downloading...", Toast.LENGTH_LONG).show();
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			Toast.makeText(getApplicationContext(), "downloaded " + values[0] + " articles", Toast.LENGTH_LONG).show();
			
		}
		
	}
	
	Button		b;
	TextView	tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b = (Button) findViewById(R.id.button1);
		tv = (TextView) findViewById(R.id.textView1);
		tv.setMovementMethod(new ScrollingMovementMethod());
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					new Tasker().execute(new URL("http://www.ynet.co.il/Integration/StoryRss2.xml"));
				} catch (MalformedURLException e) {
					Log.e("ilyag", e.getMessage());
				}
			}
		});
	}
}
