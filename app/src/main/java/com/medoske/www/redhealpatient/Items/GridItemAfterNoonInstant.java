package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 17.7.17.
 */

public class GridItemAfterNoonInstant {

    String slotId ;
    String bookingTime ;
    String bookingSession ;
    String bookingDate ;
    String appointmentType ;
    String status ;

    public GridItemAfterNoonInstant(String slotId, String bookingTime, String bookingSession, String bookingDate, String appointmentType, String status) {
        this.slotId = slotId;
        this.bookingTime = bookingTime;
        this.bookingSession = bookingSession;
        this.bookingDate = bookingDate;
        this.appointmentType = appointmentType;
        this.status = status;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getBookingSession() {
        return bookingSession;
    }

    public void setBookingSession(String bookingSession) {
        this.bookingSession = bookingSession;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
