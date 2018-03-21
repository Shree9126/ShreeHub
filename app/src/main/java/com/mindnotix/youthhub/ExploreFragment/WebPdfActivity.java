package com.mindnotix.youthhub.ExploreFragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mindnotix.youthhub.BaseActivity;
import com.mindnotix.youthhub.R;

/**
 * Created by Admin on 3/17/2018.
 */

public class WebPdfActivity extends BaseActivity {


    private static final String TAG = WebPdfActivity.class.getSimpleName();
    WebView webView1;
    String type;
    String path_pdf;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);

        if (getIntent() != null) {
            type = getIntent().getStringExtra("pdfurl");
            path_pdf = getIntent().getStringExtra("path_pdf");
        }
        Log.d(TAG, "onCreate:pdfurl " + path_pdf.concat(type));
        this.webView1 = (WebView) findViewById(R.id.webView1);
        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.getSettings().setPluginState(WebSettings.PluginState.ON);


        webView1.setWebViewClient(new Callback());
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);

        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            getSupportActionBar().setTitle("PDF view");


        } catch (Exception e) {
            e.printStackTrace();
        }



        webView1.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + path_pdf.concat(type));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return (false);
        }
    }
}
