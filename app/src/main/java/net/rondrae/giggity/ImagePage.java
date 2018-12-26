package net.rondrae.giggity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
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
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
    }
}
