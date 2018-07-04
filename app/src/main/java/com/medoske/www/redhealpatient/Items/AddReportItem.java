package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 5.7.17.
 */

public class AddReportItem {

   String patient_redhealId;
    String bookingId;
    String appointmentRefNo;
    String reportType;


    public AddReportItem() {
        this.patient_redhealId = patient_redhealId;
        this.bookingId = bookingId;
        this.appointmentRefNo = appointmentRefNo;
        this.reportType = reportType;
    }

    public String getPatient_redhealId() {
        return patient_redhealId;
    }

    public void setPatient_redhealId(String patient_redhealId) {
        this.patient_redhealId = patient_redhealId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getAppointmentRefNo() {
        return appointmentRefNo;
    }

    public void setAppointmentRefNo(String appointmentRefNo) {
        this.appointmentRefNo = appointmentRefNo;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
}
