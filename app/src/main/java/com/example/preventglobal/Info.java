package com.example.preventglobal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Info extends AppCompatActivity implements View.OnClickListener {
    int round = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    @Override
    public void onClick(View v) {
        if (round == 0) {
            round = 1;
            TextView title = (TextView) findViewById(R.id.textView2);
            title.setText(R.string.privacy);
            TextView text = (TextView) findViewById(R.id.textView3);
            text.setText(R.string.privacy_text);
            ImageView im = (ImageView) findViewById(R.id.imageView11);
            im.setImageResource(R.drawable.ic_group_3);


        } else if (round == 1) {
            round = 2;
            TextView title = (TextView) findViewById(R.id.textView2);
            title.setText(R.string.liability);
            TextView text = (TextView) findViewById(R.id.textView3);
            text.setText(R.string.liability_text);
            Button b = (Button) findViewById(R.id.button3);
            b.setText(R.string.button_i_want_to_help);
            ImageView im = (ImageView) findViewById(R.id.imageView11);
            im.setImageResource(R.drawable.ic_group_4);
        } else {
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
}
