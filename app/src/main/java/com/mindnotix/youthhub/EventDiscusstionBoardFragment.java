package com.mindnotix.youthhub;

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
import com.mindnotix.adapter.EventDiscussionBoardAdapter;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.events.discussion_board.event_discuss_add.DataEventDiscussionBoardAdd;
import com.mindnotix.model.events.discussion_board.event_discuss_remove.DataEventDiscussionBoardRemove;
import com.mindnotix.model.events.discussion_board.list.DataEventDiscussinBoardList;
import com.mindnotix.model.events.discussion_board.list.Event_chat_list;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Admin on 2/17/2018.
 */

public class EventDiscusstionBoardFragment extends Fragment implements View.OnClickListener {

    public static String event_id = "";
    private static RelativeLayout bottomLayout;
    String nextPage = "";
    EventDiscussionBoardAdapter eventDiscussionBoardAdapter;
    ArrayList<Event_chat_list> event_chat_listArrayList = new ArrayList<>();
    RecyclerViewClickListener listener;
    LinearLayoutManager linearLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    CircleImageView imgProfileImage;
    private android.support.v7.widget.Toolbar toolbar;
    private TextView likebox;
    private View divider;
    private RecyclerView myrecyclerview;
    private View divider2;
    private EditText commenttext;
    private ImageView send;
    private LinearLayout sendlinear;
    private NestedScrollView nestedscrollview;
    // Variables for scroll listener
    private boolean userScrolled = true;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("run", "onCreateView: ");
        if (getActivity() != null) {
            ((EventDetailsActivity) getActivity()).setFragInstance(getTag());
        }

        if (getActivity() != null) {
            EventDetailsActivity activity = (EventDetailsActivity) getActivity();
            event_id = activity.getEvent_id();
        }
        //    return
        View view = inflater.inflate(R.layout.fragment_event_discussion_board, container, false);


