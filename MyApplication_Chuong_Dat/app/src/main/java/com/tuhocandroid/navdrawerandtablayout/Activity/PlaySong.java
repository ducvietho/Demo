package com.tuhocandroid.navdrawerandtablayout.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tuhocandroid.navdrawerandtablayout.R;
import com.tuhocandroid.navdrawerandtablayout.Service.SongService;

import java.util.TooManyListenersException;
import java.util.concurrent.TimeUnit;

public class PlaySong extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btnPre, btnPlay, btnNext, btn_repeat, btn_shuffle;
    private ImageView iv;

    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();
    private SeekBar seekBar;
    private TextView tx1, tx2, tx3;

    private static Intent playIntent;
    private boolean songBound = false;

    private SongService songService;
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
        if (playIntent == null) {
            playIntent = new Intent(this, SongService.class);
        }
        bindService(playIntent, songConnection, Context.BIND_AUTO_CREATE);
        myHandler.postDelayed(UpdateSongTime, 1000);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        Toolbar toolbar1 = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar1);

        btnPre = (ImageButton) findViewById(R.id.bt_pre);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnNext = (ImageButton) findViewById(R.id.btn_next);
        btn_repeat = (ImageButton) findViewById(R.id.btn_repeat);
        btn_shuffle = (ImageButton) findViewById(R.id.btn_shuffle);

        iv = (ImageView) findViewById(R.id.imageView);
        tx1 = (TextView) findViewById(R.id.textView3);
        tx2 = (TextView) findViewById(R.id.textView4);
        tx3 = (TextView) findViewById(R.id.tvTitle);
        iv.setDrawingCacheEnabled(true);
        Animation animation = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.roate);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(5000);
        iv.startAnimation(animation);

//        setController();
//      onclick button
        btn_repeat.setOnClickListener(this);
        btn_shuffle.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekTo(seekBar.getProgress());
            }
        });


    }

    /**
     * thread update time when playing song
     */
    private Runnable UpdateSongTime = new Runnable() {

        @Override
        public void run() {
            startTime = getCurrentPosition();
            tx1.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))));
            finalTime = getDuration();
            tx2.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime))
            ));
            tx3.setText(getTitleSong());
            if (isPlaying()) {
                btnPlay.setBackgroundResource(R.drawable.ic_player_v4_pause_pressed);
            } else {
                btnPlay.setBackgroundResource(R.drawable.ic_player_v4_play_pressed);

            }
            if (getReapeat()) {
                btn_repeat.setBackgroundResource(R.drawable.ic_player_v4_repeat_one);
            } else {
                btn_repeat.setBackgroundResource(R.drawable.ic_player_v4_repeat_off);
            }
            if (getShuffle()) {
                btn_shuffle.setBackgroundResource(R.drawable.ic_player_v4_shuffle_on);
            } else {
                btn_shuffle.setBackgroundResource(R.drawable.ic_player_v4_shuffle_off);
            }
            seekBar.setMax((int) finalTime);
            seekBar.setProgress((int) startTime);
            myHandler.postDelayed(this, 1000);
        }


    };


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnPlay:
                if (isPlaying()) {
                    pause();
                    btnPlay.setBackgroundResource(R.drawable.ic_player_v4_play_pressed);
                } else {
                    start();
                    btnPlay.setBackgroundResource(R.drawable.ic_player_v4_pause_pressed);
                }
                break;
            case R.id.bt_pre:
                System.out.println("prev");
                playPrev();
                break;


            case R.id.btn_next:
                System.out.println("next");
                playNext();
                break;
            case R.id.btn_repeat:
                if (setRepeat()) {
                    btn_repeat.setBackgroundResource(R.drawable.ic_player_v4_repeat_one);
                } else {
                    btn_repeat.setBackgroundResource(R.drawable.ic_player_v4_repeat_off);
                }
                break;
            case R.id.btn_shuffle:
                if (setShuffle()) {
                    btn_shuffle.setBackgroundResource(R.drawable.ic_player_v4_shuffle_on);
                } else {
                    btn_shuffle.setBackgroundResource(R.drawable.ic_player_v4_shuffle_off);
                }
                break;

        }
    }

    private String getTitleSong() {
        if (songService != null && songBound) {
            return songService.getSongTitle();
        }
        return "Title Song";
    }


    //    play next
    private void playNext() {
        songService.playNext();
    }

    private void playPrev() {
        songService.playPrev();
    }

    private boolean setRepeat() {
        return songService.setRepeat();
    }

    private boolean getReapeat() {
        return songService.getRepeat();
    }

    private boolean getShuffle() {
        return songService.getShuffle();
    }

    private boolean setShuffle() {
        return songService.setShuffle();
    }

    public void start() {
        songService.go();

    }

    public void pause() {
        songService.pausePlayer();

    }


    public int getDuration() {
        if (songService != null && songBound) return songService.getDur();
        return 0;
    }


    public int getCurrentPosition() {
        if (songService != null)
            return songService.getPosn();
        return 0;
    }


    public void seekTo(int pos) {
        songService.seek(pos);

    }


    public boolean isPlaying() {
        if (songService != null && songBound)
            return songService.isPng();
        return false;
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                break;

        }
        return super.onOptionsItemSelected(item);

    }




    protected void onDestroy() {
        super.onDestroy();
        unbindService(songConnection);
    }

}



