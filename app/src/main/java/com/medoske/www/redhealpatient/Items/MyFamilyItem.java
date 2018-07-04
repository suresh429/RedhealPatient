package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 7.7.17.
 */

public class MyFamilyItem {
    String relation_redhealId;
    String email;
    String fullName;
    String relation;
    String gender;
    String bloodGroup;
    String mobileNumber;
    String age;
    String imagePath;


    public MyFamilyItem(String relation_redhealId, String email, String fullName, String relation, String gender, String bloodGroup, String mobileNumber, String age, String imagePath) {
        this.relation_redhealId = relation_redhealId;
        this.email = email;
        this.fullName = fullName;
        this.relation = relation;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.mobileNumber = mobileNumber;
        this.age = age;
        this.imagePath = imagePath;
    }

    public String getRelation_redhealId() {
        return relation_redhealId;
    }

    public void setRelation_redhealId(String relation_redhealId) {
        this.relation_redhealId = relation_redhealId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
