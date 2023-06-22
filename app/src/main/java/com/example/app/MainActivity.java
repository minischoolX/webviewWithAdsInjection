package com.example.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;


import android.view.View;
import android.view.ViewGroup;
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
    private ViewGroup activityMainLayout;

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
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Inject JavaScript code after the page finishes loading
//                injectScrollableScript();
                view.loadUrl("javascript: " +
                    "window.onscroll = function() { " +
                    "   window.AndroidInterface.onScroll(); " + // Call a JavaScript interface method
                    "};");
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        //webView.loadUrl("https://www.w3schools.com/html/tryit.asp?filename=tryhtml_basic");
        String htmlContent = "<!DOCTYPE html>\n" +
        "<html>\n" +
        "<head>\n" +
        "  <title>My Webpage</title>\n" +
        "  <style>\n" +
        "    body {\n" +
        "      margin: 0;\n" +
        "      padding: 0;\n" +
        "      font-family: Arial, sans-serif;\n" +
        "    }\n" +
        "\n" +
        "    .navbar {\n" +
        "      position: sticky;\n" +
        "      top: 0;\n" +
        "      background-color: #333;\n" +
        "      padding: 10px;\n" +
        "      color: #fff;\n" +
        "    }\n" +
        "\n" +
        "    .content {\n" +
        "      margin-top: 70px;\n" +
        "      padding: 20px;\n" +
        "    }\n" +
        "\n" +
        "    .adBoosterX {\n" +
        "      background-color: #f2f2f2;\n" +
        "      padding: 20px;\n" +
        "      margin: 0 auto;\n" +
        "      width: 70%;\n" +
        "    }\n" +
        "  </style>\n" +
        "</head>\n" +
        "<body>\n" +
        "  <div class=\"navbar\">\n" +
        "    <h1>My Webpage</h1>\n" +
        "    <!-- Add your navbar content here -->\n" +
        "    <ul>\n" +
        "      <li><a href=\"#section1\">Section 1</a></li>\n" +
        "      <li><a href=\"#section2\">Section 2</a></li>\n" +
        "      <li><a href=\"#section3\">Section 3</a></li>\n" +
        "    </ul>\n" +
        "  </div>\n" +
        "\n" +
        "  <div class=\"content\">\n" +
        "    <h2 id=\"section1\">Section 1</h2>\n" +
        "    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin gravida sollicitudin magna, sit amet posuere eros aliquam et. Duis at dolor a velit finibus finibus in sed lacus. Aliquam ac volutpat enim, vitae vestibulum nisi.</p>\n" +
        "\n" +
        "    <h2 id=\"section2\">Section 2</h2>\n" +
        "    <input type=\"text\" placeholder=\"Enter your name\"><br><br>\n" +
        "    <textarea placeholder=\"Enter your message\"></textarea><br><br>\n" +
        "\n" +
        "    <h2 id=\"section3\">Section 3</h2>\n" +
        "    <img src=\"image.jpg\" alt=\"Image\"><br><br>\n" +
        "    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin gravida sollicitudin magna, sit amet posuere eros aliquam et. Duis at dolor a velit finibus finibus in sed lacus. Aliquam ac volutpat enim, vitae vestibulum nisi.</p>\n" +
        "  </div>\n" +
        "\n" +
        "  <div id=\"adBooster\" class=\"adBoosterX\">\n" +
        "    <h2>Ad Booster</h2>\n" +
        "    <!-- Add your ad content here -->\n" +
        "  </div>\n" +
        "\n" +
        "  <div class=\"content\">\n" +
        "    <h2 id=\"section4\">Section 1</h2>\n" +
        "    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin gravida sollicitudin magna, sit amet posuere eros aliquam et. Duis at dolor a velit finibus finibus in sed lacus. Aliquam ac volutpat enim, vitae vestibulum nisi.</p>\n" +
        "\n" +
        "    <h2 id=\"section5\">Section 2</h2>\n" +
        "    <input type=\"text\" placeholder=\"Enter your name\"><br><br>\n" +
        "    <textarea placeholder=\"Enter your message\"></textarea><br><br>\n" +
        "\n" +
        "    <h2 id=\"section6\">Section 3</h2>\n" +
        "    <img src=\"image.jpg\" alt=\"Image\"><br><br>\n" +
        "    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin gravida sollicitudin magna, sit amet posuere eros aliquam et. Duis at dolor a velit finibus finibus in sed lacus. Aliquam ac volutpat enim, vitae vestibulum nisi.</p>\n" +
        "  </div>\n" +
        "\n" +
        "  <div class=\"content\">\n" +
        "    <h2 id=\"section1\">Section 1</h2>\n" +
        "    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin gravida sollicitudin magna, sit amet posuere eros aliquam et. Duis at dolor a velit finibus finibus in sed lacus. Aliquam ac volutpat enim, vitae vestibulum nisi.</p>\n" +
        "\n" +
        "    <h2 id=\"section2\">Section 2</h2>\n" +
        "    <input type=\"text\" placeholder=\"Enter your name\"><br><br>\n" +
        "    <textarea placeholder=\"Enter your message\"></textarea><br><br>\n" +
        "\n" +
        "    <h2 id=\"section3\">Section 3</h2>\n" +
        "    <img src=\"image.jpg\" alt=\"Image\"><br><br>\n" +
        "    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin gravida sollicitudin magna, sit amet posuere eros aliquam et. Duis at dolor a velit finibus finibus in sed lacus. Aliquam ac volutpat enim, vitae vestibulum nisi.</p>\n" +
        "  </div>\n" +
        "\n" +
        "  <div id=\"adBooster\" class=\"adBoosterX\">\n" +
        "    <h2>Ad Booster</h2>\n" +
        "    <!-- Add your ad content here -->\n" +
        "  </div>\n" +
        "\n" +
        "  <div class=\"content\">\n" +
        "    <h2 id=\"section4\">Section 1</h2>\n" +
        "    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin gravida sollicitudin magna, sit amet posuere eros aliquam et. Duis at dolor a velit finibus finibus in sed lacus. Aliquam ac volutpat enim, vitae vestibulum nisi.</p>\n" +
        "\n" +
        "    <h2 id=\"section5\">Section 2</h2>\n" +
        "    <input type=\"text\" placeholder=\"Enter your name\"><br><br>\n" +
        "    <textarea placeholder=\"Enter your message\"></textarea><br><br>\n" +
        "\n" +
        "    <h2 id=\"section6\">Section 3</h2>\n" +
        "    <img src=\"image.jpg\" alt=\"Image\"><br><br>\n" +
        "    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin gravida sollicitudin magna, sit amet posuere eros aliquam et. Duis at dolor a velit finibus finibus in sed lacus. Aliquam ac volutpat enim, vitae vestibulum nisi.</p>\n" +
        "  </div>\n" +
        "\n" +
        "  <div class=\"content\">\n" +
        "    <h2 id=\"section1\">Section 1</h2>\n" +
        "    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin gravida sollicitudin magna, sit amet posuere eros aliquam et. Duis at dolor a velit finibus finibus in sed lacus. Aliquam ac volutpat enim, vitae vestibulum nisi.</p>\n" +
        "\n" +
        "    <h2 id=\"section2\">Section 2</h2>\n" +
        "    <input type=\"text\" placeholder=\"Enter your name\"><br><br>\n" +
        "    <textarea placeholder=\"Enter your message\"></textarea><br><br>\n" +
        "\n" +
        "    <h2 id=\"section3\">Section 3</h2>\n" +
        "    <img src=\"image.jpg\" alt=\"Image\"><br><br>\n" +
        "    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin gravida sollicitudin magna, sit amet posuere eros aliquam et. Duis at dolor a velit finibus finibus in sed lacus. Aliquam ac volutpat enim, vitae vestibulum nisi.</p>\n" +
        "  </div>\n" +
        "\n" +
        "  <div class=\"adBooster\">\n" +
        "    <h2>Ad Booster</h2>\n" +
        "    <!-- Add your ad content here -->\n" +
        "  </div>\n" +
        "\n" +
        "  <div class=\"content\">\n" +
        "    <h2 id=\"section4\">Section 1</h2>\n" +
        "    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin gravida sollicitudin magna, sit amet posuere eros aliquam et. Duis at dolor a velit finibus finibus in sed lacus. Aliquam ac volutpat enim, vitae vestibulum nisi.</p>\n" +
        "\n" +
        "    <h2 id=\"section5\">Section 2</h2>\n" +
        "    <input type=\"text\" placeholder=\"Enter your name\"><br><br>\n" +
        "    <textarea placeholder=\"Enter your message\"></textarea><br><br>\n" +
        "\n" +
        "    <h2 id=\"section6\">Section 3</h2>\n" +
        "    <img src=\"image.jpg\" alt=\"Image\"><br><br>\n" +
        "    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin gravida sollicitudin magna, sit amet posuere eros aliquam et. Duis at dolor a velit finibus finibus in sed lacus. Aliquam ac volutpat enim, vitae vestibulum nisi.</p>\n" +
        "  </div>\n" +
        "\n" +
        "  <div class=\"content\">\n" +
        "    <h2 id=\"section1\">Section 1</h2>\n" +
        "    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin gravida sollicitudin magna, sit amet posuere eros aliquam et. Duis at dolor a velit finibus finibus in sed lacus. Aliquam ac volutpat enim, vitae vestibulum nisi.</p>\n" +
        "\n" +
        "    <h2 id=\"section2\">Section 2</h2>\n" +
        "    <input type=\"text\" placeholder=\"Enter your name\"><br><br>\n" +
        "    <textarea placeholder=\"Enter your message\"></textarea><br><br>\n" +
        "\n" +
        "    <h2 id=\"section3\">Section 3</h2>\n" +
        "    <img src=\"image.jpg\" alt=\"Image\"><br><br>\n" +
        "    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin gravida sollicitudin magna, sit amet posuere eros aliquam et. Duis at dolor a velit finibus finibus in sed lacus. Aliquam ac volutpat enim, vitae vestibulum nisi.</p>\n" +
        "  </div>\n" +
        "\n" +
        "  <div class=\"adBooster\">\n" +
        "    <h2>Ad Booster</h2>\n" +
        "    <!-- Add your ad content here -->\n" +
        "  </div>\n" +
        "\n" +
        "  <div class=\"content\">\n" +
        "    <h2 id=\"section4\">Section 1</h2>\n" +
        "    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin gravida sollicitudin magna, sit amet posuere eros aliquam et. Duis at dolor a velit finibus finibus in sed lacus. Aliquam ac volutpat enim, vitae vestibulum nisi.</p>\n" +
        "\n" +
        "    <h2 id=\"section5\">Section 2</h2>\n" +
        "    <input type=\"text\" placeholder=\"Enter your name\"><br><br>\n" +
        "    <textarea placeholder=\"Enter your message\"></textarea><br><br>\n" +
        "\n" +
        "    <h2 id=\"section6\">Section 3</h2>\n" +
        "    <img src=\"image.jpg\" alt=\"Image\"><br><br>\n" +
        "    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin gravida sollicitudin magna, sit amet posuere eros aliquam et. Duis at dolor a velit finibus finibus in sed lacus. Aliquam ac volutpat enim, vitae vestibulum nisi.</p>\n" +
        "  </div>\n" +
        "</body>\n" +
        "</html>";
        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);

        
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
        activityMainLayout = findViewById(android.R.id.content);
