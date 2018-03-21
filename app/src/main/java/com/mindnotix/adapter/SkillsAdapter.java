package com.mindnotix.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindnotix.model.Wishlist;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;

/**
 * Created by Admin on 01-02-2018.
 */

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.ViewHolder> {

    Context context;
    ArrayList<String> skills;

    public SkillsAdapter(FragmentActivity activityq, ArrayList<String> skills) {
        this.context = activityq;
        this.skills = skills;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_skills, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvSkills.setText(skills.get(position));
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSkills;

        public ViewHolder(View itemView) {
            super(itemView);

            tvSkills = itemView.findViewById(R.id.tvSkills);
        }
    }
}
