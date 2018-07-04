package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 9.6.17.
 */

public class DoctorsListItem {
    String specialization ;
    String doctor_redhealId ;
    String doctorName;
    String verfication_status ;
    String like_status ;
    String doctorImage ;
    String clinicName ;
    String areaName ;
    String areaId ;
    String consultationFee ;
    String experience ;
    String clinic_redhealId ;
    String distance ;
    String status ;
    String premiumFee ;
    String description;
    String latitude;
    String longitude;

    public DoctorsListItem(String specialization, String doctor_redhealId, String doctorName, String verfication_status, String like_status, String doctorImage, String clinicName, String areaName, String areaId, String consultationFee, String experience, String clinic_redhealId, String distance, String status, String premiumFee, String description, String latitude, String longitude) {
        this.specialization = specialization;
        this.doctor_redhealId = doctor_redhealId;
        this.doctorName = doctorName;
        this.verfication_status = verfication_status;
        this.like_status = like_status;
        this.doctorImage = doctorImage;
        this.clinicName = clinicName;
        this.areaName = areaName;
        this.areaId = areaId;
        this.consultationFee = consultationFee;
        this.experience = experience;
        this.clinic_redhealId = clinic_redhealId;
        this.distance = distance;
        this.status = status;
        this.premiumFee = premiumFee;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getVerfication_status() {
        return verfication_status;
    }

    public void setVerfication_status(String verfication_status) {
        this.verfication_status = verfication_status;
    }

    public String getLike_status() {
        return like_status;
    }

    public void setLike_status(String like_status) {
        this.like_status = like_status;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(String consultationFee) {
        this.consultationFee = consultationFee;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getClinic_redhealId() {
        return clinic_redhealId;
    }

    public void setClinic_redhealId(String clinic_redhealId) {
        this.clinic_redhealId = clinic_redhealId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPremiumFee() {
        return premiumFee;
    }

    public void setPremiumFee(String premiumFee) {
        this.premiumFee = premiumFee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
