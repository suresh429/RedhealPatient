package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 19.7.17.
 */

public class PackageDetailsPostItem {

    String relation_redhealId;
    String fullName;
    String relation;
    String gender;
    String address;
    String age;
    String mobileNumber;
    String email;

    public PackageDetailsPostItem() {
        this.relation_redhealId = relation_redhealId;
        this.fullName = fullName;
        this.relation = relation;
        this.gender = gender;
        this.address = address;
        this.age = age;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }

    public String getRelation_redhealId() {
        return relation_redhealId;
    }

    public void setRelation_redhealId(String relation_redhealId) {
        this.relation_redhealId = relation_redhealId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
