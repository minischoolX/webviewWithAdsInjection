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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.LoadAdError;


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
                Toast.makeText(MainActivity.this, "Ads have been initialized", Toast.LENGTH_SHORT).show();
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
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
              // Code to be executed when the user clicks on an ad.
                Toast.makeText(MainActivity.this, "onAdClicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
              // Code to be executed when the user is about to return
              // to the app after tapping on an ad.
                Toast.makeText(MainActivity.this, "onAdClosed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
              // Code to be executed when an ad request fails.
                String error =
                String.format(
                    "domain: %s, code: %d, message: %s",
                    adError.getDomain(), adError.getCode(), adError.getMessage());

                Toast.makeText(MainActivity.this, String.format("Ad failed to load with error %s", error) , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdImpression() {
              // Code to be executed when an impression is recorded
              // for an ad.
                Toast.makeText(MainActivity.this, "onAdImpression", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded() {
              // Code to be executed when an ad finishes loading.
                Toast.makeText(MainActivity.this, "onAdLoaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
              // Code to be executed when an ad opens an overlay that
              // covers the screen.
                Toast.makeText(MainActivity.this, "onAdOpened", Toast.LENGTH_SHORT).show();
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams adLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        adLayout.setLayoutParams(adLayoutParams);
        RelativeLayout activityMainLayout = findViewById(R.id.activity_main);
//        activityMainLayout.addView(adContainer);

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
            RelativeLayout.LayoutParams paramsAdLayout = (RelativeLayout.LayoutParams) adLayout.getLayoutParams();
            paramsAdLayout.leftMargin = specificDivX;
            paramsAdLayout.topMargin = specificDivY;
            RelativeLayout.LayoutParams paramsAdView = (RelativeLayout.LayoutParams) adView.getLayoutParams();
            paramsAdView.leftMargin = specificDivX;
            paramsAdView.topMargin = specificDivY;
            adLayout.setLayoutParams(paramsAdLayout);
            adView.setLayoutParams(paramsAdView);
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
                    activityMainLayout.addView(adContainer);
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
                    activityMainLayout.removeView(adContainer);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
