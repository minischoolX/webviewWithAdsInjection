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

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
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
        mWebView.addJavascriptInterface(new NativeViewInterface(), "NativeViewInterface");
        
        // REMOTE RESOURCE
        // mWebView.loadUrl("https://example.com");

       // Load HTML content with the native view
        String htmlContent = "<html>\n" +
        "<head>\n" +
        "    <style>\n" +
        "        #nativeViewContainer {\n" +
        "        border: 1px dotted black;\n" +
        "        width: 80%;\n" +
        "        height: 200px;\n" +
        "        margin: 0 auto;\n" +
        "        }\n" +
        "    </style>\n" +
        "    <script>\n" +
        "        function notifyAndroidToAddNativeView() {\n" +
        "            window.NativeViewInterface.addNativeView();\n" +
        "        }\n" +
        "    </script>\n" +
        "</head>\n" +
        "<body>\n" +
        "    <h1>Welcome to My App</h1>\n" +
        "    <div id=\"nativeViewContainer\"></div>\n" +
        "    <button onclick=\"notifyAndroidToAddNativeView()\">Add Native View</button>\n" +
        "</body>\n" +
        "</html>";

        
        // LOCAL RESOURCE
        // mWebView.loadUrl("file:///android_asset/index.html");
        mWebView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);

//        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

//        LayoutInflater inflater = LayoutInflater.from(this);
//        AdView nativeAdView = (AdView) inflater.inflate(R.layout.native_ad_view, null);
//        RelativeLayout nativeViewContainer = webView.findViewById(R.id.nativeViewContainer);
//        nativeViewContainer.addView(nativeAdView);

//        RelativeLayout nativeViewContainer = mWebView.findViewById(R.id.nativeViewContainer);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        nativeViewContainer.addView(adView, layoutParams);

//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    // JavaScript interface to handle communication between WebView and Android
public class NativeViewInterface {
    @JavascriptInterface
    public void addNativeView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Create and insert the AdMob banner programmatically
                AdView adView = new AdView(MainActivity.this);
                adView.setAdSize(AdSize.BANNER);
                adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

                // Find the nativeViewContainer by evaluating JavaScript within WebView
                mWebView.evaluateJavascript("document.getElementById('nativeViewContainer')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        // Check if the nativeViewContainer is found
                        if (value != null && !value.isEmpty()) {
                            // Remove the surrounding double quotes from the value
                            String containerId = value.replaceAll("\"", "");
                            // Insert the adView into the nativeViewContainer
                            mWebView.loadUrl("javascript:document.getElementById('" + containerId + "').appendChild(document.createElement('div')).appendChild(arguments[0])");
                            // Load the AdMob banner
                            AdRequest adRequest = new AdRequest.Builder().build();
                            adView.loadAd(adRequest);
                        }
                    }
                });
            }
        });
    }
}

        public class WebAppInterface {
        @JavascriptInterface
        public void addNativeView() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Create and insert the AdMob banner programmatically
                    AdView adView = new AdView(MainActivity.this);
                    adView.setAdSize(AdSize.BANNER);
                    adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

                    RelativeLayout nativeViewContainer = mWebView.findViewById(R.id.nativeViewContainer);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    nativeViewContainer.addView(adView, layoutParams);

                    // Load the AdMob banner
                    AdRequest adRequest = new AdRequest.Builder().build();
                    adView.loadAd(adRequest);
                }
            });
        }
    }
}
