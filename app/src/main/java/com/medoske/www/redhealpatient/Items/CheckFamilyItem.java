package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 27.7.17.
 */

public class CheckFamilyItem {

    String patient_redhealId;
    String relationTypeId;
    String mobileNumber;


    public CheckFamilyItem() {
        this.patient_redhealId = patient_redhealId;
        this.relationTypeId = relationTypeId;
        this.mobileNumber = mobileNumber;
    }

    public String getPatient_redhealId() {
        return patient_redhealId;
    }

    public void setPatient_redhealId(String patient_redhealId) {
        this.patient_redhealId = patient_redhealId;
    }

    public String getRelationTypeId() {
        return relationTypeId;
    }

    public void setRelationTypeId(String relationTypeId) {
        this.relationTypeId = relationTypeId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
