package net.rondrae.giggity;
/**
 * Activity for creating WebView and displaying embedded video
 *
 *
 *
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ImagePage extends AppCompatActivity {
TextView textView;
WebView webView;
FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_page);
        textView=findViewById(R.id.textView);
        frameLayout=findViewById(R.id.container);
        String url=getIntent().getStringExtra("url");
        webView=findViewById(R.id.webView);
       setUpWeb(url);
    }

    /**
     * Method setUpWeb settings and input for webview
     * Accepts a string variable with the video ID for video
     * @param url
     */
    private void setUpWeb(String url){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient(){
            FrameLayout.LayoutParams matchParentLayout = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ViewGroup content;
            ViewGroup parent;
            View customView;
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {

                super.onShowCustomView(view, callback);
                customView=view;
customView.setLayoutParams(matchParentLayout);
frameLayout.addView(customView);


            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
                parent.removeView(customView);
                customView=null;
            }
        });
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        //webView.getSettings().setPluginsEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return false;
            }
        });
        //url is actually the video ID for the video. This line attaches it to the standard embed url
        String newUrl="https://www.pornhub.com/embed/"+url;
        //This line creates a web page with basic html and embeds the video in it.
        String iframeString= "<html>\n" +
                "<head>\n" +


                "<style>\n" +
                ".video-container { \n" +
                "position: relative; \n" +
                "padding-bottom: 56.25%; \n" +
                "padding-top: 35px; \n" +
                "height: 0; \n" +
                "overflow: hidden; \n" +
                "}\n" +
                ".video-container iframe { \n" +
                "position: absolute; \n" +
                "top:0; \n" +
                "left: 0; \n" +
                "width: 100%; \n" +
                "height: 100%; \n" +
                "}\n" +
                "body {background-color: #708090;}\n" +
                "h1   {color: white;}\n" +
                "p    {color: red;}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Giggity Video</h1>\n" +

                "<div class=\"video-container\">\n" +
                "    <iframe width=\"840\" height=\"585\" src=\""+newUrl+"\"\"frameborder=\"0\" \\\"allowfullscreen\">" +
                "    </iframe>\n" +

                "</div>\n" +
                "</body>\n" +
                "</html>";
        String playVideo= "<html><body bgcolor=\"#708090\"><center><h1><color =\"#ffffff\">Giggity Video</color></h1> <br> <iframe width=\"840\" height=\"585\" src=\""+newUrl+"\"frameborder=\"0\" \"allowfullscreen\"\"scrolling=\"no\"></center></body></html>";
/*
String iframeString="<style>\n" +
        ".video-container { \n" +
        "position: relative; \n" +
        "padding-bottom: 56.25%; \n" +
        "padding-top: 35px; \n" +
        "height: 0; \n" +
        "overflow: hidden; \n" +
        "}\n" +
        ".video-container iframe { \n" +
        "position: absolute; \n" +
        "top:0; \n" +
        "left: 0; \n" +
        "width: 100%; \n" +
        "height: 100%; \n" +
        "}\n" +
        "</style>\n" +
        "<div class=\"video-container\">\n" +
        "    <iframe width=\"840\" height=\"585\" src=\""+newUrl+"\"\"frameborder=\"0\" \\\"allowfullscreen\">" +
        "    </iframe>\n" +
        "</div>";
*/
        //webView.loadUrl(url);
        webView.loadData(iframeString, "text/html", "utf-8");
    }



}
