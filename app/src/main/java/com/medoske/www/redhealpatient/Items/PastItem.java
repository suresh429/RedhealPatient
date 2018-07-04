package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 5.7.17.
 */

public class PastItem {
    String doctorName;
    String doctorImage;
    String status;
    String date;
    String time;
    String clinicName;
    String note;
    String address;
    String doctorId;
    String Speclization;
    String patientName;
    String patientImage;
    String patientId;
    String mobileNo;
    String latitude;
    String longitude;
    String clinicId;
    String appointRefNo;
    String bookingId;
    String paymentMode;

    public PastItem(String doctorName, String doctorImage, String status, String date, String time, String clinicName, String note, String address, String doctorId, String speclization, String patientName, String patientImage, String patientId, String mobileNo, String latitude, String longitude, String clinicId, String appointRefNo, String bookingId, String paymentMode) {
        this.doctorName = doctorName;
        this.doctorImage = doctorImage;
        this.status = status;
        this.date = date;
        this.time = time;
        this.clinicName = clinicName;
        this.note = note;
        this.address = address;
        this.doctorId = doctorId;
        Speclization = speclization;
        this.patientName = patientName;
        this.patientImage = patientImage;
        this.patientId = patientId;
        this.mobileNo = mobileNo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.clinicId = clinicId;
        this.appointRefNo = appointRefNo;
        this.bookingId = bookingId;
        this.paymentMode = paymentMode;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getSpeclization() {
        return Speclization;
    }

    public void setSpeclization(String speclization) {
        Speclization = speclization;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientImage() {
        return patientImage;
    }

    public void setPatientImage(String patientImage) {
        this.patientImage = patientImage;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public String getAppointRefNo() {
        return appointRefNo;
    }

    public void setAppointRefNo(String appointRefNo) {
        this.appointRefNo = appointRefNo;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
}
