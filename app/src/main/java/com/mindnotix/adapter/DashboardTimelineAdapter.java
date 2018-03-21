package com.mindnotix.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mindnotix.listener.RecyclerViewClickListener;
import com.mindnotix.model.dashboard.Postlist;
import com.mindnotix.utils.Constant;
import com.mindnotix.utils.Pref;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sridharan on 1/23/2018.
 */

public class DashboardTimelineAdapter extends RecyclerView.Adapter<DashboardTimelineAdapter.MyViewHolder> {
    private final int VIEW_ITEM = 1;

    FragmentActivity activity;
    ArrayList<Postlist> dataArrayList;
    String mediumPath;
    RecyclerView recyclerView;
    private RecyclerViewClickListener mListener;

    public DashboardTimelineAdapter(FragmentActivity activity, ArrayList<Postlist> dataArrayList, String mediumPath
            , RecyclerViewClickListener mListener) {


        this.mListener = mListener;
        this.activity = activity;
        this.dataArrayList = dataArrayList;
        this.mediumPath = mediumPath;


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_feed_item, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holders, int position) {

        holders.setIsRecyclable(false);
        try {

/*            Log.d("aaaaa", "onBindViewHolder: " + dataArrayList.get(position).getPostUser().get(0).getFirstName());
            String firstName = dataArrayList.get(position).getPostUser().get(0).getFirstName();
            String lastname = dataArrayList.get(position).getPostUser().get(0).getLastName();*/

            Log.d("aaaaa", "onBindViewHolder: " + dataArrayList.get(position).getPostUser().getFirstName());
            String firstName = dataArrayList.get(position).getPostUser().getFirstName();
            String lastname = dataArrayList.get(position).getPostUser().getLastName();
            String str = firstName;
            String cap = str.substring(0, 1).toUpperCase() + str.substring(1);

            holders.name.setText(cap.concat(" " + lastname));
            holders.timestamp.setText(dataArrayList.get(position).getPosts().getPmCreatedOn());

            Picasso.with(activity)
                    .load(Constant.BASE_URL_PROFILE_THUMBNAIL + dataArrayList.get(position).getPostUser().getProfilePic())
                    //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(holders.profilePic);

            holders.txtStatusMsg.setText(dataArrayList.get(position).getPosts().getPmDescription());
            holders.txtLikeCounts.setText(dataArrayList.get(position).getPosts().getPmLikes());
            holders.txtCommentsCounts.setText(dataArrayList.get(position).getPosts().getPmComments());
            holders.txtContributeCounts.setText(dataArrayList.get(position).getPosts().getPmContributors());


            final int size = dataArrayList.get(position).getImages().size();

            Log.d("adfsadfsdf", "onBindViewHolder: size  " + size);


            if (size == 1) {
                String filename = dataArrayList.get(position).getImages().get(0).getPgName().replace(" ", "%20");

                holders.imgJobLists.setVisibility(View.VISIBLE);
                holders.linearTwoImg.setVisibility(View.GONE);
                holders.linearMoreImg.setVisibility(View.GONE);
                Log.d("aaaaaaa", "onBindViewHolder: single " + mediumPath + filename);
                Picasso.with(activity)
                        .load(mediumPath.concat(filename))

                        //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(holders.imgJobLists, new Callback() {
                            @Override
                            public void onSuccess() {
                                holders.imgJobLists_progress.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                holders.imgJobLists_progress.setVisibility(View.GONE);
                            }
                        });


            } else if (size == 2) {

                holders.imgJobLists_progress.setVisibility(View.GONE);
                holders.linearTwoImg.setVisibility(View.VISIBLE);
                String image1 = dataArrayList.get(position).getImages().get(0).getPgName().replace(" ", "%20");

                holders.imgJobLists.setVisibility(View.GONE);
                Log.d("aaaaaaa", "onBindViewHolder: two " + mediumPath + image1);
                Picasso.with(activity)
                        .load(mediumPath.concat(image1))
                        //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .fit().centerCrop()
                        .into(holders.image1, new Callback() {
                            @Override
                            public void onSuccess() {
                                holders.image1_progress.setVisibility(View.GONE);

                            }

                            @Override
                            public void onError() {
                                holders.image1_progress.setVisibility(View.GONE);
                            }
                        });

                String image2 = dataArrayList.get(position).getImages().get(1).getPgName().replace(" ", "%20");

                Log.d("aaaaaaa", "onBindViewHolder:two " + mediumPath + image2);
                Picasso.with(activity)
                        .load(mediumPath.concat(image2))
                        //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .fit().centerCrop()
                        .into(holders.image2, new Callback() {
                            @Override
                            public void onSuccess() {
                                holders.image2_progress.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                holders.image2_progress.setVisibility(View.GONE);
                            }
                        });
            } else if (size > 2) {

                holders.imgJobLists_progress.setVisibility(View.GONE);
                holders.linearMoreImg.setVisibility(View.VISIBLE);
                holders.linearTwoImg.setVisibility(View.GONE);
                String image1 = dataArrayList.get(position).getImages().get(1).getPgName().replace(" ", "%20");

                holders.imgJobLists.setVisibility(View.GONE);
                Log.d("aaaaaaa", "onBindViewHolder:more " + mediumPath + image1);
                Picasso.with(activity)
                        .load(mediumPath.concat(image1))
                        //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .fit().centerCrop()
                        .into(holders.image3, new Callback() {
                            @Override
                            public void onSuccess() {
                                holders.image3_progress.setVisibility(View.GONE);
                                if (size > 3) {
                                    holders.countTextViewId.setText("+" + (size - 3));
                                    holders.countTextViewId.setVisibility(View.VISIBLE);
                                    holders.transparent.setVisibility(View.VISIBLE);


                                } else {
                                    holders.countTextViewId.setVisibility(View.GONE);
                                    holders.transparent.setVisibility(View.GONE);
                                }


                            }

                            @Override
                            public void onError() {
                                holders.image3_progress.setVisibility(View.GONE);
                            }
                        });

                String image2 = dataArrayList.get(position).getImages().get(0).getPgName().replace(" ", "%20");

                Log.d("aaaaaaa", "onBindViewHolder:more " + mediumPath + image2);
                Picasso.with(activity)
                        .load(mediumPath.concat(image2))
                        //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .fit().centerCrop()
                        .into(holders.image4, new Callback() {
                            @Override
                            public void onSuccess() {
                                holders.image4_progress.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                holders.image4_progress.setVisibility(View.GONE);
                            }
                        });


                String image3 = dataArrayList.get(position).getImages().get(2).getPgName().replace(" ", "%20");

                Log.d("aaaaaaa", "onBindViewHolder:more " + mediumPath + image3);
                Picasso.with(activity)
                        .load(mediumPath.concat(image3))
                        //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .fit().centerCrop()
                        .into(holders.image5, new Callback() {
                            @Override
                            public void onSuccess() {
                                holders.image5_progress.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                holders.image5_progress.setVisibility(View.GONE);
                            }
                        });
            } else {
                holders.image1_progress.setVisibility(View.GONE);
                holders.image5_progress.setVisibility(View.GONE);
                holders.imgJobLists_progress.setVisibility(View.GONE);
                holders.image2_progress.setVisibility(View.GONE);
                holders.image3_progress.setVisibility(View.GONE);
                holders.image4_progress.setVisibility(View.GONE);
            }


