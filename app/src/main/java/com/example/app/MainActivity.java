package com.example.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;


import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.widget.RelativeLayout;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class MainActivity extends Activity {

    private WebView webView;
    private RelativeLayout adLayout;
    private AdView adView;
    private boolean isSpecificDivVisible = false;
    private boolean wasSpecificDivVisible = false;
    private int specificDivX = 0;
    private int specificDivY = 0;

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

        webView = findViewById(R.id.activity_main_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("https://www.w3schools.com/html/tryit.asp?filename=tryhtml_basic");

        
        // Create the AdView programmatically
        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111"); // Use Google AdMob Sample Ad Unit ID for testing
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams adLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        adLayout.setLayoutParams(adLayoutParams);

        webView.addJavascriptInterface(new JSInterface(), "AndroidInterface");

        webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                moveAdView();
                checkForSpecificDiv();
            }
        });
    }

    private void moveAdView() {
        if (isSpecificDivVisible) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) adView.getLayoutParams();
            params.leftMargin = specificDivX;
            params.topMargin = specificDivY;
            adView.setLayoutParams(params);
        }
    }

    private void checkForSpecificDiv() {
        webView.evaluateJavascript(
                "javascript:(function() { " +
                        "var divElement = document.getElementById('adBooster');" +
                        "if (divElement != null && divElement.offsetParent !== null) { " +
                        "   AndroidInterface.onSpecificDivVisible(true, divElement.offsetLeft, divElement.offsetTop); " +
                        "} else { " +
                        "   AndroidInterface.onSpecificDivVisible(false, 0, 0); " +
                        "} " +
                        "})()",
                null
        );
    }

    private class JSInterface {
        @JavascriptInterface
        public void onSpecificDivVisible(boolean isVisible, int divX, int divY) {
            wasSpecificDivVisible = isSpecificDivVisible;
            isSpecificDivVisible = isVisible;
            specificDivX = divX;
            specificDivY = divY;

            if (wasSpecificDivVisible != isSpecificDivVisible) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSpecificDivVisible) {
                            Toast.makeText(MainActivity.this, "Specific div is now visible at X: " + specificDivX + ", Y: " + specificDivY, Toast.LENGTH_SHORT).show();
                            addAdView();
                        } else {
                            Toast.makeText(MainActivity.this, "Specific div is no longer visible", Toast.LENGTH_SHORT).show();
                            removeAdView();
                        }
                    }
                });
            }
        }

        private void addAdView() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (adView.getParent() == null) {
                        adLayout.addView(adView);
                    }
                }
            });
        }

        private void removeAdView() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adLayout.removeView(adView);
                }
            });
        }
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
