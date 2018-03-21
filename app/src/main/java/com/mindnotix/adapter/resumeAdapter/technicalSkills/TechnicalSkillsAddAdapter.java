package com.mindnotix.adapter.resumeAdapter.technicalSkills;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindnotix.model.jobs.my_jobs.AppliedList;
import com.mindnotix.model.resume.loadResume.Technicalskills;
import com.mindnotix.model.resume.technicalSkills.TechnicalSkillsAdd;
import com.mindnotix.model.resume.workExperience.workExperienceEdit.Keyresponsibilities;
import com.mindnotix.youthhub.JobDetailsActivity;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;

/**
 * Created by Admin on 17-03-2018.
 */

public class TechnicalSkillsAddAdapter extends RecyclerView.Adapter<TechnicalSkillsAddAdapter.MyViewHolder> {

    Context context;
    ArrayList<TechnicalSkillsAdd> technicalSkillsAddArrayList;

    public TechnicalSkillsAddAdapter(Context context, ArrayList<TechnicalSkillsAdd> technicalSkillsAddArrayList) {
        this.context = context;
        this.technicalSkillsAddArrayList = technicalSkillsAddArrayList;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_technical_skills, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        TechnicalSkillsAdd technicalSkillsAdd = technicalSkillsAddArrayList.get(position);
        holder.edSkills.setText(technicalSkillsAdd.getTechicalSkill());
        holder.edLevel.setText(technicalSkillsAdd.getLevel());


    }

    @Override
    public int getItemCount() {
        return technicalSkillsAddArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView edSkills;
        private TextView edLevel;



        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            edSkills = itemView.findViewById(R.id.edSkills);
            edLevel = itemView.findViewById(R.id.edLevel);


        }


        @Override
        public void onClick(View v) {


        }
    }
}