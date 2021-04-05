package com.ssit.newspaper.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.ssit.newspaper.R;
import com.ssit.newspaper.adapter.ViewPagerAdapter;

public class HomeFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findId(view);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.AddFragment(new AllNewsFragment(), "All");
        viewPagerAdapter.AddFragment(new BengaliFragment(), "Bengali");
        viewPagerAdapter.AddFragment(new OnlineFragment(), "Online");
        viewPagerAdapter.AddFragment(new EnglishFragment(), "English");
        viewPagerAdapter.AddFragment(new InternationalFragment(), "International");
        viewPagerAdapter.AddFragment(new LocalFragment(), "Local");
        viewPagerAdapter.AddFragment(new OthersFragment(), "Others");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void findId(View view) {
        tabLayout = view.findViewById(R.id.tl);
        viewPager = view.findViewById(R.id.viewpager);

    }
}