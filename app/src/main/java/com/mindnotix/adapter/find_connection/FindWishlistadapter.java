package com.mindnotix.adapter.find_connection;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mindnotix.model.find_connection.master.Wishlist;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;

/**
 * Created by Admin on 3/2/2018.
 */

public class FindWishlistadapter extends RecyclerView.Adapter<FindWishlistadapter.MyViewHolder> {
    ArrayList<Wishlist> regionalsArrayListTemp;
    private ArrayList<Wishlist> regionalsArrayList;

    public FindWishlistadapter(ArrayList<Wishlist> regionalsArrayList) {
        this.regionalsArrayList = regionalsArrayList;
        regionalsArrayListTemp = new ArrayList<>();
        regionalsArrayListTemp.addAll(regionalsArrayList);
    }

    public void search(String query) {


        String querySmall = query.toLowerCase();


        regionalsArrayListTemp.clear();

        for (Wishlist wishlist : regionalsArrayList) {


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
                .inflate(R.layout.alertdialog_check, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        Wishlist wishlist = regionalsArrayListTemp.get(position);

        holder.checkBox.setText(wishlist.getName());
        holder.checkBox.setChecked(wishlist.isStatus());


          /* holder.text_view.setText(regionalsArrayList.get(position).getName());
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
                    FindConnectYouthFilter.wishlistArrayList.get(position).setStatus("0");
                    holder.checkBox.setChecked(false);
                } else {
                    holder.checkBox.setChecked(true);
                    FindConnectYouthFilter.wishlistArrayList.get(position).setStatus("1");
                }
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.d("aaaaaaaaaaaa", "onCheckedChanged: " + isChecked);
                if (isChecked) {

                    FindConnectYouthFilter.wishlistArrayList.get(position).setStatus("1");
                } else {
                    FindConnectYouthFilter.wishlistArrayList.get(position).setStatus("0");
                }
            }
        });*/
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

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                    regionalsArrayListTemp.get(getAdapterPosition()).setStatus(isChecked);


                }
            });

        }

    }
}
