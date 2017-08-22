package com.tuhocandroid.navdrawerandtablayout.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tuhocandroid.navdrawerandtablayout.Object.Song;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HODUCVIET on 11/7/2016.
 */
public class SongProcess {
    private SQLiteDatabase database;
    private DatabaseHandler dbHelper;
    public SongProcess(Context context){
        dbHelper=new DatabaseHandler(context);
    }
    public void add(Song song){
        database=this.dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(dbHelper.KEY_ID,song.getId());
        contentValues.put(dbHelper.NAME,song.getSongName());
        contentValues.put(dbHelper.SINGER,song.getSinger());
        contentValues.put(dbHelper.URL,song.getUrlSong());
        contentValues.put(dbHelper.ALBUM,song.getAlbum());
        contentValues.put(dbHelper.FAVOURIS,song.isFavouris());
        contentValues.put(dbHelper.LISTENED,song.getListen());
        contentValues.put(dbHelper.AUTHOR,song.getAuthor());
        contentValues.put(dbHelper.TYPE,song.getType());
        contentValues.put(dbHelper.LYRICS,song.getLyrics());
        contentValues.put(dbHelper.NATIONAL,song.getNational());
        database.insert(dbHelper.TABLE_NAME, null, contentValues);
        database.close();
    }
    public ArrayList<Song> List_Song(){
        ArrayList<Song> song=new ArrayList<Song>();
        String query= "SELECT * FROM "+ dbHelper.TABLE_NAME
                +";";
        database=dbHelper.getWritableDatabase();
        Cursor cursor=database.rawQuery(query,null);
        Song sog=new Song();
        if(cursor!=null){
            cursor.moveToFirst();
            while (cursor.isAfterLast()==false){
                sog=CursorSong(cursor);
                song.add(sog);
                cursor.moveToNext();
            }
        }
        database.close();
        return song;
    }
    public ArrayList<Song> Order_By(){
        ArrayList<Song> song=new ArrayList<Song>();
        String query= "SELECT * FROM "+ dbHelper.TABLE_NAME
                +" ORDER BY "+dbHelper.NAME+" ASC ;";
        database=dbHelper.getWritableDatabase();
        Cursor cursor=database.rawQuery(query,null);
        Song sog=new Song();
        if(cursor!=null){
            cursor.moveToFirst();
            while (cursor.isAfterLast()==false){
                sog=CursorSong(cursor);
                song.add(sog);
                cursor.moveToNext();
            }
        }
        database.close();
        return song;
    }
    public ArrayList<String> Singer_Name(){
        ArrayList<String> singer_list=new ArrayList<String>();
        String query= "SELECT DISTINCT "+dbHelper.SINGER+" FROM "+ dbHelper.TABLE_NAME
                +" ORDER BY "+dbHelper.SINGER+" ASC ;";
        database=dbHelper.getWritableDatabase();
        Cursor cursor=database.rawQuery(query,null);
        String singer=null;
        if(cursor!=null){
            cursor.moveToFirst();
            while (cursor.isAfterLast()==false){
                singer=cursor.getString(0);
                singer_list.add(singer);
                cursor.moveToNext();
            }
        }
        database.close();
        return singer_list;
    }
    public void Delete_Song(double id){
            database=dbHelper.getWritableDatabase();
            String query = "DELETE FROM "+dbHelper.TABLE_NAME
                    +" WHERE "+dbHelper.KEY_ID+"=" + "'"+id+"';";
            database.execSQL(query);
            database.close();

    }
    public ArrayList<HashMap<String,String>> Album_Name(){
        ArrayList<HashMap<String,String>> album_list = new ArrayList<HashMap<String, String>>();
        String query = "SELECT DISTINCT "+dbHelper.ALBUM+","+dbHelper.SINGER+" FROM "+ dbHelper.TABLE_NAME
                +" ORDER BY "+dbHelper.ALBUM+" ASC ;";
        database=dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(query,null);

        if(cursor != null){
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false){
                HashMap<String,String> album = new HashMap<String,String>();
                album.put("Album name",cursor.getString(0));
                album.put("Singer name",cursor.getString(1));
                album_list.add(album);
                cursor.moveToNext();
            }
        }
        database.close();
        return album_list;
    }
    public ArrayList<Song> SongList(String name){
        ArrayList<Song> song=new ArrayList<Song>();
        String query= "SELECT * FROM "+ dbHelper.TABLE_NAME
                +" WHERE "+dbHelper.SINGER+"="+"'"+name+"'"+" OR "+dbHelper.ALBUM+"="+"'"+name+"'"
                +" ORDER BY "+dbHelper.NAME+" ASC ;";
        database=dbHelper.getWritableDatabase();
        Cursor cursor=database.rawQuery(query,null);
        Song sog=new Song();
        if(cursor!=null){
            cursor.moveToFirst();
            while (cursor.isAfterLast()==false){
                sog=CursorSong(cursor);
                song.add(sog);
                cursor.moveToNext();
            }
        }
        database.close();
        return song;
    }
    public Song CursorSong(Cursor cursor){
        Song song=new Song();
        song.setId(cursor.getInt(0));
        song.setSongName(cursor.getString(1));
        song.setSinger(cursor.getString(2));
        song.setUrlSong(cursor.getString(3));
        song.setAlbum(cursor.getString(4));
        song.setFavouris(cursor.isNull(5));
        song.setListen(cursor.getInt(6));
        song.setAuthor(cursor.getString(7));
        song.setType(cursor.getString(8));
        song.setLyrics(cursor.getString(9));
        song.setNational(cursor.getString(10));
        return song;
    }
}
