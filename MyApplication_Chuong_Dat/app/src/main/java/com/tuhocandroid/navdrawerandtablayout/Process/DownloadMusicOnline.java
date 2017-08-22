package com.tuhocandroid.navdrawerandtablayout.Process;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.tuhocandroid.navdrawerandtablayout.Database.SongProcess;
import com.tuhocandroid.navdrawerandtablayout.Object.Song;
import com.tuhocandroid.navdrawerandtablayout.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Chuong Phung on 11/22/2016.
 */

public class DownloadMusicOnline {

    private Song song;
    private Context context;
    private long enqueue;
    private DownloadManager downloadManager;

    public DownloadMusicOnline (Song song, Context context)  {
        this.song = song;
        this.context = context;
    }

    public void DowloadSong () {
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(song.getUrlSong()));
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, song.getSongName()+".mp3");

        request.setTitle(song.getSongName()).setDescription(song.getAuthor());
        enqueue = downloadManager.enqueue(request);
        SongProcess songProcess = new SongProcess(context);
        File downloadDir = new File("/Device storage/Android/data/com.tuhocandroid.navdrawerandtablayout/files/Download");

        getFilesFromDir(downloadDir, song);


    }

    public void pushNotifycation() {
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    long downloadId = intent.getLongExtra(
                            DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(enqueue);
                    Cursor c = downloadManager.query(query);
                    if (c.moveToFirst()) {
                        int columnIndex = c
                                .getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c
                                .getInt(columnIndex)) {
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                            mBuilder.setSmallIcon(R.drawable.cd_icon);
                            mBuilder.setContentTitle(song.getSongName());
                            mBuilder.setContentText(song.getSinger());
                            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                            manager.notify(0, mBuilder.build());
                        }
                    }
                }
            }
        };
        context.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public Song getFilesFromDir(File filesFromSD, Song songDowload) {

        File listAllFiles[] = filesFromSD.listFiles();
        Song songReturn = new Song();
        if (listAllFiles != null && listAllFiles.length > 0) {
            for (File currentFile : listAllFiles) {
                if (currentFile.isDirectory()) {
                    getFilesFromDir(currentFile, songDowload);
                } else {
                    if (currentFile.getName().endsWith("")) {
                        Log.e("File path", currentFile.getAbsolutePath());
                    }

                    if (currentFile.getName().endsWith("") && currentFile.getAbsolutePath().contains(songDowload.getSongName())) {
                        // File absolute path
                        Log.e("File path", currentFile.getAbsolutePath());
                        songReturn.setUrlSong(currentFile.getAbsolutePath());
                        songReturn.setSongName(songDowload.getSongName());
                        songReturn.setSinger(songDowload.getSinger());

                    }
                }
            }
        }
        return songReturn;
    }
}