        UiInitialization(view);


        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                DeleteDilaog(position);

            }
        };


        eventDiscussionBoardAdapter = new EventDiscussionBoardAdapter(getActivity(), event_chat_listArrayList, listener);
        myrecyclerview.setAdapter(eventDiscussionBoardAdapter);

        nextPage = "";
        getEventDiscussionBoardList();

        nestedscrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            /*    if(scrollY > 0){

                }*/

                if(v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {
                        //code to fetch more data for endless scrolling
                        Log.d(TAG, "onScrollChange:event down");
                        loadMoreListItems();
                    }
                }
            }
        });

        return view;
    }

    private void UiInitialization(View view) {
        this.nestedscrollview = (NestedScrollView) view.findViewById(R.id.nestedscrollview);

        this.sendlinear = (LinearLayout) view.findViewById(R.id.send_linear);
        this.send = (ImageView) view.findViewById(R.id.send);
        this.send.setOnClickListener(this);
        this.commenttext = (EditText) view.findViewById(R.id.commenttext);
        this.divider2 = (View) view.findViewById(R.id.divider2);
        this.myrecyclerview = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        bottomLayout = (RelativeLayout) view
                .findViewById(R.id.loadItemsLayout_recyclerView);

        this.divider = (View) view.findViewById(R.id.divider);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(linearLayoutManager);
        myrecyclerview.setNestedScrollingEnabled(false);

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
                    Toast.makeText(getActivity(), "Enter message", Toast.LENGTH_SHORT).show();
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


                //event_chat_listArrayList(position);
                getEventDiscussionBoardRemove(event_chat_listArrayList.get(position).getChat_id(), position);
                dialog.dismiss();

            }
        });
        dialog.show();

    }

    private void getEventDiscussionBoardRemove(final String chat_id, final int position) {

        Log.d(TAG, "getEventDiscussionBoardRemove:event_id " + event_id);
        Log.d(TAG, "getEventDiscussionBoardRemove:chat_id " + chat_id);
        progressDialog = Utils.createProgressDialog(getActivity());
        Map<String, String> data = new HashMap<>();

        data.put("event_id", event_id);
        data.put("chat_id", chat_id);

        //   String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";
        //   RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getTimeLinePostLists(token,data), this, 1);
        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataEventDiscussionBoardRemove> call = apiService.getEventDiscussoinBoardRemove(token, data);
        Log.d(TAG, "getpoststweaks: url " + call.request().url());

        call.enqueue(new Callback<DataEventDiscussionBoardRemove>() {
            @Override
            public void onResponse(Call<DataEventDiscussionBoardRemove> call, Response<DataEventDiscussionBoardRemove> response) {
                progressDialog.dismiss();


                Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if (response.body().getStatus().equals("1")) {

                    event_chat_listArrayList.remove(position);
                    eventDiscussionBoardAdapter.notifyDataSetChanged();
                    /*for (Event_chat_list a : event_chat_listArrayList) {

                        if (a.equals(response.body().getData().getChat_id())) {
                            event_chat_listArrayList.remove(a);
                        }
                    }*/

                    Toast.makeText(getActivity(), "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataEventDiscussionBoardRemove> call, Throwable t) {
                progressDialog.dismiss();
                // stopping swipe refresh
            }
        });

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

                                loadMoreListItems();

                        }

                    }

                });
    }

    private void loadMoreListItems() {


        // Show Progress Layout
        bottomLayout.setVisibility(View.VISIBLE);

        Log.d(TAG, "loadMoreListItems: NextPage " + nextPage);

        Map<String, String> data = new HashMap<>();
        data.put("event_id", event_id);
        data.put("fm", nextPage);

        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataEventDiscussinBoardList> call = apiService.getDiscussionBoardList(token, data);
        Log.d(TAG, "getTimeLinePostList: first time url " + call.request().url());

        call.enqueue(new Callback<DataEventDiscussinBoardList>() {
            @Override
            public void onResponse(Call<DataEventDiscussinBoardList> call, Response<DataEventDiscussinBoardList> response) {
                // dismiss Progress Layout
                bottomLayout.setVisibility(View.GONE);


                Log.d(TAG, "onResponse: scroll load " + new Gson().toJson(response.body()));

                if (response.code() == 200) {

                    if (response.body().getStatus().equals("1")) {
                        Log.d(TAG, "onResponse: if");

                        nextPage = response.body().getNextpage();

                        if (!nextPage.equals("0")) {
                            Log.d(TAG, "onResponse: " + response.body().getToken());

                            Pref.setdevicetoken("Bearer " + response.body().getToken());


                            event_chat_listArrayList.addAll(response.body().getData().getEvent_chat_list());


                            eventDiscussionBoardAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getActivity(), "No record available", Toast.LENGTH_SHORT).show();
                        }


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
            public void onFailure(Call<DataEventDiscussinBoardList> call, Throwable t) {
                // dismiss Progress Layout
                bottomLayout.setVisibility(View.GONE);

                t.printStackTrace();
            }
        });

    }


    private void getEventDiscussionBoardList() {

        event_chat_listArrayList.clear();
        Map<String, String> data = new HashMap<>();

        data.put("event_id", event_id);
        data.put("fm", nextPage);

        progressDialog = Utils.createProgressDialog(getActivity());
        //   String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";
        //   RXRetro.getInstance().retrofitEnque(ApiClient.getInterface().getTimeLinePostLists(token,data), this, 1);
        String token = Pref.getPreToken();
        Log.d(TAG, "logMeIn onResponse: token " + token);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        Call<DataEventDiscussinBoardList> call = apiService.getDiscussionBoardList(token, data);
        Log.d(TAG, "getpoststweaks: url " + call.request().url());

        call.enqueue(new Callback<DataEventDiscussinBoardList>() {
            @Override
            public void onResponse(Call<DataEventDiscussinBoardList> call, Response<DataEventDiscussinBoardList> response) {
                progressDialog.dismiss();


                Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if (response.body().getStatus().equals("1")) {

                    nextPage = response.body().getNextpage();

                    event_chat_listArrayList.addAll(response.body().getData().getEvent_chat_list());

                    eventDiscussionBoardAdapter.notifyDataSetChanged();

                    implementScrollListener();

                } else {

                }
            }

            @Override
            public void onFailure(Call<DataEventDiscussinBoardList> call, Throwable t) {
                progressDialog.dismiss();
                // stopping swipe refresh
            }
        });

    }


    private class SendMessages extends AsyncTask<Void, Void, String> {

        EventDiscusstionBoardFragment eventDiscusstionBoardFragment;
        String message;

        public SendMessages(EventDiscusstionBoardFragment eventDiscusstionBoardFragment, String comments) {

            this.eventDiscusstionBoardFragment = eventDiscusstionBoardFragment;
            this.message = comments;
        }

        @Override
        protected String doInBackground(Void... voids) {


            Map<String, String> data = new HashMap<>();
            // data.put("event_id", "59");
            data.put("event_id", event_id);
            data.put("message", message);

            //   String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InVzZXJpZCI6IjE0NTQyIiwidXNlcm5hbWUiOiJnb3d0aGFtODc2QGdtYWlsLmNvbSJ9LCJpYXQiOjE1MTY2MTQzNDgsImp0aSI6ImJHZHpVVVJRWlhSUmVqbDNNelp0YjFnMFdGST0iLCJpc3MiOiJodHRwczpcL1wvYmFja2VuZC55b3V0aGh1Yi5jby5uelwvIiwibmJmIjoxNTE2NjE0MzQ4LCJleHAiOjE1MTcyMTkxNDh9.BYBvL9AFaq3hAQMPUDt77ANgwcfeBzpdTbK7gd-S3N0";

            String token = Pref.getPreToken();
            Log.d(TAG, "getpostsnewtweaks onResponse: token " + token);

            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataEventDiscussionBoardAdd> call = apiService.addDiscussionBoardMessage(token, data);
            Log.d(TAG, "getpoststweaks: url " + call.request().url());

            call.enqueue(new Callback<DataEventDiscussionBoardAdd>() {
                @Override
                public void onResponse(Call<DataEventDiscussionBoardAdd> call, Response<DataEventDiscussionBoardAdd> response) {
                    commenttext.setText("");
                    Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if (response.body().getStatus().equals("1")) {

                        Log.d(TAG, "onResponse: " + response.body().getData().getComment());


                        Event_chat_list event_chat_list = new Event_chat_list();
                        event_chat_list.setLast_name(response.body().getData().getLast_name());
                        event_chat_list.setFirst_name(response.body().getData().getFirst_name());
                        event_chat_list.setMessage(response.body().getData().getComment());
                        event_chat_list.setDate(response.body().getData().getDate());
                        event_chat_list.setTime(response.body().getData().getTime());
                        event_chat_list.setChat_id(response.body().getData().getChat_id());
                        event_chat_list.setUser_id(response.body().getData().getUser_id());
                        event_chat_list.setProfile_image(response.body().getData().getProfile_image());
                        event_chat_list.setImage_path(response.body().getData().getImage_path());

                        event_chat_listArrayList.add(0, event_chat_list);
                        eventDiscussionBoardAdapter.notifyItemChanged(0);
                        Pref.setdevicetoken("Bearer ".concat(response.body().getToken()));


                    } else {

                    }
                }

                @Override
                public void onFailure(Call<DataEventDiscussionBoardAdd> call, Throwable t) {

                }
            });
            return null;
        }
    }
}
