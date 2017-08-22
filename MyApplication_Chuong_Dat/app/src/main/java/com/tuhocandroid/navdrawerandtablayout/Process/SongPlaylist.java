package com.tuhocandroid.navdrawerandtablayout.Process;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;

import com.tuhocandroid.navdrawerandtablayout.Database.SongProcess;
import com.tuhocandroid.navdrawerandtablayout.Object.Song;
import com.tuhocandroid.navdrawerandtablayout.Service.SongService;

import java.util.ArrayList;

/**
 * Created by HODUCVIET on 11/7/2016.
 */
public class SongPlaylist extends Application {

    public static final String SHAREPREFERENCE = "MYZING";

    public SongPlaylist(){

    }

    private SharedPreferences sharedPreferencesCheckFirst;
    private SongService songService;
    private boolean songBound = false;
    ArrayList<Song> listSong = new ArrayList<>();
    private boolean checkFirst = true;
    private SongProcess songProcess;
    public  SongProcess ListAllSongs(Context context) {

        sharedPreferencesCheckFirst = context.getSharedPreferences(SHAREPREFERENCE, MODE_PRIVATE);

        checkFirst = sharedPreferencesCheckFirst.getBoolean("checkfirst", true);
        if (checkFirst) {
            songProcess =new SongProcess(context);
            String[] STAR =  {"*"};
            String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
            Uri allsongsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            if (isSdPresent()) {
                Cursor cursor= context.getApplicationContext().getContentResolver().query(allsongsuri, STAR,selection, null, null);
                if (cursor != null) {
                    int id = 0;
                    if (cursor.moveToFirst()) {
                        do {
                            String songname = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                            System.out.println("songname: " + songname);

                            int song_id = id;
                            System.out.println("song_id: " + song_id);
                            String fullpath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                            System.out.println("fullpath: " + fullpath);
                            String author=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
                            String singer=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                            System.out.println("song_id: " +singer);
                            String type=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE));
                            System.out.println("song_type " +type);
                            String albumname = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                            System.out.println("albumname: " + albumname);
                            songProcess.add(new Song(id, songname, singer, fullpath, albumname, false, 0, author, type, "", ""));
                            id++;

                        } while (cursor.moveToNext());
                    }
                    SharedPreferences.Editor editorCheckFirst = sharedPreferencesCheckFirst.edit();
                    editorCheckFirst.putBoolean("checkfirst", false);
                    editorCheckFirst.commit();
                    cursor.close();
               }
            }
        } else {
            songProcess = new SongProcess(context);
        }

        return songProcess;

    }



    public static boolean isSdPresent() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
    private ServiceConnection songConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SongService.SongBinder binder = (SongService.SongBinder) service;
            songService = binder.getService();
            songService.setList(listSong);
            songBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            songBound = false;
        }
    };

}
