package com.mindnotix.adapter.resumeAdapter.workExperience;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindnotix.model.resume.loadResume.KeyresponsibilitiesItem;
import com.mindnotix.youthhub.R;

import java.util.List;

public class WorkExperienceChildAdapter extends RecyclerView.Adapter<WorkExperienceChildAdapter.MyViewHolder> {

    Context context;
    List<KeyresponsibilitiesItem> keyresponsibilities;



    public WorkExperienceChildAdapter(Context context, List<KeyresponsibilitiesItem> keyresponsibilities) {

        this.context=context;
        this.keyresponsibilities=keyresponsibilities;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_key_responsibiltites, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        KeyresponsibilitiesItem keyresponsibilitiesItem = keyresponsibilities.get(position);
        holder.tvkeyresponsibilties.setText(keyresponsibilitiesItem.getTitle());

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

            tvkeyresponsibilties = itemView.findViewById(R.id.tvkeyresponsibilties);
            viewDelete = itemView.findViewById(R.id.viewDelete);

            viewDelete.setVisibility(View.GONE);

            viewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    workExperienceEditActivity.keyConfirmationDilaog(getAdapterPosition(),
//                            keyresponsibilities.get(getAdapterPosition()).getId(),
//                            keyresponsibilities.get(getAdapterPosition()).getStu_qualification_id());
                }
            });


        }



    }
}