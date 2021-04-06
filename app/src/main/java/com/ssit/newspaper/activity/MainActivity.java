package com.ssit.newspaper.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.ssit.newspaper.R;
import com.ssit.newspaper.fragment.AllNewsFragment;
import com.ssit.newspaper.fragment.HomeFragment;
import com.ssit.newspaper.fragment.NewsDetailsFragment;

public class MainActivity extends AppCompatActivity {
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adView=findViewById(R.id.adView);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFragment();
    }
    private void loadFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_panel,new HomeFragment()).commit();
       // navigationView.getMenu().getItem(0).setChecked(true);
    }
    @Override
    public void onBackPressed() {
            Fragment frag = getSupportFragmentManager().findFragmentById(R.id.main_panel);
            if (frag instanceof HomeFragment){
                finish();
            }

    }
}