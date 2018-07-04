package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 6.7.17.
 */

public class MyReportItem {

    String report_link;
    String timestamp;
    String doctorName;
    String patient_redhealId;


    public MyReportItem(String report_link, String timestamp, String doctorName, String patient_redhealId) {
        this.report_link = report_link;
        this.timestamp = timestamp;
        this.doctorName = doctorName;
        this.patient_redhealId = patient_redhealId;
    }

    public String getReport_link() {
        return report_link;
    }

    public void setReport_link(String report_link) {
        this.report_link = report_link;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatient_redhealId() {
        return patient_redhealId;
    }

    public void setPatient_redhealId(String patient_redhealId) {
        this.patient_redhealId = patient_redhealId;
    }
}
