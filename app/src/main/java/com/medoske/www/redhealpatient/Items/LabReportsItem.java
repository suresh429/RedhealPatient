package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 8.7.17.
 */

public class LabReportsItem {

    String bookingId;
    String bookingDate;
    String redheal_refNo;
    String report;
    String report_date;
    String description;
    String packageName;
    String actualPrice;
    String discountPrice;
    String from_date;
    String to_date;
    String diagnosticCenterName;
    String paymentMode;
    String status;

    public LabReportsItem(String bookingId, String bookingDate, String redheal_refNo, String report, String report_date, String description, String packageName, String actualPrice, String discountPrice, String from_date, String to_date, String diagnosticCenterName, String paymentMode, String status) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.redheal_refNo = redheal_refNo;
        this.report = report;
        this.report_date = report_date;
        this.description = description;
        this.packageName = packageName;
        this.actualPrice = actualPrice;
        this.discountPrice = discountPrice;
        this.from_date = from_date;
        this.to_date = to_date;
        this.diagnosticCenterName = diagnosticCenterName;
        this.paymentMode = paymentMode;
        this.status = status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getRedheal_refNo() {
        return redheal_refNo;
    }

    public void setRedheal_refNo(String redheal_refNo) {
        this.redheal_refNo = redheal_refNo;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getDiagnosticCenterName() {
        return diagnosticCenterName;
    }

    public void setDiagnosticCenterName(String diagnosticCenterName) {
        this.diagnosticCenterName = diagnosticCenterName;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
