package ilyag.ah101;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by gorban on 2/20/2015.
 */
public class MyService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    boolean bGoingOn = false;
    private final IBinder mBinder = new LocalBinder();
    int running_number;
    LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleAPIClient;
    private Location mCurrentLocation;
    List<ParseObject> list;

    /**
     * Called by the system when the service is first created.  Do not call this method directly.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleAPIClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bGoingOn = false;
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleAPIClient, this);
    }


    public void stopProcess() {
        bGoingOn = false;
    }

    public void startMyProcess(final long msInterval, final MainActivity mContext) {
        Log.e("ilyag1", "started my process");

        if (msInterval < 1) {
            return;
        }
        bGoingOn = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("gps_info");
                query.orderByDescending("updatedAt");
                try {
                    list = query.find();
                } catch (ParseException e) {
                    Log.e("ilyag1", e.getMessage());
                }
                if (list == null) {
                    mContext.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "No objects found in database", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    running_number = list.get(0).getInt("running_number");
                    createLocationUpdate(msInterval);
                    while (bGoingOn) {
                        running_number = (running_number + 1)%10;
                        mContext.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mContext.tv.setText("Last updated gps running number:" + running_number + ". Longitude: " + mCurrentLocation.getLongitude() + " / Latitude: " + mCurrentLocation.getLatitude());
                            }
                        });
                        try {
                            Thread.sleep(msInterval);
                        } catch (InterruptedException e) {
                            Log.e("ilyag1", e.getMessage());
                            bGoingOn = false;
                        }
                    }
                }
            }
        }).start();

    }

    public void createLocationUpdate(long interval) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setFastestInterval(interval);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(interval);
        Log.i("ilyag1", "set location request in createLocationUpdate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (bGoingOn) {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleAPIClient, mLocationRequest, this);
        Log.i("ilyag1", "started LocationServices in startLocationUpdates + object of GoogleApiClient=" + mGoogleAPIClient.toString());
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        Log.i("ilyag1", "onLocationChanged");
        try {
            updateParseDatabase(running_number, mCurrentLocation);
        } catch (ParseException e) {
            Log.e("ilyag1", e.getMessage());
        }
    }

    private void updateParseDatabase(int running_number, Location mCurrentLocation) throws ParseException {
        if (list != null) {
            Log.i("ilyag1","before updateParseDatabase update");
            ParseObject poToUpdate = list.get(running_number);
            poToUpdate.put("geopoint", new ParseGeoPoint(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
            Log.i("ilyag1", mCurrentLocation.getLatitude() + "=======================" + mCurrentLocation.getLongitude());
            poToUpdate.save();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public class LocalBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}
