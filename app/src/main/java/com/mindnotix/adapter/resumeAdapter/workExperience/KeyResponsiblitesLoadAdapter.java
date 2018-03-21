package com.mindnotix.adapter.resumeAdapter.workExperience;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.youthhub.R;
import com.mindnotix.model.resume.loadResume.KeyresponsibilitiesItem;


import java.util.ArrayList;

/**
 * Created by Admin on 03-03-2018.
 */

public class KeyResponsiblitesLoadAdapter extends RecyclerView.Adapter<KeyResponsiblitesLoadAdapter.MyViewHolder> {

    Context context;
    ArrayList<KeyresponsibilitiesItem> keyResponsibiltites;

    public KeyResponsiblitesLoadAdapter(Context context,
                                        ArrayList<KeyresponsibilitiesItem> keyresponsibilities) {

        this.context=context;
        this.keyResponsibiltites=keyresponsibilities;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_key_responsibiltites, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        KeyresponsibilitiesItem keyresponse = keyResponsibiltites.get(position);

        holder.tvkeyresponsibilties.setText(keyresponse.getTitle());

        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();



    }

    @Override
    public int getItemCount() {
        return keyResponsibiltites.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



        private TextView tvkeyresponsibilties;


        public MyViewHolder(View itemView) {
            super(itemView);

            this.tvkeyresponsibilties = itemView.findViewById(R.id.tvkeyresponsibilties);


        }


    }
}

