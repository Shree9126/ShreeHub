package com.mindnotix.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.jobs.apply_master.Job_questions;
import com.mindnotix.youthhub.JobApplyActivity;
import com.mindnotix.youthhub.R;

import java.util.ArrayList;

/**
 * Created by Admin on 2/14/2018.
 */

public class JobQuestionsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    JobApplyActivity jobApplyActivity;
    ArrayList<Job_questions> job_questionsArrayList;
    private RecyclerViewClickListener mListener;

    public JobQuestionsListAdapter(RecyclerViewClickListener listener, JobApplyActivity jobApplyActivity, ArrayList<Job_questions> job_questionsArrayList) {
        this.mListener = listener;
        this.jobApplyActivity = jobApplyActivity;
        this.job_questionsArrayList = job_questionsArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_job_apply, parent, false);
            return new FooterViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.apply_job_questions_item, parent, false);
            return new MyViewHolder(v, mListener);
        }

        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof FooterViewHolder) {
            final FooterViewHolder footerViewHolder = (FooterViewHolder) holder;

            footerViewHolder.tvApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    boolean isValidate = false;
                    for (int i = 0; i < job_questionsArrayList.size(); i++) {

                        if (!job_questionsArrayList.get(i).getEdt_value().isEmpty()) {

                        } else {
                            Toast.makeText(jobApplyActivity, "Enter the answer for question " + job_questionsArrayList.get(i).getId(), Toast.LENGTH_SHORT).show();
                            isValidate = true;
                            break;
                        }
                    }

                    if (!isValidate) {
                        jobApplyActivity.applyJob();

                    }

                    // JobApplyActivity.applyJob();
                }
            });

            footerViewHolder.tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jobApplyActivity.finish();
                }
            });
        } else if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.txtQuestions.setText(job_questionsArrayList.get(position).getQuestion_details());


            myViewHolder.edtQuestionAnswer.setText(job_questionsArrayList.get(position).getEdt_value());


        }


    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionFooter(int position) {
        return position == job_questionsArrayList.size();
    }


    @Override
    public int getItemCount() {
        return job_questionsArrayList.size() + 1;
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView tvApply;
        TextView tvCancel;

        public FooterViewHolder(View itemView) {
            super(itemView);
            this.tvApply = itemView.findViewById(R.id.tvApply);
            this.tvCancel = itemView.findViewById(R.id.tvCancel);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerViewClickListener mListener;
        private TextView txtQuestions;
        private EditText edtQuestionAnswer;

        public MyViewHolder(View itemView, RecyclerViewClickListener mListener) {
            super(itemView);
            this.mListener = mListener;
            this.edtQuestionAnswer = itemView.findViewById(R.id.edtQuestionAnswer);
            this.txtQuestions = itemView.findViewById(R.id.txtQuestions);

            edtQuestionAnswer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    job_questionsArrayList.get(getAdapterPosition()).setEdt_value(s.toString());
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    job_questionsArrayList.get(getAdapterPosition()).setEdt_value(s.toString());
                }
            });

        }


    }
}
