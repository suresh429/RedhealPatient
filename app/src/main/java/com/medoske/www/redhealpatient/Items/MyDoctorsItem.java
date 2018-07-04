package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 7.7.17.
 */

public class MyDoctorsItem {


    String doctor_redhealId ;
    String fullName ;
    String doctorImage ;
    String experience ;
    String specialization;
    String description ;
    String verfication_status ;
    String like_status ;

    public MyDoctorsItem(String doctor_redhealId, String fullName, String doctorImage, String experience, String specialization, String description, String verfication_status, String like_status) {
        this.doctor_redhealId = doctor_redhealId;
        this.fullName = fullName;
        this.doctorImage = doctorImage;
        this.experience = experience;
        this.specialization = specialization;
        this.description = description;
        this.verfication_status = verfication_status;
        this.like_status = like_status;
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
