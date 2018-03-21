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

import com.mindnotix.model.find_connection.master.Partner_tag;
import com.mindnotix.model.find_connection.master.Regionals;
import com.mindnotix.youthhub.R;
import com.mindnotix.youthhub.find_connection.FindConnectYouthFilter;

import java.util.ArrayList;

/**
 * Created by Admin on 3/2/2018.
 */

public class FindRegionalAdapter extends RecyclerView.Adapter<FindRegionalAdapter.MyViewHolder> {
    private ArrayList<Regionals> regionalsArrayList;
    private ArrayList<Regionals> regionalsArrayListTemp;
    Activity activity;
    public FindRegionalAdapter(ArrayList<Regionals> regionalsArrayList, Activity activity) {
        this.regionalsArrayList = regionalsArrayList;
        this.activity = activity;
        regionalsArrayListTemp = new ArrayList<>();
        regionalsArrayListTemp.addAll(regionalsArrayList);
    }

    public void search(String query) {


        String querySmall = query.toLowerCase();


        regionalsArrayListTemp.clear();

        for (Regionals wishlist : regionalsArrayList) {


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

    public ArrayList<Regionals> getModifyRegionalList() {
        return regionalsArrayListTemp;
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


           text_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("aaaaaaaaaaaa", "onClick:texxt view click ");


                    if (checkBox.isChecked()) {
                        //FindConnectYouthFilter.regionalsArrayList.get(position).setStatus("0");
                        regionalsArrayListTemp.get(getAdapterPosition()).setStatus("0");

                        checkBox.setChecked(false);
                    } else {
                        checkBox.setChecked(true);
                        regionalsArrayListTemp.get(getAdapterPosition()).setStatus("1");
                        //FindConnectYouthFilter.regionalsArrayList.get(position).setStatus("1");
                    }
                }
            });
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    Log.d("aaaaaaaaaaaa", "onCheckedChanged: " + isChecked);
                    if (isChecked) {

                        regionalsArrayListTemp.get(getAdapterPosition()).setStatus("1");
                    } else {
                        regionalsArrayListTemp.get(getAdapterPosition()).setStatus("0");
                    }
                }
            });
        }
    }
}
