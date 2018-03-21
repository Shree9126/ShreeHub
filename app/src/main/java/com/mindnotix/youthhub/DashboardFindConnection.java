package com.mindnotix.youthhub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mindnotix.youthhub.find_connection.FindBusinessActivity;
import com.mindnotix.youthhub.find_connection.FindConnectinYouthActivity;
import com.mindnotix.youthhub.find_connection.FindConnectionServiceProviderActivity;
import com.mindnotix.youthhub.find_connection.FindNavigatorsActivity;
import com.mindnotix.youthhub.find_connection.FindSchoolsActivity;
import com.mindnotix.youthhub.find_connection.FindTeachersActivity;
import com.mindnotix.youthhub.find_connection.FindTertiaryActivity;

/**
 * Created by Admin on 1/25/2018.
 */

public class DashboardFindConnection extends Fragment implements View.OnClickListener {

    private LinearLayout linearYouth;
    private LinearLayout linearServiceProvider;
    private LinearLayout linearTertiary;
    private LinearLayout linearSchools;
    private LinearLayout linearTeachers;
    private LinearLayout linearBusiness;
    private LinearLayout linearNavigators;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_find_connections, container, false);

        UiInitialization(view);

        return view;
    }

    private void UiInitialization(View view) {

        this.linearNavigators = (LinearLayout) view.findViewById(R.id.linearNavigators);
        this.linearNavigators.setOnClickListener(this);
        this.linearBusiness = (LinearLayout) view.findViewById(R.id.linearBusiness);
        this.linearBusiness.setOnClickListener(this);
        this.linearTeachers = (LinearLayout) view.findViewById(R.id.linearTeachers);
        this.linearTeachers.setOnClickListener(this);
        this.linearSchools = (LinearLayout) view.findViewById(R.id.linearSchools);
        this.linearSchools.setOnClickListener(this);
        this.linearTertiary = (LinearLayout) view.findViewById(R.id.linearTertiary);
        this.linearTertiary.setOnClickListener(this);
        this.linearServiceProvider = (LinearLayout) view.findViewById(R.id.linearServiceProvider);
        this.linearServiceProvider.setOnClickListener(this);
        this.linearYouth = (LinearLayout) view.findViewById(R.id.linearYouth);
        this.linearYouth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.linearYouth:

                Intent intent = new Intent(getActivity(), FindConnectinYouthActivity.class);
                startActivity(intent);

                break;
            case R.id.linearServiceProvider:

                Intent mIntent1 = new Intent(getActivity(),FindConnectionServiceProviderActivity.class);
                startActivity(mIntent1);
                break;
            case R.id.linearTertiary:
                Intent tertiaryIntent = new Intent(getActivity(),FindTertiaryActivity.class);
                startActivity(tertiaryIntent);
                break;
            case R.id.linearSchools:
                Intent schoolsIntent = new Intent(getActivity(),FindSchoolsActivity.class);
                startActivity(schoolsIntent);
                break;
            case R.id.linearTeachers:

                Intent teacherIntent = new Intent(getActivity(),FindTeachersActivity.class);
                startActivity(teacherIntent);

                break;
            case R.id.linearBusiness:

                Intent businessIntent = new Intent(getActivity(),FindBusinessActivity.class);
                startActivity(businessIntent);

                break;
            case R.id.linearNavigators:

                Intent navigatorsIntent = new Intent(getActivity(),FindNavigatorsActivity.class);
                startActivity(navigatorsIntent);
                break;


        }
    }
}
