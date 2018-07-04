package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 17.7.17.
 */

public class PatientRelationItem {
    String redhealId;
    String relationype;
    String relationTypeId;

    public PatientRelationItem(String redhealId, String relationype, String relationTypeId) {
        this.redhealId = redhealId;
        this.relationype = relationype;
        this.relationTypeId = relationTypeId;
    }

    public String getRedhealId() {
        return redhealId;
    }

    public void setRedhealId(String redhealId) {
        this.redhealId = redhealId;
    }

    public String getRelationype() {
        return relationype;
    }

    public void setRelationype(String relationype) {
        this.relationype = relationype;
    }

    public String getRelationTypeId() {
        return relationTypeId;
    }

    public void setRelationTypeId(String relationTypeId) {
        this.relationTypeId = relationTypeId;
    }
}
