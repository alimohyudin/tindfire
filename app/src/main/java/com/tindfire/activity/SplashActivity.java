package com.tindfire.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tindfire.R;
import com.tindfire.preference.PreferenceManager;

/**
 * Created by vcareall on 27/12/16.
 */
public class SplashActivity extends AppCompatActivity {
    private static final String TAG=SplashActivity.class.getSimpleName();
    private Context context;
    private int DELAY;
    private PreferenceManager mPref;
    private boolean isFirstTime ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context=this;
        mPref=PreferenceManager.getInstatnce(context);
        DELAY=3000;
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isFirstTime = mPref.getFirstTime();
                if (isFirstTime) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, ActivityMain.class));
                    finish();
                }
            }
        },DELAY);
    }
}
