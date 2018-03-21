package com.mindnotix.adapter.resumeAdapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindnotix.model.resume.extraActivites.ExtraActivities;
import com.mindnotix.model.resume.loadResume.Employments;
import com.mindnotix.model.resume.loadResume.Interests;
import com.mindnotix.model.resume.loadResume.Resume;
import com.mindnotix.model.resume.loadResume.Volunteer;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.mindnotix.youthhub.R;
import com.mindnotix.youthhub.cvresume.Education.EducationResumeActivity;
import com.mindnotix.youthhub.cvresume.volunteerExperience.VolunteerExperienceActivity;
import com.mindnotix.youthhub.cvresume.volunteerExperience.VolunterExperienceEditActivity;
import com.mindnotix.youthhub.cvresume.workExperience.WorkExperienceEditActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_volunteer_resume, parent, false);
            return new GenericViewHolder(v);
        }
        return null;
    }

    private Volunteer getItem(int position) {
        return volunteers.get(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
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
            Volunteer volunteer = getItem(position - 1);
            GenericViewHolder genericViewHolder = (GenericViewHolder) holder;

            genericViewHolder.tvTitle.setText(volunteer.getTitle());
            genericViewHolder.tvStartDate.setText(volunteer.getFrom_date()+" "+"-"+" ");
            genericViewHolder.tvEndDate.setText(volunteer.getTo_date());
            genericViewHolder.tvProviderName.setText(volunteer.getProvider_name());
            genericViewHolder.tvsubcategory_name.setText(volunteer.getSubcategory_name());
            genericViewHolder.tvQualifiactionCatagorey.setText(volunteer.getCategory_name());
            genericViewHolder.subsubcategory_name.setText(volunteer.getSubsubcategory_name());
            genericViewHolder.tvDescription.setText(volunteer.getDescription());


            if(volunteer.getTotal_exp()!=null&&!volunteer.getTotal_exp().equals("")){
                genericViewHolder.tvEmployeeage.setText(volunteer.getTotal_exp());
                genericViewHolder.tvEmployeeage.setVisibility(View.VISIBLE);
            }else{
                genericViewHolder.tvEmployeeage.setVisibility(View.GONE);
            }



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

                    Intent intent = new Intent(context, VolunteerExperienceActivity.class);
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
        TextView tvEmployeeage;


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
            this.tvEmployeeage = itemView.findViewById(R.id.tvEmployeeage);

            deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   Volunteer volunteer = getItem(getAdapterPosition() - 1);
                    deleteConfirmationDilaog(volunteer.getId()
                            ,context,
                            getAdapterPosition()-1);

                }
            });
            editView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Volunteer volunteer= getItem(getAdapterPosition() - 1);

                    Intent intent = new Intent(context, VolunterExperienceEditActivity.class);
                    intent.putExtra("qualificationid",volunteer.getId());
                    intent.putExtra("student_id",volunteer.getStudent_id());
                    context.startActivity(intent);


                }
            });
        }
    }


    public void deleteConfirmationDilaog(final String volunteer_id,
                                         final Context context,
                                         final int adapterPosition) {


        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dilaog_remove);
        Button btCancel = dialog.findViewById(R.id.btCancel);
        TextView text_dialog = dialog.findViewById(R.id.text_dialog);
        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        txtTitle.setText("Delete Confirmation");
        text_dialog.setText("Are you sure you want to delete this volunteer?");

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

                deletevolunteerResume(volunteer_id,context,adapterPosition);

                dialog.dismiss();
            }
        });
        dialog.show();


    }


    Call<Resume> deletevolunterResume;

    public void deletevolunteerResume(final String volunteer_id, final Context context, final int adapterPosition) {

        final ProgressDialog progressDialog = Utils.createProgressDialog(context);

        Map<String, String> data = new HashMap<>();
        data.put("qid", volunteer_id);
        data.put("sid", Pref.getClientId());


        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        deletevolunterResume = apiService.deleteQualification(Pref.getPreToken(), data);

        deletevolunterResume.enqueue(new Callback<Resume>() {
            @Override
            public void onResponse(@NonNull Call<Resume> call, @NonNull Response<Resume> response) {

                progressDialog.dismiss();

                if (response.code() == 200 || response.code() == 201) {

                    Resume resume = response.body();

                    assert resume != null;
                    if (resume.getStatus().equals("1")) {
                        try {

                            Pref.setdevicetoken("Bearer " + resume.getToken());
                            volunteers.remove(adapterPosition);
                            notifyItemRemoved(adapterPosition);
                            notifyDataSetChanged();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "failed to delete", Toast.LENGTH_SHORT).show();

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
