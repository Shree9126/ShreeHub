package com.mindnotix.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mindnotix.listener.Item;
import com.mindnotix.model.profile.update.EntryItem;
import com.mindnotix.model.profile.update.SectionItem;
import com.mindnotix.youthhub.ProfileUpdateActivity;
import com.mindnotix.youthhub.R;
import com.mindnotix.youthhub.cvresume.Education.EducationEditActivity;
import com.mindnotix.youthhub.cvresume.Education.EducationResumeActivity;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2018.
 */

public class EntryAdapter extends ArrayAdapter<Item> {

    private ProfileUpdateActivity context;
    EducationResumeActivity activity;
    EducationEditActivity educationEditActivity;
    private ArrayList<Item> items;
    private LayoutInflater vi;
    String var = "resume";


    public EntryAdapter(ProfileUpdateActivity context, ArrayList<Item> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public EntryAdapter(EducationResumeActivity activity, ArrayList<Item> items, String resume) {
        super(activity, 0, items);
        this.activity = activity;
        this.items = items;
        vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public EntryAdapter(EducationEditActivity activity, ArrayList<Item> items, String resume) {
        super(activity, 0, items);
        this.educationEditActivity = activity;
        this.items = items;
        vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        final Item i = items.get(position);
        if (i != null) {
            if (i.isSection()) {
                SectionItem si = (SectionItem) i;
                v = vi.inflate(R.layout.list_item_section, null);

                v.setOnClickListener(null);
                v.setOnLongClickListener(null);
                v.setLongClickable(false);

                TextView sectionView = (TextView) v.findViewById(R.id.tvTitle);
                sectionView.setText(si.getTitle());

            } else {
                final EntryItem ei = (EntryItem) i;
                v = vi.inflate(R.layout.list_item_enty, null);


                TextView title = (TextView) v.findViewById(R.id.list_item_entry_title);
                CheckBox check_list = v.findViewById(R.id.check_list);
                check_list.setChecked(ei.getSetchecked());
                check_list.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                        ei.setSetchecked(b);


                    }
                });

                if (var.equals("resume")) {
                    title.setVisibility(View.VISIBLE);
                    check_list.setVisibility(View.GONE);

                } else {
                    title.setVisibility(View.GONE);
                    check_list.setVisibility(View.VISIBLE);
                }


                if (check_list != null)
                    check_list.setText(ei.title);

                if (title != null)
                    title.setText(ei.title);


            }
        }
        return v;
    }

}