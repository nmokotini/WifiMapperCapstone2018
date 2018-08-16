package com.example.ntaolengmokotini.wifimapper;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;




public class WifiApp extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
   // private FusedLocationProviderClient mFusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_app);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       // mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


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
        mMap = googleMap;

        WifiManager wifiManager = (WifiManager)this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        //set number of RSSI Levels
        final int rssiLevels = 5;
        //calculate signal level based on  RSSI levels
        int wifiStrength = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), rssiLevels);

        LatLng compsci = new LatLng(-33.95686760901559, 18.46106835175306);
        mMap.addMarker(new MarkerOptions().position(compsci).title("Computer Science Building"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(compsci));
    }


}
