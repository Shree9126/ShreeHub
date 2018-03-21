package com.mindnotix.adapter.message_adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mindnotix.model.message.chat_messages_list.Users_info;
import com.mindnotix.utils.Pref;
import com.mindnotix.youthhub.R;
import com.mindnotix.youthhub.dashboard_message.ChatActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 3/14/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int CHAT_SENDER = 1;
    public static final int CHAT_SENDER_IMAGE = 3;
    public static final int CHAT_RECEIVER = 2;
    public static final int CHAT_RECEIVER_IMAGE = 4;
    public static final int CHAT_DATE_HEADER = 5;
    private static final String TAG = "SingleChatAdapter";
    ArrayList<Users_info> messagesArrayList;
    ChatActivity chatActivity;

    int flag_date = 1;

    public ChatAdapter(ArrayList<Users_info> messagesArrayList, ChatActivity chatActivity) {
        this.messagesArrayList = messagesArrayList;
        this.chatActivity = chatActivity;
        Log.d(TAG, "ChatAdapter: arrayList size = " + messagesArrayList.size());

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        Log.d(TAG, "onCreateViewHolder: viewType " + viewType);

        switch (viewType) {
            case CHAT_SENDER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_sender, parent, false);
                return new SenderViewHolder(view);
            case CHAT_RECEIVER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_receiver, parent, false);
                return new ReceiverViewHolder(view);
            case CHAT_SENDER_IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_image_sender, parent, false);
                return new SenderImageViewHolder(view);
            case CHAT_RECEIVER_IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflat_image_reciever, parent, false);
                return new ReceiverImageViewHolder(view);
            case CHAT_DATE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_header, parent, false);
                return new DateViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int userType = 0;

        if (Pref.getClientId().equals(messagesArrayList.get(position).getUser_id()) &&
                !messagesArrayList.get(position).getMessage().equals("")) {
            userType = 1;
        } else if (Pref.getClientId().equals(messagesArrayList.get(position).getUser_id()) &&
                messagesArrayList.get(position).getMessage().equals("")) {
            userType = 3;
        } else if (!Pref.getClientId().equals(messagesArrayList.get(position).getUser_id()) &&
                messagesArrayList.get(position).getMessage().equals("")) {
            userType = 4;
        } else if (!Pref.getClientId().equals(messagesArrayList.get(position).getUser_id()) &&
                !messagesArrayList.get(position).getMessage().equals("")) {
            userType = 2;
        }


        Log.d(TAG, "onBindViewHolder: usertype " + userType);
        bindSwitchCase(userType, holder, position);
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (messagesArrayList != null) {

            if (Pref.getClientId().equals(messagesArrayList.get(position).getUser_id()) &&
                    !messagesArrayList.get(position).getMessage().equals("")) {
                return 1;
            } else if (Pref.getClientId().equals(messagesArrayList.get(position).getUser_id()) &&
                    messagesArrayList.get(position).getMessage().equals("")) {
                return 3;
            } else if (!Pref.getClientId().equals(messagesArrayList.get(position).getUser_id()) &&
                    messagesArrayList.get(position).getMessage().equals("")) {
                return 4;
            } else if (!Pref.getClientId().equals(messagesArrayList.get(position).getUser_id()) &&
                    !messagesArrayList.get(position).getMessage().equals("")) {
                return 2;
            }
        }


        return 0;
    }

    private void bindSwitchCase(int userType, final RecyclerView.ViewHolder holder, int position) {

        switch (userType) {

            case CHAT_SENDER_IMAGE:
                final SenderImageViewHolder senderImageViewHolder = (SenderImageViewHolder) holder;
                senderImageViewHolder.time.setText(messagesArrayList.get(position).getPost_date()
                        .concat(" " + messagesArrayList.get(position).getPost_time()));

                Log.d(TAG, "SenderImageViewHolder bindSwitchCase: image url " + messagesArrayList.get(position).getAttachment());

                Picasso.with(chatActivity)
                        .load(messagesArrayList.get(position).getIs_attached())
                        //this is optional the image to display while the url image is downloading
                        //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(senderImageViewHolder.image, new Callback() {
                            @Override
                            public void onSuccess() {
                                senderImageViewHolder.progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                senderImageViewHolder.progressBar.setVisibility(View.GONE);
                            }
                        });

                break;

            case CHAT_RECEIVER_IMAGE:


                final ReceiverImageViewHolder receiverImageViewHolder = (ReceiverImageViewHolder) holder;
                receiverImageViewHolder.time.setText(messagesArrayList.get(position).getPost_date()
                        .concat(" " + messagesArrayList.get(position).getPost_time()));

                Picasso.with(chatActivity)
                        .load(messagesArrayList.get(position).getIs_attached())
                        //this is optional the image to display while the url image is downloading
                        //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(receiverImageViewHolder.ivImg, new Callback() {
                            @Override
                            public void onSuccess() {
                                receiverImageViewHolder.progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                receiverImageViewHolder.progressBar.setVisibility(View.GONE);
                            }
                        });
                break;
            case CHAT_SENDER:
                Log.d(TAG, "SenderViewHolder: " + messagesArrayList.get(position).getMessage());
                SenderViewHolder senderViewHolder = (SenderViewHolder) holder;
                senderViewHolder.txtSendMsg.setText(messagesArrayList.get(position).getMessage());
                senderViewHolder.txtDateTime.setText(messagesArrayList.get(position).getPost_date()
                        .concat(" " + messagesArrayList.get(position).getPost_time()));

                break;

            case CHAT_RECEIVER:
                Log.d(TAG, "ReceiverViewHolder: " + messagesArrayList.get(position).getMessage());
                ReceiverViewHolder receiverViewHolder = (ReceiverViewHolder) holder;
                receiverViewHolder.txtSendMsg.setText(messagesArrayList.get(position).getMessage());
                receiverViewHolder.txtDateTime.setText(messagesArrayList.get(position).getPost_date()
                        .concat(" " + messagesArrayList.get(position).getPost_time()));

                break;
            case CHAT_DATE_HEADER:
                Log.d(TAG, "ReceiverViewHolder: " + messagesArrayList.get(position).getMessage());
                DateViewHolder dateViewHolder = (DateViewHolder) holder;
                dateViewHolder.tvHeaderDate.setText(messagesArrayList.get(position).getPost_date()
                        .concat(" " + messagesArrayList.get(position).getPost_time()));

                break;


        }

    }

    public static class SenderViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView txtSendMsg;
        private TextView txtDateTime, txtOtherSideID;
        private CircleImageView imgProfilePic;
        private ImageView imgDouble, imgSingle;

        public SenderViewHolder(View itemView) {
            super(itemView);

            txtSendMsg = (TextView) itemView.findViewById(R.id.message_text);
            txtDateTime = (TextView) itemView.findViewById(R.id.message_date_time);
            imgDouble = (ImageView) itemView.findViewById(R.id.imgDoubleTick);
            imgSingle = (ImageView) itemView.findViewById(R.id.imgSingleTick);

        }
    }

    public static class SenderImageViewHolder extends RecyclerView.ViewHolder {
        protected TextView time;
        private ImageView image;
        private ProgressBar progressBar;
        private ImageView imgDouble, imgSingle;

        public SenderImageViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            time = (TextView) itemView.findViewById(R.id.tv_time);
            progressBar = (ProgressBar) itemView.findViewById(R.id.sending_progress);
            imgDouble = (ImageView) itemView.findViewById(R.id.imgDoubleTick);
            imgSingle = (ImageView) itemView.findViewById(R.id.imgSingleTick);
        }
    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView txtSendMsg;
        private TextView txtDateTime;
        private ImageView imgDouble, imgSingle;

        public ReceiverViewHolder(View itemView) {
            super(itemView);

            txtSendMsg = (TextView) itemView.findViewById(R.id.message_text);
            txtDateTime = (TextView) itemView.findViewById(R.id.message_date_time);
            imgDouble = (ImageView) itemView.findViewById(R.id.imgDoubleTick);
            imgSingle = (ImageView) itemView.findViewById(R.id.imgSingleTick);

        }
    }

    public static class ReceiverImageViewHolder extends RecyclerView.ViewHolder {

        protected TextView time;
        private ImageView ivImg;
        private ProgressBar progressBar;

        public ReceiverImageViewHolder(View itemView) {
            super(itemView);

            ivImg = (ImageView) itemView.findViewById(R.id.ivImg);
            time = (TextView) itemView.findViewById(R.id.tv_time);
            progressBar = (ProgressBar) itemView.findViewById(R.id.sending_progress);
        }
    }

    private class DateViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvHeaderDate;

        public DateViewHolder(View view) {
            super(view);

            tvHeaderDate = (TextView) itemView.findViewById(R.id.tvHeaderDate);
        }
    }
}
