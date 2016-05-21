package shehan.com.migrainetrigger.utility;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Shehan on 4/13/2016.
 */
public class GeoLocationService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final int PERMISSION_ACCESS_FINE_LOCATION = 1;
    private FragmentActivity activity;
    private GeoLocationListener geoLocationListener;
    private GoogleApiClient googleApiClient;

    public GeoLocationService(FragmentActivity activity, GeoLocationListener geoLocationListener) {
        this.activity = activity;
        this.geoLocationListener = geoLocationListener;
        googleApiClient = new GoogleApiClient.Builder(activity, this, this).enableAutoManage(activity, this).addApi(LocationServices.API).build();
        Log.i("GeoLocationService", "Created new googleApiClient");

    }

    public void connect() {
        if (googleApiClient != null) {
            googleApiClient.connect();
            Log.d("GeoLocationService", "googleApiClient.connect");
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("GeoLocationService", "Connected to Google Play Services!");

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null) {
                Log.d("GeoLocationService", "Latitude : " + lastLocation.getLatitude() + " Longitude : " + lastLocation.getLongitude());
            } else {
                Log.e("GeoLocationService", "null location");
            }
            //When location relieved tell to listener aka the calling fragment

            geoLocationListener.onLocationReceived(lastLocation);
            disconnect();
        } else {
            Log.e("GeoLocationService", "permission denied");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    public void disconnect() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.stopAutoManage(activity);
            googleApiClient.disconnect();
            Log.d("GeoLocationService", "googleApiClient.disconnect");
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("GeoLocationService", "Can't connect to Google Play Services!");
    }

    public interface GeoLocationListener {
        void onLocationReceived(Location location);
    }
}
