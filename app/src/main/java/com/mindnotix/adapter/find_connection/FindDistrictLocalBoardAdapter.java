package com.mindnotix.adapter.find_connection;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mindnotix.model.find_connection.master.Regionals;
import com.mindnotix.model.jobs.filter_master.local_board.Local_board_list;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;

/**
 * Created by Admin on 3/5/2018.
 */


public class FindDistrictLocalBoardAdapter extends RecyclerView.Adapter<FindDistrictLocalBoardAdapter.MyViewHolder> {
    Activity actvity;
    private ArrayList<Local_board_list> regionalsArrayList;
    private ArrayList<Local_board_list> regionalsArrayListTemp;

    public FindDistrictLocalBoardAdapter(ArrayList<Local_board_list> regionalsArrayList, Activity actvity) {
        this.regionalsArrayList = regionalsArrayList;
        this.actvity = actvity;
        regionalsArrayListTemp = new ArrayList<>();
        regionalsArrayListTemp.addAll(regionalsArrayList);
    }


    public void search(String query) {


        String querySmall = query.toLowerCase();


        regionalsArrayListTemp.clear();

        for (Local_board_list wishlist : regionalsArrayList) {


            String memberName = wishlist.getName().toLowerCase();


            if (memberName.contains(querySmall)) {

                regionalsArrayListTemp.add(wishlist);
            }

        }

        notifyDataSetChanged();
    }

    public ArrayList<Local_board_list> getModifyDistrictList() {
        return regionalsArrayListTemp;
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
        holder.text_view.setText(regionalsArrayListTemp.get(position).getName());
        if (regionalsArrayListTemp.get(position).getStatus_chk().equals("1")) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("aaaaaaaaaaaa", "onClick:texxt view click ");


                if (holder.checkBox.isChecked()) {

                    regionalsArrayListTemp.get(position).setStatus_chk("0");
                    holder.checkBox.setChecked(false);
                } else {
                    holder.checkBox.setChecked(true);
                    regionalsArrayListTemp.get(position).setStatus_chk("1");
                }
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.d("aaaaaaaaaaaa", "onCheckedChanged: " + isChecked);
                if (isChecked) {


                    regionalsArrayListTemp.get(position).setStatus_chk("1");
                } else {
                    regionalsArrayListTemp.get(position).setStatus_chk("0");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return regionalsArrayListTemp.size();
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
