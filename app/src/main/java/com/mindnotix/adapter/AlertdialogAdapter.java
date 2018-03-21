package com.mindnotix.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.model.Wishlist;
import com.mindnotix.model.find_connection.master.Partner_tag;
import com.mindnotix.utils.Constant;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;

/**
 * Created by Admin on 1/19/2018.
 */

public class AlertdialogAdapter extends RecyclerView.Adapter<AlertdialogAdapter.MyViewHolder> {
    ArrayList<Wishlist> wishlistArrayList;
    ArrayList<Wishlist> wishlistArrayListTemp;

    public AlertdialogAdapter(ArrayList<Wishlist> wishlistArrayList) {
        this.wishlistArrayList = wishlistArrayList;
        wishlistArrayListTemp = new ArrayList<>();
        wishlistArrayListTemp.addAll(wishlistArrayList);
    }

    public void search(String query) {


        String querySmall = query.toLowerCase();


        wishlistArrayListTemp.clear();

        for (Wishlist wishlist : wishlistArrayList) {


            String memberName = wishlist.getName().toLowerCase();


            if ( memberName.contains(querySmall)) {

                wishlistArrayListTemp.add(wishlist);
            }

        }

        notifyDataSetChanged();
    }

    @Override
    public AlertdialogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alertdialog_check, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AlertdialogAdapter.MyViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        Wishlist wishlist = wishlistArrayListTemp.get(position);

        holder.checkBox.setText(wishlist.getName());
        holder.checkBox.setChecked(wishlist.getChecked());



    }

    @Override
    public int getItemCount() {
        return wishlistArrayListTemp.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_list);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                    wishlistArrayListTemp.get(getAdapterPosition()).setChecked(isChecked);



                }
            });

        }
    }
}
