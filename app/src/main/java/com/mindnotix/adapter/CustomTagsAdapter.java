package com.mindnotix.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mindnotix.model.dashboard.tags.taglist.Data;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;

/**
 * Created by Admin on 2/8/2018.
 */

public class CustomTagsAdapter extends RecyclerView.Adapter<CustomTagsAdapter.MyViewHolder> {

    ArrayList<Data> dataArrayListTags;

    public CustomTagsAdapter(ArrayList<Data> dataArrayListTags) {
        this.dataArrayListTags = dataArrayListTags;
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


        holder.text_view.setText(dataArrayListTags.get(position).getPtg_name());
        if (dataArrayListTags.get(position).getStatus().equals("1")) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("aaaaaaaaaaaa", "onClick:texxt view click ");


                if (holder.checkBox.isChecked()) {
                    dataArrayListTags.get(position).setStatus("0");
                    holder.checkBox.setChecked(false);
                } else {
                    holder.checkBox.setChecked(true);
                    dataArrayListTags.get(position).setStatus("1");
                }
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    dataArrayListTags.get(position).setStatus("1");
                } else {
                    dataArrayListTags.get(position).setStatus("0");
                }
            }
        });

    }

    @Override
    public int getItemCount() {


        return dataArrayListTags.size();

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

