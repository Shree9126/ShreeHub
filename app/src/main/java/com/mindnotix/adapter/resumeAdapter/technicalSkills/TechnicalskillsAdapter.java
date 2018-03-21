package com.mindnotix.adapter.resumeAdapter.technicalSkills;

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
import com.mindnotix.model.resume.loadResume.Technicalskills;
import com.mindnotix.network.ApiClient;
import com.mindnotix.network.YouthHubApi;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.utils.Utils;
import com.mindnotix.youthhub.R;
import com.mindnotix.youthhub.cvresume.addExtraActivites.TechnicalSkillsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_technical_resume, parent, false);
            return new GenericViewHolder(v);
        }
        return null;
    }

    private Technicalskills getItem(int position) {
        return technicalskills.get(position);
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
            Technicalskills technicalskills = getItem(position - 1);
            GenericViewHolder genericViewHolder = (GenericViewHolder) holder;

            genericViewHolder.tvTitle.setText(technicalskills.getName());

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

            if(!technicalskills.getLevel().equals("")&&technicalskills.getLevel()!=null){
                genericViewHolder.tvSkills.setVisibility(View.VISIBLE);
                genericViewHolder.tvSkills.setText(technicalskills.getLevel());
            }else{
                genericViewHolder.tvSkills.setVisibility(View.GONE);
                genericViewHolder.tvSkills.setText(technicalskills.getLevel());
            }

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

                    Intent intent = new Intent(context, TechnicalSkillsActivity.class);
                    intent.putExtra("title","");
                    intent.putExtra("id","");
                    intent.putExtra("skills","");
                    intent.putExtra("update","0");
                    context.startActivity(intent);


                }
            });

        }
    }

    class GenericViewHolder extends RecyclerView.ViewHolder {
        TextView tvSkills;
        TextView tvTitle;
        View viewDelete;
        View viewEdit;


        public GenericViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.viewDelete = itemView.findViewById(R.id.viewDelete);
            this.tvSkills = itemView.findViewById(R.id.tvSkills);
            this.viewEdit = itemView.findViewById(R.id.viewEdit);


            viewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Technicalskills technicalskills = getItem(getAdapterPosition() - 1);
                    deleteConfirmationDilaog(technicalskills.getId()
                            ,context,
                            getAdapterPosition()-1);

                }
            });


            viewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Technicalskills technicalskills = getItem(getAdapterPosition() - 1);


                    Intent intent = new Intent(context, TechnicalSkillsActivity.class);
                    intent.putExtra("title",technicalskills.getName());
                    intent.putExtra("id",technicalskills.getId());
                    intent.putExtra("skills",technicalskills.getLevel());
                    intent.putExtra("update","1");
                    context.startActivity(intent);




                }
            });




        }
    }

    public void deleteConfirmationDilaog(final String language_id,
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
        text_dialog.setText("Are you sure you want to delete this record?");

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

                deleteEducationResume(language_id,context,adapterPosition);

                dialog.dismiss();
            }
        });
        dialog.show();


    }


    Call<ExtraActivities> deleteEducationResume;

    public void deleteEducationResume(final String language_id, final Context context, final int adapterPosition) {

        final ProgressDialog progressDialog = Utils.createProgressDialog(context);

        Map<String, String> data = new HashMap<>();
        data.put("id", language_id);
        data.put("sid", Pref.getClientId());
        data.put("type", "1");

        YouthHubApi apiService =
                ApiClient.getClient(Constant.BASE_URL).create(YouthHubApi.class);

        deleteEducationResume = apiService.deleteExtraActivities(Pref.getPreToken(), data);

        deleteEducationResume.enqueue(new Callback<ExtraActivities>() {
            @Override
            public void onResponse(@NonNull Call<ExtraActivities> call, @NonNull Response<ExtraActivities> response) {

                progressDialog.dismiss();

                if (response.code() == 200 || response.code() == 201) {

                    ExtraActivities extraActivities = response.body();

                    assert extraActivities != null;
                    if (extraActivities.getStatus().equals("1")) {
                        try {

                            Pref.setdevicetoken("Bearer " + extraActivities.getToken());
                            technicalskills.remove(adapterPosition);
                            notifyDataSetChanged();

                            Toast.makeText(context, extraActivities.getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(@NonNull Call<ExtraActivities> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();

            }
        });


    }


}



