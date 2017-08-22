package com.tuhocandroid.navdrawerandtablayout.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.tuhocandroid.navdrawerandtablayout.Database.SongProcess;
import com.tuhocandroid.navdrawerandtablayout.Object.Song;
import com.tuhocandroid.navdrawerandtablayout.Process.RecycleViewAdapter;
import com.tuhocandroid.navdrawerandtablayout.Process.SongPlaylist;
import com.tuhocandroid.navdrawerandtablayout.R;

import java.util.ArrayList;


public class SongFragment extends Fragment {
    RecyclerView recyclerView;
    private StaggeredGridLayoutManager GridLayoutManager;
    public SongFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_song, container, false);;
        SongPlaylist songPlaylist=new SongPlaylist();
        SongProcess process=new SongProcess(v.getContext());
        recyclerView=(RecyclerView)v.findViewById(R.id.recyclesong);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(GridLayoutManager);
        ArrayList<Song> list=new ArrayList<Song>();
        process=songPlaylist.ListAllSongs(v.getContext());
        list=process.Order_By();
        RecycleViewAdapter rcAdapter1=new RecycleViewAdapter(v.getContext(),list  );
        recyclerView.setAdapter(rcAdapter1);
        Intent intent=new Intent(v.getContext(),MyMusicFragment.class);
        intent.putExtra("Value", list.size());
        getActivity().setResult(100,intent);
        return v;


    }

}
