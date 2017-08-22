package com.tuhocandroid.navdrawerandtablayout.Object;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by HODUCVIET on 9/27/2016.
 */
public class Song implements Serializable {
    private double id;
    private String SongName;
    private String Singer;
    private String UrlSong;
    private String album;
    private boolean favouris;
    private double Listen;
    private String author;
    private String Type;
    private String lyrics;
    private String national;
    private boolean isSelected;

    public Song () {

    }

    public Song(int id, String SongName, String singer, String UrlSong, String album, boolean favouris, int listened, String author, String type, String lyrics, String national) {
        this.id = id;
        this.SongName = SongName;
        this.Singer =singer;
        this.UrlSong = UrlSong;
        this.album = album;
        this.favouris = favouris;
        this.Listen = listened;
        this.author = author;
        this.Type = type;
        this.lyrics = lyrics;
        this.national = national;
    }

    public String getSinger() {
        return Singer;
    }

    public void setSinger(String singer) {
        this.Singer = singer;
    }

    public String getAlbum() {
        return album;
    }
    public String getSongName() {
        return SongName;
    }
    public void setSongName(String songName) {
        this.SongName = songName;
    }
    public void setAlbum(String album) {
        this.album = album;
    }

    public double getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlSong() {
        return UrlSong;
    }

    public void setUrlSong(String urlSong) {
        this.UrlSong = urlSong;
    }

    public boolean isFavouris() {
        return favouris;
    }

    public void setFavouris(boolean favouris) {
        this.favouris = favouris;
    }

    public double getListen() {
        return Listen;
    }

    public void setListen(int listen) {
        this.Listen = listen;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}