//        RelativeLayout activityMainLayout = findViewById(R.id.activity_main);
//        activityMainLayout.addView(adContainer);

        webView.addJavascriptInterface(new JSInterface(), "AndroidInterface");

/**        webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                moveAdView();
                checkForSpecificDiv();
            }
        });
*/
    }

    private void injectScrollableScript() {
        // Define the JavaScript code to make the page scrollable
        String javascriptCode = "var screenHeight = window.innerHeight;" +
                "document.body.style.height = (5 * screenHeight) + 'px';";

        // Execute the JavaScript code in the WebView
        webView.evaluateJavascript(javascriptCode, null);
    }

    private void moveAdView() {
//        Toast.makeText(MainActivity.this, "moveAdView called \n" + "isSpecificDivVisible : " + isSpecificDivVisible + "\n" + "specificDivX : " + specificDivX + "\n" + "specificDivY : " + specificDivY , Toast.LENGTH_SHORT).show();
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
//        Toast.makeText(MainActivity.this, "checkForSpecificDiv called", Toast.LENGTH_SHORT).show();
        webView.evaluateJavascript(
                "javascript:(function() { " +
                        "var divElement = document.getElementById('adBooster');" +
                        "if (divElement != null && divElement.offsetParent !== null) { " +
                        "   window.AndroidInterface.onSpecificDivVisible(true, divElement.offsetLeft, divElement.offsetTop); " +
                        "} else { " +
                        "   window. AndroidInterface.onSpecificDivVisible(false, 0, 0); " +
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
            Toast.makeText(MainActivity.this, "onSpecificDivVisible called \n" + "wasSpecificDivVisible : " + wasSpecificDivVisible + "\n" + "isSpecificDivVisible : " + isSpecificDivVisible + "\n" + "specificDivX : " + specificDivX + "\n" + "specificDivY : " + specificDivY , Toast.LENGTH_SHORT).show();

            
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

        @JavascriptInterface
        public void onScrollChanged(int scrollY) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "Scroll is now placed at y: " + scrollY , Toast.LENGTH_SHORT).show();
                    moveAdView();
                    checkForSpecificDiv();                    
                }
            });
        }

        @JavascriptInterface
        public void onScroll() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    moveAdView();
                    checkForSpecificDiv();
                }
            });
        }

        private void addAdView() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activityMainLayout.addView(adLayout);
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
                    activityMainLayout.removeView(adLayout);
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
