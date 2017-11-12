package com.example.ckmj.interactivemap;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EasySplashScreen splashScreen= new EasySplashScreen(SplashActivity.this)
                .withFullScreen()
                .withSplashTimeOut(4000)
                .withTargetActivity(MainDrawer.class)
                .withBackgroundColor(Color.parseColor("#000032"))
                .withFooterText("Team CKMJ 2017")
                .withLogo(R.mipmap.splash)
                .withAfterLogoText("AllViet");

        Typeface tf1= Typeface.createFromAsset(getAssets(), "fonts/AGaramondPro-BoldItalic.otf");
        splashScreen.getAfterLogoTextView().setTextColor(Color.WHITE);
        splashScreen.getAfterLogoTextView().setTextSize(50);
        splashScreen.getAfterLogoTextView().setTypeface(tf1);
        splashScreen.getFooterTextView().setTextColor(Color.WHITE);
        splashScreen.getFooterTextView().setTextSize(18);

        View view= splashScreen.create();
        setContentView(view);
    }
}
