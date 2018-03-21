package com.mindnotix.adapter;

/**
 * Created by Sridharan on 1/9/2018.
 */


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.mindnotix.youthhub.R;
import java.util.ArrayList;


public class SimplePagerAdapter extends PagerAdapter {
    private ArrayList<IntroModel> data;
    private LayoutInflater mInflator = null;
    private Context mContext = null;

    public SimplePagerAdapter(Context context, ArrayList<IntroModel> d) {
        mContext = context;
        data = d;

        mInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public Object instantiateItem(ViewGroup collection, int position) {
        ViewGroup layout;
        layout = (ViewGroup) mInflator.inflate(R.layout.tutotial_view_items, collection, false);
        ImageView t =  layout.findViewById(R.id.image_slide);
        t.setImageResource(data.get(position).getImage());
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}