package com.mindnotix.youthhub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.mindnotix.adapter.ProfileGalleryFragmentAdapter;
import com.mindnotix.listener.RecyclerViewClickListener;

import com.mindnotix.model.profile.DataProfileGalleryItems;
import com.mindnotix.model.profile.Gallerylist;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 3/1/2018.
 */

public class FragmentProfileGallery extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = FragmentProfileGallery.class.getSimpleName();
    View bottom_layout;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Gallerylist> gallerylistArrayList;
    RecyclerViewClickListener listener;
    String NextPage = "";
    ProfileGalleryFragmentAdapter galleryFragmentAdapter;
    private RecyclerView recyclerViewGallery;
    private TextView txtEmpty;
    private RelativeLayout loadItemsLayoutrecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean userScrolled = true;
    GridLayoutManager gridLayoutManager;
    public FragmentProfileGallery() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_gallery, container, false);


        UiInitialization(view);


        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        NextPage = "";
                                        getProfileGalleryList(NextPage);
                                    }
                                }
        );

        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };
        implementScrollListener();
        return view;
    }

    private void UiInitialization(View view) {

        this.bottom_layout = view.findViewById(R.id.loadItemsLayout_recyclerView);
        this.txtEmpty = (TextView) view.findViewById(R.id.txtEmpty);
        this.recyclerViewGallery = (RecyclerView) view.findViewById(R.id.recyclerViewGallery);
      /*  linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewGallery.setHasFixedSize(true);
        recyclerViewGallery.setLayoutManager(linearLayoutManager);*/

       gridLayoutManager = new GridLayoutManager(getActivity(),2);
      gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
      recyclerViewGallery.setHasFixedSize(true);
      recyclerViewGallery.setLayoutManager(gridLayoutManager);

/*
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recyclerViewGallery.setHasFixedSize(true);
        this.recyclerViewGallery.setLayoutManager(manager);*/

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);


    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        NextPage = "";
        getProfileGalleryList(NextPage);
    }

    private void implementScrollListener() {

        recyclerViewGallery
                .addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView,
                                                     int newState) {

                        super.onScrollStateChanged(recyclerView, newState);

                        // If scroll state is touch scroll then set userScrolled
                        // true
                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                            userScrolled = true;

                        }

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx,
                                           int dy) {

                        super.onScrolled(recyclerView, dx, dy);
                        // Here get the child count, item count and visibleitems
                        // from layout manager

                        visibleItemCount = gridLayoutManager.getChildCount();
                        totalItemCount = gridLayoutManager.getItemCount();
                        pastVisiblesItems = gridLayoutManager
                                .findFirstVisibleItemPosition();

                        // Now check if userScrolled is true and also check if
                        // the item is end then update recycler view and set
                        // userScrolled to false
                        if (userScrolled
                                && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                            userScrolled = false;
                            loadMoreListItems();
                        }

                    }

                });
    }

    private void loadMoreListItems() {

        // Show Progress Layout
        bottom_layout.setVisibility(View.VISIBLE);


        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Map<String, String> data = new HashMap<>();
        data.put("profileid", Pref.getClientId());
        data.put("fm", NextPage);


        Call<DataProfileGalleryItems> call = apiService.getGalleryList(Pref.getPreToken(), data);

        Log.d(TAG, "getProfileGalleryList:URL  " + call.request().url());
        ;
        call.enqueue(new Callback<DataProfileGalleryItems>() {
            @Override
            public void onResponse(Call<DataProfileGalleryItems> call, Response<DataProfileGalleryItems> response) {
                Log.d(TAG, "onResponse:getProfileGalleryList  " + new Gson().toJson(response.body()));

                // Show Progress Layout
                bottom_layout.setVisibility(View.GONE);


                try {
                    if (response.code() == 200 || response.code() == 201) {

                        if (response.body().getStatus().equals("1")) {

                            gallerylistArrayList.addAll(response.body().getData().getGallerylist());
                            NextPage = response.body().getNextpage();

                            galleryFragmentAdapter.notifyDataSetChanged();

                        } else {

                            NextPage = "";

                        }

                    } else if (response.code() == 304) {

                        Toast.makeText(getActivity(), "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(getActivity(), "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(getActivity(), "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(getActivity(), "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(getActivity(), "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(getActivity(), "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(getActivity(), "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataProfileGalleryItems> call, Throwable t) {

                // Show Progress Layout
                bottom_layout.setVisibility(View.GONE);


                t.printStackTrace();
            }
        });
    }

    private void getProfileGalleryList(String nextPage) {
        gallerylistArrayList = new ArrayList<>();

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Map<String, String> data = new HashMap<>();
        data.put("profileid", Pref.getClientId());
        data.put("fm", nextPage);


        Call<DataProfileGalleryItems> call = apiService.getGalleryList(Pref.getPreToken(), data);

        call.enqueue(new Callback<DataProfileGalleryItems>() {
            @Override
            public void onResponse(Call<DataProfileGalleryItems> call, Response<DataProfileGalleryItems> response) {
                Log.d(TAG, "onResponse:getProfileGalleryList  " + new Gson().toJson(response.body()));

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);


                try {
                    if (response.code() == 200 || response.code() == 201) {

                        if (response.body().getStatus().equals("1")) {
                            recyclerViewGallery.setVisibility(View.VISIBLE);
                            txtEmpty.setVisibility(View.GONE);

                            gallerylistArrayList.addAll(response.body().getData().getGallerylist());
                            NextPage = response.body().getNextpage();

                            String image_medium = response.body().getData().getPath_medium();
                            String video_image_medium = response.body().getData().getVidimg_path();
                            String video_medium = response.body().getData().getVid_path();

                            galleryFragmentAdapter = new ProfileGalleryFragmentAdapter(getActivity(), gallerylistArrayList, listener,
                                    image_medium,video_image_medium,video_medium );
                            recyclerViewGallery.setAdapter(galleryFragmentAdapter);
                        } else {
                            recyclerViewGallery.setVisibility(View.GONE);
                            txtEmpty.setVisibility(View.VISIBLE);
                        }

                    } else if (response.code() == 304) {

                        Toast.makeText(getActivity(), "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(getActivity(), "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(getActivity(), "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(getActivity(), "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(getActivity(), "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(getActivity(), "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(getActivity(), "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataProfileGalleryItems> call, Throwable t) {

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);

                t.printStackTrace();
            }
        });


    }


}