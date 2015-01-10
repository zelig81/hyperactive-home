package ilyag.ac52;

import android.R.integer;
import android.app.Activity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import org.xmlpull.v1.*;

public class MainActivity extends Activity {
	Button		b;
	TextView	tv;
	
	protected String getRSSTitles() {
		StringBuilder sb = new StringBuilder();
		Resources res = getResources();
		XmlResourceParser xrp = res.getXml(R.xml.ny_times_international);
		try {
			int et = xrp.getEventType();
			boolean takeItem = false;
			boolean takeTitleInItem = false;
			while (et != XmlResourceParser.END_DOCUMENT) {
				String object = xrp.getName();
				if (et == XmlResourceParser.START_TAG) {
					if ("item".equals(object)) {
						takeItem = true;
					}
					if (takeItem == true && "title".equals(object)) {
						takeTitleInItem = true;
					}
				}
				if (et == XmlResourceParser.END_TAG) {
					if ("item".equals(object)) {
						takeItem = false;
					}
					if ("title".equals(object)) {
						takeTitleInItem = false;
					}
				}
				if (takeTitleInItem == true && et == XmlResourceParser.TEXT) {
					sb.append(xrp.getText());
				}
				et = xrp.next();
				
			}
		} catch (XmlPullParserException e) {
			Log.e("test", e.getMessage());
		} catch (IOException e) {
			Log.e("test", e.getMessage());
		}
		
		return sb.toString();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b = (Button) findViewById(R.id.button1);
		tv = (TextView) findViewById(R.id.textView);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String s = getRSSTitles();
				tv.setMovementMethod(new ScrollingMovementMethod());
				tv.setText(s);
			}
		});
	}
}
