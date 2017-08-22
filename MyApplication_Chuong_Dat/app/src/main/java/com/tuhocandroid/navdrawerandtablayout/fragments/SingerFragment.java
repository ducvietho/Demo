package com.tuhocandroid.navdrawerandtablayout.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tuhocandroid.navdrawerandtablayout.Activity.HotMusicActivity;
import com.tuhocandroid.navdrawerandtablayout.Activity.SingerActivity;
import com.tuhocandroid.navdrawerandtablayout.Database.SongProcess;
import com.tuhocandroid.navdrawerandtablayout.Object.Singer;
import com.tuhocandroid.navdrawerandtablayout.Object.Type;
import com.tuhocandroid.navdrawerandtablayout.Process.RecycleViewAdapterMusicType;
import com.tuhocandroid.navdrawerandtablayout.Process.RecyclerViewAdapterSinger;
import com.tuhocandroid.navdrawerandtablayout.Process.SongPlaylist;
import com.tuhocandroid.navdrawerandtablayout.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Chuong on 3/29/16.
 */
public class SingerFragment extends Fragment {

    private List<Singer> singerList = new ArrayList<Singer>();
    private RecyclerViewAdapterSinger adapterSinger;
    private RecyclerView recyclerView;
    private GridLayoutManager GridLayout;

    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_singer, container, false);
        progressDialog = new ProgressDialog(v.getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Wait a second");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.show();
        progressDialog.onStart();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycle_singer);
        adapterSinger = new RecyclerViewAdapterSinger(getActivity(), singerList);
        GridLayout = new GridLayoutManager(getActivity(), 3);

        recyclerView.setLayoutManager(GridLayout);
        recyclerView.setAdapter(adapterSinger);

        recyclerView.setHasFixedSize(true);

        adapterSinger.setOnClickItemListener(new RecyclerViewAdapterSinger.OnClickItemListener() {
            @Override
            public void OnItemClick(int position) {
                Intent intentType = new Intent(getActivity(), SingerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Singer", singerList.get(position));
                intentType.putExtra("Singer", bundle);
                startActivity(intentType);
            }
        });

        databaseReference.child("Singer").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                progressDialog.dismiss();
                Singer singer = dataSnapshot.getValue(Singer.class);
                singerList.add(singer);
                Collections.sort(singerList, new Comparator<Singer>() {
                    @Override
                    public int compare(Singer t0, Singer t1) {

                        return (t0.getSingerName().charAt(0)- t1.getSingerName().charAt(0));
                    }
                });

                adapterSinger.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return v;
    }


}
