package com.example.ntaolengmokotini.wifimapper;

import android.Manifest;
import java.util.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.location.LocationManager;
import android.os.Looper;
import android.os.AsyncTask;
import com.loopj.android.http.*;
import org.json.*;

 import java.io.IOException;
 import java.io.InputStream;
 



import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdate;


import android.net.wifi.WifiInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.widget.TextView;

import cz.msebera.android.httpclient.HttpHeaders;


public class WifiApp extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private SupportMapFragment mapFragment;
    //private LocationRequest mLocationRequest;
    //Location mCurrentLocation;
    //private long UPDATE_INTERVAL = 10000;  //10 secs
    //private long FASTEST_INTERVAL = 2000; // 2 secs
    //private final static String KEY_LOCATION = "location";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_app);// creates the layout of the screen

        /*if (savedInstanceState != null && savedInstanceState.keySet().contains(KEY_LOCATION)) {
            // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocation
            // is not null.
            mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }*/

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //startLocationUpdates();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


    }
    /*@SuppressWarnings({"MissingPermission"})
    private void startLocationUpdates() {
        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);


        mFusedLocationClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    public void onLocationChanged(Location location) {
        // You can now create a LatLng Object for use with maps
        if (location == null) {
            return;
        }
        mCurrentLocation = location;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable(KEY_LOCATION, mCurrentLocation);
        super.onSaveInstanceState(savedInstanceState);
    }

    @SuppressWarnings({"MissingPermission"})
    public void getLastLocation() {
    mMap.setMyLocationEnabled(true);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                });
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }*/
    /*
    Calculates the strength of the WIFI signal
     */
    public String getWifiStrength(){
             WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
             WifiInfo wifiInfo = wifiManager.getConnectionInfo();//obtains information about wifi connection ( including the rssi)
             //List<WifiConfiguration> configs = wifiManager.getConfigureNetworks();
             //wifiManager.addNetwork((WifiConfiguration) configs);

             // sets levels out of which the RSSI signal level will be calculated
             final int rssiLevels = 5;
             //calculate signal level based on  RSSI levels
             int wifiStrength = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), rssiLevels)
             String strWifiInfo = "Wifi Strength: " + wifiStrength + "/" + rssiLevels;
             return strWifiInfo;
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //initializes the map object
        mMap = googleMap;
        // creates a Latlng object based on location's latitude & longitude value
        LatLng latLng = new LatLng(-33.956818038110015,18.461076503153894);
        //adds map marker based on location
        mMap.addMarker(new MarkerOptions().position(latLng).title("CSC Building:"+getWifiStrength()));
        //moves map camera zoom to location of the marker
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    public void GET() throws JSONException {

        RestClient.get("http://196.24.186.131:8080/", null,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, HttpHeaders[] headers, JSONObject response) {
                //for a JSON Object
            }
            @Override
            public void onSuccess(int statusCode, HttpHeaders[] headers, JSONArray pointers) throws JSONException {
                   JSONObject pointer = (JSONObject) pointers.get(0);


            }
            });

    }

    























}
