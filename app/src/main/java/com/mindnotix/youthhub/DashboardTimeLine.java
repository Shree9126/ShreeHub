package com.mindnotix.youthhub;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.DashboardTimelineAdapter;
import com.mindnotix.eventbus.Events;
import com.mindnotix.eventbus.GlobalBus;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.listener.RecyclerViewLoadMoreScroll;
import com.mindnotix.model.BasicResponse;
import com.mindnotix.model.dashboard.DataDashboardTimeLine;
import com.mindnotix.model.dashboard.Postlist;
import com.mindnotix.model.dashboard.like_comments_contribute.DataLikeCommentsContriubes;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.IBaseResponseListener;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.MyCustomLayoutManager;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sridharan on 1/23/2018.
 */

public class DashboardTimeLine extends Fragment implements IBaseResponseListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "DashboardTimeLine";
    public static String mediumPath;
    public static String largePath;
    private static RelativeLayout bottomLayout;
    DashboardTimelineAdapter dashboardTimelineAdapter;
    RecyclerView TimeLinePostList;
    String thumbpath;
    TextView edtPost, txtEmpty;
    ProgressDialog progressDialog;
    String smallPath;
    ArrayList<Postlist> dataArrayList = new ArrayList<Postlist>();
    ArrayList<Postlist> dataArrayListLoad = new ArrayList<Postlist>();
    RecyclerViewClickListener listener;
    String NextPage = "";
    LinearLayoutManager linearLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    CircleImageView imgProfileImage;
    View naviagateMypost;
    MyCustomLayoutManager mLayoutManager;
    FloatingActionButton floatingActionButton;
    List<String> pageIdList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    // Variables for scroll listener
    private boolean userScrolled = true;
    private RecyclerViewLoadMoreScroll scrollListener;
    private Gson mGson;



    protected Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        NextPage = "";
        getTimeLinePostItems();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.dashboard_timeline_post, container, false);
        this.TimeLinePostList = (RecyclerView) view.findViewById(R.id.TimeLinePostList);
        edtPost = (TextView) view.findViewById(R.id.edtPost);
        txtEmpty = (TextView) view.findViewById(R.id.txtEmpty);
        naviagateMypost = view.findViewById(R.id.naviagateMypost);
        floatingActionButton = view.findViewById(R.id.fab);
        imgProfileImage = (CircleImageView) view.findViewById(R.id.imgProfileImage);
        bottomLayout = (RelativeLayout) view
                .findViewById(R.id.loadItemsLayout_recyclerView);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        // mLayoutManager = new MyCustomLayoutManager(getActivity());
        //TimeLinePostList.setLayoutManager(mLayoutManager);
        // TimeLinePostList.smoothScrollToPosition(position);


        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        TimeLinePostList.setLayoutManager(linearLayoutManager);
        TimeLinePostList.setHasFixedSize(true);
