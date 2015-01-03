package ilyag.ah82;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends ActionBarActivity {
    static ArrayAdapter<String> aa;
    static int number;
    static String sToAdd;
    static MainActivity activity;
    Handler handler = new Handler();
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        Button bRun = (Button) findViewById(R.id.bRun);
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(aa);
        bRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                DialogFragment df = MyDialog.newInstance("http://www.ynet.co.il/Integration/StoryRss2.xml", handler, MainActivity.this);
                df.show(ft, "dialog");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MyDialog extends DialogFragment {
        static Handler handler;
        static MainActivity context;
        String url;
        TextView tv;
        int runner;

        public static MyDialog newInstance(String url, Handler handler, MainActivity context) {
            MyDialog df = new MyDialog();
            Bundle args = new Bundle();
            MyDialog.handler = handler;
            MyDialog.context = context;
            args.putString("url", url);
            df.setArguments(args);
            return df;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            url = getArguments().getString("url");
            setCancelable(false);
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Dialog);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_dialog, container, false);
            tv = (TextView) v.findViewById(R.id.tvOutput);
            tv.setText(url);
            View bCancel = v.findViewById(R.id.bCancel);
            ((Button) bCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyDialog.this.dismiss();
                }
            });
            return v;
        }

        @Override
        public void onStart() {
            super.onStart();
            new Thread() {
                @Override
                public void run() {
                    try {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                number = 0;
                                aa.clear();
                                aa.notifyDataSetChanged();
                            }
                        });

                        XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();
                        XmlPullParser xpp = xppf.newPullParser();
                        URL url = new URL(MyDialog.this.url);
                        InputStream is = url.openStream();
                        xpp.setInput(is, null);
                        int et = xpp.getEventType();
                        int status = 0;
                        while (et != XmlPullParser.END_DOCUMENT) {
                            if (et == XmlPullParser.START_TAG) {
                                String name = xpp.getName();
                                if (status == 0 && "item".equals(name)) {
                                    status = 1;
                                } else if (status == 1 && "title".equals(name)) {
                                    status = 2;
                                }

                            }
                            if (et == XmlPullParser.END_TAG) {
                                String name = xpp.getName();
                                if (status == 1 && "item".equals(name)) {
                                    status = 0;
                                } else if (status == 2 && "title".equals(name)) {
                                    status = 0;
                                }
                            }
                            if (et == XmlPullParser.TEXT) {
                                if (status == 2) {
                                    final String x = xpp.getText();
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            number++;
                                            tv.setText("fetched " + number);
                                            aa.add(x);
                                            aa.notifyDataSetChanged();
                                        }
                                    });
                                }
                            }
                            et = xpp.next();
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "was fetched " + number + " articles", Toast.LENGTH_LONG).show();
                            }
                        });
                        dismiss();
                    } catch (MalformedURLException e) {
                        Log.e("ilyag1", e.getMessage());
                    } catch (XmlPullParserException e) {
                        Log.e("ilyag1", e.getMessage());
                    } catch (IOException e) {
                        Log.e("ilyag1", e.getMessage());
                    }
                }
            }.start();
        }
    }
}
