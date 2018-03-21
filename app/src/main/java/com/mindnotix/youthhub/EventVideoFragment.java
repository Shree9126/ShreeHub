package com.mindnotix.youthhub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindnotix.adapter.FragmentEventVideoAdapter;
import com.mindnotix.model.events.views.Video_list;

import java.util.ArrayList;

/**
 * Created by Admin on 2/22/2018.
 */

public class EventVideoFragment extends Fragment {

    private static final String TAG = EventVideoFragment.class.getSimpleName();
    ArrayList<Video_list> videoListArrayList = new ArrayList<>();
    FragmentEventVideoAdapter fragmentEventVideoAdapter;
    TextView txtEmpty;
    private android.support.v7.widget.RecyclerView recyclerViewPhotos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("run", "onCreateView: ");
        if (getActivity() != null) {
            ((EventDetailsActivity) getActivity()).setFragInstance(getTag());
        }
        //    return
        View view = inflater.inflate(R.layout.fragment_event_photo, container, false);

        UiInitialization(view);

        return view;
    }

    private void UiInitialization(View view) {
        // get the reference of RecyclerView
        this.recyclerViewPhotos = (RecyclerView) view.findViewById(R.id.recyclerViewPhotos);
        this.txtEmpty = (TextView) view.findViewById(R.id.txtEmpty);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        this.recyclerViewPhotos.setLayoutManager(manager);
    }

    public void setVideoListArrayList(ArrayList<Video_list> videoListArrayLists) {

        this.videoListArrayList = new ArrayList<>();
        this.videoListArrayList.clear();
        this.videoListArrayList.addAll(videoListArrayLists);
        Log.d(TAG, "setPhotoArrayList: " + videoListArrayLists.size());

        if (videoListArrayLists.size() > 0) {

            fragmentEventVideoAdapter = new FragmentEventVideoAdapter(videoListArrayLists, getActivity());
            recyclerViewPhotos.setAdapter(fragmentEventVideoAdapter);
        } else {
            txtEmpty.setVisibility(View.VISIBLE);
            txtEmpty.setText(getResources().getString(R.string.no_video_avail));
            recyclerViewPhotos.setVisibility(View.GONE);
        }


    }
}
