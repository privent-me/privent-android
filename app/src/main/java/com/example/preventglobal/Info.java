package com.example.preventglobal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.os.Handler;


public class Info extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    @Override
    public void onClick(View v) {
        new Handler().post(new Runnable() {
                @Override
                public void run() {
                    Intent splash = new Intent(Info.this, GetData.class);
                    startActivity(splash);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            });
    }
}
