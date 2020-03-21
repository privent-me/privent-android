package com.example.preventglobal;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Random;

public class Score extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
    }

    @Override
    public void onClick(View v) {
        final EditText t = (EditText) findViewById(R.id.editText);

        final Random r = new Random();
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues();
        animator.setObjectValues(0, r.nextInt(100));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                t.setText(String.valueOf(animation.getAnimatedValue()));
            }
        });
        animator.setEvaluator(new TypeEvaluator<Integer>() {
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return r.nextInt(100);
            }
        });
        animator.setDuration(1000);
        animator.start();
    }
}
