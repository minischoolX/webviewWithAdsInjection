package com.example.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import android.view.LayoutInflater;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity {

    private WebView mWebView;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mWebView = findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());
//        mWebView.setWebViewClient(new MyWebViewClient());

        // REMOTE RESOURCE
        // mWebView.loadUrl("https://example.com");

        // Load HTML content with the native view
        String htmlContent = "<html>\n" +
                "<body>\n" +
                "    <h1>Welcome to My App</h1>\n" +
                "    <div id=\"nativeViewContainer\"></div>\n" +
                "</body>\n" +
                "</html>";
        
        // LOCAL RESOURCE
        // mWebView.loadUrl("file:///android_asset/index.html");
        mWebView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        RelativeLayout nativeViewContainer = mWebView.findViewById(R.id.nativeViewContainer);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        nativeViewContainer.addView(adView, layoutParams);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
