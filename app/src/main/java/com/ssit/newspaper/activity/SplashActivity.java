package com.ssit.newspaper.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ssit.newspaper.R;
import com.ssit.newspaper.presenter.BasePresenter;
import com.ssit.newspaper.presenterImplementation.SplashImplementation;


public class SplashActivity extends AppCompatActivity implements BasePresenter.view {
    private SplashImplementation implementation;
    private int SLEEP_TIME = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        implementation = new SplashImplementation(this);
        implementation.loadSplash(SLEEP_TIME);
    }

    @Override
    public void onSuccess(String message) {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onFailure(String message) {
       /* startActivity(new Intent(SplashActivity.this,MainActivity.class));
        finish();*/
    }
}