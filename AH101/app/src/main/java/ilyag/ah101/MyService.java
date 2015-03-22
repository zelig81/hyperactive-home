package ilyag.ah101;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by gorban on 2/20/2015.
 */
public class MyService extends Service implements LocationListener {
    private final IBinder mBinder = new LocalBinder();
    public boolean bGoingOn = false;
    int running_number;
    List<ParseObject> list;
    LocationManager locationManager;
    LocationListener locationListener;
    private MainActivity mainActivity;
    private Location lastLocation;
    private String provider;

    /**
     * Called by the system when the service is first created.  Do not call this method directly.
     */

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMyProcess();
    }

    /**
     * Called by the system when the service is first created.  Do not call this method directly.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) MyService.this.getSystemService(Context.LOCATION_SERVICE);
    }

    public void stopMyProcess() {
        bGoingOn = false;
        if (locationListener != null)
            locationManager.removeUpdates(locationListener);
    }

    public void startMyProcess(final long msInterval, final MainActivity mContext) {
        Log.e("ilyag1", "started my process");
        mainActivity = mContext;
        if (msInterval < 1) {
            return;
        }
        bGoingOn = true;
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        provider = LocationManager.NETWORK_PROVIDER;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            provider = LocationManager.GPS_PROVIDER;
        }else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            provider = LocationManager.NETWORK_PROVIDER;
        }else{
            mContext.handler.post(new Runnable() {
                @Override
                public void run() {
                    mContext.tv.setText("no location providers enabled");
                    mContext.switch1.setChecked(false);
                }
            });
        }
        mContext.handler.post(new Runnable() {
            @Override
            public void run() {
                mContext.tv.setText("is provider " + provider + " available? " + locationManager.isProviderEnabled(provider));
            }
        });
        locationManager.requestLocationUpdates(provider, 1, 0f, locationListener);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("gps_info");
        query.orderByDescending("updatedAt");
        try {
            list = query.find();
        } catch (ParseException e) {
            Log.e("ilyag1", e.getMessage());
        }
        running_number = list.get(0).getInt("running_number");

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (list == null) {
                    mContext.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "No objects found in database", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    while (bGoingOn) {
                        running_number = (running_number + 1) % 10;
                        lastLocation = locationManager.getLastKnownLocation(provider);
                        Log.e("ilyag1", "is last location null: " + (lastLocation == null) + "\n provider is: " + provider);
                        try {
                            list.get(running_number).put("geopoint", new ParseGeoPoint(lastLocation.getLatitude(), lastLocation.getLongitude())); //NPE on gps off
                            list.get(running_number).save();
                        } catch (ParseException e) {
                            Log.e("ilyag1", e.getMessage());
                            bGoingOn = false;
                        }
                        mContext.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mContext.tv.setText("Last updated gps running number:" + running_number + "\nLongitude: " + lastLocation.getLongitude() + " / Latitude: " + lastLocation.getLatitude());
                            }
                        });
                        try {
                            Thread.sleep(msInterval);
                        } catch (InterruptedException e) {
                            Log.e("ilyag1", e.getMessage());
                            bGoingOn = false;
                        }
                    }
                    stopMyProcess();
                }
            }
        }).start();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onLocationChanged(final Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public class LocalBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}