            if (dataArrayList.get(position).getPostUserActivity().getIsEncourageUser().equals("1")) {
                holders.imgLike.setImageResource(R.drawable.thumbs_up_like_fill);
                holders.txtEncourage.setText("Encouraged");
            } else {
                holders.imgLike.setImageResource(R.drawable.thumbs_up_like);
            }


            int tagSize = dataArrayList.get(position).getTagsName().size();
            String tagName = "";

            for (int i = 0; i < tagSize; i++) {

                if (tagSize > 1) {

                    String tagNames = dataArrayList.get(position).getTagsName().get(i).getName().trim();

                    tagName = tagName + "#".concat(tagNames) + ", ";
                    Log.d("tags data ", "onBindViewHolder:if " + tagName);

                } else {
                    String tagNames = dataArrayList.get(position).getTagsName().get(i).getName().trim();
                    tagName = tagName + "#".concat(tagNames) + ", ";
                    Log.d("tags data ", "onBindViewHolder:else " + tagName);

                }
            }
            if (tagName.length() > 0 && tagName.charAt(tagName.length() - 2) == ',') {
                tagName = tagName.substring(0, tagName.length() - 2);
            }
            Log.d("rfwergwegertwetwetwer", tagName);


            if (tagName.length() != 0)
                holders.txtUrl.setText(tagName.trim());
            else
                holders.txtUrl.setVisibility(View.GONE);

