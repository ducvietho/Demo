package com.tuhocandroid.navdrawerandtablayout.Service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.tuhocandroid.navdrawerandtablayout.Activity.PlaySong;
import com.tuhocandroid.navdrawerandtablayout.Object.Song;
import com.tuhocandroid.navdrawerandtablayout.R;

import java.io.FileFilter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by HODUCVIET on 10/26/2016.
 */
public class SongService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private MediaPlayer player;
    private ArrayList<Song> songs = new ArrayList<>();
    //    current position
    private int songPos;
    private final IBinder songBind = new SongBinder();
    private boolean repeatSong = false;

    private String songTitle = "";
    private boolean shuffle = false;
    private Random rand;
    private NotificationReceiver notificationRecevier;
    Notification.Builder builderPlay, builderPause, builder = null;
    NotificationManager nm = null;
    Intent intent = null;
    PendingIntent pendInt = null;
    boolean statusNotification = true;
    private boolean statusOnline = false;


    private static SongService sSongService;

    public static SongService getInstance() {
        if (sSongService == null) {
            sSongService = new SongService();
        }
        return sSongService;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return songBind;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Override
    public boolean onUnbind(Intent intent) {

        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (repeatSong) {
            playSong();

        } else {
            if (player.getCurrentPosition() > 0) {
                mp.reset();
                playNext();
            }
        }

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onPrepared(MediaPlayer mp) {
        mp.start();

        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        intent = new Intent(getApplicationContext(), PlaySong.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendInt = PendingIntent.getActivities(getApplicationContext(), 0, new Intent[]{intent}, PendingIntent.FLAG_UPDATE_CURRENT);
//
        builderPlay = new Notification.Builder(this);
        builderPause = new Notification.Builder(this);
        builderPlay.setContentIntent(pendInt).setContentTitle("Zing").setContentText(songTitle).
                setSmallIcon(R.drawable.cd_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.cd_icon))
                .setOngoing(true);
        builderPause.setContentIntent(pendInt).setContentTitle("Zing").setContentText(songTitle).
                setSmallIcon(R.drawable.cd_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.cd_icon))
                .setOngoing(true);

//        prev intent
        Intent yesReceive = new Intent();
        yesReceive.setAction(AppConstant.BACK_ACTION);
        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(this, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        builderPlay.addAction(R.drawable.back_icon, "back", pendingIntentYes);
        builderPause.addAction(R.drawable.back_icon, "back", pendingIntentYes);

//        play intent
        Intent maybeReceive = new Intent();
        maybeReceive.setAction(AppConstant.PLAY_ACTION);
        PendingIntent pendingIntentMaybe = PendingIntent.getBroadcast(this, 12345, maybeReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        builderPlay.addAction(R.drawable.pause_icon, "play", pendingIntentMaybe);
        builderPause.addAction(R.drawable.play_icon, "pause", pendingIntentMaybe);

        Intent stopReceive = new Intent();
        stopReceive.setAction(AppConstant.STOPNOTIFICATION);
        PendingIntent pendingIntentStop = PendingIntent.getBroadcast(this, 12345, stopReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        builderPlay.addAction(R.drawable.destroy, "stop", pendingIntentStop);
        builderPause.addAction(R.drawable.destroy, "stop", pendingIntentStop);
        builder = builderPlay;
        statusNotification = true;
        nm.notify(0, builder.getNotification());

    }

    public void onCreate() {

        super.onCreate();
        songPos = 0;
        rand = new Random();
        player = new MediaPlayer();
        initMusicPlayer();
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppConstant.BACK_ACTION);
        filter.addAction(AppConstant.PLAY_ACTION);
        filter.addAction(AppConstant.STOPNOTIFICATION);
        notificationRecevier = new NotificationReceiver();
        registerReceiver(notificationRecevier, filter);


    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        stopForeground(false);
        nm.cancel(0);
        statusNotification = false;
    }

    public void initMusicPlayer() {
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    public void setStatusOnline() {

        if (!statusOnline) {
            statusOnline = true;
        }
    }

    public void setStatusOffline() {
        if (statusOnline) {
            statusOnline = false;
        }
    }

    public void setList(ArrayList<Song> theSongs) {

        if (!songs.equals(theSongs)) {
//            statusOnline = true;
            songs = theSongs;
        }
    }


    public class SongBinder extends Binder {
        public SongService getService() {
            return SongService.this;
        }
    }


    public void playSong() {
        player.reset();
        Song playSong = songs.get(songPos);

        songTitle = playSong.getSongName();
        if (statusOnline) {
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                player.setDataSource(playSong.getUrlSong());
            } catch (Exception e) {
                Log.e("MUSIC SERVICE", "Error setting data source", e);
            }
            try {
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            Uri uri = Uri.parse(playSong.getUrlSong());
            try {
                System.out.println(playSong.getUrlSong());
                System.out.println(getApplicationContext());
                player.setDataSource(getApplicationContext(), uri);
            } catch (Exception e) {
                Log.e("MUSIC SERVICE", "Error setting data source", e);
            }
            player.prepareAsync();
        }


    }


    public void setSong(int songIndex) {
        songPos = songIndex;
    }

    public int getPosn() {
        return player.getCurrentPosition();
    }

    public int getDur() {
        System.out.println("Duration: "+player.getDuration());
        return player.getDuration();
    }

    public boolean isPng() {
        return player.isPlaying();
    }

    public void pausePlayer() {
        nm.cancel(0);

        statusNotification = false;
        player.pause();
    }

    public void seek(int posn) {
        player.seekTo(posn);

    }

    public String getSongTitle() {
        return songTitle;
    }

    public void go() {

        if (!statusNotification) {
            nm.notify(0, builder.getNotification());
            statusNotification = true;
        }
        player.start();
    }

    public void playPrev() {
        if (!statusNotification) {
            nm.notify(0, builder.getNotification());
            statusNotification = true;
        }
        songPos--;
        System.out.println("songPos play prev:" + songPos);
        if (songPos < 0)
            songPos = songs.size() - 1;
        playSong();

    }

    //    skip to next
    public void playNext() {
        if (!statusNotification) {
            nm.notify(0, builder.getNotification());
            statusNotification = true;
        }
        if (shuffle) {
            int newSong = songPos;
            while (newSong == songPos) {
                newSong = rand.nextInt(songs.size());
            }
            songPos = newSong;

        } else {
            songPos++;
            if (songPos >= songs.size())
                songPos = 0;
        }
        System.out.println("song: " + songPos);
        playSong();

    }


    public boolean setShuffle() {
        if (shuffle) {
            shuffle = false;
            return false;
        } else {
            shuffle = true;
            return true;
        }

    }

    public boolean getShuffle() {
        return shuffle;
    }

    public boolean getRepeat() {
        return repeatSong;
    }

    public boolean setRepeat() {
        if (!repeatSong) {
            repeatSong = true;
            return true;
        } else {
            repeatSong = false;
            return false;
        }
    }

    public class NotificationReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            System.out.println(action);
            if (AppConstant.BACK_ACTION.equals(action)) {
                playPrev();

            } else if (AppConstant.PLAY_ACTION.equals(action)) {
                if (isPng()) {
                    player.pause();
                    builder = builderPause;
                    nm.notify(0, builder.getNotification());


                } else {
                    go();
                    builder = builderPlay;
                    nm.notify(0, builder.getNotification());

                }

            } else if (AppConstant.STOPNOTIFICATION.equals(action)) {
                nm.cancel(0);
                if (isPng() && statusNotification) {
                    pausePlayer();
                    statusNotification = false;

                }

            }

        }
    }


}
