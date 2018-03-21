package com.mindnotix.adapter.resumeAdapter.workExperience;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mindnotix.model.resume.Education.KeyResponsibiltites;
import com.mindnotix.youthhub.R;
import com.mindnotix.youthhub.cvresume.workExperience.WorkExperienceActivity;
import com.mindnotix.youthhub.cvresume.workExperience.WorkExperienceEditActivity;

import java.util.ArrayList;

/**
 * Created by Admin on 02-03-2018.
 */

public class KeyResponsibiltesAddAdapter extends RecyclerView.Adapter<KeyResponsibiltesAddAdapter.MyViewHolder> {

    WorkExperienceActivity workExperienceActivity;
    ArrayList<KeyResponsibiltites> keyResponsibiltites;
    WorkExperienceEditActivity workExperienceEditActivity;

    public KeyResponsibiltesAddAdapter(WorkExperienceActivity workExperienceActivity,
                                       ArrayList<KeyResponsibiltites> keyResponsibiltites) {
        this.workExperienceActivity = workExperienceActivity;
        this.keyResponsibiltites = keyResponsibiltites;


    }

    public KeyResponsibiltesAddAdapter(WorkExperienceEditActivity workExperienceEditActivity,
                                       ArrayList<KeyResponsibiltites> keyResponsibiltites) {
        this.workExperienceEditActivity = workExperienceEditActivity;
        this.keyResponsibiltites = keyResponsibiltites;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.work_experience_resume_key_responsibiltites_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        KeyResponsibiltites keyresponse = keyResponsibiltites.get(position);

        holder.tvEmployer.setText(keyresponse.getKeydata());

    }

    @Override
    public int getItemCount() {
        return keyResponsibiltites.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private EditText tvEmployer;
        private TextView addKeyResponsibilties;


        public MyViewHolder(View itemView) {
            super(itemView);

            this.tvEmployer = itemView.findViewById(R.id.tvLoadData);

            tvEmployer.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if (s.length() != 0)
                        keyResponsibiltites.get(getAdapterPosition()).setKeydata(s.toString());

                }
            });


        }


    }
}

