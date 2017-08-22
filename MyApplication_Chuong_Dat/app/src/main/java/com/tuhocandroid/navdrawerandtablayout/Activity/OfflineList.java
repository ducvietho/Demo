package com.tuhocandroid.navdrawerandtablayout.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;

import com.tuhocandroid.navdrawerandtablayout.Database.SongProcess;
import com.tuhocandroid.navdrawerandtablayout.Object.Song;
import com.tuhocandroid.navdrawerandtablayout.Process.SongPlaylist;
import com.tuhocandroid.navdrawerandtablayout.Process.ViewPagerAdapter;
import com.tuhocandroid.navdrawerandtablayout.R;

import java.util.ArrayList;

public class OfflineList extends AppCompatActivity {
    ViewPager pager;
    TabLayout tabLayout;
    Toolbar toolbar;
    int chosenLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_list);
        pager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        FragmentManager manager = getSupportFragmentManager();
        setUpToolbar();
        ViewPagerAdapter adapter = new ViewPagerAdapter(manager);
        pager.setAdapter(adapter);
        pager.setPageMargin(1);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);

        Intent i=getIntent();
        int position=i.getIntExtra("Value", 0);
        load(position);



    }
    public void load(int postion){
        pager.setCurrentItem(postion,true);
    }
    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_offline);
        toolbar.setTitle("Nhạc Offline");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        SongPlaylist songPlaylist=new SongPlaylist();
        SongProcess process=new SongProcess(OfflineList.this);
        ArrayList<Song> list=new ArrayList<Song>();
        final ArrayList<Song> listfilter=new ArrayList<Song>();
        process=songPlaylist.ListAllSongs(OfflineList.this);
        list=process.Order_By();
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
