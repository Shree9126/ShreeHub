package com.mindnotix.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindnotix.model.loadResume.Volunteer;
import com.mindnotix.youthhub.R;
import com.mindnotix.youthhub.cvresume.Education.EducationResumeActivity;

import java.util.ArrayList;

/**
 * Created by Admin on 06-03-2018.
 */

public class VolunterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    Context context;
    ArrayList<Volunteer> volunteers;

    public VolunterAdapter(Context context, ArrayList<Volunteer> volunteers) {
        this.context = context;
        this.volunteers = volunteers;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_resume_honorsawards_item, parent, false);
            return new VolunterAdapter.HeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_volunteer_resume, parent, false);
            return new VolunterAdapter.GenericViewHolder(v);
        }
        return null;
    }

    private Volunteer getItem(int position) {
        return volunteers.get(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VolunterAdapter.HeaderViewHolder) {
            VolunterAdapter.HeaderViewHolder headerHolder = (VolunterAdapter.HeaderViewHolder) holder;
            ///          headerHolder.txtTitleHeader.setText ("Header");
//            headerHolder.txtTitleHeader.setOnClickListener (new View.OnClickListener () {
//                @Override
//                public void onClick (View view) {
//                    Toast.makeText (context, "Clicked Header", Toast.LENGTH_SHORT).show ();
//                }
//            });
        } else if (holder instanceof VolunterAdapter.GenericViewHolder) {
            Volunteer volunteer = getItem(position - 1);
            VolunterAdapter.GenericViewHolder genericViewHolder = (VolunterAdapter.GenericViewHolder) holder;

            genericViewHolder.tvTitle.setText(volunteer.getTitle());
            genericViewHolder.tvStartDate.setText(volunteer.getStart_date());
            genericViewHolder.tvEndDate.setText(volunteer.getEnd_date());
            genericViewHolder.tvProviderName.setText(volunteer.getProvider_name());
            genericViewHolder.tvsubcategory_name.setText(volunteer.getSubcategory_name());
            genericViewHolder.tvQualifiactionCatagorey.setText(volunteer.getCategory_name());
            genericViewHolder.subsubcategory_name.setText(volunteer.getSubsubcategory_name());
            genericViewHolder.tvDescription.setText(volunteer.getDescription());

//            genericViewHolder.tvSubWorkIndustry.setText(employments.getSubcategory_name());
//            if (employments.getDescription() != null && !employments.getDescription().trim().equals("")) {
//                genericViewHolder.tvDescription.setVisibility(View.VISIBLE);
//                genericViewHolder.tvDescription.setText(employments.getDescription());
//            } else {
//
//                genericViewHolder.tvDescription.setVisibility(View.GONE);
//            }
//
//            genericViewHolder.tvEmployeeName.setText(employments.getProvider_name());
//
//            Log.v("uefeufuefhef", employments.getTitle());
//            genericViewHolder.txtName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context, "Clicked item" + (position-1), Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }

    //    need to override this method
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    @Override
    public int getItemCount() {
        return volunteers.size() + 1;
    }


    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvAdd;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            this.tvAdd = (TextView) itemView.findViewById(R.id.tvAdd);
            tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, EducationResumeActivity.class);
                    context.startActivity(intent);


                }
            });

        }
    }

    class GenericViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvEmployeeName;
        TextView tvStartDate;
        TextView tvEndDate;
        TextView tvProviderName;
        TextView tvsubcategory_name;
        TextView subsubcategory_name;
        TextView tvDescription;
        TextView tvQualifiactionCatagorey;
        View deleteView;
        View editView;


        public GenericViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            this.tvEmployeeName = (TextView) itemView.findViewById(R.id.tvEmployeeName);
            this.tvStartDate = (TextView) itemView.findViewById(R.id.tvStartDate);
            this.tvEndDate = (TextView) itemView.findViewById(R.id.tvEndDate);
            this.tvProviderName = (TextView) itemView.findViewById(R.id.tvProviderName);
            this.tvsubcategory_name = (TextView) itemView.findViewById(R.id.tvsubcategory_name);
            this.subsubcategory_name = (TextView) itemView.findViewById(R.id.subsubcategory_name);
            this.deleteView = itemView.findViewById(R.id.deleteView);
            this.editView = itemView.findViewById(R.id.editView);
            this.tvDescription = itemView.findViewById(R.id.tvDescription);
            this.tvQualifiactionCatagorey = itemView.findViewById(R.id.tvQualifiactionCatagorey);

            deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            editView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

}
