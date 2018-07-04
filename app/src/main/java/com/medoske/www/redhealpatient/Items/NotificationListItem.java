package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 2.6.17.
 */

public class NotificationListItem {
    private String imagetype;
    private String imagePath;
    private String title;
    private String shortmessage;
    private String lngmessage;
    private String action;
    private String reference;

    public NotificationListItem(String imagetype, String imagePath, String title, String shortmessage, String lngmessage, String action, String reference) {
        this.imagetype = imagetype;
        this.imagePath = imagePath;
        this.title = title;
        this.shortmessage = shortmessage;
        this.lngmessage = lngmessage;
        this.action = action;
        this.reference = reference;
    }

    public String getImagetype() {
        return imagetype;
    }

    public void setImagetype(String imagetype) {
        this.imagetype = imagetype;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortmessage() {
        return shortmessage;
    }

    public void setShortmessage(String shortmessage) {
        this.shortmessage = shortmessage;
    }

    public String getLngmessage() {
        return lngmessage;
    }

    public void setLngmessage(String lngmessage) {
        this.lngmessage = lngmessage;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}


