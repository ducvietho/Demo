package com.tuhocandroid.navdrawerandtablayout.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HODUCVIET on 11/21/2016.
 */
public class DatabasePlaylist extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SongManager";
    public static final String TABLE_NAME = "Song";

    public static final String NAME= "name";
    public DatabasePlaylist(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREAT_SONG_TABLE="CREATE TABLE "+TABLE_NAME+"("
                +NAME + " TEXT PRIMARY KEY);";
        db.execSQL(CREAT_SONG_TABLE);

    }
    public DatabasePlaylist(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
