package com.mindnotix.youthhub.ExploreFragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.ExploreDiscussionBoardAdapter;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.explore.discussionboard.add.DataExploreDiscussionBoardAdd;
import com.mindnotix.model.explore.discussionboard.list.DataExploreDiscussionBoardList;
import com.mindnotix.model.explore.discussionboard.list.Explore_chat_list;
import com.mindnotix.model.explore.discussionboard.remove.DataExploreDiscussionBoardRemove;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.mindnotix.youthhub.ExploreViewActivity;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Admin on 2/17/2018.
 */

public class ExplorerDiscusstionBoardFragment extends Fragment implements View.OnClickListener {

    private static RelativeLayout bottomLayout;
    String nextPage = "";
    ProgressDialog progressDialog;
    ExploreDiscussionBoardAdapter eventDiscussionBoardAdapter;
    ArrayList<Explore_chat_list> event_chat_listArrayList = new ArrayList<>();
    ;
    RecyclerViewClickListener listener;
    LinearLayoutManager linearLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    CircleImageView imgProfileImage;
    String event_id = "";
    List<String> pageIds = new ArrayList<>();
    private android.support.v7.widget.Toolbar toolbar;
    private TextView likebox;
    private TextView txtEmpty;
    private View divider;
    private RecyclerView myrecyclerview;
    private View divider2;
    private EditText commenttext;
    private ImageView send;
    private LinearLayout sendlinear;
    private NestedScrollView nestedscrollview;
    // Variables for scroll listener
    private boolean userScrolled = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //    return
        View view = inflater.inflate(R.layout.fragment_event_discussion_board, container, false);


