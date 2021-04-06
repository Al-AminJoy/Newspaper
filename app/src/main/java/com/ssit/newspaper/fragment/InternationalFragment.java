package com.ssit.newspaper.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.ssit.newspaper.R;
import com.ssit.newspaper.adapter.NewsAdapter;
import com.ssit.newspaper.communication.FragmentCommunication;
import com.ssit.newspaper.model.News;
import com.ssit.newspaper.singleton.VolleySingleton;
import com.ssit.newspaper.utlis.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class InternationalFragment extends Fragment {
    private List<News> newsList;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private FrameLayout frameLayout;
    private InterstitialAd mInterstitialAd;
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
        });        return inflater.inflate(R.layout.fragment_international, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initId(view);
        initRecyclerView(view);
        loadData();
    }
    private void initId(View view) {
        frameLayout=view.findViewById(R.id.frame_international);
    }
    private void loadData() {
        newsList=Common.internationalList;
        adapter=new NewsAdapter(getContext(),newsList,communication);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    FragmentCommunication communication=new FragmentCommunication() {
        @Override
        public void respond(String url) {
            if (mInterstitialAd != null) {
                mInterstitialAd.show(getActivity());
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
            NewsDetailsFragment fragment=new NewsDetailsFragment();
            Bundle bundle=new Bundle();
            bundle.putString("URL",url);
            fragment.setArguments(bundle);
            FragmentManager manager=getFragmentManager();
            FragmentTransaction transaction=manager.beginTransaction();
            transaction.replace(R.id.frame_international,fragment).addToBackStack("my_fragment").commit();
        }

    };


    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }
}