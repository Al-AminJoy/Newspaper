package com.ssit.newspaper.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.navigation.NavigationView;

import com.ssit.newspaper.R;
import com.ssit.newspaper.fragment.AllNewsFragment;
import com.ssit.newspaper.fragment.HomeFragment;
import com.ssit.newspaper.fragment.NewsDetailsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private AdView adView;
    private InterstitialAd mInterstitialAd;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adView = findViewById(R.id.adView);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i("TAG", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i("TAG", loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        findId();
        toolbar();
        loadFragment();

    }


    private void toolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        mToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        //View headerView = navigationView.getHeaderView(0);
        //TextView title = headerView.findViewById(R.id.textView);
        // ImageView ivUserImage = headerView.findViewById(R.id.imageView);
        //  title.setText(CommonTask.getDataFromSharedPreference(getApplicationContext(),CommonTask.USER_NAME));


    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_Rate: {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://market://details?id=" + getPackageName()));
                startActivity(i);
                break;
            }
            case R.id.nav_Share: {
                ShareCompat.IntentBuilder.from(this)
                        .setType("text/plain")
                        .setChooserTitle("Share App")
                        .setText("http://play.google.com/store/apps/details?id=" + this.getPackageName())
                        .startChooser();
                break;
            }
        }

        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findId() {
        //  searchView = findViewById(R.id.searchView);
        toolbar = findViewById(R.id.main_toolbar);
        navigationView = findViewById(R.id.homeNav);
        drawerLayout = findViewById(R.id.drawer_layout);

    }

    private void loadFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_panel, new HomeFragment()).commit();
    }

    private void showDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.alert_dialog_layout, null);
        TextView tvYes = view.findViewById(R.id.tvDialogYesId);
        TextView tvNo = view.findViewById(R.id.tvDialogNoId);
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view).setCancelable(false).create();
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.main_panel);
        if (frag instanceof HomeFragment) {
            if (mInterstitialAd != null) {
                mInterstitialAd.show(this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
            showDialog();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
       // loadFragment();
    }
}