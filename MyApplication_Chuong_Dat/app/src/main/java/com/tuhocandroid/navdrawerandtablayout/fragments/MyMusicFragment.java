package com.tuhocandroid.navdrawerandtablayout.fragments;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuhocandroid.navdrawerandtablayout.Database.SongProcess;
import com.tuhocandroid.navdrawerandtablayout.Object.ItemObject;
import com.tuhocandroid.navdrawerandtablayout.Object.Song;
import com.tuhocandroid.navdrawerandtablayout.Process.Convert;
import com.tuhocandroid.navdrawerandtablayout.Process.SampleRecyclerViewAdapter;
import com.tuhocandroid.navdrawerandtablayout.Process.SongPlaylist;
import com.tuhocandroid.navdrawerandtablayout.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by minhpq on 3/29/16.
 */
public class MyMusicFragment extends Fragment {
    private StaggeredGridLayoutManager GridLayoutManager;
    private StaggeredGridLayoutManager GridLayoutManager1;
    Convert convert;
    RecyclerView recyclerView;
    RecyclerView recyclerViewOnline;
    private TextView tv;
    public MyMusicFragment() {
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

        View v=inflater.inflate(R.layout.fragment_my_music, container, false);
        tv=(TextView)v.findViewById(R.id.tv_count);

        recyclerView=(RecyclerView)v.findViewById(R.id.recyclerViewList);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(GridLayoutManager);
        List<ItemObject> list=new ArrayList<>();
        SongPlaylist songPlaylist = new SongPlaylist();
        SongProcess songProcess=songPlaylist.ListAllSongs(v.getContext());
        ArrayList<Song> song =new ArrayList<>();
        song=songProcess.Order_By();
        int sizesong=song.size();
        ArrayList<String> singer=songProcess.Singer_Name();
        int sizesinger=singer.size();
        ArrayList<HashMap<String,String>> album=songProcess.Album_Name();
        int sizealbum=album.size();
        list.add(new ItemObject(convert.getBytesFromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_song)), "  Bài hát",sizesong));
        list.add(new ItemObject(convert.getBytesFromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_album)), "  Album", sizesinger));
        list.add(new ItemObject(convert.getBytesFromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_artist)), "  Nghệ sĩ", sizealbum));
        list.add(new ItemObject(convert.getBytesFromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_playlist)), "  Playlist", 0));
        list.add(new ItemObject(convert.getBytesFromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_dow)), "  Download", 0));
        SampleRecyclerViewAdapter rcAdapter=new SampleRecyclerViewAdapter(MyMusicFragment.this,list  );
        recyclerView.setAdapter(rcAdapter);
        tv.setText(sizesong + " bài hát");
        return v;

    }

}

