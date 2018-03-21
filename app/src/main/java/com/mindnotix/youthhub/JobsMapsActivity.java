package com.mindnotix.youthhub;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class JobsMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = JobsMapsActivity.class.getSimpleName();
    String lattitude, longtitude, companyName;
    private GoogleMap mMap;

    private android.support.design.widget.FloatingActionButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_maps);
        this.imageButton = (FloatingActionButton) findViewById(R.id.imageButton);
        this.imageButton.setVisibility(View.GONE);

        if (getIntent() != null) {
            lattitude = getIntent().getStringExtra("lat");
            companyName = getIntent().getStringExtra("companyName");
            longtitude = getIntent().getStringExtra("lang");
            Log.d(TAG, "onCreate: companyname " + companyName);

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {

            mMap = googleMap;

            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(Double.parseDouble(lattitude), Double.parseDouble(longtitude));
            mMap.addMarker(new MarkerOptions().position(sydney).title(companyName)).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lattitude), Double.parseDouble(longtitude)), 12.0f));
        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}
