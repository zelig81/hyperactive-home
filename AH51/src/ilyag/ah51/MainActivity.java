package ilyag.ah51;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import static android.content.res.XmlResourceParser.*;
import android.util.Log;

public class MainActivity extends Activity {
	TextView	tv;
	
	private String getTitlesFromRss() {
		StringBuilder sb = new StringBuilder();
		Resources res = getResources();
		XmlResourceParser parser = res.getXml(R.xml.ynet);
		try {
			int et = parser.getEventType();
			boolean bItem = false, bTitle = false;
			while (et != END_DOCUMENT) {
				String tagName = parser.getName();
				if (et == START_TAG) {
					if ("item".equals(tagName)) {
						bItem = true;
					}
					if (bItem == true && "title".equals(tagName)) {
						bTitle = true;
					}
				}
				
				if (et == END_TAG) {
					if ("item".equals(tagName)) {
						bItem = false;
					}
					if ("title".equals(tagName)) {
						bTitle = false;
					}
					
				}
				if (bTitle == true && et == TEXT) {
					Log.i("test", parser.getText());
					sb.append(parser.getText());
				}
				et = parser.next();
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
		tv = (TextView) findViewById(R.id.textView);
		tv.setMovementMethod(new ScrollingMovementMethod());
		String s = getTitlesFromRss();
		tv.setText(s);
	}
}
