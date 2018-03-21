package com.mindnotix.youthhub.dashboard_message;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.adapter.message_adapter.ChatAdapter;
import com.mindnotix.model.message.add_message.DataAddMessageItems;
import com.mindnotix.model.message.chat_messages_list.DataChatMessageListItems;
import com.mindnotix.model.message.chat_messages_list.Users_info;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.youthhub.BaseActivity;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 3/14/2018.
 */

public class ChatActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = ChatActivity.class.getSimpleName();
    private static final int REQUEST_IMAGE_PICKER = 222;
    String status = "";
    String chat_id = "";
    String user_id = "";
    String isonline = "";
    String first_name = "";
    String last_name = "";
    String profile_image = "";
    String image_path = "";
    String post_id, nextPage = "";
    ChatAdapter chatAdapter;
    ArrayList<Users_info> listItemsArrayList;
    private de.hdodenhof.circleimageview.CircleImageView imgProfilePicture;
    private android.widget.TextView txtusername;
    private android.widget.TextView userStatus;
    private android.widget.LinearLayout toolbarlinear;
    private android.support.v7.widget.Toolbar toolbar;
    private android.support.v7.widget.RecyclerView recycleindividual;
    private android.widget.ImageView emojiButton;
    private android.widget.ImageView attachimage;
    private android.widget.EditText chatedittext1;
    private android.widget.ImageView enterchat;
    private android.widget.LinearLayout rootview;
    private android.widget.LinearLayout chatlayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if (getIntent() != null) {
            user_id = getIntent().getStringExtra("user_id");
            chat_id = getIntent().getStringExtra("chat_id");
            status = getIntent().getStringExtra("status");
            isonline = getIntent().getStringExtra("isonline");
            first_name = getIntent().getStringExtra("first_name");
            last_name = getIntent().getStringExtra("last_name");
            profile_image = getIntent().getStringExtra("profile_image");
            image_path = getIntent().getStringExtra("image_path");

            Log.d(TAG, "onCreate: status " + status);
            Log.d(TAG, "onCreate: chat_id " + chat_id);
            Log.d(TAG, "onCreate: user_id " + user_id);
            Log.d(TAG, "onCreate: isonline " + isonline);
            Log.d(TAG, "onCreate: last_name " + last_name);
            Log.d(TAG, "onCreate: first_name " + first_name);
            Log.d(TAG, "onCreate: profile_image " + profile_image);
            Log.d(TAG, "onCreate: image_path " + image_path);
        }

        UiInitialization();


        if (isonline.equals("offline")) {
            userStatus.setText("Offline");
            userStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_offline, 0, 0, 0);
        } else {
            userStatus.setText("Online");
            userStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_online, 0, 0, 0);

        }

        listItemsArrayList = new ArrayList<>();

        chatAdapter = new ChatAdapter(listItemsArrayList, ChatActivity.this);
        recycleindividual.setAdapter(chatAdapter);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        nextPage = "";

                                        if (!status.equals("4"))
                                            getChatMessageList();
                                        else
                                            getChatMessageFollow();
                                    }
                                }
        );
    }

    private void getChatMessageFollow() {
    }

    @Override
    public void onRefresh() {
        // swipeRefreshLayout.setRefreshing(true);

        //  getChatMessageList();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!nbIsNetworkAvailable(this)) {
            nbShowNoInternet(this);
        }
    }

    private void UiInitialization() {

        this.chatlayout = (LinearLayout) findViewById(R.id.chat_layout);
        this.rootview = (LinearLayout) findViewById(R.id.root_view);
        this.enterchat = (ImageView) findViewById(R.id.enter_chat);
        this.enterchat.setOnClickListener(this);
        this.chatedittext1 = (EditText) findViewById(R.id.chat_edit_text1);
        this.attachimage = (ImageView) findViewById(R.id.attachimage);
        this.emojiButton = (ImageView) findViewById(R.id.emojiButton);
        this.recycleindividual = (RecyclerView) findViewById(R.id.recycle_individual);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbarlinear = (LinearLayout) findViewById(R.id.toolbar_linear);
        this.userStatus = (TextView) findViewById(R.id.userStatus);
        this.userStatus.setText(isonline);
        this.txtusername = (TextView) findViewById(R.id.txtusername);
        this.txtusername.setText(first_name.concat(" " + last_name));
        this.imgProfilePicture = (CircleImageView) findViewById(R.id.imgProfilePicture);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        this.recycleindividual.setLayoutManager(layoutManager);

        setSupportActionBar(toolbar);
        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (profile_image != null)
            Picasso.with(this)
                    .load(image_path.concat(profile_image))
                    .placeholder(R.drawable.profile_pic) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.profile_pic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(imgProfilePicture);


    }

    private void getChatMessageList() {
        listItemsArrayList = new ArrayList<>();

        String token = Pref.getPreToken();


        Map<String, String> data = new HashMap<>();
        data.put("chat_id", chat_id);
        data.put("new_user_id", user_id);
        data.put("status", status);
        data.put("fm", nextPage);


        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("getChatMessageList  key, " + key + " value " + value);
        }


        YouthHubApi apiService =
                ApiClient.getClient(com.mindnotix.utils.Constant.BASE_URL).create(YouthHubApi.class);

        Log.d(TAG, "getChatMessageList: token " + token);

        Call<DataChatMessageListItems> call = apiService.getChatMessageBasedOnChatID(token, data);
        Log.d(TAG, "getChatMessageList: url " + call.request().url());

        call.enqueue(new Callback<DataChatMessageListItems>() {
            @Override
            public void onResponse(Call<DataChatMessageListItems> call, Response<DataChatMessageListItems> response) {
                swipeRefreshLayout.setRefreshing(false);
                try {

                    Log.d(TAG, "onResponse:getChatMessageList " + new Gson().toJson(response.body()));

                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("1")) {

                            listItemsArrayList.addAll(response.body().getData().getUsers_info());

                            Log.d(TAG, "onResponse:  array list size " + listItemsArrayList.size());
                            Pref.setdevicetoken("Bearer " + response.body().getToken());
                            chatAdapter = new ChatAdapter(listItemsArrayList, ChatActivity.this);
                            recycleindividual.setAdapter(chatAdapter);
                        } else {

                        }
                    } else if (response.code() == 304) {

                        Toast.makeText(ChatActivity.this, "304 Not Modified", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {

                        Toast.makeText(ChatActivity.this, "400 Bad Request", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {

                        Toast.makeText(ChatActivity.this, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {

                        Toast.makeText(ChatActivity.this, "403 Forbidden", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {

                        Toast.makeText(ChatActivity.this, "404 Not Found", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 422) {

                        Toast.makeText(ChatActivity.this, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(ChatActivity.this, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    swipeRefreshLayout.setRefreshing(false);
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<DataChatMessageListItems> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }


    public void attachImage(View view) {

        Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickIntent.setType("image/*");

        startActivityForResult(pickIntent, REQUEST_IMAGE_PICKER);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enter_chat:

                // Toast.makeText(this, "sendmessage", Toast.LENGTH_SHORT).show();
                new sendMessage(chatedittext1.getText().toString().trim()).execute();
                break;
        }
    }

    public void onResultscrollBottom(boolean result) {
        if (result) {
            if (chatAdapter != null) {

                chatAdapter.notifyDataSetChanged();
                if (chatAdapter.getItemCount() > 1)
                    recycleindividual.getLayoutManager().smoothScrollToPosition(recycleindividual, null, chatAdapter.getItemCount() - 1);

            }

        }

    }

    @SuppressLint("StaticFieldLeak")
    protected class sendMessage extends AsyncTask<String, Void, String> {

        String message;

        public sendMessage(String trim) {
            this.message = trim;
        }

        @Override
        protected String doInBackground(String... strings) {


            Map<String, String> data = new HashMap<>();
            data.put("chat_id", chat_id);
            data.put("message", message);
            data.put("image", "");
            data.put("new_user_id", Pref.getClientId());

            String token = Pref.getPreToken();
            Log.d(TAG, "getpostsnewtweaks onResponse: token " + token);

            YouthHubApi apiService =
                    ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

            Call<DataAddMessageItems> call = apiService.sendMessages(token, data);
            Log.d(TAG, "sendMessage: url " + call.request().url());

            call.enqueue(new Callback<DataAddMessageItems>() {
                @Override
                public void onResponse(Call<DataAddMessageItems> call, Response<DataAddMessageItems> response) {
                    chatedittext1.setText("");
                    Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if (response.body().getStatus().equals("1")) {

                        Log.d(TAG, "onResponse: " + response.body().getData().getChat_message_list());


                        Users_info users_info = new Users_info();
                        users_info.setChat_id(response.body().getData().getChat_message_list().get(0).getChat_id());
                        users_info.setUser_id(response.body().getData().getChat_message_list().get(0).getUser_id());
                        users_info.setGroup_id(response.body().getData().getChat_message_list().get(0).getGroup_id());
                        users_info.setFisrt_name(response.body().getData().getChat_message_list().get(0).getFirst_name());
                        users_info.setLast_name(response.body().getData().getChat_message_list().get(0).getLast_name());
                        users_info.setPost_date(response.body().getData().getChat_message_list().get(0).getPost_date());
                        users_info.setPost_time(response.body().getData().getChat_message_list().get(0).getPost_time());
                        users_info.setMessage_id(response.body().getData().getChat_message_list().get(0).getMessage_id());
                        users_info.setMessage(response.body().getData().getChat_message_list().get(0).getMessage());
                        users_info.setIs_attached(response.body().getData().getChat_message_list().get(0).getIs_attached());
                        users_info.setAttachment(response.body().getData().getChat_message_list().get(0).getAttached_path()
                                .concat(response.body().getData().getChat_message_list().get(0).getAttached()));
                        users_info.setStatus(response.body().getData().getChat_message_list().get(0).getStatus());

                        listItemsArrayList.add(users_info);



                        Pref.setdevicetoken("Bearer ".concat(response.body().getToken()));
                        onResultscrollBottom(true);

                    } else {

                    }
                }

                @Override
                public void onFailure(Call<DataAddMessageItems> call, Throwable t) {

                }
            });


            return null;
        }
    }
}
