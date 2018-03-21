package com.mindnotix.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mindnotix.model.Wishlist;
import com.mindnotix.utils.Constant;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Admin on 1/19/2018.
 */

public class SearchDialogAdapter extends RecyclerView.Adapter<SearchDialogAdapter.MyViewHolder> implements Filterable {
    ArrayList<Wishlist> wishlistArrayList;

    private final List<Wishlist> filteredUserList;

    private UserFilter userFilter;

    public SearchDialogAdapter(ArrayList<Wishlist> wishlistArrayList) {
        this.wishlistArrayList = wishlistArrayList;
        this.filteredUserList = new ArrayList<>();
    }

    @Override
    public SearchDialogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alertdialog_check, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SearchDialogAdapter.MyViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.text_view.setText(wishlistArrayList.get(position).getName());
        if (wishlistArrayList.get(position).getStatus().equals("1")) {
            holder.checkBox.setChecked(true);
            wishlistArrayList.get(position).setStatus("1");
        } else {
            holder.checkBox.setChecked(false);
            wishlistArrayList.get(position).setStatus("0");
        }

        holder.text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("aaaaaaaaaaaa", "onClick:texxt view click ");


                if (holder.checkBox.isChecked()) {
                    Constant.wishlistArrayList.get(position).setStatus("0");
                    holder.checkBox.setChecked(false);
                } else {
                    holder.checkBox.setChecked(true);
                    Constant.wishlistArrayList.get(position).setStatus("1");
                }
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.d("aaaaaaaaaaaa", "onCheckedChanged: "+isChecked);
                if (isChecked) {

                    Constant.wishlistArrayList.get(position).setStatus("1");
                } else {
                    Constant.wishlistArrayList.get(position).setStatus("0");
                }
            }
        });
    }

    @Override
    public Filter getFilter() {
        if(userFilter == null)
            userFilter = new UserFilter(this, wishlistArrayList);
        return userFilter;
    }

    @Override
    public int getItemCount() {
        return wishlistArrayList.size();
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



    private static class UserFilter extends Filter {

        private final SearchDialogAdapter adapter;

        private final List<Wishlist> originalList;

        private final List<Wishlist> filteredList;

        private UserFilter(SearchDialogAdapter adapter, List<Wishlist> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new ArrayList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            Log.d(TAG, "performFiltering:onQueryTextChange constraint "+constraint);
            if (constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();


                for (Wishlist d : originalList) {
                    if (d.getName() != null && d.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        Log.d(TAG, "performFiltering: "+d);
                        filteredList.add(d);
                    }

                }
              /*  for (final Wishlist user : originalList) {
                    if (user.getName().contains(filterPattern)) {
                        filteredList.add(user);
                    }
                }*/
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
        //// //   adapter.filteredUserList.clear();
           // adapter.filteredUserList.addAll((ArrayList<Wishlist>) results.values);
            Log.d(TAG, "publishResults:b "+adapter.wishlistArrayList.size());

            adapter.wishlistArrayList = (ArrayList<Wishlist>) results.values;
            Log.d(TAG, "publishResults:a  "+adapter.wishlistArrayList.size());
            adapter.notifyDataSetChanged();
        }
    }
}

