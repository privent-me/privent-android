package com.example.preventglobal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Questionary2 extends AppCompatActivity implements View.OnTouchListener {

    ImageView _view;
    ViewGroup _root;
    private int _xDelta;
    RelativeLayout.LayoutParams saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionary2);

        _root = (ViewGroup)findViewById(R.id.root);

        _view = (ImageView)_root.findViewById(R.id.imageView7);

        _view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int X = (int) event.getX();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                saved = new RelativeLayout.LayoutParams( (RelativeLayout.LayoutParams) v.getLayoutParams());
                _xDelta = X;
                break;
            case MotionEvent.ACTION_UP:
                v.setLayoutParams(saved);
                if (event.getRawX() > 1200) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            Intent splash = new Intent(Questionary2.this, Questionary3.class);
                            startActivity(splash);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();
                        }
                    });
                } else if (event.getRawX() < 400) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            Intent splash = new Intent(Questionary2.this, Questionary3.class);
                            startActivity(splash);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();
                        }
                    });
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                layoutParams.leftMargin += X - _xDelta;
                layoutParams.rightMargin -= X - _xDelta;
                v.setLayoutParams(layoutParams);
                break;
        }
        _root.invalidate();
        return true;
    }
}
