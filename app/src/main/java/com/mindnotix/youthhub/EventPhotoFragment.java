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

import com.mindnotix.adapter.FragmentEventPhotoAdapter;
import com.mindnotix.model.events.views.Photo_list;

import java.util.ArrayList;

/**
 * Created by Admin on 2/22/2018.
 */

public class EventPhotoFragment extends Fragment {
    private static final String TAG = EventPhotoFragment.class.getSimpleName();
    ArrayList<Photo_list> photoListArrayList;
    TextView txtEmpty;
    FragmentEventPhotoAdapter fragmentEventPhotoAdapter;
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

    public void setPhotoArrayList(ArrayList<Photo_list> photoListArrayLists) {
        this.photoListArrayList = new ArrayList<>();
        this.photoListArrayList.clear();
        this.photoListArrayList = photoListArrayLists;
        Log.d(TAG, "setPhotoArrayList: " + photoListArrayList.size());

        if(photoListArrayLists.size() > 0){

            fragmentEventPhotoAdapter = new FragmentEventPhotoAdapter(photoListArrayLists, getActivity());
            recyclerViewPhotos.setAdapter(fragmentEventPhotoAdapter);
        }else{
            //txtEmpty.setText("Sorry! No photo available");
            txtEmpty.setVisibility(View.VISIBLE);
            recyclerViewPhotos.setVisibility(View.GONE);
        }


    }

    private void UiInitialization(View view) {
        // get the reference of RecyclerView
        this.recyclerViewPhotos = (RecyclerView) view.findViewById(R.id.recyclerViewPhotos);
        this.txtEmpty = (TextView) view.findViewById(R.id.txtEmpty);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        this.recyclerViewPhotos.setLayoutManager(manager);
    }


}
