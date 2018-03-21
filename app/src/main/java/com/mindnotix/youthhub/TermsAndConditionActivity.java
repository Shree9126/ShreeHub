package com.mindnotix.youthhub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TermsAndConditionActivity extends AppCompatActivity {

    WebView webView1;
    String type;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);

        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");
        }
        this.webView1 = (WebView) findViewById(R.id.webView1);
        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.getSettings().setPluginState(WebSettings.PluginState.ON);

        //---you need this to prevent the webview from
        // launching another browser when a url
        // redirection occurs---
        webView1.setWebViewClient(new Callback());
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);

        try {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if (type.equals("1")) {
                getSupportActionBar().setTitle(R.string.terms_condition);
            } else {
                getSupportActionBar().setTitle("Privacy policy");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (type.equals("1")) {


            String pdf = "https://youthhub.co.nz/viewtc/WW91dGggSHViIFVzZXIgVGVybXMgLSBZb3V0aC5wZGY=";
            webView1.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
        } else {


            String pdf = "https://youthhub.co.nz/viewtc/WW91dGhfSHViX1ByaXZhY3lfUG9saWN5LnBkZg==";
            webView1.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
        }

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
