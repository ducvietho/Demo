package com.tuhocandroid.navdrawerandtablayout.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuhocandroid.navdrawerandtablayout.Database.SongProcess;
import com.tuhocandroid.navdrawerandtablayout.Process.RecycleViewAdapterAlbum;
import com.tuhocandroid.navdrawerandtablayout.Process.SongPlaylist;
import com.tuhocandroid.navdrawerandtablayout.R;

import java.util.ArrayList;
import java.util.HashMap;


public class AlbumFragment extends Fragment {

    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager GridLayoutManager;
    public AlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        SongPlaylist songPlaylist=new SongPlaylist();
        SongProcess process=new SongProcess(view.getContext());
        recyclerView=(RecyclerView)view.findViewById(R.id.recycle_album);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(GridLayoutManager);
        ArrayList<HashMap<String,String>> listalbum=new ArrayList<HashMap<String, String>>();
        process=songPlaylist.ListAllSongs(view.getContext());
        listalbum=process.Album_Name();
        RecycleViewAdapterAlbum adapter=new RecycleViewAdapterAlbum(AlbumFragment.this,listalbum);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
