package com.mindnotix.youthhub;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.ViewPagerAdapter;
import com.mindnotix.model.explore.explorerView.ExplorerBasicView;
import com.mindnotix.model.explore.explorerView.Exploreview;
import com.mindnotix.model.explore.explorerView.Topic_list;
import com.mindnotix.model.explore.my_explore_add.DataMyExploreAddItems;
import com.mindnotix.model.explore.my_explore_remove.DataMyExploreRemove;
import com.mindnotix.model.explore.rating.DataExploreRatingItems;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.mindnotix.youthhub.ExploreFragment.ExplorerDiscusstionBoardFragment;
import com.mindnotix.youthhub.ExploreFragment.TagListFragment;
import com.mindnotix.youthhub.ExploreFragment.TopicViewFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 22-02-2018.
 */

public class ExploreViewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ExploreViewActivity.class.getSimpleName();
    public static Boolean status = true;
    public static String explorerId = " ";
    public static String ismyexplore = " ";
    public static String topicId = " ";
    protected TextView tvTitle;
    protected TextView TvMyExplore;
    protected TextView tvPostedBy;
    protected TextView tvDescription;
    protected TextView tvPostedDate;
    protected TabLayout tabLayout;
    TagListFragment tagListFragment;

    TopicViewFragment topicViewFragment;
    ExplorerDiscusstionBoardFragment explorerDiscusstionBoardFragment;
    ArrayList<Exploreview> exploreviews;
    ArrayList<Topic_list> topicList;
    ProgressDialog progressBarLayout;
    LinearLayout progressBarLayout1;
    ImageView imgLeft;
    ImageView imgRight;
    Call<ExplorerBasicView> loadViewExplorerAndTag;
    View viewTabLayout;
    View customTabLayoutView;
    ProgressDialog progressDialog;
    private TextView tvlastDate;
    private TextView tvViews;
    private RatingBar ratingBar;
    private TextView tvRating;
    private LinearLayout layoutTopicList;
    private View view1;
    private LinearLayout layoutTopicView;
    private View view2;
    private LinearLayout linearMyExplore;
    private View view3;
    private LinearLayout layoutTopicDiscussionBoard;
    private TableLayout tabs;
    private ViewPager viewpager;
    private android.support.v7.widget.Toolbar toolbar;
    private String tvDescription_str = "";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_explore_view);

        UiInitialization();


        if (getIntent() != null) {

            explorerId = getIntent().getStringExtra("explorer_id");
            ismyexplore = getIntent().getStringExtra("ismyexplore");

            loadViewExplorer(explorerId);
        }


        if (ismyexplore.equals("2")) {
            TvMyExplore.setText("Remove from My Explore");
        } else {
            TvMyExplore.setText("Add to My Explore");

          /*  Drawable img = getResources().getDrawable(R.drawable.ic_delete_black_24dp);
            TvMyExplore.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);*/
        }

        this.TvMyExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ismyexplore.equals("1")) {

                    DeleteDilaog("Add My Explore", "Are you sure want to add My Explore?");

                } else {

                    DeleteDilaog("Remove Explore", "Are you sure want to remove from My Explore?");
                }


            }
        });

        this.ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float touchPositionX = event.getX();
                    float width = ratingBar.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    int stars = (int) starsf + 1;
                    ratingBar.setRating(stars);


                    new RatingExplore(stars).execute();
                    v.setPressed(false);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setPressed(true);
                }

                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setPressed(false);
                }


                return true;
            }
        });


    }

    private void RatingExplore() {

        Log.d(TAG, "RatingExplore: ");
    }

    public void DeleteDilaog(String title, String desc) {


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dilaog_remove);
        Button btCancel = dialog.findViewById(R.id.btCancel);
        TextView txtTitle_head = dialog.findViewById(R.id.txtTitle);
        txtTitle_head.setText(title);
        TextView txtTitle = dialog.findViewById(R.id.text_dialog);
        txtTitle.setText(desc);
        txtTitle.setAllCaps(false);
        Button btOkay = dialog.findViewById(R.id.btOkay);
        ImageView imgRegpply = dialog.findViewById(R.id.imgRegpply);

        imgRegpply.setOnClickListener(new View.OnClickListener() {
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

                if (ismyexplore.equals("1")) {
                    AddMyExplore();

                } else {
                    RemoveMyExplore();
                }


                dialog.dismiss();

            }
        });
        dialog.show();

    }

    @Override
    public void onClick(View v) {
        int tab_position;
        switch (v.getId()) {


            case R.id.left:

                tab_position = tabLayout.getSelectedTabPosition();

                Log.d(TAG, "onClick:tab_position right " + tab_position);
                Log.d(TAG, "onClick:tab_position left " + tab_position--);
                if (tab_position < 2)
                    viewpager.setCurrentItem(tab_position--);

                break;
            case R.id.right:
                tab_position = tabLayout.getSelectedTabPosition();
                Log.d(TAG, "onClick:tab_position left " + tab_position);
                Log.d(TAG, "onClick:tab_position left " + tab_position++);
                if (tab_position >= 0)
                    viewpager.setCurrentItem(tab_position++);
                break;

        }
    }

    private void UiInitialization() {

        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        this.tabLayout = findViewById(R.id.tabs);
        this.progressBarLayout1 = findViewById(R.id.progressBarLayout);
        //this.layoutTopicDiscussionBoard = (LinearLayout) findViewById(R.id.layoutTopicDiscussionBoard);
        this.view3 = (View) findViewById(R.id.view3);
        this.linearMyExplore = (LinearLayout) findViewById(R.id.linearMyExplore);
        this.view2 = (View) findViewById(R.id.view2);
        // this.layoutTopicView = (LinearLayout) findViewById(R.id.layoutTopicView);
        this.view1 = (View) findViewById(R.id.view1);
        // this.layoutTopicList = (LinearLayout) findViewById(R.id.layoutTopicList);
        this.tvRating = (TextView) findViewById(R.id.tvRating);
        this.ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        this.tvViews = (TextView) findViewById(R.id.tvViews);
        this.tvlastDate = (TextView) findViewById(R.id.tvlastDate);
        this.tvPostedDate = (TextView) findViewById(R.id.tvPostedDate);
        this.tvPostedBy = (TextView) findViewById(R.id.tvPostedBy);
        this.TvMyExplore = (TextView) findViewById(R.id.TvMyExplore);
        this.tvTitle = (TextView) findViewById(R.id.tvTitle);
        imgRight = findViewById(R.id.left);
        imgLeft = findViewById(R.id.right);


        this.viewTabLayout = findViewById(R.id.viewTabLayout);
        this.customTabLayoutView = findViewById(R.id.customTabLayoutView);


        this.tvDescription = findViewById(R.id.tvDescription1);
        this.toolbar = findViewById(R.id.toolbar);

        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("Explore Details");


        } catch (Exception e) {
            e.printStackTrace();
        }


        tvDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadDilaog(tvDescription_str);


            }
        });


        setupViewPager(viewpager);

        tabLayout.setupWithViewPager(viewpager);


        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                switch (position) {
                    case 0:
                        viewpager.setCurrentItem(0);


                        break;
                    case 1:
                        viewpager.setCurrentItem(1);


                        topicViewFragment.loadViewExplorer(explorerId, topicId);
                        break;
                    case 2:
                        viewpager.setCurrentItem(2);

                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        imgLeft.setOnClickListener(this);
        imgRight.setOnClickListener(this);


    }

    private void AddMyExplore() {

        Map<String, String> data = new HashMap<>();

        data.put("explodeid", ExploreViewActivity.explorerId);
        data.put("shareby", Pref.getClientId());
        //   String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";
        //   RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getTimeLinePostLists(token,data), this, 1);
        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        progressDialog = Utils.createProgressDialog(this);
        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataMyExploreAddItems> call = apiService.getMyExploreAdd(token, data);
        Log.d(TAG, "getpoststweaks: url " + call.request().url());

        call.enqueue(new Callback<DataMyExploreAddItems>() {
            @Override
            public void onResponse(Call<DataMyExploreAddItems> call, Response<DataMyExploreAddItems> response) {
                progressDialog.dismiss();


                Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if (response.body().getStatus().equals("1")) {

                    String msg = response.body().getData().getAddmyexplore().get(0).getMessage();
                    Toast.makeText(ExploreViewActivity.this, msg, Toast.LENGTH_SHORT).show();

                    TvMyExplore.setText("Remove from My Explore");

                    ismyexplore = "2";

                } else {
                    Toast.makeText(ExploreViewActivity.this, "Can't add now. Try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataMyExploreAddItems> call, Throwable t) {
                progressDialog.dismiss();
                // stopping swipe refresh
            }
        });
    }

    private void RemoveMyExplore() {
        progressDialog = Utils.createProgressDialog(this);

        Map<String, String> data = new HashMap<>();

        data.put("explodeid", ExploreViewActivity.explorerId);
        data.put("shareby", Pref.getClientId());
        //   String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";
        //   RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getTimeLinePostLists(token,data), this, 1);
        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataMyExploreRemove> call = apiService.getMyExploreRemove(token, data);
        Log.d(TAG, "getpoststweaks: url " + call.request().url());

        call.enqueue(new Callback<DataMyExploreRemove>() {
            @Override
            public void onResponse(Call<DataMyExploreRemove> call, Response<DataMyExploreRemove> response) {
                progressDialog.dismiss();


                Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if (response.body().getStatus().equals("1")) {

                    String msg = response.body().getData().getMessage();
                    Toast.makeText(ExploreViewActivity.this, msg, Toast.LENGTH_SHORT).show();
                    TvMyExplore.setText("Add to my explore");
                    ismyexplore = "1";


                } else {
                    Toast.makeText(ExploreViewActivity.this, "Can't remove now. Try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataMyExploreRemove> call, Throwable t) {
                progressDialog.dismiss();
                // stopping swipe refresh
            }
        });


    }


    private void loadDilaog(String description) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_exploredescription);

        TextView text = dialog.findViewById(R.id.text_dialog);
        ImageView imageView = dialog.findViewById(R.id.imgRegpply);
        text.setText(description);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void loadViewExplorer(String explorerId) {
        progressBarLayout1.setVisibility(View.VISIBLE);


        //swipe_container.setRefreshing(true);

        exploreviews = new ArrayList<>();
        topicList = new ArrayList<>();

        Map<String, String> data = new HashMap<>();
        data.put("exploreid", explorerId);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        loadViewExplorerAndTag = apiService.loadExplorerView(Pref.getPreToken(), data);

        loadViewExplorerAndTag.enqueue(new Callback<ExplorerBasicView>() {
            @Override
            public void onResponse(@NonNull Call<ExplorerBasicView> call, @NonNull Response<ExplorerBasicView> response) {
                progressBarLayout1.setVisibility(View.GONE);


                try {

                    if (response.code() == 200 || response.code() == 201) {

                        // swipe_container.setRefreshing(false);
                        ExplorerBasicView explorerBasicView = response.body();

                        Pref.setdevicetoken("Bearer " + explorerBasicView.getToken());

                        if (explorerBasicView.getStatus().equals("1")) {

                            if (status) {

                                status = false;

                                exploreviews.addAll(explorerBasicView.getData().getExploreview());
                                topicList.addAll(explorerBasicView.getData().getTopic_list());

                                topicId = topicList.get(0).getTopic_id();


                                viewTabLayout.setVisibility(View.VISIBLE);
                                customTabLayoutView.setVisibility(View.VISIBLE);

                                tvTitle.setText(exploreviews.get(0).getTitle());
                                tvPostedBy.setText(exploreviews.get(0).getPost_by_firstname() + " " +
                                        exploreviews.get(0).getPost_by_lastname());
                                tvPostedDate.setText(exploreviews.get(0).getPost_date());
                                tvlastDate.setText(exploreviews.get(0).getUpdate_on());
                                tvViews.setText(exploreviews.get(0).getPage_view_count());
                                tvRating.setText(exploreviews.get(0).getRating_name());
                                tvDescription_str = exploreviews.get(0).getDescription();

                                if (exploreviews.get(0).getDescription().trim().length() > 150) {
                                    String s = exploreviews.get(0).getDescription().substring(0, 120);

                                    s = s.concat(" Read more");

                                    //   String sourceString = s.concat("<b>...Read more </b> ");
                                    String sourceString = s.concat("<html><body><font size=14 color=#027aff ><b>...Read more </b></font> </body><html>");
                                    Log.d(TAG, "onResponse:describtions - if " + s);
                                    tvDescription.setText(Html.fromHtml(sourceString));

                                } else {

                                    Log.d(TAG, "onResponse:describtions - Else " + exploreviews.get(0).getDescription());
                                    tvDescription.setText(exploreviews.get(0).getDescription());
                                }

                                ratingBar.setRating(Float.parseFloat(exploreviews.get(0).getRating()));


                                tagListFragment.load(topicList);

                                viewpager.setCurrentItem(1);

                            }


                        } else {
                            viewTabLayout.setVisibility(View.GONE);
                            customTabLayoutView.setVisibility(View.GONE);
                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(ExploreViewActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(ExploreViewActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(ExploreViewActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(ExploreViewActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {
                        progressBarLayout.dismiss();
                        Toast.makeText(ExploreViewActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(ExploreViewActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(ExploreViewActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    progressBarLayout1.setVisibility(View.GONE);
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(@NonNull Call<ExplorerBasicView> call, @NonNull Throwable t) {

                t.printStackTrace();

            }
        });


    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager());

        tagListFragment = new TagListFragment();
        topicViewFragment = new TopicViewFragment();
        explorerDiscusstionBoardFragment = new ExplorerDiscusstionBoardFragment();

        adapter.addFragment(tagListFragment, " Topic List ");
        adapter.addFragment(topicViewFragment, " Topic View ");
        adapter.addFragment(explorerDiscusstionBoardFragment, " Discussion Board ");

        viewPager.setAdapter(adapter);
    }


    public void switchTab(int indexTabToSwitchTo, String topic_id) {

        viewpager.setCurrentItem(indexTabToSwitchTo);

        topicId = topic_id;
    }


    @Override
    public void onBackPressed() {
        finish();
//        Intent explore = new Intent(ExploreViewActivity.this, ExploreActivity.class);
//        explore.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        explore.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(explore);
//        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class RatingExplore extends AsyncTask<Void, Void, String> {

        int stars;
        ProgressDialog progressDialog;

        public RatingExplore(int stars) {

            this.stars = stars;
            Log.d(TAG, "RatingExplore: star rating " + stars);
            progressDialog = Utils.createProgressDialog(ExploreViewActivity.this);
        }

        @Override
        protected String doInBackground(Void... voids) {

            final Map<String, String> data = new HashMap<>();
            data.put("explore_id", explorerId);
            data.put("rate_value", String.valueOf(stars));


            //     String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";
            //   RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getTimeLinePostLists(token,data), this, 1);
            String token = Pref.getPreToken();
            Log.d(TAG, "logMeIn onResponse: token " + token);

            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataExploreRatingItems> call = apiService.getExploreRating(token, data);
            Log.d(TAG, "logMeIn: url " + call.request().url());

            call.enqueue(new Callback<DataExploreRatingItems>() {
                @Override
                public void onResponse(Call<DataExploreRatingItems> call, Response<DataExploreRatingItems> response) {
                    progressDialog.dismiss();
                    if (response.code() == 200 || response.code() == 201) {

                        if (response.body().getStatus().equals("1")) {

                            ratingBar.setRating(stars);

                            exploreviews.get(0).setRating(response.body().getData().getRating_value());
                            exploreviews.get(0).setRating_name(response.body().getData().getRating_name());
                            tvRating.setText(response.body().getData().getRating_name());


                        } else {


                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(ExploreViewActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(ExploreViewActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(ExploreViewActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(ExploreViewActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(ExploreViewActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(ExploreViewActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(ExploreViewActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<DataExploreRatingItems> call, Throwable t) {

                    progressDialog.dismiss();
                }
            });
            return null;
        }
    }
}
