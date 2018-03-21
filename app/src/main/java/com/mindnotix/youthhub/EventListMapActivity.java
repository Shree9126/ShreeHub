package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.mindnotix.adapter.EventsListAdapter;
import com.mindnotix.model.events.list.DataEventsListItems;
import com.mindnotix.model.events.list.Event_list;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = EventListMapActivity.class.getSimpleName();
    private GoogleMap mMap;
    ArrayList<Event_list> event_listArrayList = new ArrayList<>();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

        getAllEvents();
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
        mMap = googleMap;

       /* // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    private void getAllEvents() {

        progressDialog = Utils.createProgressDialog(this);

        event_listArrayList.clear();
        Map<String, String> data = new HashMap<>();
        data.put("ismyevent", "3");

        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataEventsListItems> call = apiService.getEventsList(token, data);
        Log.d(TAG, "DataEventsListItems: first time url " + call.request().url());

        call.enqueue(new Callback<DataEventsListItems>() {
            @Override
            public void onResponse(Call<DataEventsListItems> call, Response<DataEventsListItems> response) {
                progressDialog.dismiss();


                Log.d(TAG, "onResponse:DataDashboardTimeLine " + new Gson().toJson(response.body()));
                if (response.code() == 200) {

                    if (response.body().getStatus().equals("1")) {

                        event_listArrayList.addAll(response.body().getData().getEvent_list());


                        List<Marker> markers = new ArrayList<>();

                        for (int i = 0; i < event_listArrayList.size(); i++) {

                            //150 event id

                            Marker marker = mMap.addMarker(new MarkerOptions()
                                    .position
                                            (new LatLng(Double.parseDouble(event_listArrayList.get(i).getLatitude())
                                                    , Double.parseDouble(event_listArrayList.get(i).getLongitude())))
                                                .icon(BitmapDescriptorFactory.
                                            defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));;
                            markers.add(marker);



                        }

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (Marker marker : markers) {
                            builder.include(marker.getPosition());
                        }

                        LatLngBounds bounds = builder.build();
                        int width = getResources().getDisplayMetrics().widthPixels;
                        int height = getResources().getDisplayMetrics().heightPixels;
                        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

                        mMap.moveCamera(cu);
                        mMap.animateCamera(cu);


                    } else {
                        Log.d(TAG, "onResponse: else");

                    }
                } else if (response.code() == 304) {

                    Toast.makeText(EventListMapActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(EventListMapActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(EventListMapActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(EventListMapActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(EventListMapActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(EventListMapActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                   // Toast.makeText(getActivity(), "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<DataEventsListItems> call, Throwable t) {
                progressDialog.dismiss();

                t.printStackTrace();
            }
        });
    }


    protected Marker createMarker(String lat, String lng) {


        try {

            return mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)))
                    .draggable(false)
                    .icon(BitmapDescriptorFactory.
                            defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));




        } catch (Exception e) {

        }
        return null;
    }

    public void myLocationCall(View view) {
        Intent mIntent = new Intent(EventListMapActivity.this,EventListMapFilter.class);
        startActivity(mIntent);
    }
}
