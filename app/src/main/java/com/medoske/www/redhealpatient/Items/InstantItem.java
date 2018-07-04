package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 10.6.17.
 */

public class InstantItem {

    String doctorName;
    String speclization;
    String avalibulity;
    String address;
    String experience;
    String price;
    int doctorImage;
    String PremiumFee;

    public InstantItem(String doctorName, String speclization, String avalibulity, String address, String experience, String price, int doctorImage, String premiumFee) {
        this.doctorName = doctorName;
        this.speclization = speclization;
        this.avalibulity = avalibulity;
        this.address = address;
        this.experience = experience;
        this.price = price;
        this.doctorImage = doctorImage;
        PremiumFee = premiumFee;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpeclization() {
        return speclization;
    }

    public void setSpeclization(String speclization) {
        this.speclization = speclization;
    }

    public String getAvalibulity() {
        return avalibulity;
    }

    public void setAvalibulity(String avalibulity) {
        this.avalibulity = avalibulity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(int doctorImage) {
        this.doctorImage = doctorImage;
    }

    public String getPremiumFee() {
        return PremiumFee;
    }

    public void setPremiumFee(String premiumFee) {
        PremiumFee = premiumFee;
    }
}
