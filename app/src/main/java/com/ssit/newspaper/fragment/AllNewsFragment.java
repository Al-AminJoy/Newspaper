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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
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

public class AllNewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<News> newsList = new ArrayList<>();
    private List<News> bengaliList = new ArrayList<>();
    private List<News> englishList = new ArrayList<>();
    private List<News> onlineList = new ArrayList<>();
    private List<News> localList = new ArrayList<>();
    private List<News> othersList = new ArrayList<>();
    private List<News> interNationalList = new ArrayList<>();
    private NewsAdapter adapter;
    private RequestQueue requestQueue;
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
        });
        return inflater.inflate(R.layout.fragment_all_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initId(view);
        initRecyclerView(view);
        requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        loadData();

    }

    private void initId(View view) {
    frameLayout=view.findViewById(R.id.frame_all);
    }

    private void loadData() {
        requestQueue.getCache().clear();
        Common.onlineList.clear();
        Common.bengaliList.clear();
        Common.englishList.clear();
        Common.localList.clear();
        Common.internationalList.clear();
        Common.othersList.clear();
        newsList.clear();

        String url = "https://jsonkeeper.com/b/R9HK";
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject receive = response.getJSONObject(i);
                        String id = receive.getString("n_id");
                        String serialNo = receive.getString("sl_no");
                        String newsType = receive.getString("n_type");
                        String englishName = receive.getString("en_name");
                        String banglaName = receive.getString("bn_name");
                        String image = receive.getString("n_image");
                        String url = receive.getString("link");
                        News news = new News(id, serialNo, newsType, englishName, banglaName, image, url);

                         if (newsType.equals("online")){
                            onlineList.add(news);

                        }
                        else if (newsType.equals("Bn_paper")){
                            bengaliList.add(news);
                            Toast.makeText(getContext(),"Adding",Toast.LENGTH_SHORT).show();
                        }
                        else if (newsType.equals("En_paper")){
                            englishList.add(news);
                        }

                        else if (newsType.equals("local")){
                            localList.add(news);
                        }
                        else if (newsType.equals("world_news")){
                            interNationalList.add(news);
                        }
                        else {
                            othersList.add(news);
                        }
                        newsList.add(news);
                    }

                    Common.englishList=englishList;
                    Common.internationalList=interNationalList;
                    Common.onlineList=onlineList;
                    Common.localList=localList;
                    Common.othersList=othersList;
                    Common.bengaliList=bengaliList;
                    adapter = new NewsAdapter(getActivity(), newsList,communication);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(arrayRequest);
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
            transaction.replace(R.id.frame_all,fragment).addToBackStack("my_fragment").commit();
        }

    };


    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }


}