            if (dataArrayList.get(position).getPosts().getPmStep().equals("1")
                    && Pref.getClientId().equals(dataArrayList.get(position).getPosts().getPmUserId())) {

                holders.imgFavorite.setVisibility(View.VISIBLE);
                holders.imgFavorite.setImageResource(R.drawable.roadpng);
            } else {
                holders.imgFavorite.setVisibility(View.GONE);
            }
            if (dataArrayList.get(position).getPostUserActivity().getIs_favour_user().equals("1")) {

                holders.imgFavoriteImg.setVisibility(View.VISIBLE);
                holders.imgFavoriteImg.setImageResource(R.drawable.ic_bookmark_black_24dp);
            } else {
                holders.imgFavoriteImg.setVisibility(View.GONE);
            }

            if (!dataArrayList.get(position).getPosts().getPmLikes().equals("0"))
                holders.linear_likes.setVisibility(View.VISIBLE);

            if (!dataArrayList.get(position).getPosts().getPmComments().equals("0"))
                holders.linear_comments.setVisibility(View.VISIBLE);

            if (!dataArrayList.get(position).getPosts().getPmContributors().equals("0"))
                holders.linear_contribute.setVisibility(View.VISIBLE);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int position;
        ProgressBar imgJobLists_progress;
        ProgressBar image5_progress;
        ProgressBar image3_progress;
        ProgressBar image4_progress;
        ProgressBar image1_progress;
        ProgressBar image2_progress;
        TextView countTextViewId;
        private CircleImageView profilePic;
        private TextView name;
        private TextView timestamp;
        private TextView txtStatusMsg;
        private TextView txtUrl;
        private TextView txtLikeCounts;
        private TextView txtEncourage;
        private TextView txtCommentsCounts;
        private TextView txtContributeCounts;
        private ImageView imgLike;
        private ImageView ImagPopUp;
        private ImageView image1;
        private ImageView image2;
        private ImageView image3;
        private ImageView image4;
        private ImageView image5;
        private ImageView imgFavorite;
        private ImageView imgFavoriteImg;
        private ImageView imgJobLists;
        private LinearLayout imgComments;
        private LinearLayout linear_likes;
        private LinearLayout linear_comments;
        private LinearLayout linear_contribute;
        private LinearLayout imgContribute;
        private LinearLayout imgLikes;
        private LinearLayout linearTwoImg;
        private LinearLayout linearMoreImg;
        private View transparent;
        private RecyclerViewClickListener mListener;

