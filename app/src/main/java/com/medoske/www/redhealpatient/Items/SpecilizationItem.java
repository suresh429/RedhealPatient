package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 11.5.17.
 */

public class SpecilizationItem {


    String specializationId;
    String specialization;
    String imagepath;
    String presentLatitude;
    String presentLongitude;
    String presentPlaceName;

    public SpecilizationItem(String specializationId, String specialization, String imagepath, String presentLatitude, String presentLongitude, String presentPlaceName) {
        this.specializationId = specializationId;
        this.specialization = specialization;
        this.imagepath = imagepath;
        this.presentLatitude = presentLatitude;
        this.presentLongitude = presentLongitude;
        this.presentPlaceName = presentPlaceName;
    }

    public String getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(String specializationId) {
        this.specializationId = specializationId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
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
