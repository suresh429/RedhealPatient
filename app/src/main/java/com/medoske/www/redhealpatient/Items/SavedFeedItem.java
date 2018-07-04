package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 7.7.17.
 */

public class SavedFeedItem {
    String categoryId ;
    String doctor_redhealId;
    String tip_date ;
    String tip_time ;
    String doctorName ;
    String doctorImage ;
    String tipName ;
    String description ;
    String tipImage ;
    String id ;
    String categoryName ;
    String color ;
    String like_status ;

    public SavedFeedItem(String categoryId, String doctor_redhealId, String tip_date, String tip_time, String doctorName, String doctorImage, String tipName, String description, String tipImage, String id, String categoryName, String color, String like_status) {
        this.categoryId = categoryId;
        this.doctor_redhealId = doctor_redhealId;
        this.tip_date = tip_date;
        this.tip_time = tip_time;
        this.doctorName = doctorName;
        this.doctorImage = doctorImage;
        this.tipName = tipName;
        this.description = description;
        this.tipImage = tipImage;
        this.id = id;
        this.categoryName = categoryName;
        this.color = color;
        this.like_status = like_status;
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

    public String getTipName() {
        return tipName;
    }

    public void setTipName(String tipName) {
        this.tipName = tipName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTipImage() {
        return tipImage;
    }

    public void setTipImage(String tipImage) {
        this.tipImage = tipImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
