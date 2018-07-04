package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 7.7.17.
 */

public class FavouriteItem {
    String patientId;
    String doctorId;
    String like;

    public FavouriteItem() {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.like = like;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }
}
