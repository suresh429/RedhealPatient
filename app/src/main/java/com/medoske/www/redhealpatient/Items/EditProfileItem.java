package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 14.7.17.
 */

public class EditProfileItem {
    String patientId;
    String name;
    String email;
    String gender;
    String age;
    String mobile;
    String bloodGroup;

    public EditProfileItem(String patientId, String name, String email, String gender, String age, String mobile, String bloodGroup) {
        this.patientId = patientId;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.mobile = mobile;
        this.bloodGroup = bloodGroup;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
