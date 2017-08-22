package com.tuhocandroid.navdrawerandtablayout.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tuhocandroid.navdrawerandtablayout.Database.PlaylistProcess;
import com.tuhocandroid.navdrawerandtablayout.Database.SongProcess;
import com.tuhocandroid.navdrawerandtablayout.Object.Song;
import com.tuhocandroid.navdrawerandtablayout.Process.RecycleViewAdapterPlaylist;
import com.tuhocandroid.navdrawerandtablayout.Process.RecycleViewAdapterSelect;
import com.tuhocandroid.navdrawerandtablayout.Process.SongPlaylist;
import com.tuhocandroid.navdrawerandtablayout.R;

import java.util.ArrayList;


public class Playlist extends Fragment {
    private Button bt_playlist;
    private EditText playlist;
    private Button cancel,add;
    private Button cancel1,add1;
    private RecyclerView recyclerView;
    ArrayList<String>arrayList=new ArrayList<String>();
    PlaylistProcess process;
    private StaggeredGridLayoutManager GridLayoutManager;

    String name;

    public Playlist() {
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

        View v=inflater.inflate(R.layout.fragment_playlist, container, false);
        bt_playlist=(Button)v.findViewById(R.id.bt_playlist);
        recyclerView=(RecyclerView)v.findViewById(R.id.recycle_playlist);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(GridLayoutManager);
        RecycleViewAdapterPlaylist adapter = new RecycleViewAdapterPlaylist(Playlist.this, arrayList);
        recyclerView.setAdapter(adapter);
        bt_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.playlist);
                dialog.setTitle("Tên Playlist ");
                dialog.show();
                dialog.setCancelable(false);
                playlist = (EditText) dialog.findViewById(R.id.tv_playlist);
                cancel = (Button) dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                add = (Button) dialog.findViewById(R.id.add);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name = playlist.getText().toString();
                        if (name != null) {
                            arrayList.add(name);
                            final Dialog addplaylist = new Dialog(v.getContext());
                            addplaylist.setTitle("Chọn bài hát");
                            addplaylist.setContentView(R.layout.select_song);
                            RecyclerView recyclerView;
                            StaggeredGridLayoutManager GridLayoutManager;
                            SongPlaylist songPlaylist = new SongPlaylist();
                            SongProcess process = new SongProcess(addplaylist.getContext());
                            recyclerView = (RecyclerView) addplaylist.findViewById(R.id.select);
                            recyclerView.setHasFixedSize(true);
                            GridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(GridLayoutManager);
                            ArrayList<Song> list = new ArrayList<Song>();
                            process = songPlaylist.ListAllSongs(addplaylist.getContext());
                            list = process.List_Song();
                            RecycleViewAdapterSelect rcAdapter1 = new RecycleViewAdapterSelect(addplaylist.getContext(), list);
                            recyclerView.setAdapter(rcAdapter1);
                            addplaylist.setCancelable(false);
                            cancel1 = (Button) addplaylist.findViewById(R.id.bt_cancel);
                            add1 = (Button) addplaylist.findViewById(R.id.bt_add);
                            cancel1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    addplaylist.cancel();
                                }
                            });
                            final ArrayList<Song> finalList = list;
                            add1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ArrayList<Song> songs = new ArrayList<Song>();
                                    for (int i = 0; i < finalList.size(); i++) {
                                        if (finalList.get(i).isSelected() == true) {
                                            songs.add(finalList.get(i));
                                        }
                                    }
                                    addplaylist.cancel();
                                }
                            });
                            addplaylist.show();
                            dialog.cancel();

                        } else {

                        }
                    }
                });

            }
        });

        return v;
    }

}
