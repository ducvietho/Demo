package com.tuhocandroid.navdrawerandtablayout.Activity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.telecom.ConnectionService;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tuhocandroid.navdrawerandtablayout.Object.Song;
import com.tuhocandroid.navdrawerandtablayout.Process.RecycleViewAdapterSongOnline;

import com.tuhocandroid.navdrawerandtablayout.R;
import com.tuhocandroid.navdrawerandtablayout.Service.SongService;
import com.tuhocandroid.navdrawerandtablayout.fragments.Playlist;

import java.util.ArrayList;
import java.util.List;


public class HotMusicActivity extends AppCompatActivity {

    private List<Song> songList;
    private RecycleViewAdapterSongOnline adapterSongOnline;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private String TypeName;
    private Toolbar mToolbar;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private PopupMenu popupMenu;
    private boolean songBound = false;
    private static Intent intentPlay;

    private static SongService songService;
    private ServiceConnection songConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SongService.SongBinder binder = (SongService.SongBinder) service;

            songService = binder.getService();
            songBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            songBound = false;
        }
    };

    protected void onStart() {
        super.onStart();
        if (intentPlay == null) {
            intentPlay = new Intent(this, SongService.class);
            bindService(intentPlay, songConnection, Context.BIND_AUTO_CREATE);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_music);
        setUpToolbar();
        progressDialog = new ProgressDialog(HotMusicActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Wait a second");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.show();
        progressDialog.onStart();


        final Bundle bundle = getIntent().getBundleExtra("TypeName");
        TypeName = bundle.getString("TypeName");
        setUpToolbar();
        songList = new ArrayList<Song>();
        adapterSongOnline = new RecycleViewAdapterSongOnline(HotMusicActivity.this, songList);
        linearLayoutManager = new LinearLayoutManager(HotMusicActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerSongOnline);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterSongOnline);


        adapterSongOnline.setOnItemClickListener(new RecycleViewAdapterSongOnline.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                //TODO Dat Pham add more code, please.
                songService.setSong(position);
                songService.setList((ArrayList<Song>) songList);
                songService.setStatusOnline();
                songService.playSong();
                Intent intent = new Intent(getApplicationContext(), PlaySong.class);
                startActivity(intent);

            }
        });

        databaseReference.child("Song").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Song song = dataSnapshot.getValue(Song.class);

                System.out.println(dataSnapshot);

                if (song.getType().equalsIgnoreCase(TypeName)) {
                    songList.add(song);
                }
                adapterSongOnline.notifyDataSetChanged();
                progressDialog.dismiss();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                popupMenu = new PopupMenu(HotMusicActivity.this, searchView);
                for (int i = 0; i < songList.size(); i++) {
                    String songName = songList.get(i).getSongName();
                    if (songName.contains(newText)) {
                        popupMenu.getMenu().add(songName);

                    }
                }
                popupMenu.setGravity(100);
                popupMenu.show();
                return true;
            }
        });
        return true;
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(TypeName);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }
}
