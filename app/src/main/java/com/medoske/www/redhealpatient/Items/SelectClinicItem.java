package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 22.6.17.
 */

public class SelectClinicItem {

    String clinic_redhealId ;
    String doctor_redhealId ;
    String clinicName ;
    String primaryNumber ;
    String latitude ;
    String longitude ;
    String imagePath ;
    String consultationFee ;
    String instantConsultationFee ;
    String distance ;
    String doctorName ;
    String doctorImage ;
    String doctorSpeclization ;
    String clinicAddress ;


    public SelectClinicItem(String clinic_redhealId, String doctor_redhealId, String clinicName, String primaryNumber, String latitude, String longitude, String imagePath, String consultationFee, String instantConsultationFee, String distance, String doctorName, String doctorImage, String doctorSpeclization, String clinicAddress) {
        this.clinic_redhealId = clinic_redhealId;
        this.doctor_redhealId = doctor_redhealId;
        this.clinicName = clinicName;
        this.primaryNumber = primaryNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imagePath = imagePath;
        this.consultationFee = consultationFee;
        this.instantConsultationFee = instantConsultationFee;
        this.distance = distance;
        this.doctorName = doctorName;
        this.doctorImage = doctorImage;
        this.doctorSpeclization = doctorSpeclization;
        this.clinicAddress = clinicAddress;
    }

    public String getClinic_redhealId() {
        return clinic_redhealId;
    }

    public void setClinic_redhealId(String clinic_redhealId) {
        this.clinic_redhealId = clinic_redhealId;
    }

    public String getDoctor_redhealId() {
        return doctor_redhealId;
    }

    public void setDoctor_redhealId(String doctor_redhealId) {
        this.doctor_redhealId = doctor_redhealId;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getPrimaryNumber() {
        return primaryNumber;
    }

    public void setPrimaryNumber(String primaryNumber) {
        this.primaryNumber = primaryNumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(String consultationFee) {
        this.consultationFee = consultationFee;
    }

    public String getInstantConsultationFee() {
        return instantConsultationFee;
    }

    public void setInstantConsultationFee(String instantConsultationFee) {
        this.instantConsultationFee = instantConsultationFee;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }

    public String getDoctorSpeclization() {
        return doctorSpeclization;
    }

    public void setDoctorSpeclization(String doctorSpeclization) {
        this.doctorSpeclization = doctorSpeclization;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }
}
