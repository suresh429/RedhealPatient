package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 30.6.17.
 */

public class FeedsItem {

    String doctorName ;
    String specialization ;
    String doctorImage ;
    String tip_date ;
    String tip_time ;
    String description ;
    String tipName ;
    String imagePath ;
    String categoryName ;
    String id ;
    String categoryId ;
    String doctor_redhealId ;
    String color ;
    String like_status;

    public FeedsItem(String doctorName, String specialization, String doctorImage, String tip_date, String tip_time, String description, String tipName, String imagePath, String categoryName, String id, String categoryId, String doctor_redhealId, String color, String like_status) {
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.doctorImage = doctorImage;
        this.tip_date = tip_date;
        this.tip_time = tip_time;
        this.description = description;
        this.tipName = tipName;
        this.imagePath = imagePath;
        this.categoryName = categoryName;
        this.id = id;
        this.categoryId = categoryId;
        this.doctor_redhealId = doctor_redhealId;
        this.color = color;
        this.like_status = like_status;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }

    public String getTip_date() {
        return tip_date;
    }

    public void setTip_date(String tip_date) {
        this.tip_date = tip_date;
    }

    public String getTip_time() {
        return tip_time;
    }

    public void setTip_time(String tip_time) {
        this.tip_time = tip_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTipName() {
        return tipName;
    }

    public void setTipName(String tipName) {
        this.tipName = tipName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDoctor_redhealId() {
        return doctor_redhealId;
    }

    public void setDoctor_redhealId(String doctor_redhealId) {
        this.doctor_redhealId = doctor_redhealId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLike_status() {
        return like_status;
    }

    public void setLike_status(String like_status) {
        this.like_status = like_status;
    }
}
