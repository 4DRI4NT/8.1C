package com.example.a81c;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.a81c.data.DatabaseHelper;

import java.util.List;

public class PlayActivity extends AppCompatActivity {

    WebView webView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //link element to id
        webView = findViewById(R.id.webView);

        //collect url
        Intent intent = getIntent();
        String videoUrl = intent.getStringExtra("url");

        //split url for videoId
        String[] videoId = videoUrl.split("v=");

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:player.playVideo()");
            }
        });

        //add videoId to html
        webView.loadData("<html>" +
                        "<body>" +
                        "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"
                        + videoId[1] + "?enablejsapi=1\" frameborder=\"0\" allowfullscreen>" +
                        "</iframe>" +
                        "</body>" +
                        "</html>",
                "text/html",
                "utf-8");
    }
}