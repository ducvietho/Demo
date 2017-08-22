package com.tuhocandroid.navdrawerandtablayout.Object;

/**
 * Created by HODUCVIET on 7/29/2016.
 */
public class ItemObject {
    private byte[] image;
    private String name;
    private int infor;

    public ItemObject(byte[] image, String name, int infor) {
        this.image = image;
        this.name = name;
        this.infor = infor;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInfor() {
        return infor;
    }

    public void setInfor(int infor) {
        this.infor = infor;
    }
}