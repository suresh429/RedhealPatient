package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 21.6.17.
 */

public class ClinicSearchItem {

    String clinicImage ;
    String clinic_redhealId ;
    String clinicName ;
    String address ;
    String distance ;
    String presentLatitude;
    String presentLongitude;

    public ClinicSearchItem(String clinicImage, String clinic_redhealId, String clinicName, String address, String distance, String presentLatitude, String presentLongitude) {
        this.clinicImage = clinicImage;
        this.clinic_redhealId = clinic_redhealId;
        this.clinicName = clinicName;
        this.address = address;
        this.distance = distance;
        this.presentLatitude = presentLatitude;
        this.presentLongitude = presentLongitude;
    }

    public String getClinicImage() {
        return clinicImage;
    }

    public void setClinicImage(String clinicImage) {
        this.clinicImage = clinicImage;
    }

    public String getClinic_redhealId() {
        return clinic_redhealId;
    }

    public void setClinic_redhealId(String clinic_redhealId) {
        this.clinic_redhealId = clinic_redhealId;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
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
}
