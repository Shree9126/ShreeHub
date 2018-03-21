package com.mindnotix.youthhub;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.ExploreListAdapter;
import com.mindnotix.model.explore.filter_master.Course_sortby;
import com.mindnotix.model.explore.list.DataExploreListItems;
import com.mindnotix.model.explore.list.Explore_list;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mindnotix.youthhub.ProfileActivity.TAG;

/**
 * Created by Admin on 2/19/2018.
 */

public class ExploreShareExploreFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    public static String path_image = " ";
    public static String path_pdf = " ";
    ExploreListAdapter exploreListAdapter;
    RecyclerView recyclerViewEventList;
    ExploreActivity exploreActivity;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager linearLayoutManager;
    View bottom_layout;
    String NextPage = "";
    ArrayList<Explore_list> exploreLists = new ArrayList<>();
    ProgressDialog progressDialog;
    String course_sortbyss = "";
    String course_postby = "";
    String course_name = "";
    ArrayList<Explore_list> explore_listArrayList;
    View browseClick;
    TextView txtEmpty;
    ArrayList<Course_sortby> course_sortby;
    private boolean userScrolled = true;
    private SwipeRefreshLayout swipeRefreshLayout;

    List<String> pageIdList = new ArrayList<>();
    private String courseTitle="";
    private String postedBy="";
    private int soretBy=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shared_explore, container, false);

        UiInitialization(view);



        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        loadExploreData("1", NextPage);

                                    }
                                }
        );

        implementScrollListener();
        return view;

    }

    @Override
    public void onRefresh() {
        NextPage = "";

        loadExploreData("1", NextPage);
    }

    private void UiInitialization(View view) {

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        bottom_layout = view.findViewById(R.id.loadItemsLayout_recyclerView);
        recyclerViewEventList = view.findViewById(R.id.recyclerViewEventList);
        txtEmpty = view.findViewById(R.id.txtEmpty);
        browseClick = view.findViewById(R.id.browseClick);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewEventList.setHasFixedSize(true);
        recyclerViewEventList.setLayoutManager(linearLayoutManager);


        exploreActivity = (ExploreActivity) getActivity();

        assert exploreActivity != null;


        browseClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogLoadSpinner();


            }
        });
    }

    private void dialogLoadSpinner() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_filter);

        final EditText edtCourseTitle = (EditText) dialog.findViewById(R.id.edtCourseTitle);
        final EditText edtPostBy = (EditText) dialog.findViewById(R.id.edtPostBy);
        final Spinner spnrSortByRating = (Spinner) dialog.findViewById(R.id.spnrSortByRating);
        setSpinner(spnrSortByRating);
        Button btSearch = (Button) dialog.findViewById(R.id.btSearch);
        dialog.show();

        edtCourseTitle.setText(courseTitle);
        edtPostBy.setText(postedBy);
        spnrSortByRating.setSelection(soretBy);

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                course_name = edtCourseTitle.getText().toString();
                course_postby = edtPostBy.getText().toString();
                if (!course_sortby.get(spnrSortByRating.getSelectedItemPosition()).getId().equals("0"))
                    course_sortbyss = course_sortby.get(spnrSortByRating.getSelectedItemPosition()).getId();
                else
                    course_sortbyss = "";

                NextPage = "";

                Log.d(TAG, "onClick:course_sortbyss " + course_sortbyss);
                Log.d(TAG, "onClick:course_postby " + course_postby);
                Log.d(TAG, "onClick:course_name " + course_name);
                loadExploreDataSearch("1", NextPage);


                courseTitle=course_name;
                postedBy=course_postby;
                soretBy= spnrSortByRating.getSelectedItemPosition();
            }
        });


    }

    public void loadExploreDataSearch(final String status, String fm) {
        swipeRefreshLayout.setRefreshing(true);
        // exploreLists.clear();
        exploreLists = new ArrayList<>();
        // progressDialog = Utils.createProgressDialog(getActivity());

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Map<String, String> data = new HashMap<>();
        data.put("ismyexplore", status);
        data.put("course_name", course_name);
        data.put("course_postby", course_postby);
        data.put("course_sortby", course_sortbyss);
        data.put("fm", fm);

        Call<DataExploreListItems> call = apiService.getLoadExploreData(Pref.getPreToken(), data);

        call.enqueue(new Callback<DataExploreListItems>() {
            @Override
            public void onResponse(Call<DataExploreListItems> call, Response<DataExploreListItems> response) {
                Log.d(TAG, "onResponse:firsttime  " + new Gson().toJson(response.body()));

                // progressDialog.dismiss();
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);


                try {
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body().getStatus().equals("1")) {

                            Pref.setdevicetoken("Bearer " + response.body().getToken());

                            recyclerViewEventList.setVisibility(View.VISIBLE);
                            txtEmpty.setVisibility(View.GONE);

                            path_image = response.body().getData().getPath_image();
                            path_pdf = response.body().getData().getPath_pdf();

                            NextPage = response.body().getNextpage();
                            exploreLists.addAll(response.body().getData().getExplore_list());


                            Log.d(TAG, "onResponse:exploreLists " + exploreLists.size());
                            exploreListAdapter = new ExploreListAdapter(getActivity(), exploreLists);
                            recyclerViewEventList.setAdapter(exploreListAdapter);


                            // exploreListAdapter.notifyDataSetChanged();


                        } else {
                            recyclerViewEventList.setVisibility(View.GONE);
                            txtEmpty.setVisibility(View.VISIBLE);
                        //   Toast.makeText(getActivity(), "No record available", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<DataExploreListItems> call, Throwable t) {
                //  progressDialog.dismiss();
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);

                t.printStackTrace();
            }
        });


    }

    public void loadExploreData(final String status, String fm) {

        pageIdList.clear();
        exploreLists = new ArrayList<>();
        // progressDialog = Utils.createProgressDialog(getActivity());

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Map<String, String> data = new HashMap<>();
        data.put("ismyexplore", status);
        data.put("fm", fm);

        Call<DataExploreListItems> call = apiService.getLoadExploreData(Pref.getPreToken(), data);

        call.enqueue(new Callback<DataExploreListItems>() {
            @Override
            public void onResponse(Call<DataExploreListItems> call, Response<DataExploreListItems> response) {
                Log.d(TAG, "onResponse:firsttime  " + new Gson().toJson(response.body()));

                // progressDialog.dismiss();
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);


                try {
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body().getStatus().equals("1")) {

                            Pref.setdevicetoken("Bearer " + response.body().getToken());
                            recyclerViewEventList.setVisibility(View.VISIBLE);
                            txtEmpty.setVisibility(View.GONE);

                            path_image = response.body().getData().getPath_image();
                            path_pdf = response.body().getData().getPath_pdf();

                            NextPage = response.body().getNextpage();
                            exploreLists.addAll(response.body().getData().getExplore_list());


                            Log.d(TAG, "onResponse:exploreLists " + exploreLists.size());
                            exploreListAdapter = new ExploreListAdapter(getActivity(), exploreLists);
                            recyclerViewEventList.setAdapter(exploreListAdapter);


                            // exploreListAdapter.notifyDataSetChanged();


                        } else {
                            recyclerViewEventList.setVisibility(View.GONE);
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
            public void onFailure(Call<DataExploreListItems> call, Throwable t) {
                //  progressDialog.dismiss();
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);

                t.printStackTrace();
            }
        });


    }

    private void implementScrollListener() {

        recyclerViewEventList
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

                        visibleItemCount = linearLayoutManager.getChildCount();
                        totalItemCount = linearLayoutManager.getItemCount();
                        pastVisiblesItems = linearLayoutManager
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

        if(!pageIdList.contains(NextPage)){




            bottom_layout.setVisibility(View.VISIBLE);

            Map<String, String> data = new HashMap<>();
            data.put("ismyexplore", "1");
            data.put("fm", NextPage);
            data.put("course_name", course_name);
            data.put("course_postby", course_postby);
            data.put("course_sortby", course_sortbyss);


            Log.d(TAG, "loadMoreListItems: next page "+NextPage);
            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataExploreListItems> call = apiService.getLoadExploreData(Pref.getPreToken(), data);

            Log.d(TAG, "loadMoreListItems: shared explore url "+call.request().url());

            call.enqueue(new Callback<DataExploreListItems>() {
                @Override
                public void onResponse(Call<DataExploreListItems> call, Response<DataExploreListItems> response) {

                    bottom_layout.setVisibility(View.GONE);
                    try {

                        // dismiss Progress Layout
                        Log.d(TAG, "onResponse:load more  " + new Gson().toJson(response.body()));


                        if (response.code() == 200) {
                            if (response.body().getStatus().equals("1")) {

                                NextPage = response.body().getNextpage();
                                pageIdList.add(NextPage);
                                if (!NextPage.equals("0")) {
                                    exploreLists.addAll(response.body().getData().getExplore_list());

                                    NextPage = response.body().getNextpage();
                                    exploreListAdapter.notifyDataSetChanged();

                                } else {
                                    Toast.makeText(getActivity(), "No more data", Toast.LENGTH_SHORT).show();
                                }

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

                        bottom_layout.setVisibility(View.GONE);
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<DataExploreListItems> call, Throwable t) {
                    // dismiss Progress Layout
                    bottom_layout.setVisibility(View.GONE);
                    t.printStackTrace();
                }
            });

        }


    }

    public void loadFilterData(ArrayList<Course_sortby> course_sortby) {

        this.course_sortby = course_sortby;


    }

    private void setSpinner(Spinner spnrSortByRating) {


        ArrayAdapter<Course_sortby> dataAdapters = new ArrayAdapter<Course_sortby>(exploreActivity,
                android.R.layout.simple_spinner_dropdown_item, course_sortby);
        dataAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnrSortByRating.setAdapter(dataAdapters);
        spnrSortByRating.setSelection(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
