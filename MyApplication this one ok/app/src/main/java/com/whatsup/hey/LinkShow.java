package com.whatsup.hey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class LinkShow extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;
    Intent intent;
    String websitelinkintent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_show);
        webView= (WebView) findViewById(R.id.linkshow_wv);
        WebSettings webSettings = webView.getSettings();
        progressBar= (ProgressBar) findViewById(R.id.linkshow_prog);
        intent=getIntent();

        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                // Page loading started
                // Do something
                progressBar.setVisibility(View.VISIBLE);

            }

            @Override
            public void onPageFinished(WebView view, String url){
                // Page loading finished
                // Toast.makeText(getApplicationContext(),"Page Loaded.",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
        websitelinkintent = (String) intent.getSerializableExtra("websitelink");
        webView.loadUrl(websitelinkintent);

        webView.setDownloadListener(new DownloadListener() {


            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }
}