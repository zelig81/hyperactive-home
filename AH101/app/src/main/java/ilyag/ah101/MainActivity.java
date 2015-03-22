package ilyag.ah101;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    Button button;
    Switch switch1;
    TextView tv;
    ListView lv;
    MyService mService;
    boolean mBound;
    List<ParseObject> poList;

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
                        long interval = 10;
                        if (!"".equals(etInteval.getText().toString())) {
                            interval = Long.parseLong(etInteval.getText().toString());
                        }
                        mService.startMyProcess(interval, MainActivity.this);
                        tv.setText("running service");
                    } else {
                        mService.stopMyProcess();
                        tv.setText("service is stopped");
                    }
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("gps_info");
                query.orderByDescending("updatedAt");
                try {
                    poList = query.find();
                    List<String> sList = new ArrayList<String>();
                    for (ParseObject po : poList) {
                        ParseGeoPoint geoPoint = (ParseGeoPoint) po.get("geopoint");
                        sList.add(po.get("running_number") + ": " + geoPoint.toString() + " / " + DateFormat.getDateTimeInstance().format(po.getUpdatedAt()));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, sList);
                    lv.setAdapter(adapter);
                } catch (ParseException e) {
                    Log.e("ilyag1", e.getMessage());
                }

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ParseObject po = poList.get(i);
                ParseGeoPoint pgp = (ParseGeoPoint)po.get("geopoint");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String sUri = "geo:" + pgp.getLatitude() + "," + pgp.getLongitude() + "?z=16";
                Uri uri = Uri.parse(sUri);
                intent.setData(uri); //geo:47.6,-122.3?z=11
                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        if (mService.bGoingOn) {
            mService.stopMyProcess();
        }
        super.onPause();
    }


}
