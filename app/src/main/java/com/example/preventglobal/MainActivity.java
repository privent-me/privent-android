package com.example.preventglobal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.View;

import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.preventglobal.ui.main.AsyncHTTP;
import android.os.Handler;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    Handler h = null;
    Class<?> next = Info.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        int check = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (check == PackageManager.PERMISSION_GRANTED) {
            //Do something
        } else {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1024);
        }

        webView = (WebView) findViewById(R.id.webView1);
        webView.setVisibility(View.INVISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient()
        {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.startsWith("https://myaccount.google.com/")) {
                    next = Info.class;
                }
            }
        });
        h = new Handler();
        webView.loadUrl("https://accounts.google.com/servicelogin");
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splash = new Intent(MainActivity.this, next);
                startActivity(splash);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, 3000);


    }
}

