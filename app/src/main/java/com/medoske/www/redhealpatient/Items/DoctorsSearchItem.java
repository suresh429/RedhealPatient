package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 21.6.17.
 */

public class DoctorsSearchItem {

    String  doctor_redhealId ;
    String  fullName ;
    String  doctorImage ;
    String  specialization ;
    String  clinic_redhealId ;
    String  clinicName ;
    String  clinicAddress ;
    String  distance ;

    String  experience ;
    String  consultationFee ;
    String  premiumConsultationFee ;
    String  description ;
    String like_status;
    String presentLatitude;
    String presentLongitude;


    public DoctorsSearchItem(String doctor_redhealId, String fullName, String doctorImage, String specialization, String clinic_redhealId, String clinicName, String clinicAddress, String distance, String experience, String consultationFee, String premiumConsultationFee, String description, String like_status, String presentLatitude, String presentLongitude) {
        this.doctor_redhealId = doctor_redhealId;
        this.fullName = fullName;
        this.doctorImage = doctorImage;
        this.specialization = specialization;
        this.clinic_redhealId = clinic_redhealId;
        this.clinicName = clinicName;
        this.clinicAddress = clinicAddress;
        this.distance = distance;
        this.experience = experience;
        this.consultationFee = consultationFee;
        this.premiumConsultationFee = premiumConsultationFee;
        this.description = description;
        this.like_status = like_status;
        this.presentLatitude = presentLatitude;
        this.presentLongitude = presentLongitude;
    }

    public String getDoctor_redhealId() {
        return doctor_redhealId;
    }

    public void setDoctor_redhealId(String doctor_redhealId) {
        this.doctor_redhealId = doctor_redhealId;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
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

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(String consultationFee) {
        this.consultationFee = consultationFee;
    }

    public String getPremiumConsultationFee() {
        return premiumConsultationFee;
    }

    public void setPremiumConsultationFee(String premiumConsultationFee) {
        this.premiumConsultationFee = premiumConsultationFee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLike_status() {
        return like_status;
    }

    public void setLike_status(String like_status) {
        this.like_status = like_status;
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
