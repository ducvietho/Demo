package com.tuhocandroid.navdrawerandtablayout.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tuhocandroid.navdrawerandtablayout.Object.NamePlaylist;

import java.util.ArrayList;

/**
 * Created by HODUCVIET on 11/21/2016.
 */
public class PlaylistProcess {
    private SQLiteDatabase database;
    private DatabasePlaylist dbHelper;
    public PlaylistProcess(Context context){
        dbHelper= new DatabasePlaylist(context);
    }
    public void add(NamePlaylist playlist){
        database=this.dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(dbHelper.NAME,playlist.getName());
        database.insert(dbHelper.TABLE_NAME, null, contentValues);
        database.close();
    }
    public ArrayList<NamePlaylist> allplaylist(){
        ArrayList<NamePlaylist> playlist=new ArrayList<NamePlaylist>();
        String query= "SELECT * FROM "+ dbHelper.TABLE_NAME
                +";";
        database=dbHelper.getWritableDatabase();
        Cursor cursor=database.rawQuery(query,null);
        NamePlaylist namePlaylist=new NamePlaylist();
        if(cursor!=null){
            cursor.moveToFirst();
            while (cursor.isAfterLast()==false){
                namePlaylist=CursorPlaylist(cursor);
                playlist.add(namePlaylist);
                cursor.moveToNext();
            }
        }
        database.close();
        return playlist;
    }
    public NamePlaylist CursorPlaylist(Cursor cursor){
        NamePlaylist name= new NamePlaylist();
        name.setName(cursor.getString(0));
        return name;
    }
}
