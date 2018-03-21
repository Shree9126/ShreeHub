package com.mindnotix.adapter.find_connection;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mindnotix.model.find_connection.master.Local_boards;
import com.mindnotix.model.find_connection.master.Regionals;
import com.mindnotix.youthhub.R;
import com.mindnotix.youthhub.find_connection.FindConnectYouthFilter;

import java.util.ArrayList;

/**
 * Created by Admin on 3/2/2018.
 */

public class FindDistrictAdapter extends RecyclerView.Adapter<FindDistrictAdapter.MyViewHolder> {
    private ArrayList<Local_boards> regionalsArrayList;

    public FindDistrictAdapter(ArrayList<Local_boards> regionalsArrayList) {
        this.regionalsArrayList = regionalsArrayList;
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alert_dialog_check_one, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.text_view.setText(regionalsArrayList.get(position).getName());
        if (regionalsArrayList.get(position).getStatus().equals("1")) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("aaaaaaaaaaaa", "onClick:texxt view click ");


                if (holder.checkBox.isChecked()) {
                    FindConnectYouthFilter.local_boardsArrayList.get(position).setStatus("0");
                    holder.checkBox.setChecked(false);
                } else {
                    holder.checkBox.setChecked(true);
                    FindConnectYouthFilter.local_boardsArrayList.get(position).setStatus("1");
                }
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.d("aaaaaaaaaaaa", "onCheckedChanged: " + isChecked);
                if (isChecked) {

                    FindConnectYouthFilter.local_boardsArrayList.get(position).setStatus("1");
                } else {
                    FindConnectYouthFilter.local_boardsArrayList.get(position).setStatus("0");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return regionalsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_view;
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_view = (TextView) itemView.findViewById(R.id.text_view);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_list);
        }
    }
}
