package com.mindnotix.adapter.resumeAdapter.workExperience;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.model.resume.loadResume.Resume;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.mindnotix.youthhub.R;
import com.mindnotix.youthhub.cvresume.workExperience.WorkExperienceActivity;
import com.mindnotix.model.resume.loadResume.Employments;
import com.mindnotix.youthhub.cvresume.workExperience.WorkExperienceEditActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 26-02-2018.
 */

public class ResumeWorkExperienceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    Context context;
    ArrayList<Employments> employments;

    public ResumeWorkExperienceAdapter(Context context, ArrayList<Employments> employments) {
        this.context = context;
        this.employments = employments;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.resume_work_experience_header, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_resume_work_experience, parent, false);
            return new GenericViewHolder(v);
        }
        return null;
    }

    private Employments getItem(int position) {
        return employments.get(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        holder.setIsRecyclable(false);

        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            ///          headerHolder.txtTitleHeader.setText ("Header");
//            headerHolder.txtTitleHeader.setOnClickListener (new View.OnClickListener () {
//                @Override
//                public void onClick (View view) {
//                    Toast.makeText (context, "Clicked Header", Toast.LENGTH_SHORT).show ();
//                }
//            });
        } else if (holder instanceof GenericViewHolder) {

            Employments employments = getItem(position - 1);
            GenericViewHolder genericViewHolder = (GenericViewHolder) holder;

            genericViewHolder.tvTitle.setText(employments.getTitle());



            genericViewHolder.tvStartDate.setText(employments.getFrom_date()+" "+"-"+" ");
            genericViewHolder.tvEndDate.setText(employments.getTo_date());
            if(employments.getTotal_exp()!=null){
                genericViewHolder.tvEmployeeage.setVisibility(View.VISIBLE);
                genericViewHolder.tvEmployeeage.setText(employments.getTotal_exp());
            }else{
                genericViewHolder.tvEmployeeage.setVisibility(View.INVISIBLE);
                genericViewHolder.tvEmployeeage.setText(employments.getTotal_exp());
            }

            genericViewHolder.tvWorkIndustry.setText(employments.getCategory_name());
            genericViewHolder.tvSubWorkIndustry.setText(employments.getSubcategory_name());
            if (employments.getDescription() != null && !employments.getDescription().trim().equals("")) {
                genericViewHolder.tvDescription.setVisibility(View.VISIBLE);
                genericViewHolder.tvDescription.setText(employments.getDescription());
            } else {

                genericViewHolder.tvDescription.setVisibility(View.GONE);
            }

            genericViewHolder.tvEmployeeName.setText(employments.getProvider_name());

            if (employments.getKeyresponsibilities().size() > 0) {
                genericViewHolder.keyResponsibiltites.setVisibility(View.VISIBLE);
            } else {
                genericViewHolder.keyResponsibiltites.setVisibility(View.GONE);
            }

            WorkExperienceChildAdapter childAdapter = new WorkExperienceChildAdapter(context, employments.getKeyresponsibilities());
            genericViewHolder.keyResponsinbiltesrecylerView.setLayoutManager(new LinearLayoutManager(context));
            genericViewHolder.keyResponsinbiltesrecylerView.setAdapter(childAdapter);

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
        return employments.size() + 1;
    }


    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvAdd;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            this.tvAdd = (TextView) itemView.findViewById(R.id.tvAdd);
            tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context, WorkExperienceActivity.class);
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
        TextView tvDescription;
        TextView tvEmployeeage;
        TextView tvWorkIndustry;
        TextView tvSubWorkIndustry;
        View deleteView;
        View editView;
        RecyclerView keyResponsinbiltesrecylerView;
        TextView keyResponsibiltites;


        public GenericViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.tvEmployeeName = itemView.findViewById(R.id.tvEmployeeName);
            this.tvStartDate = itemView.findViewById(R.id.tvStartDate);
            this.tvEndDate = itemView.findViewById(R.id.tvEndDate);
            this.tvDescription = itemView.findViewById(R.id.tvDescription);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.tvEmployeeage = itemView.findViewById(R.id.tvEmployeeage);
            this.tvWorkIndustry = itemView.findViewById(R.id.tvWorkIndustry);
            this.tvSubWorkIndustry = itemView.findViewById(R.id.tvSubWorkIndustry);
            this.deleteView = itemView.findViewById(R.id.deleteView);
            this.editView = itemView.findViewById(R.id.editView);
            this.keyResponsinbiltesrecylerView = itemView.findViewById(R.id.keyResponsinbiltesrecylerView);
            this.keyResponsibiltites = itemView.findViewById(R.id.keyResponsibiltites);

            deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Employments employments = getItem(getAdapterPosition() - 1);
                    deleteConfirmationDilaog(employments.getId()
                            , employments.getStudent_id()
                            , context,
                            getAdapterPosition() - 1);


                }
            });
            editView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Employments employments = getItem(getAdapterPosition() - 1);

                    Intent intent = new Intent(context, WorkExperienceEditActivity.class);
                    intent.putExtra("qualificationid", employments.getId());
                    intent.putExtra("student_id", employments.getStudent_id());
                    context.startActivity(intent);

                }
            });

        }
    }


    public void deleteConfirmationDilaog(final String employe_id, final String student_id, final Context context, final int adapterPosition) {


        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dilaog_remove);
        Button btCancel = dialog.findViewById(R.id.btCancel);
        TextView text_dialog = dialog.findViewById(R.id.text_dialog);
        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        txtTitle.setText("Delete Confirmation");
        text_dialog.setText("Are you sure you want to delete this qualification?");

        Button btOkay = dialog.findViewById(R.id.btOkay);
        ImageView imageView = dialog.findViewById(R.id.imgRegpply);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        btOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteEducationResume(employe_id, student_id, context, adapterPosition);

                dialog.dismiss();
            }
        });
        dialog.show();


    }


    Call<Resume> deleteEducationResume;

    public void deleteEducationResume(String id, String student_id, final Context context, final int adapterPosition) {

        final ProgressDialog progressDialog = Utils.createProgressDialog(context);

        Map<String, String> data = new HashMap<>();
        data.put("qid", id);
        data.put("sid", student_id);

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        deleteEducationResume = apiService.deleteWork(Pref.getPreToken(), data);

        deleteEducationResume.enqueue(new Callback<Resume>() {
            @Override
            public void onResponse(@NonNull Call<Resume> call, @NonNull Response<Resume> response) {

                if (response.code() == 200 || response.code() == 201) {

                    progressDialog.dismiss();

                    Resume resume = response.body();

                    assert resume != null;
                    if (resume.getStatus().equals("1")) {
                        try {

                            Pref.setdevicetoken("Bearer " + resume.getToken());
                            employments.remove(adapterPosition);
                            notifyItemRemoved(adapterPosition);
                            notifyDataSetChanged();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "failed datat", Toast.LENGTH_SHORT).show();

                    }


                } else if (response.code() == 304) {

                    Toast.makeText(context, "304 Not Modified", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {

                    Toast.makeText(context, "400 Bad Request", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {

                    Toast.makeText(context, "401 Unauthorized", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {

                    Toast.makeText(context, "403 Forbidden", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {

                    Toast.makeText(context, "404 Not Found", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 422) {

                    Toast.makeText(context, "422 Unprocessable Entity", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(context, "500 Internal Server Error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<Resume> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();

            }
        });


    }


}
