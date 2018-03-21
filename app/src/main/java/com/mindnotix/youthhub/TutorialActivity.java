package com.mindnotix.youthhub;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mindnotix.adapter.IntroModel;
import com.mindnotix.adapter.SimplePagerAdapter;
import com.mindnotix.slider.ViewPagerIndicator;
import com.mindnotix.utils.Utils;

import java.util.ArrayList;

public class TutorialActivity extends BaseActivity implements View.OnClickListener {

    Button btnSignup, btnLogin;
    TextView txtContent, txtLogin;
    ArrayList<IntroModel> list;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, android.Manifest.permission.INTERNET};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        txtContent = findViewById(R.id.txtContent);
        txtLogin = findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        ViewPagerIndicator indicator = (ViewPagerIndicator) findViewById(R.id.pager_indicator);

        list = new ArrayList<>();

        IntroModel introModel = new IntroModel(R.drawable.three, "Let us help you to find the exciting opportunities you will love.");
        list.add(introModel);

        IntroModel introModel2 = new IntroModel(R.drawable.two, "Our mission is to connect youth with the organisations they would like to work with.");
        list.add(introModel2);

        IntroModel introModel1 = new IntroModel(R.drawable.one, "The best way to predict the future is to create it.");
        list.add(introModel1);


        SimplePagerAdapter adapter = new SimplePagerAdapter(this, list);
        pager.setAdapter(adapter);

        indicator.setPager(pager);

        adapter.notifyDataSetChanged();
        pager.setCurrentItem(0);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                txtContent.setText(list.get(position).getContent());

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnLogin:
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
                break;
            case R.id.btnSignup:
                Intent intentSignup = new Intent(this, Signup_step_one.class);
                startActivity(intentSignup);
                finish();
                break;
            case R.id.txtLogin:
                Intent txtLogin = new Intent(this, Signup_step_one.class);
                startActivity(txtLogin);
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (!Utils.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        if (!nbIsNetworkAvailable(this)) {
            nbShowNoInternet(this);
        }
    }

}