//        TimeLinePostList.setItemViewCacheSize(20);
//        TimeLinePostList.setDrawingCacheEnabled(true);
//        TimeLinePostList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        TimeLinePostList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    showFab();

                } else if (dy < 0) {
                    ;
                    hideFab();
                }
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) TimeLinePostList
                        .getLayoutManager();
                layoutManager.scrollToPositionWithOffset(0, 0);
            }
        });


        Log.d(TAG, "onCreateView:profile path name " + Pref.getClientProfilePictureName());
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }


        Log.d(TAG, "onCreateView: profile path = " + Pref.getClientImageThumbnailPath());
        Picasso.with(getActivity())
                .load(Pref.getClientImageThumbnailPath().concat(Pref.getClientProfilePictureName()))
                .placeholder(R.drawable.profile_pic) //this is optional the image to display while the url image is downloading
                .error(R.drawable.profile_pic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(imgProfileImage);


        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        NextPage = "";
                                        getTimeLinePostItems();
                                    }
                                }
        );



        listener = new RecyclerViewClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(final View view, final int position) {

                switch (view.getId()) {

                    case R.id.linear_likes:
                        Intent intent = new Intent(getActivity(), PostLikeActivity.class);
                        intent.putExtra("post_id", dataArrayList.get(position).getPosts().getPmPostId());
                        intent.putExtra("position", position);

                        startActivity(intent);

                        break;
                    case R.id.imgLikes:


                        Log.d(TAG, "onClick: like dislike status " + dataArrayList.get(position).getPostUserActivity().getIsEncourageUser());
                        if (dataArrayList.get(position).getPostUserActivity().getIsEncourageUser().equals("1")) {
                            new DisLike(position, view).execute();
                        } else {
                            new getCommentsLikes(position, view).execute();
                        }


                        break;

                    case R.id.imgComments:




                        Intent likeIntent = new Intent(getActivity(), PostCommentsActivity.class);
                        likeIntent.putExtra("post_id", dataArrayList.get(position).getPosts().getPmPostId());
                        likeIntent.putExtra("position", position);
                        startActivity(likeIntent);
                        break;

                    case R.id.linear_comments:

                        Intent linear_comments = new Intent(getActivity(), PostCommentsActivity.class);
                        linear_comments.putExtra("post_id", dataArrayList.get(position).getPosts().getPmPostId());

                        linear_comments.putExtra("position", position);
                        startActivity(linear_comments);
                        break;

                    case R.id.linear_contribute:

                        Intent linear_contribute = new Intent(getActivity(), PostContributeActivity.class);
                        linear_contribute.putExtra("post_id", dataArrayList.get(position).getPosts().getPmPostId());

                        linear_contribute.putExtra("position", position);
                        startActivity(linear_contribute);
                        break;
                    case R.id.imgContribute:

                        Intent imgContribute = new Intent(getActivity(), PostContributeActivity.class);
                        imgContribute.putExtra("post_id", dataArrayList.get(position).getPosts().getPmPostId());

                        imgContribute.putExtra("position", position);
                        startActivity(imgContribute);
                        break;


                    case R.id.ImagPopUp:

                        Log.d(TAG, "onClick:Pref.getClientId() " + Pref.getClientId());
                        Log.d(TAG, "onClick:getPmUserId() " + dataArrayList.get(position).getPosts().getPmUserId());
                        if (dataArrayList.get(position).getPosts().getPmUserId().equals(Pref.getClientId())) {
                            showPopupWindowMypost(view, position);
                        } else {
                            showPopupWindow(view, position);
                        }

                        break;

                    case R.id.imgJobLists:


                        try {
                            if(dataArrayList != null){
                                Intent i = new Intent(getActivity(), FullScreenViewActivity.class);
                                i.putExtra("position", 0);
                                i.putExtra("largePath", largePath);
                                i.putExtra("list", (Serializable) dataArrayList.get(position).getImages());
                                startActivity(i);
                            }
                        } catch (Exception e) {

                        }


                        break;

                    case R.id.image5:

                        Intent image5 = new Intent(getActivity(), FullScreenViewActivity.class);
                        image5.putExtra("position", 2);
                        image5.putExtra("largePath", largePath);
                        image5.putExtra("list", (Serializable) dataArrayList.get(position).getImages());
                        startActivity(image5);

                        break;

                    case R.id.image4:

                        Intent image4 = new Intent(getActivity(), FullScreenViewActivity.class);
                        image4.putExtra("position", 0);
                        image4.putExtra("largePath", largePath);
                        image4.putExtra("list", (Serializable) dataArrayList.get(position).getImages());
                        startActivity(image4);

                        break;

                    case R.id.image3:

                        Intent image3 = new Intent(getActivity(), FullScreenViewActivity.class);
                        image3.putExtra("position", 1);
                        image3.putExtra("largePath", largePath);
                        image3.putExtra("list", (Serializable) dataArrayList.get(position).getImages());
                        startActivity(image3);

                        break;

                    case R.id.image2:

                        Intent image2 = new Intent(getActivity(), FullScreenViewActivity.class);
                        image2.putExtra("position", 1);
                        image2.putExtra("largePath", largePath);
                        image2.putExtra("list", (Serializable) dataArrayList.get(position).getImages());
                        startActivity(image2);

                        break;

                    case R.id.image1:

                        Intent image1 = new Intent(getActivity(), FullScreenViewActivity.class);
                        image1.putExtra("position", 0);
                        image1.putExtra("largePath", largePath);
                        image1.putExtra("list", (Serializable) dataArrayList.get(position).getImages());
                        startActivity(image1);

                        break;


                }

            }
        };


        //   TimeLinePostList.addOnScrollListener(scrollListener);

        //  TimeLinePostList.addOnScrollListener(scrollListener);


        edtPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent mIntent = new Intent(getActivity(), CreatePostActivity.class);
                startActivity(mIntent);
                getActivity().finish();
                
            }
        });

        implementScrollListener();
        return view;
    }

    private void showFab() {

        floatingActionButton.setVisibility(View.VISIBLE);
    }

    private void hideFab() {
        floatingActionButton.setVisibility(View.GONE);


    }

    private void implementScrollListener() {


        TimeLinePostList
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


                        if (dy > 0) {
                            System.out.println("Scrolled Downwards");
                        } else if (dy < 0) {
                            System.out.println("Scrolled Upwards");
                        } else {
                            System.out.println("No Vertical Scrolled");
                        }

                        visibleItemCount = linearLayoutManager.getChildCount();
                        totalItemCount = linearLayoutManager.getItemCount();
                        pastVisiblesItems = linearLayoutManager
                                .findFirstVisibleItemPosition();

                        // Now check if userScrolled is true and also check if
                        // the item is end then update recycler view and set
                        // userScrolled to false


                        Log.d(TAG, "onScrolled:visibleItemCount  " + visibleItemCount + pastVisiblesItems);
                        Log.d(TAG, "onScrolled:totalItemCount " + totalItemCount);
                        Log.d(TAG, "onScrolled:userScrolled " + userScrolled);

                        if (userScrolled && (visibleItemCount + pastVisiblesItems) == totalItemCount) {


                            loadMoreListItems();
                            userScrolled = false;
                        }


                    }

                });
    }

    private void loadMoreListItems() {

        if (!pageIdList.contains(NextPage)) {
            // Show Progress Layout
            bottomLayout.setVisibility(View.VISIBLE);

            Log.d(TAG, "loadMoreListItems: NextPage " + NextPage);

            Map<String, String> data = new HashMap<>();
            data.put("timeline", "1");
            data.put("fm", NextPage);


            String token = Pref.getPreToken();
            Log.d(TAG, "logMeIn onResponse: token " + token);

            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataDashboardTimeLine> call = apiService.getTimeLinePostList(token, data);
            Log.d(TAG, "getTimeLinePostList: first time url " + call.request().url());

            pageIdList.add(NextPage);
            call.enqueue(new Callback<DataDashboardTimeLine>() {
                @Override
                public void onResponse(Call<DataDashboardTimeLine> call, Response<DataDashboardTimeLine> response) {
                    // dismiss Progress Layout
                    bottomLayout.setVisibility(View.GONE);


                    if (response.code() == 200) {

                        if (response.body().getStatus().equals("1")) {
                            Log.d(TAG, "onResponse: if");

                            NextPage = response.body().getNextpage();


                            Pref.setdevicetoken("Bearer " + response.body().getToken());

                            String thumbpath = response.body().getData().getPathThumb();
                            mediumPath = response.body().getData().getPathMedium();
                            largePath = response.body().getData().getPathLarge();
                            String smallPath = response.body().getData().getPathSmall();
                            dataArrayList.addAll(response.body().getData().getPostlist());
                            NextPage = response.body().getNextpage();

                            Log.d(TAG, "onResponse NextPage: " + NextPage);

                            Log.d(TAG, "onSuccess: " + dataArrayList.size());
                            Log.d(TAG, "onSuccess:thumbpath " + thumbpath);
                            Log.d(TAG, "onSuccess: mediumPath " + mediumPath);


                            dashboardTimelineAdapter.notifyDataSetChanged();


                        } else {
                            Log.d(TAG, "onResponse: else");
                            // dismiss Progress Layout
                            bottomLayout.setVisibility(View.GONE);

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


                }

                @Override
                public void onFailure(Call<DataDashboardTimeLine> call, Throwable t) {
                    // dismiss Progress Layout
                    bottomLayout.setVisibility(View.GONE);
                    pageIdList.remove(NextPage);
                    t.printStackTrace();
                }
            });
        }
    }


    void showPopupWindow(final View view, final int position) {
        PopupMenu popup = new PopupMenu(getActivity(), view, Gravity.LEFT);

        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        popup.getMenuInflater().inflate(R.menu.dashboard_time_line_others_post, popup.getMenu());


        if (dataArrayList.get(position).getPostUserActivity().getIs_favour_user().equals("1"))
            popup.getMenu().getItem(1).setIcon(R.drawable.ic_bookmark_black_fill_24dp);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().equals("Report")) {

                    ReportDialog(position, view, item.getTitle().toString());

                }
                if (item.getTitle().equals("Favourite")) {


                    if (dataArrayList.get(position).getPostUserActivity().getIs_favour_user().equals("0")) {
                        Log.d(TAG, "onMenuItemClick: if");
                        item.setIcon(R.drawable.ic_bookmark_black_fill_24dp);
                        new addFavourite(position, view, item).execute();
                    } else {
                        Log.d(TAG, "onMenuItemClick: else ");
                        new UnFavorite(position, view, item).execute();
                    }
                }
                return true;

            }
        });
        popup.show();


    }

    void showPopupWindowMypost(final View view, final int position) {
        PopupMenu popup = new PopupMenu(getActivity(), view);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.dashboard_time_line_my_post, popup.getMenu());
        if (dataArrayList.get(position).getPosts().getPmStep().equals("1")) {
            popup.getMenu().getItem(2).setTitle("Hide from timeline");
            popup.getMenu().getItem(2).setIcon(R.drawable.ic_ban_circle_symbol);
        }
        if (dataArrayList.get(position).getPostUserActivity().getIs_favour_user().equals("1"))
            popup.getMenu().getItem(3).setIcon(R.drawable.ic_bookmark_black_fill_24dp);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {


                if (item.getTitle().equals("Favourite")) {

                    if (dataArrayList.get(position).getPostUserActivity().getIs_favour_user().equals("0")) {

                        Log.d(TAG, "onMenuItemClick: if ");
                        new addFavourite(position, view, item).execute();
                    } else {
                        Log.d(TAG, "onMenuItemClick: else ");
                        new UnFavorite(position, view, item).execute();
                    }


                } else if (item.getTitle().equals("Remove")) {

                    DeleteDilaog(position);


                } else if (item.getTitle().equals("Edit")) {

                    String myStep = dataArrayList.get(position).getPosts().getPmStep();
                    String timeLine = dataArrayList.get(position).getPosts().getPmTimeline();
                    String tag = dataArrayList.get(position).getPosts().getPmTags();
                    UpdatePostDialog(position, view, "Update post", myStep, timeLine, tag);
                } else if (item.getTitle().equals("Add to step")) {


                    AddStepDilaog(position, view, "Add to step");

                } else if (item.getTitle().equals("Hide from timeline")) {


                    RemoveTimelineDilaog(position, "Hide from timeline");
                }
                return true;

            }
        });
        popup.show();
    }

    private void HideFromTimeLine(final int position) {
        progressDialog = Utils.createProgressDialog(getActivity());

        Map<String, String> data = new HashMap<>();
        data.put("postid", dataArrayList.get(position).getPosts().getPmPostId());
        data.put("stepid", "1");
        data.put("timelineid", "0");


        String token = Pref.getPreToken();
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<BasicResponse> call = apiService.AddToMyStept(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                progressDialog.dismiss();

                if (response.code() == 200) {
                    if (response.body().getStatus().equals("1")) {

                        Pref.setdevicetoken("Bearer " + response.body().getToken());
                        dataArrayList.remove(position);
                        dashboardTimelineAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "failure!", Toast.LENGTH_SHORT).show();

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


            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void AddToMyStep(final int position, final View viewww) {


        progressDialog = Utils.createProgressDialog(getActivity());

        Map<String, String> data = new HashMap<>();
        data.put("postid", dataArrayList.get(position).getPosts().getPmPostId());
        data.put("stepid", "1");
        data.put("timelineid", "1");


        String token = Pref.getPreToken();
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);


        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<BasicResponse> call = apiService.AddToMyStept(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                progressDialog.dismiss();

                if (response.code() == 200) {
                    if (response.body().getStatus().equals("1")) {

                        dataArrayList.get(position).getPosts().setPmStep("1");
                        dashboardTimelineAdapter.notifyDataSetChanged();

                        Pref.setdevicetoken("Bearer " + response.body().getToken());
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "failure!", Toast.LENGTH_SHORT).show();

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


            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void DeleteDilaog(final int adapterPosition) {


        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dilaog_remove);
        Button btCancel = dialog.findViewById(R.id.btCancel);
        TextView txtTitle = dialog.findViewById(R.id.text_dialog);
//        txtTitle.setText("Are you sure want to remove?");
        txtTitle.setAllCaps(false);
        Button btOkay = dialog.findViewById(R.id.btOkay);
        ImageView imageView = dialog.findViewById(R.id.imgRegpply);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        btOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                RemovePostFromTimeline(adapterPosition);

                dialog.dismiss();

            }
        });
        dialog.show();

    }


    public void AddStepDilaog(final int adapterPosition, final View viewsss, String s) {


        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dilaog_remove);
        Button btCancel = dialog.findViewById(R.id.btCancel);
        TextView text_dialog = dialog.findViewById(R.id.text_dialog);
        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        txtTitle.setText(s);
        txtTitle.setAllCaps(false);
        text_dialog.setText("Are you sure want to add in step?");
        Button btOkay = dialog.findViewById(R.id.btOkay);
        ImageView imageView = dialog.findViewById(R.id.imgRegpply);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        btOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AddToMyStep(adapterPosition, viewsss);

                dialog.dismiss();

            }
        });
        dialog.show();


      /*  final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_delete);
        Button btCancel = dialog.findViewById(R.id.btCancel);
        TextView txtTitle = dialog.findViewById(R.id.text_dialog);
        TextView txtTitleHead = dialog.findViewById(R.id.txtTitle);
        txtTitleHead.setText(s);
        txtTitle.setText("Are you sure want to add in step?");
        txtTitle.setAllCaps(false);
        Button btOkay = dialog.findViewById(R.id.btOkay);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        btOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AddToMyStep(adapterPosition, viewsss);
                dialog.dismiss();

            }
        });
        dialog.show();*/

    }

    public void RemoveTimelineDilaog(final int adapterPosition, String s) {


        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dilaog_remove);
        Button btCancel = dialog.findViewById(R.id.btCancel);
        TextView text_dialog = dialog.findViewById(R.id.text_dialog);
        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        txtTitle.setText(s);
        txtTitle.setAllCaps(false);
        text_dialog.setText("Are you sure want to hide from timeline?");
        Button btOkay = dialog.findViewById(R.id.btOkay);
        ImageView imageView = dialog.findViewById(R.id.imgRegpply);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        btOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                HideFromTimeLine(adapterPosition);

                dialog.dismiss();

            }
        });
        dialog.show();

    /*    final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_delete);
        Button btCancel = dialog.findViewById(R.id.btCancel);
        TextView txtTitleHead = dialog.findViewById(R.id.txtTitle);
        txtTitleHead.setText(s);
        TextView txtTitle = dialog.findViewById(R.id.text_dialog);
        txtTitle.setText("Are you sure want to hide from timeline?");
        txtTitle.setAllCaps(false);
        Button btOkay = dialog.findViewById(R.id.btOkay);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        btOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HideFromTimeLine(adapterPosition);

                dialog.dismiss();

            }
        });
        dialog.show();*/

    }

    private void RemovePostFromTimeline(final int position) {

        progressDialog = Utils.createProgressDialog(getActivity());

        Map<String, String> data = new HashMap<>();
        data.put("postid", dataArrayList.get(position).getPosts().getPmPostId());

        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<BasicResponse> call = apiService.deletePost(token, data);
        Log.d(TAG, "favorioute: url " + call.request().url());

        call.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                progressDialog.dismiss();

                if (response.code() == 200 || response.code() == 201) {

                    if (response.body().getStatus().equals("1")) {

                        dataArrayList.remove(position);
                        dashboardTimelineAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onResume() {
        super.onResume();

        Picasso.with(getActivity())
                .load(Pref.getClientImageThumbnailPath().concat(Pref.getClientProfilePictureName()))
                .placeholder(R.drawable.profile_pic) //this is optional the image to display while the url image is downloading
                .error(R.drawable.profile_pic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(imgProfileImage);

        Log.d(TAG, "onResume: ");

    }

    @Override
    public void onPause() {
        super.onPause();

      /*  Log.d(TAG, "onPause: ");
        Events.ActivityRefreshCommandsContributeCount activityActivityMessageEvent = EventBus.getDefault().getStickyEvent(Events.ActivityRefreshCommandsContributeCount.class);
        // Better check that an event was actually posted before
        if (activityActivityMessageEvent != null) {
            // "Consume" the sticky event
            EventBus.getDefault().removeStickyEvent(activityActivityMessageEvent);
            // Now do something with it
        }*/
        //  GlobalBus.getBus().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

        Events.ActivityRefreshCommandsContributeCount activityActivityMessageEvent = EventBus.getDefault().getStickyEvent(Events.ActivityRefreshCommandsContributeCount.class);
        // Better check that an event was actually posted before
        if (activityActivityMessageEvent != null) {
            // "Consume" the sticky event
            EventBus.getDefault().removeStickyEvent(activityActivityMessageEvent);
            // Now do something with it
        }
        GlobalBus.getBus().unregister(this);
    }

    private void getTimeLinePostItems() {
        //   progressDialog = Utils.createProgressDialog(getActivity());
        pageIdList.clear();
        dataArrayList = new ArrayList<>();


        Map<String, String> data = new HashMap<>();
        data.put("timeline", "1");
        data.put("fm", NextPage);


        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataDashboardTimeLine> call = apiService.getTimeLinePostList(token, data);
        Log.d(TAG, "getTimeLinePostList: first time url " + call.request().url());

        call.enqueue(new Callback<DataDashboardTimeLine>() {
            @Override
            public void onResponse(Call<DataDashboardTimeLine> call, Response<DataDashboardTimeLine> response) {
                //  progressDialog.dismiss();

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);

                Log.d(TAG, "onResponse:DataDashboardTimeLine " + new Gson().toJson(response.body()));
                if (response.code() == 200) {

                    if (response.body().getStatus().equals("1")) {
                        Log.d(TAG, "onResponse: if");

                        Log.d(TAG, "onResponse: " + response.body().getToken());

                        Pref.setdevicetoken("Bearer " + response.body().getToken());

                        String thumbpath = response.body().getData().getPathThumb();
                        mediumPath = response.body().getData().getPathMedium();
                        largePath = response.body().getData().getPathLarge();
                        smallPath = response.body().getData().getPathSmall();
                        dataArrayList.addAll(response.body().getData().getPostlist());
                        NextPage = response.body().getNextpage();
                      //  pageIdList.add(NextPage);
                        Log.d(TAG, "onResponse NextPage: " + NextPage);

                        Log.d(TAG, "onSuccess: " + dataArrayList.size());
                        Log.d(TAG, "onSuccess:thumbpath " + thumbpath);
                        Log.d(TAG, "onSuccess: mediumPath " + mediumPath);
                        dashboardTimelineAdapter = new DashboardTimelineAdapter(getActivity(), dataArrayList, largePath, listener);
                        TimeLinePostList.setAdapter(dashboardTimelineAdapter);
                        dashboardTimelineAdapter.notifyDataSetChanged();




                    } else {
                        Log.d(TAG, "onResponse: else");
                        TimeLinePostList.setVisibility(View.GONE);
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


            }

            @Override
            public void onFailure(Call<DataDashboardTimeLine> call, Throwable t) {
                // progressDialog.dismiss();
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);


                TimeLinePostList.setVisibility(View.GONE);
                txtEmpty.setVisibility(View.VISIBLE);
                t.printStackTrace();
            }
        });
    }


    @Override
    public void serverError(String stringFromByte, int paramInt, int errorCode) {

    }

    @Override
    public void onFailure(Throwable t, int requestId) {

    }

    @Override
    public void onSuccess(String completed, int i) {

    }

    //Event Bus subscribe events here
    //User chat state change event while typing...

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getMessage(Events.ActivityRefreshCommandsContributeCount activityRefreshCommandsCount) {

        Log.d(TAG, "getMessage:Chat state " + activityRefreshCommandsCount.getType());


        int count = 0;
        if (activityRefreshCommandsCount.getType().equals("0")) {
            count = Integer.parseInt(dataArrayList.get(activityRefreshCommandsCount.getPosition()).getPosts().getPmComments());
            Log.d(TAG, "getMessage:old count " + count);
            count = count + activityRefreshCommandsCount.getCount();
            Log.d(TAG, "getMessage:new count  " + count);
            dataArrayList.get(activityRefreshCommandsCount.getPosition()).getPosts().setPmComments(String.valueOf(count));

            if (dashboardTimelineAdapter != null)
                dashboardTimelineAdapter.notifyDataSetChanged();

        } else if (activityRefreshCommandsCount.getType().equals("1")) {
            count = Integer.parseInt(dataArrayList.get(activityRefreshCommandsCount.getPosition()).getPosts().getPmContributors());
            Log.d(TAG, "getMessage:old count " + count);
            count = count + activityRefreshCommandsCount.getCount();
            Log.d(TAG, "getMessage:new count  " + count);

            dataArrayList.get(activityRefreshCommandsCount.getPosition()).getPosts().setPmContributors(String.valueOf(count));

            if (dashboardTimelineAdapter != null)
                dashboardTimelineAdapter.notifyDataSetChanged();

        } else if (activityRefreshCommandsCount.getType().equals("2")) {
            count = Integer.parseInt(dataArrayList.get(activityRefreshCommandsCount.getPosition()).getPosts().getPmContributors());
            Log.d(TAG, "getMessage:old count " + count);
            if (count != 0)
                count = count - activityRefreshCommandsCount.getCount();

            Log.d(TAG, "getMessage:new count  " + count);

            dataArrayList.get(activityRefreshCommandsCount.getPosition()).getPosts().setPmContributors(String.valueOf(count));
            if (dashboardTimelineAdapter != null)
                dashboardTimelineAdapter.notifyDataSetChanged();
        } else if (activityRefreshCommandsCount.getType().equals("3")) {
            count = Integer.parseInt(dataArrayList.get(activityRefreshCommandsCount.getPosition()).getPosts().getPmComments());
            Log.d(TAG, "getMessage:old count " + count);
            if (count != 0)
                count = count - activityRefreshCommandsCount.getCount();
            Log.d(TAG, "getMessage:new count  " + count);
            dataArrayList.get(activityRefreshCommandsCount.getPosition()).getPosts().setPmComments(String.valueOf(count));
            if (dashboardTimelineAdapter != null)
                dashboardTimelineAdapter.notifyDataSetChanged();

        }


    }

    private void UpdatePostDialog(final int position, final View view, String title, final String myStep, final String timeLine, final String tag) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_report_post_update);
        ImageView btCancel = dialog.findViewById(R.id.btCancel);
//        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
//        txtTitle.setText(title);
        final EditText editText = dialog.findViewById(R.id.text_dialog);

        editText.setText(dataArrayList.get(position).getPosts().getPmDescription());
        Button btOkay = dialog.findViewById(R.id.btOkay);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        btOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter post decription", Toast.LENGTH_SHORT).show();
                } else {

                    String post_id = dataArrayList.get(position).getPosts().getPmPostId();
                    updatePost(editText.getText().toString().trim(), myStep, timeLine, tag, post_id, position);
                }


            }
        });
        dialog.show();

    }

    private void ReportDialog(final int position, final View view, final String title) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_report_post_update);
        ImageView btCancel = dialog.findViewById(R.id.btCancel);
        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        txtTitle.setText(title);
        final EditText editText = dialog.findViewById(R.id.text_dialog);

        Button btOkay = dialog.findViewById(R.id.btOkay);

        btOkay.setText("Submit");
        editText.setHint("Write your text here...");

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        btOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!Utils.hasText(editText)) {
                    Toast.makeText(getActivity(), "Enter your content", Toast.LENGTH_SHORT).show();
                } else {

                    new putReport(position, view, editText.getText().toString().trim()).execute();
                    dialog.dismiss();
                }


            }
        });
        dialog.show();

    }

    private void updatePost(final String description, String myStep, String timeLine, String tag,
                            String post_id, final int position) {

        progressDialog = Utils.createProgressDialog(getActivity());

        Map<String, String> data = new HashMap<>();
        data.put("pm_tags", tag);
        data.put("pm_description", description);
        data.put("postid", post_id);
        data.put("pm_timeline", timeLine);
        data.put("pm_step", myStep);


        String token = Pref.getPreToken();
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);


        //token = "Bearer "+ Pref.getPreToken();


        Log.d(TAG, "changePassword: token " + token);

        Call<DataDashboardTimeLine> call = apiService.updatePost(token, data);
        Log.d(TAG, "logMeIn: url " + call.request().url());

        call.enqueue(new Callback<DataDashboardTimeLine>() {
            @Override
            public void onResponse(Call<DataDashboardTimeLine> call, Response<DataDashboardTimeLine> response) {
                progressDialog.dismiss();

                if (response.code() == 200) {
                    if (response.body().getStatus().equals("1")) {

                        Pref.setdevicetoken("Bearer " + response.body().getToken());

                        dataArrayList.get(position).getPosts().setPmDescription(description);
                        dashboardTimelineAdapter.notifyDataSetChanged();

                        Toast.makeText(getActivity(), "Your  post update successfully!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "Your  post update failure!", Toast.LENGTH_SHORT).show();

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


            }

            @Override
            public void onFailure(Call<DataDashboardTimeLine> call, Throwable t) {
                t.printStackTrace();
                progressDialog.show();
            }
        });
    }

    public void switchTabInActivity(int indexTabToSwitchTo) {

        ((DashBoardActivity) getActivity()).switchTab(indexTabToSwitchTo);

    }

    private class DisLike extends AsyncTask<Void, Void, String> {
        int position;
        View view;

        public DisLike(int position, View view) {
            this.position = position;
            this.view = view;
        }

        @Override
        protected String doInBackground(Void... voids) {

            final Map<String, String> data = new HashMap<>();
            data.put("postid", dataArrayList.get(position).getPosts().getPmPostId());
            data.put("tweakid", dataArrayList.get(position).getPostUserActivity().getEncourageFeedId());


            //     String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";
            //   RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getTimeLinePostLists(token,data), this, 1);
            String token = Pref.getPreToken();
            Log.d(TAG, "logMeIn onResponse: token " + token);

            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataLikeCommentsContriubes> call = apiService.getDislike(token, data);
            Log.d(TAG, "logMeIn: url " + call.request().url());

            call.enqueue(new Callback<DataLikeCommentsContriubes>() {
                @Override
                public void onResponse(Call<DataLikeCommentsContriubes> call, Response<DataLikeCommentsContriubes> response) {

                    if (response.code() == 200 || response.code() == 201) {

                        if (response.body().getStatus().equals("1")) {

                            int count = Integer.parseInt(dataArrayList.get(position).getPosts().getPmLikes());

                            count = count - 1;
                            dataArrayList.get(position).getPosts().setPmLikes(String.valueOf(count));

                            dataArrayList.get(position).getPostUserActivity().setIsEncourageUser("0");
                            dataArrayList.get(position).getPostUserActivity().setEncourageFeedId("");
                            dashboardTimelineAdapter.notifyDataSetChanged();
                            ImageView imageView = view.findViewById(R.id.imgLike);
                            LinearLayout linearLayout = view.findViewById(R.id.imgLikes);
                            linearLayout.setEnabled(true);
                            linearLayout.setClickable(true);
                            imageView.setImageResource(R.drawable.thumbs_up_like);

                        } else {

                            ImageView imageView = view.findViewById(R.id.imgLike);
                            LinearLayout linearLayout = view.findViewById(R.id.imgLikes);
                            linearLayout.setEnabled(true);
                            linearLayout.setClickable(true);
                            imageView.setImageResource(R.drawable.thumbs_up_like_fill);


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

                }

                @Override
                public void onFailure(Call<DataLikeCommentsContriubes> call, Throwable t) {

                    ImageView imageView = view.findViewById(R.id.imgLike);
                    LinearLayout linearLayout = view.findViewById(R.id.imgLikes);
                    linearLayout.setEnabled(true);
                    linearLayout.setClickable(true);
                    imageView.setImageResource(R.drawable.thumbs_up_like_fill);
                    t.printStackTrace();

                }
            });
            return null;
        }


    }

    private class UnFavorite extends AsyncTask<Void, Void, String> {
        int position;
        View view;

        MenuItem item;

        public UnFavorite(int position, View view, MenuItem item) {
            this.position = position;
            this.view = view;
            this.item = item;
        }

        @Override
        protected String doInBackground(Void... voids) {

            final Map<String, String> data = new HashMap<>();
            data.put("postid", dataArrayList.get(position).getPosts().getPmPostId());
            data.put("tweakid", dataArrayList.get(position).getPostUserActivity().getFavour_id());


            //     String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";
            //   RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getTimeLinePostLists(token,data), this, 1);
            String token = Pref.getPreToken();
            Log.d(TAG, "logMeIn onResponse: token " + token);

            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataLikeCommentsContriubes> call = apiService.getDislike(token, data);
            Log.d(TAG, "logMeIn: url " + call.request().url());

            call.enqueue(new Callback<DataLikeCommentsContriubes>() {
                @Override
                public void onResponse(Call<DataLikeCommentsContriubes> call, Response<DataLikeCommentsContriubes> response) {

                    if (response.code() == 200 || response.code() == 201) {

                        if (response.body().getStatus().equals("1")) {

                            dataArrayList.get(position).getPostUserActivity().setIs_favour_user("0");
                            dataArrayList.get(position).getPostUserActivity().setEncourageFeedId("");
                            item.setIcon(R.drawable.ic_bookmark_border_black_24dp);
                            dashboardTimelineAdapter.notifyDataSetChanged();

                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

                }

                @Override
                public void onFailure(Call<DataLikeCommentsContriubes> call, Throwable t) {


                }
            });
            return null;
        }


    }

    private class getCommentsLikes extends AsyncTask<Void, Void, String> {

        int position;
        View view;

        public getCommentsLikes(int position, View view) {
            this.position = position;
            this.view = view;
        }


        @Override
        protected String doInBackground(Void... voids) {


            Log.d(TAG, "logMeIn onResponse:  like");
            Map<String, String> data = new HashMap<>();
            data.put("pid", dataArrayList.get(position).getPosts().getPmPostId());
            data.put("typ", dataArrayList.get(position).getPosts().getPmType());
            data.put("comment", "");

            // String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";
            //   RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getTimeLinePostLists(token,data), this, 1);
            String token = Pref.getPreToken();
            Log.d(TAG, "logMeIn onResponse: token " + token);

            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataLikeCommentsContriubes> call = apiService.getLikesCommentsContribute(token, data);
            Log.d(TAG, "logMeIn: url " + call.request().url());

            call.enqueue(new Callback<DataLikeCommentsContriubes>() {
                @Override
                public void onResponse(Call<DataLikeCommentsContriubes> call, Response<DataLikeCommentsContriubes> response) {

                    if (response.code() == 200 || response.code() == 201) {

                        if (response.body().getStatus().equals("1")) {

                            int count = Integer.parseInt(dataArrayList.get(position).getPosts().getPmLikes());

                            count = count + 1;
                            dataArrayList.get(position).getPosts().setPmLikes(String.valueOf(count));
                            dataArrayList.get(position).getPostUserActivity().setIsEncourageUser("1");
                            dataArrayList.get(position).getPostUserActivity().setEncourageFeedId(response.body().getData().getPf_feed_id());
                            dashboardTimelineAdapter.notifyDataSetChanged();
                            ImageView imageView = view.findViewById(R.id.imgLike);
                            LinearLayout linearLayout = view.findViewById(R.id.imgLikes);
                            linearLayout.setEnabled(true);
                            linearLayout.setClickable(true);
                            imageView.setImageResource(R.drawable.thumbs_up_like_fill);


                        } else {

                            ImageView imageView = view.findViewById(R.id.imgLike);
                            LinearLayout linearLayout = view.findViewById(R.id.imgLikes);
                            linearLayout.setEnabled(true);
                            linearLayout.setClickable(true);
                            imageView.setImageResource(R.drawable.thumbs_up_like);

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
                }

                @Override
                public void onFailure(Call<DataLikeCommentsContriubes> call, Throwable t) {
                    ImageView imageView = view.findViewById(R.id.imgLike);
                    LinearLayout linearLayout = view.findViewById(R.id.imgLikes);
                    linearLayout.setEnabled(true);
                    linearLayout.setClickable(true);
                    imageView.setImageResource(R.drawable.thumbs_up_like);

                    t.printStackTrace();
                }
            });

            return null;
        }


    }

    @SuppressLint("StaticFieldLeak")
    private class putReport extends AsyncTask<Void, Void, String> {
        int position;
        View view;
        String reportData;

        putReport(int position, View view, String trim) {

            this.view = view;
            this.position = position;
            this.reportData = trim;
            progressDialog=Utils.createProgressDialog(getActivity());


        }

        @Override
        protected String doInBackground(Void... voids) {

            Log.d(TAG, "logMeIn onResponse:  like");
            Map<String, String> data = new HashMap<>();
            data.put("pid", dataArrayList.get(position).getPosts().getPmPostId());
            data.put("typ", "4");
            data.put("comment", reportData);

            // String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";
            //   RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getTimeLinePostLists(token,data), this, 1);
            String token = Pref.getPreToken();
            Log.d(TAG, "logMeIn onResponse: token " + token);

            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataLikeCommentsContriubes> call = apiService.getpostsnewtweaks(token, data);
            Log.d(TAG, "logMeIn: url " + call.request().url());

            call.enqueue(new Callback<DataLikeCommentsContriubes>() {
                @Override
                public void onResponse(Call<DataLikeCommentsContriubes> call, Response<DataLikeCommentsContriubes> response) {
                    progressDialog.dismiss();
                    if (response.code() == 200 || response.code() == 201) {

                        if (response.body().getStatus().equals("1")) {

                            Toast.makeText(getActivity(), "Report send successfully..!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                }

                @Override
                public void onFailure(Call<DataLikeCommentsContriubes> call, Throwable t) {
                    t.printStackTrace();
                    progressDialog.dismiss();
                }
            });

            return null;
        }
    }

    private class addFavourite extends AsyncTask<Void, Void, String> {

        int position;
        View view;
        MenuItem item;

        public addFavourite(int position, View view, MenuItem item) {
            this.position = position;
            this.view = view;
            this.item = item;
        }

        @Override
        protected String doInBackground(Void... voids) {
            Log.d(TAG, "logMeIn onResponse:  like");
            Map<String, String> data = new HashMap<>();
            data.put("pid", dataArrayList.get(position).getPosts().getPmPostId());
            data.put("typ", "5");
            data.put("comment", "");

            //   String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";
            //   RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getTimeLinePostLists(token,data), this, 1);
            String token = Pref.getPreToken();
            Log.d(TAG, "logMeIn onResponse: token " + token);

            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataLikeCommentsContriubes> call = apiService.getpostsnewtweaks(token, data);
            Log.d(TAG, "favorioute: url " + call.request().url());

            call.enqueue(new Callback<DataLikeCommentsContriubes>() {
                @Override
                public void onResponse(Call<DataLikeCommentsContriubes> call, Response<DataLikeCommentsContriubes> response) {

                    if (response.code() == 200 || response.code() == 201) {

                        if (response.body().getStatus().equals("1")) {

                            dataArrayList.get(position).getPostUserActivity().setIs_favour_user("1");
                            dataArrayList.get(position).getPostUserActivity().setFavour_id(response.body().getData().getPf_feed_id());
                            item.setIcon(R.drawable.ic_bookmark_black_fill_24dp);
                            dashboardTimelineAdapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                }

                @Override
                public void onFailure(Call<DataLikeCommentsContriubes> call, Throwable t) {
                    t.printStackTrace();
                }
            });
            return null;
        }
    }

}
