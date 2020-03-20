package com.example.preventglobal.ui.main;

import android.os.AsyncTask;

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

public class AsyncHTTP  extends AsyncTask<URL, Integer, Long> {
    protected Long doInBackground(URL... urls) {
        try {
            String s = AndroidCM.cm.getCookie("https://www.google.com/maps");

            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);

            String[] divS = s.split(";");
            for (int i = 0; i < divS.length; i++) {
                String[] divSi = divS[i].split("=");
                HttpCookie cookie = new HttpCookie(divSi[0], divSi[1]);
                cookie.setDomain("google.com");
                cookie.setPath("/");
                cookie.setVersion(0);
                try {
                    cookieManager.getCookieStore().add(new URI("https://www.google.com/"), cookie);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }

            URL url2 = new URL("https://www.google.com/maps/timeline/kml?authuser=0&pb=!1m8!1m3!1i2017!2i3!3i16!2m3!1i2017!2i3!3i16");
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url2.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    byte[] contents = new byte[1024];

                    int bytesRead = 0;
                    String strFileContents = "";
                    while((bytesRead = in.read(contents)) != -1) {
                        strFileContents += new String(contents, 0, bytesRead);
                    }

                    System.out.print(strFileContents);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return Long.valueOf(0);
    }
}
