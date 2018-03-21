package com.mindnotix.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindnotix.model.loadResume.Technicalskills;
import com.mindnotix.youthhub.R;
import com.mindnotix.youthhub.cvresume.Education.EducationResumeActivity;

import java.util.ArrayList;

/**
 * Created by Admin on 07-03-2018.
 */

public class TechnicalskillsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    Context context;
    ArrayList<Technicalskills> technicalskills;

    public TechnicalskillsAdapter(Context context, ArrayList<Technicalskills> technicalskills) {
        this.context = context;
        this.technicalskills = technicalskills;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_resume_technical_skills_item, parent, false);
            return new TechnicalskillsAdapter.HeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_technical_resume, parent, false);
            return new TechnicalskillsAdapter.GenericViewHolder(v);
        }
        return null;
    }

    private Technicalskills getItem(int position) {
        return technicalskills.get(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TechnicalskillsAdapter.HeaderViewHolder) {
            TechnicalskillsAdapter.HeaderViewHolder headerHolder = (TechnicalskillsAdapter.HeaderViewHolder) holder;
            ///          headerHolder.txtTitleHeader.setText ("Header");
//            headerHolder.txtTitleHeader.setOnClickListener (new View.OnClickListener () {
//                @Override
//                public void onClick (View view) {
//                    Toast.makeText (context, "Clicked Header", Toast.LENGTH_SHORT).show ();
//                }
//            });
        } else if (holder instanceof TechnicalskillsAdapter.GenericViewHolder) {
            Technicalskills technicalskills = getItem(position - 1);
            TechnicalskillsAdapter.GenericViewHolder genericViewHolder = (TechnicalskillsAdapter.GenericViewHolder) holder;

//            genericViewHolder.tvTitle.setText(honorsawards.getTitle());
//            genericViewHolder.tvStartDate.setText(honorsawards.getStart_date());
//            genericViewHolder.tvEndDate.setText(honorsawards.getEnd_date());
//            genericViewHolder.tvProviderName.setText(honorsawards.getProvider_name());
//            genericViewHolder.tvsubcategory_name.setText(honorsawards.getSubcategory_name());
//            genericViewHolder.tvQualifiactionCatagorey.setText(honorsawards.getCategory_name());
//            genericViewHolder.subsubcategory_name.setText(honorsawards.getSubsubcategory_name());
//            genericViewHolder.tvDescription.setText(honorsawards.getDescription());

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
        return technicalskills.size() + 1;
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



