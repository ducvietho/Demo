package com.tuhocandroid.navdrawerandtablayout.Database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HODUCVIET on 10/25/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SongManager";
    public static final String TABLE_NAME = "Song";

    public static final String KEY_ID = "id";
    public static final String NAME = "name";
    public static final String SINGER = "singer";
    public static final String URL = "url";
    public static final String ALBUM = "album";
    public static final String FAVOURIS= "favouris";
    public static final String LISTENED = "listended";
    public static final String AUTHOR = "author";
    public static final String TYPE = "type";
    public static final String LYRICS = "lyrics";
    public static final String NATIONAL = "national";
    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }
    public DatabaseHandler(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREAT_SONG_TABLE="CREATE TABLE "+TABLE_NAME+"("
                +KEY_ID + " INTEGER PRIMARY KEY,"
                +NAME + " TEXT,"
                +SINGER + " TEXT,"
                +URL + " TEXT,"
                +ALBUM + " TEXT,"
                +FAVOURIS + " TEXT,"
                +LISTENED + " INT,"
                +AUTHOR + " TEXT,"
                +TYPE + " TEXT,"
                +LYRICS + " TEXT,"
                +NATIONAL + " TEXT"+");";
        db.execSQL(CREAT_SONG_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
