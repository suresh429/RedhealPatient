package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 17.8.17.
 */

public class TipsSeasonItem {

    String tipName;
    String tipType;
    int Imageview;

    public TipsSeasonItem(String tipName, String tipType, int imageview) {
        this.tipName = tipName;
        this.tipType = tipType;
        Imageview = imageview;
    }

    public String getTipName() {
        return tipName;
    }

    public void setTipName(String tipName) {
        this.tipName = tipName;
    }

    public String getTipType() {
        return tipType;
    }

    public void setTipType(String tipType) {
        this.tipType = tipType;
    }

    public int getImageview() {
        return Imageview;
    }

    public void setImageview(int imageview) {
        Imageview = imageview;
    }
}