        UiInitialization(view);


        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                DeleteDilaog(position);

            }
        };

        eventDiscussionBoardAdapter = new ExploreDiscussionBoardAdapter(getActivity(), event_chat_listArrayList, listener);
        myrecyclerview.setAdapter(eventDiscussionBoardAdapter);

        nextPage = "";
        getEventDiscussionBoardList();

        nestedscrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {
                        //code to fetch more data for endless scrolling
                        Log.d(TAG, "onScrollChange:explore down");
                        loadMoreListItems();
                    }
                }
            }
        });

        return view;
    }

    private void UiInitialization(View view) {

        this.sendlinear = (LinearLayout) view.findViewById(R.id.send_linear);
        this.nestedscrollview = (NestedScrollView) view.findViewById(R.id.nestedscrollview);
        this.send = (ImageView) view.findViewById(R.id.send);
        this.send.setOnClickListener(this);
        this.commenttext = (EditText) view.findViewById(R.id.commenttext);
        this.divider2 = (View) view.findViewById(R.id.divider2);
        this.myrecyclerview = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        this.txtEmpty = (TextView) view.findViewById(R.id.txtEmpty);

        bottomLayout = (RelativeLayout) view
                .findViewById(R.id.loadItemsLayout_recyclerView);

        this.divider = (View) view.findViewById(R.id.divider);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myrecyclerview.setNestedScrollingEnabled(false);
        myrecyclerview.setLayoutManager(linearLayoutManager);


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.send:
                if (!Utils.hasText(commenttext)) {
                    Toast.makeText(getActivity(), "Enter messages", Toast.LENGTH_SHORT).show();
                } else {

                    String message = commenttext.getText().toString().trim();

                    commenttext.setText("");
                    //new SendContributes(this).execute();
                    new SendMessages(this, message).execute();
                }

                break;
        }

    }

    private void DeleteDilaog(final int position) {


        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dilaog_remove);
        Button btCancel = dialog.findViewById(R.id.btCancel);
        TextView txtTitle = dialog.findViewById(R.id.text_dialog);
        TextView txtTitle_head = dialog.findViewById(R.id.txtTitle);
        ImageView imgRegpply = dialog.findViewById(R.id.imgRegpply);
        txtTitle_head.setText("Remove");
        txtTitle.setText("Are you sure want to remove?");
        txtTitle.setAllCaps(false);
        Button btOkay = dialog.findViewById(R.id.btOkay);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        imgRegpply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        btOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //   event_chat_listArrayList(adapterPosition);

                getEventDiscussionBoardRemove(event_chat_listArrayList.get(position).getChat_id(), position);

                dialog.dismiss();

            }
        });
        dialog.show();

    }

    private void implementScrollListener() {

        myrecyclerview
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
                            // Toast.makeText(getActivity(), "wdwdwdwdwdw", Toast.LENGTH_SHORT).show();
                            loadMoreListItems();
                        }

                    }

                });
    }

    private void loadMoreListItems() {

        if (!pageIds.contains(nextPage)) {


            // Show Progress Layout
            bottomLayout.setVisibility(View.VISIBLE);

            Log.d(TAG, "loadMoreListItems: NextPage " + nextPage);

            Map<String, String> data = new HashMap<>();

            data.put("explodeid", ExploreViewActivity.explorerId);
            data.put("topicid", ExploreViewActivity.topicId);
            data.put("fm", nextPage);

            //   String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";
            //   RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getTimeLinePostLists(token,data), this, 1);
            String token = Pref.getPreToken();
            Log.d(TAG, "logMeIn onResponse: token " + token);

            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataExploreDiscussionBoardList> call = apiService.getExploreDiscussionBoardList(token, data);
            Log.d(TAG, "getpoststweaks: url " + call.request().url());

            call.enqueue(new Callback<DataExploreDiscussionBoardList>() {
                @Override
                public void onResponse(Call<DataExploreDiscussionBoardList> call, Response<DataExploreDiscussionBoardList> response) {
                    //  progressDialog.dismiss();
                    // Show Progress Layout
                    bottomLayout.setVisibility(View.GONE);


                    Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if (response.body().getStatus().equals("1")) {

                        nextPage = response.body().getNextpage();

                        Log.d(TAG, "onResponse: next page count " + nextPage);

                        event_chat_listArrayList.addAll(response.body().getData().getExplore_chat_list());
                        eventDiscussionBoardAdapter.notifyDataSetChanged();

                    } else {

                    }
                }

                @Override
                public void onFailure(Call<DataExploreDiscussionBoardList> call, Throwable t) {
                    // Show Progress Layout
                    bottomLayout.setVisibility(View.GONE);
                    pageIds.remove(nextPage);
                }
            });

        }


    }

    private void getEventDiscussionBoardRemove(String chat_id, final int position) {

        Log.d(TAG, "getEventDiscussionBoardRemove:event_id " + chat_id);
        Log.d(TAG, "getEventDiscussionBoardRemove:position " + position);
        Log.d(TAG, "getEventDiscussionBoardRemove:ExploreViewActivity.explorerId  " + ExploreViewActivity.explorerId);
        Log.d(TAG, "getEventDiscussionBoardRemove:ExploreViewActivity.topicId  " + ExploreViewActivity.topicId);

        progressDialog = Utils.createProgressDialog(getActivity());
        Map<String, String> data = new HashMap<>();

        data.put("explore_id", ExploreViewActivity.explorerId);
        data.put("topic_id", ExploreViewActivity.topicId);
        data.put("chat_id", chat_id);

        //   String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";
        //   RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getTimeLinePostLists(token,data), this, 1);
        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataExploreDiscussionBoardRemove> call = apiService.getExploreDiscussionBoardRemove(token, data);
        Log.d(TAG, "getpoststweaks: url " + call.request().url());

        call.enqueue(new Callback<DataExploreDiscussionBoardRemove>() {
            @Override
            public void onResponse(Call<DataExploreDiscussionBoardRemove> call, Response<DataExploreDiscussionBoardRemove> response) {
                progressDialog.dismiss();


                Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if (response.body().getStatus().equals("1")) {


                    event_chat_listArrayList.remove(position);
                    eventDiscussionBoardAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "" + response.body().getData().getMesage(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "" + response.body().getData().getMesage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataExploreDiscussionBoardRemove> call, Throwable t) {
                progressDialog.dismiss();
                // stopping swipe refresh
            }
        });

    }


    private void getEventDiscussionBoardList() {
        event_chat_listArrayList.clear();
        //  event_chat_listArrayList = new ArrayList<>();

        Map<String, String> data = new HashMap<>();

        data.put("explodeid", ExploreViewActivity.explorerId);
        data.put("topicid", ExploreViewActivity.topicId);
        data.put("fm", nextPage);

        progressDialog = Utils.createProgressDialog(getActivity());
        //   String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";
        //   RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getTimeLinePostLists(token,data), this, 1);
        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataExploreDiscussionBoardList> call = apiService.getExploreDiscussionBoardList(token, data);
        Log.d(TAG, "getpoststweaks: url " + call.request().url());

        call.enqueue(new Callback<DataExploreDiscussionBoardList>() {
            @Override
            public void onResponse(Call<DataExploreDiscussionBoardList> call, Response<DataExploreDiscussionBoardList> response) {
                progressDialog.dismiss();


                Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if (response.body().getStatus().equals("1")) {

                    myrecyclerview.setVisibility(View.VISIBLE);
                    txtEmpty.setVisibility(View.GONE);
                    event_chat_listArrayList.addAll(response.body().getData().getExplore_chat_list());

                    nextPage = response.body().getNextpage();

                    Log.d(TAG, "onResponse: next page count " + nextPage);
                    eventDiscussionBoardAdapter.notifyDataSetChanged();
                    implementScrollListener();


                } else {
                    // myrecyclerview.setVisibility(View.GONE);
                    //  txtEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<DataExploreDiscussionBoardList> call, Throwable t) {
                progressDialog.dismiss();
                // stopping swipe refresh
            }
        });

    }

    @Override
    public void onDestroyView() {
        event_chat_listArrayList.clear();
        super.onDestroyView();
    }

    @SuppressLint("StaticFieldLeak")
    private class SendMessages extends AsyncTask<Void, Void, String> {

        ExplorerDiscusstionBoardFragment explorerDiscusstionBoardFragment;
        String message;

        public SendMessages(ExplorerDiscusstionBoardFragment explorerDiscusstionBoardFragment, String comments) {

            this.explorerDiscusstionBoardFragment = explorerDiscusstionBoardFragment;
            this.message = comments;
        }

        @Override
        protected String doInBackground(Void... voids) {


            Map<String, String> data = new HashMap<>();
            // data.put("event_id", "59");
            data.put("explore_id", ExploreViewActivity.explorerId);
            data.put("topic_id", ExploreViewActivity.topicId);
            data.put("message", message);

            //   String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";

            String token = Pref.getPreToken();
            Log.d(TAG, "getpostsnewtweaks onResponse: token " + token);


            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataExploreDiscussionBoardAdd> call = apiService.getExploreDiscussionBoardAdd(token, data);
            Log.d(TAG, "getpoststweaks: url " + call.request().url());

            call.enqueue(new Callback<DataExploreDiscussionBoardAdd>() {
                @Override
                public void onResponse(Call<DataExploreDiscussionBoardAdd> call, Response<DataExploreDiscussionBoardAdd> response) {
                    commenttext.setText("");
                    Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if (response.body().getStatus().equals("1")) {
                        myrecyclerview.setVisibility(View.VISIBLE);
                        txtEmpty.setVisibility(View.GONE);


                        Log.d(TAG, "onResponse: " + response.body().getData().getMessage());

                        Explore_chat_list event_chat_list = new Explore_chat_list();
                        event_chat_list.setPost_by_firstname(response.body().getData().getFirst_name());
                        event_chat_list.setPost_by_lastname(response.body().getData().getLast_name());
                        event_chat_list.setMessage(response.body().getData().getMessage());
                        event_chat_list.setDate(response.body().getData().getDate());
                        //            event_chat_list.set(response.body().getData().getLc_time());
                        event_chat_list.setChat_id(response.body().getData().getChat_id());
                        event_chat_list.setUser_id(response.body().getData().getUser_id());
                        event_chat_list.setProfile_image(response.body().getData().getProfile_image());
                        //event_chat_list.set(response.body().getData().getImage_path());


                        event_chat_listArrayList.add(0, event_chat_list);

                        eventDiscussionBoardAdapter.notifyItemInserted(0);

                        Pref.setdevicetoken("Bearer ".concat(response.body().getToken()));


                    } else {

                    }
                }

                @Override
                public void onFailure(Call<DataExploreDiscussionBoardAdd> call, Throwable t) {

                }
            });
            return null;
        }
    }
}