        public MyViewHolder(View itemView, RecyclerViewClickListener mListener) {
            super(itemView);
            this.mListener = mListener;

            imgJobLists_progress = itemView.findViewById(R.id.imgJobLists_progress);
            image1_progress = itemView.findViewById(R.id.image1_progress);
            image2_progress = itemView.findViewById(R.id.image2_progress);
            image4_progress = itemView.findViewById(R.id.image4_progress);
            image3_progress = itemView.findViewById(R.id.image3_progress);
            image5_progress = itemView.findViewById(R.id.image5_progress);
            countTextViewId = itemView.findViewById(R.id.countTextViewId);
            transparent = itemView.findViewById(R.id.transparent);

            this.imgContribute = (LinearLayout) itemView.findViewById(R.id.imgContribute);
            this.imgLikes = (LinearLayout) itemView.findViewById(R.id.imgLikes);

            this.imgComments = (LinearLayout) itemView.findViewById(R.id.imgComments);
            this.linear_likes = (LinearLayout) itemView.findViewById(R.id.linear_likes);
            this.linear_comments = (LinearLayout) itemView.findViewById(R.id.linear_comments);
            this.linear_contribute = (LinearLayout) itemView.findViewById(R.id.linear_contribute);
            this.linearTwoImg = (LinearLayout) itemView.findViewById(R.id.two_image_view);
            this.linearMoreImg = (LinearLayout) itemView.findViewById(R.id.two_image_more);


            this.imgLike = (ImageView) itemView.findViewById(R.id.imgLike);
            this.imgJobLists = (ImageView) itemView.findViewById(R.id.imgJobLists);
            this.imgJobLists.setOnClickListener(this);
            this.ImagPopUp = (ImageView) itemView.findViewById(R.id.ImagPopUp);
            this.image1 = (ImageView) itemView.findViewById(R.id.image1);
            this.image1.setOnClickListener(this);
            this.image2 = (ImageView) itemView.findViewById(R.id.image2);
            this.image2.setOnClickListener(this);
            this.image3 = (ImageView) itemView.findViewById(R.id.image3);
            this.image3.setOnClickListener(this);
            this.image4 = (ImageView) itemView.findViewById(R.id.image4);
            this.image4.setOnClickListener(this);
            this.image5 = (ImageView) itemView.findViewById(R.id.image5);
            this.image5.setOnClickListener(this);


            this.imgFavorite = (ImageView) itemView.findViewById(R.id.imgFavoriteStep);
            this.imgFavoriteImg = (ImageView) itemView.findViewById(R.id.imgFavorite);
            this.txtContributeCounts = (TextView) itemView.findViewById(R.id.txtContributeCounts);
            this.txtCommentsCounts = (TextView) itemView.findViewById(R.id.txtCommentsCounts);

            this.txtLikeCounts = (TextView) itemView.findViewById(R.id.txtLikeCounts);
            this.txtEncourage = (TextView) itemView.findViewById(R.id.txtEncourage);
            this.txtUrl = (TextView) itemView.findViewById(R.id.txtUrl);
            this.txtStatusMsg = (TextView) itemView.findViewById(R.id.txtStatusMsg);
            this.timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.profilePic = (CircleImageView) itemView.findViewById(R.id.profilePic);

            if (linear_likes != null)
                this.linear_likes.setOnClickListener(this);

            if (imgLikes != null)
                this.imgLikes.setOnClickListener(this);
            if (imgLike != null)
                this.imgLike.setOnClickListener(this);
            if (imgComments != null)
                this.imgComments.setOnClickListener(this);
            if (ImagPopUp != null)
                this.ImagPopUp.setOnClickListener(this);
            if (imgContribute != null)
                this.imgContribute.setOnClickListener(this);
            if (this.linear_contribute != null)
                this.linear_contribute.setOnClickListener(this);
            if (this.linear_comments != null)
                this.linear_comments.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            position = getAdapterPosition();
            switch (view.getId()) {
                case R.id.linear_likes:
                    mListener.onClick(view, position);
                    break;
                case R.id.imgLikes:
                    if (dataArrayList.get(position).getPostUserActivity().getIsEncourageUser().equals("0")) {
                        this.imgLike.setImageResource(R.drawable.thumbs_up_like_fill);
                        this.txtEncourage.setText("Encouraged");
                        mListener.onClick(view, position);
                        this.imgLikes.setEnabled(false);
                        this.imgLikes.setClickable(false);

                    } else {
                        this.imgLike.setImageResource(R.drawable.thumbs_up_like);
                        this.txtEncourage.setText("Encourage");
                        mListener.onClick(view, position);
                        this.imgLikes.setEnabled(false);
                        this.imgLikes.setClickable(false);

                    }

                    break;


                case R.id.imgComments:
                    // Toast.makeText(activity, "" + position, Toast.LENGTH_SHORT).show();
                    mListener.onClick(view, position);

                    break;
                case R.id.imgContribute:
                    mListener.onClick(view, position);

                    break;
                case R.id.ImagPopUp:
                    mListener.onClick(view, position);
                    break;
                case R.id.imgJobLists:
                    mListener.onClick(view, position);
                    break;
                case R.id.image1:
                    mListener.onClick(view, position);
                    break;
                case R.id.image2:
                    mListener.onClick(view, position);
                    break;
                case R.id.image3:
                    mListener.onClick(view, position);
                    break;
                case R.id.image4:
                    mListener.onClick(view, position);
                    break;
                case R.id.image5:
                    mListener.onClick(view, position);
                    break;
                case R.id.linear_contribute:
                    mListener.onClick(view, position);
                    break;

                case R.id.linear_comments:
                    mListener.onClick(view, position);
                    break;


            }
        }
    }
}
