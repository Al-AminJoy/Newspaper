package com.ssit.newspaper.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.volley.RequestQueue;
import com.ssit.newspaper.R;
import com.ssit.newspaper.adapter.NewsAdapter;
import com.ssit.newspaper.communication.FragmentCommunication;
import com.ssit.newspaper.model.News;
import com.ssit.newspaper.singleton.VolleySingleton;
import com.ssit.newspaper.utlis.Common;

import java.util.ArrayList;
import java.util.List;


public class OthersFragment extends Fragment {
    private List<News> newsList;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private FrameLayout frameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_others, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initId(view);
        initRecyclerView(view);
        loadData();
    }
    private void loadData() {
        newsList= Common.onlineList;
        adapter=new NewsAdapter(getContext(),newsList,communication);
        recyclerView.setAdapter(adapter);
    }
    FragmentCommunication communication=new FragmentCommunication() {
        @Override
        public void respond(String url) {
            NewsDetailsFragment fragment=new NewsDetailsFragment();
            Bundle bundle=new Bundle();
            bundle.putString("URL",url);
            fragment.setArguments(bundle);
            FragmentManager manager=getFragmentManager();
            FragmentTransaction transaction=manager.beginTransaction();
            transaction.replace(R.id.frame_others,fragment).addToBackStack("my_fragment").commit();
        }

    };
    private void initId(View view) {
        frameLayout=view.findViewById(R.id.frame_others);
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
    }
}