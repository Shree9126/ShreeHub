package com.mindnotix.youthhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.mindnotix.model.jobs.list.Data;
import com.mindnotix.model.jobs.list.DataJobListItems;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 2/22/2018.
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = MapsActivity.class.getSimpleName();
    ProgressDialog progressDialog;
    String lattitude, longtitude, companyName;
    ArrayList<Data> dataJobArrayList = new ArrayList<>();
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_maps);

        getJobList();
        //getJobLists();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getJobList() {


        dataJobArrayList.clear();
        progressDialog = Utils.createProgressDialog(this);
        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("profileid", Pref.getClientId());
        data.put("ismap", "1");
       /* data.put("keywords", edtSearchTxt);
        data.put("category", spnrJobCategoryTxt_ID);
        data.put("subcategory", spnrJobSubCategoryTxt_ID);
        data.put("jobtype", spnrJobTypeTxt_ID);
        data.put("regional", spnrRegionTxt_ID);
        data.put("local_board", spnrDistrictTxt_ID);
        data.put("salary_type", spnrSalaryRange_Type_ID);
        data.put("salary_offered_from", spnrSalaryFromRange_ID);
        data.put("salary_offered_to", spnrSalaryToRange_ID);
        data.put("sort_by", spnrSortByDate_ID);
        data.put("favourites", favourites);
        data.put("fm", "");*/


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("MapJobAcitivity  key, " + key + " value " + value);
        }


        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);


        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<DataJobListItems> call = apiService.getJobLists(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataJobListItems>() {
            @Override
            public void onResponse(Call<DataJobListItems> call, Response<DataJobListItems> response) {

                try {

                    Log.d(TAG, "onResponse:DataJobListItems " + new Gson().toJson(response.body()));

                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("1")) {

                            dataJobArrayList.addAll(response.body().getData());

                            List<Marker> markers = new ArrayList<>();

                            for (int i = 0; i < dataJobArrayList.size(); i++) {


                                Marker marker = mMap.addMarker(new MarkerOptions()
                                        .position
                                        (new LatLng(Double.parseDouble(dataJobArrayList.get(i).getLatitude())
                                        , Double.parseDouble(dataJobArrayList.get(i).getLongitude()))).icon(BitmapDescriptorFactory.
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

                            Toast.makeText(MapsActivity.this, "No record available", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(MapsActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(MapsActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(MapsActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(MapsActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(MapsActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(MapsActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(MapsActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataJobListItems> call, Throwable t) {
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
            mMap.addMarker(new MarkerOptions().position(sydney).title(companyName));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lattitude), Double.parseDouble(longtitude)), 12.0f));
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public void myLocationCall(View view) {

        Intent mIntent = new Intent(MapsActivity.this, MapJobFilterActivity.class);
        startActivity(mIntent);
    }
}

