package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 19.7.17.
 */

public class BookPackageItem {

    String patient_redhealId;
    String clinic_redhealId;
    String doctor_redhealId;
    String bookingDate;
    String bookingTime;
    String slotId;
    String relationTypeId;
    String relation_redhealId;
    String packageId;

    public BookPackageItem() {
        this.patient_redhealId = patient_redhealId;
        this.clinic_redhealId = clinic_redhealId;
        this.doctor_redhealId = doctor_redhealId;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.slotId = slotId;
        this.relationTypeId = relationTypeId;
        this.relation_redhealId = relation_redhealId;
        this.packageId = packageId;
    }

    public String getPatient_redhealId() {
        return patient_redhealId;
    }

    public void setPatient_redhealId(String patient_redhealId) {
        this.patient_redhealId = patient_redhealId;
    }

    public String getClinic_redhealId() {
        return clinic_redhealId;
    }

    public void setClinic_redhealId(String clinic_redhealId) {
        this.clinic_redhealId = clinic_redhealId;
    }

    public String getDoctor_redhealId() {
        return doctor_redhealId;
    }

    public void setDoctor_redhealId(String doctor_redhealId) {
        this.doctor_redhealId = doctor_redhealId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getRelationTypeId() {
        return relationTypeId;
    }

    public void setRelationTypeId(String relationTypeId) {
        this.relationTypeId = relationTypeId;
    }

    public String getRelation_redhealId() {
        return relation_redhealId;
    }

    public void setRelation_redhealId(String relation_redhealId) {
        this.relation_redhealId = relation_redhealId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
}
