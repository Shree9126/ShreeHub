package com.mindnotix.youthhub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TradeJobViewActivity extends AppCompatActivity {

    String trade_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent() != null)
            trade_url = getIntent().getStringExtra("trade_url");
        setContentView(R.layout.activity_trade_job_view);
        WebView mywebview = (WebView) findViewById(R.id.webView1);
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.setWebViewClient(new TradeJobViewActivity.MyBrowser());

        mywebview.getSettings().setPluginState(WebSettings.PluginState.ON);
        mywebview.getSettings().setSupportZoom(true);
        mywebview.getSettings().setBuiltInZoomControls(true);
        mywebview.getSettings().setDisplayZoomControls(true);
        mywebview.loadUrl(trade_url);
    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
