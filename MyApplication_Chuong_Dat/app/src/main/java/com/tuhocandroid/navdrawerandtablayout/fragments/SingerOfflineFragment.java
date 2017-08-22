package com.tuhocandroid.navdrawerandtablayout.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuhocandroid.navdrawerandtablayout.Database.SongProcess;
import com.tuhocandroid.navdrawerandtablayout.Object.Song;
import com.tuhocandroid.navdrawerandtablayout.Process.RecycleViewAdapter;
import com.tuhocandroid.navdrawerandtablayout.Process.RecycleViewAdapterSinger;
import com.tuhocandroid.navdrawerandtablayout.Process.SongPlaylist;
import com.tuhocandroid.navdrawerandtablayout.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingerOfflineFragment extends Fragment {
    RecyclerView recyclerView;
    private StaggeredGridLayoutManager GridLayoutManager;

    public SingerOfflineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_singer_offline, container, false);
        SongPlaylist songPlaylist=new SongPlaylist();
        SongProcess process=new SongProcess(v.getContext());
        recyclerView=(RecyclerView)v.findViewById(R.id.recycle_singeroffline);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(GridLayoutManager);
        ArrayList<String> list=new ArrayList<String>();
        process=songPlaylist.ListAllSongs(v.getContext());
        list=process.Singer_Name();
        RecycleViewAdapterSinger rcAdapter1=new RecycleViewAdapterSinger(SingerOfflineFragment.this,list  );
        recyclerView.setAdapter(rcAdapter1);
        return v;
    }

}
