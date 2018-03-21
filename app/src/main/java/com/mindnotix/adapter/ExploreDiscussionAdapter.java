package com.mindnotix.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.events.discussion_board.event_discuss_add.DataEventDiscussionBoardAdd;
import com.mindnotix.model.events.discussion_board.list.Event_chat_list;
import com.mindnotix.model.explore.discussionboard.add.DataExploreDiscussionBoardAdd;
import com.mindnotix.model.explore.discussionboard.list.Explore_chat_list;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.mindnotix.youthhub.EventDiscusstionBoardFragment;
import com.mindnotix.youthhub.ExploreViewActivity;

import com.mindnotix.youthhub.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Admin on 08-03-2018.
 */

public class ExploreDiscussionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    Activity activity;
    private ArrayList<Explore_chat_list> event_chat_listArrayList;
    private RecyclerViewClickListener mListener;


    public ExploreDiscussionAdapter(Activity activity, ArrayList<Explore_chat_list> event_chat_listArrayList,
                                    RecyclerViewClickListener listener) {
        this.activity = activity;
        this.event_chat_listArrayList = event_chat_listArrayList;
        mListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.discussion_board_header, parent, false);
            return new ExploreDiscussionAdapter.HeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_items, parent, false);
            return new ExploreDiscussionAdapter.GenericViewHolder(v, mListener);
        }
        return null;
    }

    private Explore_chat_list getItem(int position) {
        return event_chat_listArrayList.get(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof EventDiscussionAdapter.HeaderViewHolder) {
            EventDiscussionAdapter.HeaderViewHolder headerHolder = (EventDiscussionAdapter.HeaderViewHolder) holder;
            ///          headerHolder.txtTitleHeader.setText ("Header");
//            headerHolder.txtTitleHeader.setOnClickListener (new View.OnClickListener () {
//                @Override
//                public void onClick (View view) {
//                    Toast.makeText (context, "Clicked Header", Toast.LENGTH_SHORT).show ();
//                }
//            });
        } else if (holder instanceof EventDiscussionAdapter.GenericViewHolder) {
            Explore_chat_list event_chat_list = getItem(position - 1);
            ExploreDiscussionAdapter.GenericViewHolder genericViewHolder = (ExploreDiscussionAdapter.GenericViewHolder) holder;

            genericViewHolder.txtComments.setText(event_chat_list.getMessage());
            String firtname = event_chat_list.getPost_by_firstname();
            String lastname = event_chat_list.getPost_by_lastname();
            genericViewHolder.txtUserName.setText(firtname.concat(" " + lastname));

            genericViewHolder.txtDate.setText(event_chat_list.getDate());

            if (event_chat_list.getUser_id().equals(Pref.getClientId()))
                genericViewHolder.imgDelete.setVisibility(View.VISIBLE);
            else
                genericViewHolder.imgDelete.setVisibility(View.INVISIBLE);


            Log.d(TAG, "onBindViewHolderexplorer: " + event_chat_list.getProfile_image());

            if (event_chat_listArrayList.get(position).getProfile_image() != null)
                Picasso.with(activity)
                        .load("https://images.youthhub.co.nz/profile/thumbnail/".concat(event_chat_list.getProfile_image()))
                        .placeholder(R.drawable.profile_pic) //this is optional the image to display while the url image is downloading
                        .error(R.drawable.profile_pic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(genericViewHolder.ivProfile);




        }
    }

    //    need to override this method
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    @Override
    public int getItemCount() {
        return event_chat_listArrayList.size() + 1;
    }


    class HeaderViewHolder extends RecyclerView.ViewHolder {
        EditText commenttext;
        ImageView send;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            this.commenttext = (EditText) itemView.findViewById(R.id.commenttext);
            this.send = (ImageView) itemView.findViewById(R.id.send);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (!Utils.hasText(commenttext)) {
                        Toast.makeText(activity, "Enter messages", Toast.LENGTH_SHORT).show();
                    } else {
                        String message = commenttext.getText().toString();

                        commenttext.setText("");

                        new ExploreDiscussionAdapter.SendMessages(activity, message).execute();

                        //  EventDiscusstionBoardFragment eventDiscusstionBoardFragment = new EventDiscusstionBoardFragment();
                        //  eventDiscusstionBoardFragment.sendFunction(message);

                    }

                }
            });

        }


    }

    class GenericViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private de.hdodenhof.circleimageview.CircleImageView ivProfile;
        private android.widget.TextView txtUserName;
        private android.widget.TextView txtDate;
        private android.widget.TextView txtComments;
        private android.widget.RelativeLayout rlParent;
        private android.widget.ImageView imgDelete;
        private RecyclerViewClickListener mListener;


        public GenericViewHolder(View itemView, RecyclerViewClickListener mListener) {
            super(itemView);
            this.mListener = this.mListener;
            this.rlParent = (RelativeLayout) itemView.findViewById(R.id.rlParent);
            this.txtComments = (TextView) itemView.findViewById(R.id.txtComments);
            this.txtUserName = (TextView) itemView.findViewById(R.id.txtUserName);
            this.txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            this.imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            this.imgDelete.setOnClickListener(this);
            this.ivProfile = (CircleImageView) itemView.findViewById(R.id.ivProfile);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgDelete:
                    mListener.onClick(v, getAdapterPosition());
                    break;
            }
        }
    }

    private class SendMessages extends AsyncTask<Void, Void, String> {

        Context context;
        String message;

        public SendMessages(Context context, String comments) {

            this.context = context;
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

                    Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if (response.body().getStatus().equals("1")) {

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
                        notifyDataSetChanged();
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
