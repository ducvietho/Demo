package com.tuhocandroid.navdrawerandtablayout.Object;

import java.io.Serializable;

/**
 * Created by Chuong Phung on 11/26/2016.
 */

public class Singer implements Serializable {

    private String SingerName;
    private String UrlPhoto;
    private String InfoSinger;
    private double id;

    public Singer() {

    }

    public Singer(String singerName, String urlPhoto, String InfoSinger, double id) {
        this.SingerName = singerName;
        this.UrlPhoto = urlPhoto;
        this.InfoSinger = InfoSinger;
        this.id = id;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getInfoSinger() {
        return InfoSinger;
    }

    public void setInfoSinger(String infoSinger) {
        InfoSinger = infoSinger;
    }

    public String getSingerName() {
        return SingerName;
    }

    public void setSingerName(String singerName) {
        SingerName = singerName;
    }

    public String getUrlPhoto() {
        return UrlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        UrlPhoto = urlPhoto;
    }
}
