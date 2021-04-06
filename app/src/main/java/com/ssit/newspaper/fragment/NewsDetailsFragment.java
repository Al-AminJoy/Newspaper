package com.ssit.newspaper.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.ssit.newspaper.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class NewsDetailsFragment extends Fragment {
    private WebView webView;
    private String url;
    private InterstitialAd mInterstitialAd;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url=getArguments().getString("URL");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(getContext(),"ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
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
        return inflater.inflate(R.layout.fragment_news_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getContext(),url,Toast.LENGTH_SHORT).show();
        initId(view);
        loadURL();
    }
    private void initId(View view) {
        webView =view.findViewById(R.id.wvId);
    }
    private void loadURL() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Document document = Jsoup.connect(url).get();
           /* document.getElementsByClass("cbz-leaderboard-banner").remove();
            document.getElementsByClass("row cbz-div-header").remove();
            document.getElementsByClass("table-responsive").remove();
            document.getElementsByClass("footer-list-menu").remove();
            document.getElementsByClass("ui-footer").remove();
            document.getElementById("bottom-banner").remove();*/
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadDataWithBaseURL(url, document.toString(), "text/html", "utf-8", "");
            webView.setWebViewClient(new MyWebViewClient());
            webView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((keyCode == KeyEvent.KEYCODE_BACK) && (webView.canGoBack())) {
                        webView.goBack();
                        return true;
                    }
                    return false;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            webView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    }
   /* private void showDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.alert_dialog_layout, null);
        TextView tvYes = view.findViewById(R.id.tvDialogYesId);
        TextView tvNo = view.findViewById(R.id.tvDialogNoId);
        TextView tvRate = view.findViewById(R.id.tvDialogRateId);
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view).setCancelable(false).create();
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                alertDialog.cancel();
            }
        });
        tvYes.setOnClickListener(this);
        tvRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateApp();
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();

        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(getActivity());
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.");
                        }
                        Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                        /*AllNewsFragment fragment=new AllNewsFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("URL",url);
                        fragment.setArguments(bundle);
                        FragmentManager manager=getFragmentManager();
                        FragmentTransaction transaction=manager.beginTransaction();
                        transaction.replace(R.id.main_panel,fragment).addToBackStack("my_fragment").commit();*/
                        return true;
                    }
                }
                return false;
            }
        });
    }
}