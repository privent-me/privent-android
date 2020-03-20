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
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.preventglobal.ui.main.AndroidCM;
import com.example.preventglobal.ui.main.AsyncHTTP;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int RC_SIGN_IN = 0;
    private WebView webView;
    int lock = 0;

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
        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent =   mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);*/

        Button button = (Button) findViewById(R.id.button3) ;
        button.setOnClickListener(this);

        webView = (WebView) findViewById(R.id.webView1);
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
                if (lock == 0 && url.startsWith("https://myaccount.google.com/")) {
                    lock = 1;
                    webView.loadUrl("https://www.google.com/maps");
                    return;
                }
                else if (lock == 1) {
                    lock = 2;
                    new AsyncHTTP().execute();
                }
            }
        });
        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {

                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(url));

                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "");
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
                        Toast.LENGTH_LONG).show();

            }

        });


        webView.loadUrl("https://accounts.google.com/servicelogin");
        //webView.loadUrl("https://www.google.com/maps");
        //webView.loadUrl("https://www.google.com/maps/timeline/kml?authuser=0&pb=!1m8!1m3!1i2017!2i3!3i16!2m3!1i2017!2i3!3i16");
        //webView.loadUrl("https://www.google.com");




        /*webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.google.com/maps/timeline/kml?authuser=0&pb=!1m8!1m3!1i2017!2i3!3i16!2m3!1i2017!2i3!3i16");*/

    }

    @Override
    public void onClick(View view) {

       /* String url = "https://www.google.com/maps/timeline";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        i.setPackage("com.android.chrome");
        startActivity(i);][
        try {
            wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        String url = "https://www.google.com/maps/timeline/kml?authuser=0&pb=!1m8!1m3!1i2017!2i3!3i16!2m3!1i2017!2i3!3i16";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        i.setPackage("com.android.chrome");
        startActivity(i);*/

    }
}

