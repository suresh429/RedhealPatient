package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 12.7.17.
 */

public class AddFavItem {
    String tipId;
    String patientId;
    String likeStatus;

    public AddFavItem() {
        this.tipId = tipId;
        this.patientId = patientId;
        this.likeStatus = likeStatus;
    }

    public String getTipId() {
        return tipId;
    }

    public void setTipId(String tipId) {
        this.tipId = tipId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
    }
}
