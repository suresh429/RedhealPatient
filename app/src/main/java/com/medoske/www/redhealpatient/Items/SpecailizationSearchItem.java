package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 21.6.17.
 */

public class SpecailizationSearchItem {

    String id ;
    String specialization ;
    String doctor_redhealId ;
    String status ;
    String fullName ;
    String doctorImage ;
    String presentLatitude;
    String presentLongitude;
    String presentPlaceName;


    public SpecailizationSearchItem(String id, String specialization, String doctor_redhealId, String status, String fullName, String doctorImage, String presentLatitude, String presentLongitude, String presentPlaceName) {
        this.id = id;
        this.specialization = specialization;
        this.doctor_redhealId = doctor_redhealId;
        this.status = status;
        this.fullName = fullName;
        this.doctorImage = doctorImage;
        this.presentLatitude = presentLatitude;
        this.presentLongitude = presentLongitude;
        this.presentPlaceName = presentPlaceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDoctor_redhealId() {
        return doctor_redhealId;
    }

    public void setDoctor_redhealId(String doctor_redhealId) {
        this.doctor_redhealId = doctor_redhealId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }

    public String getPresentLatitude() {
        return presentLatitude;
    }

    public void setPresentLatitude(String presentLatitude) {
        this.presentLatitude = presentLatitude;
    }

    public String getPresentLongitude() {
        return presentLongitude;
    }

    public void setPresentLongitude(String presentLongitude) {
        this.presentLongitude = presentLongitude;
    }

    public String getPresentPlaceName() {
        return presentPlaceName;
    }

    public void setPresentPlaceName(String presentPlaceName) {
        this.presentPlaceName = presentPlaceName;
    }
}
