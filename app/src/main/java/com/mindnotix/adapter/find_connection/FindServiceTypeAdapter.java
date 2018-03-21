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
import com.mindnotix.model.find_connection.master.Ysp_partnertype;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;

/**
 * Created by Admin on 3/5/2018.
 */

public class FindServiceTypeAdapter extends RecyclerView.Adapter<FindServiceTypeAdapter.MyViewHolder> {
    Activity activity;
    private ArrayList<Ysp_partnertype> regionalsArrayList;
    private ArrayList<Ysp_partnertype> regionalsArrayListTemp;

    public FindServiceTypeAdapter(ArrayList<Ysp_partnertype> regionalsArrayList, Activity activity) {
        this.regionalsArrayList = regionalsArrayList;
        this.activity = activity;
        regionalsArrayListTemp = new ArrayList<>();
        regionalsArrayListTemp.addAll(regionalsArrayList);
    }

    public void search(String query) {


        String querySmall = query.toLowerCase();


        regionalsArrayListTemp.clear();

        for (Ysp_partnertype wishlist : regionalsArrayList) {


            String memberName = wishlist.getName().toLowerCase();


            if (memberName.contains(querySmall)) {

                regionalsArrayListTemp.add(wishlist);
            }

        }

        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alert_dialog_check_one, parent, false);

        return new MyViewHolder(itemView);
    }

    public ArrayList<Ysp_partnertype> getModifyServiceTypeList() {
        return regionalsArrayList;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.text_view.setText(regionalsArrayListTemp.get(position).getName());
        if (regionalsArrayListTemp.get(position).getStatus().equals("1")) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("aaaaaaaaaaaa", "onClick:texxt view click ");


                if (holder.checkBox.isChecked()) {

                    regionalsArrayListTemp.get(position).setStatus("0");
                    holder.checkBox.setChecked(false);
                } else {
                    holder.checkBox.setChecked(true);

                    regionalsArrayListTemp.get(position).setStatus("1");
                }
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.d("aaaaaaaaaaaa", "onCheckedChanged: " + isChecked);
                if (isChecked) {


                    regionalsArrayListTemp.get(position).setStatus("1");
                } else {

                    regionalsArrayListTemp.get(position).setStatus("0");
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
