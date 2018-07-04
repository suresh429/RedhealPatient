package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 17.8.17.
 */

public class AlternateTreatmentItem {
    String name;
    int image;

    public AlternateTreatmentItem(String name, int image) {

        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
