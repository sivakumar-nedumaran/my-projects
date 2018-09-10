package com.cynosurecreations.mithostels;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class SplashScreen extends Activity
{
    private ImageView logoimg;
    private FrameLayout splash;
    private int dimensionInPixel = 250;
    int dimensionInDp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPixel, getResources().getDisplayMetrics());

        splash =(FrameLayout) findViewById(R.id.splash);
        logoimg = (ImageView) findViewById(R.id.logoimg);
        scheduleRedirect();
    }

    private void scheduleRedirect() {
        // Calls login activity after splash screen timeout
        new Handler().postDelayed(new Runnable() {
            public void run() {
                first();
            }
        }, 2000);
    }

    void first()
    {
        logoimg.setImageResource(R.drawable.logo);
        logoimg.getLayoutParams().height = dimensionInDp;
        logoimg.getLayoutParams().width = dimensionInDp;
        //splash.setBackground(R.drawable.boxes);
        splash.setBackgroundColor(Color.parseColor("#033855"));
        second();
    }
    private void second()
    {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashScreen.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
