package com.tuhocandroid.navdrawerandtablayout.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;

import com.tuhocandroid.navdrawerandtablayout.Database.SongProcess;
import com.tuhocandroid.navdrawerandtablayout.Object.Song;
import com.tuhocandroid.navdrawerandtablayout.Process.RecycleViewAdapter;
import com.tuhocandroid.navdrawerandtablayout.Process.SongPlaylist;
import com.tuhocandroid.navdrawerandtablayout.R;

import java.util.ArrayList;

public class PlaylistSong extends AppCompatActivity {
    private Toolbar mToolbar;
    int chosenLevel;
    private String title;
    RecyclerView recyclerView;
    private StaggeredGridLayoutManager GridLayoutManager;
    ArrayList<Song> song = new ArrayList<>();
    ArrayList<Song> songlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_playlist);
        recyclerView = (RecyclerView)findViewById(R.id.playlist);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager=new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(GridLayoutManager);
        Intent i = getIntent();
        title = i.getStringExtra("Value");
        SongPlaylist songPlaylist = new SongPlaylist();
        SongProcess songProcess = songPlaylist.ListAllSongs(PlaylistSong.this);
        songlist = songProcess.List_Song();
        for(int k=0;k<songlist.size();k++){
            if(songlist.get(k).isSelected()==true){
                song.add(songlist.get(k));
            }
        }
        RecycleViewAdapter rcAdapter1 = new RecycleViewAdapter(PlaylistSong.this,songlist );
        recyclerView.setAdapter(rcAdapter1);
        setUpToolbar();

    }
    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.tv_vol:{
                final AudioManager audioManager;
                audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,audioManager.getStreamVolume(AudioManager.STREAM_MUSIC),audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
                return true;

            }
            case R.id.id_hen:{
                final Dialog dialog=new Dialog(this);
                dialog.setContentView(R.layout.timer);
                SeekBar timer=(SeekBar)dialog.findViewById(R.id.procbar_timer);
                timer.setMax(120);
                dialog.setTitle("Tắt nhạc sau: " + 0 + " phút");
                timer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        dialog.setTitle("Tắt nhạc sau: " + progress + " phút");
                        chosenLevel=progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                timer.setProgress(chosenLevel);
                dialog.dismiss();
                dialog.show();
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
