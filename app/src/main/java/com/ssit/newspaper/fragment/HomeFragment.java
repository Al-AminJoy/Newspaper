package com.ssit.newspaper.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.tabs.TabLayout;
import com.ssit.newspaper.R;
import com.ssit.newspaper.adapter.NewsAdapter;
import com.ssit.newspaper.adapter.ViewPagerAdapter;
import com.ssit.newspaper.model.News;
import com.ssit.newspaper.singleton.VolleySingleton;
import com.ssit.newspaper.utlis.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findId(view);

        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPagerAdapter.AddFragment(new AllNewsFragment(), "All");
        viewPagerAdapter.AddFragment(new BengaliFragment(), "Bengali");
        viewPagerAdapter.AddFragment(new OnlineFragment(), "Online");
        viewPagerAdapter.AddFragment(new EnglishFragment(), "English");
        viewPagerAdapter.AddFragment(new InternationalFragment(), "International");
        viewPagerAdapter.AddFragment(new LocalFragment(), "Local");
        viewPagerAdapter.AddFragment(new OthersFragment(), "Others");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void findId(View view) {
        tabLayout = view.findViewById(R.id.tl);
        viewPager = view.findViewById(R.id.viewpager);

    }
}