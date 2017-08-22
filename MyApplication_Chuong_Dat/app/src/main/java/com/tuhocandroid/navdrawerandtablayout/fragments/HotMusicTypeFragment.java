package com.tuhocandroid.navdrawerandtablayout.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tuhocandroid.navdrawerandtablayout.Activity.HotMusicActivity;
import com.tuhocandroid.navdrawerandtablayout.Object.Type;
import com.tuhocandroid.navdrawerandtablayout.Process.RecycleViewAdapterMusicType;
import com.tuhocandroid.navdrawerandtablayout.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotMusicTypeFragment extends Fragment {

    List<Type> typeList = new ArrayList<Type>();
    RecycleViewAdapterMusicType adapterMusicType;
    RecyclerView recyclerView;
    GridLayoutManager GridLayout;

    DatabaseReference databaseReference;


    public HotMusicTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerMusicType);
        adapterMusicType = new RecycleViewAdapterMusicType(getActivity(), typeList);
        GridLayout = new GridLayoutManager(getActivity(), 3);

        recyclerView.setLayoutManager(GridLayout);
        recyclerView.setAdapter(adapterMusicType);

        recyclerView.setHasFixedSize(true);

        adapterMusicType.setOnClickItemListener(new RecycleViewAdapterMusicType.OnClickItemListener() {
            @Override
            public void OnItemClick(int position) {
                Intent intentType = new Intent(getActivity(), HotMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TypeName", typeList.get(position).getTypeName());
                intentType.putExtra("TypeName", bundle);
                startActivity(intentType);
            }
        });

        databaseReference.child("Type").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Type type = dataSnapshot.getValue(Type.class);
                typeList.add(type);
                Collections.sort(typeList, new Comparator<Type>() {
                    @Override
                    public int compare(Type t0, Type t1) {

                        return (t0.getTypeName().charAt(0)- t1.getTypeName().charAt(0));
                    }
                });

                adapterMusicType.notifyDataSetChanged();
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_hot_music_type, container, false);
    }

}
