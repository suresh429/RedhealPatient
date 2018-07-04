package com.medoske.www.redhealpatient.Items;

import com.medoske.www.redhealpatient.SearchActivity;

/**
 * Created by USER on 17.8.17.
 */

public class TopSpelitiesItem {

    String id;
    String specialization;
    String image;
    String presentLatitude;
    String presentLongitude;
    String locality;

    public TopSpelitiesItem(String id, String specialization, String image, String presentLatitude, String presentLongitude, String locality) {
        this.id = id;
        this.specialization = specialization;
        this.image = image;
        this.presentLatitude = presentLatitude;
        this.presentLongitude = presentLongitude;
        this.locality = locality;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPresentLatitude() {
        return presentLatitude;
    }

    public void setPresentLatitude(String presentLatitude) {
        this.presentLatitude = presentLatitude;
    }

    public String getPresentLongitude() {
        return presentLongitude;
    }

    public void setPresentLongitude(String presentLongitude) {
        this.presentLongitude = presentLongitude;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }
}
