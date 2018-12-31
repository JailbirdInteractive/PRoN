package net.rondrae.giggity;
/**
 * Activity for creating WebView and displaying embedded video
 *
 *
 *
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class ImagePage extends AppCompatActivity {
TextView textView;
WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_page);
        textView=findViewById(R.id.textView);
        String url=getIntent().getStringExtra("url");
        //textView.setText(url);
        webView=findViewById(R.id.webView);
       setUpWeb(url);
        //webView.loadDataWithBaseURL("https://www.redtube.com",playVideo,"utf-8","text/html",null);
    }

    /**
     * Method setUpWeb settings and input for webview
     * Accepts a string variable with the video ID for video
     * @param url
     */
    private void setUpWeb(String url){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(false);
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
        String playVideo= "<html><body><center><h1>Giggity Video</h1> <br> <iframe width=\"440\" height=\"285\" src=\""+newUrl+"\"frameborder=\"0\" \"allowfullscreen\"></center></body></html>";

        webView.loadUrl(url);
        webView.loadData(playVideo, "text/html", "utf-8");
    }
}
