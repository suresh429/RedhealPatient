package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 8.7.17.
 */

public class AddFamilyItem {

    String patient_redhealId;
    String fullName;
    String relationTypeId;
    String gender;
    String bloodGroup;
    String age;
    String mobileNumber;
    String email;

    public AddFamilyItem() {
        this.patient_redhealId = patient_redhealId;
        this.fullName = fullName;
        this.relationTypeId = relationTypeId;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.age = age;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }

    public String getPatient_redhealId() {
        return patient_redhealId;
    }

    public void setPatient_redhealId(String patient_redhealId) {
        this.patient_redhealId = patient_redhealId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRelationTypeId() {
        return relationTypeId;
    }

    public void setRelationTypeId(String relationTypeId) {
        this.relationTypeId = relationTypeId;
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
