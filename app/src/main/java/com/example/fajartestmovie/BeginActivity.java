package com.example.fajartestmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class BeginActivity extends AppCompatActivity {
    private ImageView splashImage;
    private static int splashTimeOut=5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
        splashImage=findViewById(R.id.animateLogo);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.splash);
        splashImage.startAnimation(anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(BeginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, splashTimeOut);
    }
}
