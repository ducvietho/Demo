package com.tuhocandroid.navdrawerandtablayout.Object;

/**
 * Created by Chuong Phung on 11/24/2016.
 */

public class Comment {
    private String mPersonName;
    private String mUrlPhoto;
    private String mComment;

    public Comment() {

    }

    public Comment(String PersonName, String Comment, String urlPhoto) {
        this.mPersonName = PersonName;
        this.mComment = Comment;
        this.mUrlPhoto = urlPhoto;
    }

    public String getmPersonName() {
        return mPersonName;
    }

    public void setmPersonName(String mPersonName) {
        this.mPersonName = mPersonName;
    }

    public String getmComment() {
        return mComment;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

    public String getmUrlPhoto() {
        return mUrlPhoto;
    }

    public void setmUrlPhoto(String mUrlPhoto) {
        this.mUrlPhoto = mUrlPhoto;
    }
}
