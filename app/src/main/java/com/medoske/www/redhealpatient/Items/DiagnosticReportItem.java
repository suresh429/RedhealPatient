package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 6.7.17.
 */

public class DiagnosticReportItem {

    String id;
    String patient_redhealId;
    String bookingId;
    String appointmentRefNo;
    String diagnostic_report;
    String reportName;
    String upload_date;
    String upload_time;


    public DiagnosticReportItem(String id, String patient_redhealId, String bookingId, String appointmentRefNo, String diagnostic_report, String reportName, String upload_date, String upload_time) {
        this.id = id;
        this.patient_redhealId = patient_redhealId;
        this.bookingId = bookingId;
        this.appointmentRefNo = appointmentRefNo;
        this.diagnostic_report = diagnostic_report;
        this.reportName = reportName;
        this.upload_date = upload_date;
        this.upload_time = upload_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDiagnostic_report() {
        return diagnostic_report;
    }

    public void setDiagnostic_report(String diagnostic_report) {
        this.diagnostic_report = diagnostic_report;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }
}
