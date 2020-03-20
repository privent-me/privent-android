package com.example.preventglobal.ui.main;

import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AsyncHTTP  extends AsyncTask<URL, Integer, Long> {
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected Long doInBackground(URL... urls) {
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
        String id = "";
        try {
            URL registerUrl = new URL("http://prevent.mash.stronazen.pl/api/user/register");
            HttpURLConnection registerUrlConnection = (HttpURLConnection) registerUrl.openConnection();
            try {
                registerUrlConnection.setDoOutput(true);

                    OutputStream registerOut = new BufferedOutputStream(registerUrlConnection.getOutputStream());
                registerOut.write("{\"recentTest\":\"positive\"}".getBytes());
                registerOut.flush();
                registerOut.close();

                int responseCode = registerUrlConnection.getResponseCode();

                System.out.print(responseCode);
                InputStream content = (InputStream) registerUrlConnection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                    id = line.substring(7, 20);
                }

            } finally {
                registerUrlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);

            for (int i = 0; i < 14; i++) {
                if (day != 0) {
                    day -= 1;
                } else {
                    month = 1;
                    day = 29;
                }

                URL url2 = null;
                try {
                    url2 = new URL("https://www.google.com/maps/timeline/kml?authuser=0&pb=!1m8!1m3!1i2020!2i" + Integer.toString(month) + "!3i" + Integer.toString(day) + "!2m3!1i2020!2i" + Integer.toString(month) + "!3i" + Integer.toString(day));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) url2.openConnection();
                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                        URL serverUrl = new URL("http://prevent.mash.stronazen.pl/api/kml/upload");
                        HttpURLConnection serverUrlConnection = (HttpURLConnection) serverUrl.openConnection();
                        try {
                            serverUrlConnection.setDoOutput(true);
                            serverUrlConnection.setRequestProperty("X-UserId", id);

                            OutputStream out = new BufferedOutputStream(serverUrlConnection.getOutputStream());

                            File tmp = File.createTempFile("tempUserLocationHistoryDataPipeline", null);
                            Files.copy(in, tmp.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            Files.copy(tmp.toPath(), out);

                            out.flush();
                            out.close();

                            int responseCode = serverUrlConnection.getResponseCode();

                            System.out.print(responseCode);
                            InputStream serverIn = new BufferedInputStream(serverUrlConnection.getInputStream());
                            BufferedReader serverIn2 = new BufferedReader(new InputStreamReader(serverIn));
                            String line;
                            while ((line = serverIn2.readLine()) != null) {
                                System.out.println(line);
                            }
                        } finally {
                            serverUrlConnection.disconnect();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return Long.valueOf(0);
    }
}
