package ilyag.ah101;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class MainActivity extends ActionBarActivity {
    Button button;
    Switch switch1;
    TextView tv;
    ListView lv;
    MyService mService;
    boolean mBound;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };
    EditText etInteval;
    Handler handler = new Handler();
    Location mCurrentLocation;
    private String mLastUpdateTime;

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    /**
     * Dispatch onStart() to all fragments.  Ensure any created loaders are
     * now started.
     */
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        switch1 = (Switch) findViewById(R.id.switch1);
        tv = (TextView) findViewById(R.id.textView);
        lv = (ListView) findViewById(R.id.listView);
        etInteval = (EditText) findViewById(R.id.etInterval);
        tv.setText("service is stopped");
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "1U2uuILYFaYH8wnzNY3WQCcHXy2wmjudN3YvxPNP", "8Ix1v3GfzSwnXyqmvzY1dov4hgixOseXSFQllVsz");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("gps_info");
        try {
            int count = query.count();
            if (count != 10) {
                Toast.makeText(this, "count of parseobjects in gps_info: " + count, Toast.LENGTH_LONG).show();
                for (int i = 0; i < 10; i++) {
                    ParseObject po = new ParseObject("gps_info");
                    po.put("running_number", i);
                    po.save();
                }
            }
        } catch (ParseException e) {
            Log.e("ilyag1", e.getMessage());
        }

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBound) {
                    if (switch1.isChecked()) {
                        long interval = 10000;
                        if (!"".equals(etInteval.getText().toString())) {
                            interval = Long.parseLong(etInteval.getText().toString());
                        }
                        mService.startMyProcess(interval, MainActivity.this);
                        tv.setText("running service");
                    } else {
                        mService.stopProcess();
                        tv.setText("service is stopped");
                    }
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("button clicked");
            }
        });
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        mService.stopProcess();
        super.onPause();
    }


}
