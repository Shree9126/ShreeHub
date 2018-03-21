package com.mindnotix.adapter.resumeAdapter.workExperience;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindnotix.model.resume.workExperience.workExperienceEdit.Keyresponsibilities;
import com.mindnotix.youthhub.R;
import com.mindnotix.youthhub.cvresume.workExperience.WorkExperienceEditActivity;

import java.util.ArrayList;

/**
 * Created by Admin on 14-03-2018.
 */

public class KeyResponsiblitesEditLoadAdapter  extends RecyclerView.Adapter<KeyResponsiblitesEditLoadAdapter.MyViewHolder> {

    WorkExperienceEditActivity workExperienceEditActivity;
    ArrayList<Keyresponsibilities> keyresponsibilities;

    public KeyResponsiblitesEditLoadAdapter(WorkExperienceEditActivity workExperienceEditActivity,
                                            ArrayList<Keyresponsibilities> keyresponsibilities) {

        this.workExperienceEditActivity=workExperienceEditActivity;
        this.keyresponsibilities=keyresponsibilities;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_key_responsibiltites, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Keyresponsibilities keyresponse = keyresponsibilities.get(position);

        holder.tvkeyresponsibilties.setText(keyresponse.getTitle());


    }

    @Override
    public int getItemCount() {
        return keyresponsibilities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



        private TextView tvkeyresponsibilties;
        private View viewDelete;


        public MyViewHolder(View itemView) {
            super(itemView);

            this.tvkeyresponsibilties = itemView.findViewById(R.id.tvkeyresponsibilties);
            this.viewDelete = itemView.findViewById(R.id.viewDelete);

            viewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    workExperienceEditActivity.keyConfirmationDilaog(getAdapterPosition(),
                            keyresponsibilities.get(getAdapterPosition()).getId(),
                            keyresponsibilities.get(getAdapterPosition()).getStu_qualification_id());

                }
            });


        }


    }
}

