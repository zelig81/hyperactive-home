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

/**
 * Created by gorban on 2/20/2015.
 */
public class MyService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    boolean bGoingOn = false;
    private final IBinder mBinder = new LocalBinder();

    @Override
    public void onDestroy() {
        super.onDestroy();
        bGoingOn = false;
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
                LocationRequest mLocationRequest = new LocationRequest();
                mLocationRequest.setInterval(msInterval).setFastestInterval(msInterval).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                while (bGoingOn) {
                    mContext.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "Still going on", Toast.LENGTH_SHORT).show();
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
        }).start();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onConnected(Bundle bundle) {
        
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

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
