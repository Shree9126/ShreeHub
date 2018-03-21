package com.mindnotix.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.contactsupport.list.Supportlist;
import com.mindnotix.youthhub.ContactSupportActivity;
import com.mindnotix.youthhub.R;
import com.mindnotix.youthhub.TicketViewActivity;

import java.util.ArrayList;

/**
 * Created by Admin on 19-02-2018.
 */

public class ContactSupportListAdapter extends RecyclerView.Adapter<ContactSupportListAdapter.MyViewHolder> {

    private static final String TAG = ContactSupportListAdapter.class.getSimpleName();
    Activity activity;
    ArrayList<Supportlist> supportlistArrayList;
    private RecyclerViewClickListener mListener;

    public ContactSupportListAdapter(ContactSupportActivity contactSupportActivity, ArrayList<Supportlist> supportlistArrayList, RecyclerViewClickListener listener) {
        this.activity = contactSupportActivity;
        this.supportlistArrayList = supportlistArrayList;
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ticket_list, parent, false);

        return new MyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        holder.txtDate.setText(" " + supportlistArrayList.get(position).getPost_date());
        holder.txtTicketID.setText(supportlistArrayList.get(position).getTicket_code());
        holder.txtReplies.setText("Replies Count:" + " " + supportlistArrayList.get(position).getReplies());

        holder.txtTicketSubject.setText("Subject:" + " " + supportlistArrayList.get(position).getSubject());

        if (supportlistArrayList.get(position).getTicket_status_id().equals("1")) {
            holder.txtStatus.setText(supportlistArrayList.get(position).getTicket_status());
        } else if (supportlistArrayList.get(position).getTicket_status_id().equals("5")) {
            holder.txtStatus.setText(supportlistArrayList.get(position).getTicket_status());
            holder.txtStatus.setTextColor(activity.getResources().getColor(R.color.viloet));
        } else if (supportlistArrayList.get(position).getTicket_status_id().equals("3")) {
            holder.txtStatus.setText(supportlistArrayList.get(position).getTicket_status());
            holder.txtStatus.setTextColor(activity.getResources().getColor(R.color.black));
        } else if (supportlistArrayList.get(position).getTicket_status_id().equals("2")) {
            holder.txtStatus.setText(supportlistArrayList.get(position).getTicket_status());
            holder.txtStatus.setTextColor(activity.getResources().getColor(R.color.orange));
        } else {
            holder.txtStatus.setText(supportlistArrayList.get(position).getTicket_status());
            holder.txtStatus.setTextColor(activity.getResources().getColor(R.color.red));
        }

    }

    @Override
    public int getItemCount() {
        return supportlistArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        RecyclerViewClickListener mListener;
        private TextView txtTicketID;
        private TextView txtTicketSubject;
        private TextView txtReplies;
        private TextView txtDate;
        private TextView txtStatus;

        public MyViewHolder(View itemView, RecyclerViewClickListener mListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.mListener = mListener;

            this.txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            this.txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            this.txtReplies = (TextView) itemView.findViewById(R.id.txtReplies);
            this.txtTicketSubject = (TextView) itemView.findViewById(R.id.txtTicketSubject);
            this.txtTicketID = (TextView) itemView.findViewById(R.id.txtTicketID);
        }

        @Override
        public void onClick(View v) {

            Log.d(TAG, "onClick: ticket_id " + supportlistArrayList.get(getAdapterPosition()).getTicket_id());
            Intent mIntent = new Intent(activity, TicketViewActivity.class);
            mIntent.putExtra("ticket_id", supportlistArrayList.get(getAdapterPosition()).getTicket_id());
            activity.startActivity(mIntent);

        }
    }
}
