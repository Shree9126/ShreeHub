package com.mindnotix.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindnotix.Assymetric.AGVRecyclerViewAdapter;
import com.mindnotix.Assymetric.AsymmetricItem;
import com.mindnotix.Assymetric.model.ItemImage;
import com.mindnotix.youthhub.R;
import com.squareup.picasso.Picasso;

import java.util.List;

class ChildAdapter extends AGVRecyclerViewAdapter<ViewHolder> {
    private final List<ItemImage> items;
    Activity activity;
    private int mDisplay = 0;
    private int mTotal = 0;

    public ChildAdapter(List<ItemImage> items, int mDisplay, int mTotal, Activity activity) {
        this.items = items;
        this.mDisplay = mDisplay;
        this.mTotal = mTotal;
        this.activity = activity;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("RecyclerViewActivity", "onCreateView");
        return new ViewHolder(parent, viewType, items);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("RecyclerViewActivity", "onBindView position=" + position);
        holder.bind(items, position, mDisplay, mTotal, activity);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public AsymmetricItem getItem(int position) {
        return (AsymmetricItem) items.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? 1 : 0;
    }
}


class ViewHolder extends RecyclerView.ViewHolder {
    private final ImageView mImageView;
    private final TextView textView;

    public ViewHolder(ViewGroup parent, int viewType, List<ItemImage> items) {
        super(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_item, parent, false));

        mImageView = (ImageView) itemView.findViewById(R.id.mImageView);
        textView = (TextView) itemView.findViewById(R.id.tvCount);


    }


    public void bind(List<ItemImage> item, int position, int mDisplay, int mTotal, Activity activity) {
/*    ImageLoader.getInstance().displayImage(String.valueOf(item.get(position).getImagePath()), mImageView);*/


        Log.d("aaasdfasdflkjhkjsad", "bind:image path " + item.get(position).getImagePath().concat(item.get(position).getThumb()));
        Picasso.with(activity)
                .load(item.get(position).getImagePath().concat(item.get(position).getThumb()))
                .placeholder(R.drawable.profile_pic) //this is optional the image to display while the url image is downloading
                .error(R.drawable.profile_pic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(mImageView);


        textView.setText("+" + (mTotal - mDisplay));
        if (mTotal > mDisplay) {
            if (position == mDisplay - 1) {
                textView.setVisibility(View.VISIBLE);
                mImageView.setAlpha(72);
            } else {
                textView.setVisibility(View.INVISIBLE);
                mImageView.setAlpha(255);
            }
        } else {
            mImageView.setAlpha(255);
            textView.setVisibility(View.INVISIBLE);
        }

        // textView.setText(String.valueOf(item.getPosition()));
    }
}
