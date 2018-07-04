package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 14.7.17.
 */

public class AddFavDoctorItem {
    String likeStatus;
    String patientId;
    String doctorId;

    public AddFavDoctorItem() {
        this.likeStatus = likeStatus;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public String getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
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
}
