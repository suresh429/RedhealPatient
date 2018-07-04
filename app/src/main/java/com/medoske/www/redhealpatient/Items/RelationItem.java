package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 17.7.17.
 */

public class RelationItem {

    String realtionId;
    String relationType;

    public RelationItem(String realtionId, String relationType) {
        this.realtionId = realtionId;
        this.relationType = relationType;
    }

    public String getRealtionId() {
        return realtionId;
    }

    public void setRealtionId(String realtionId) {
        this.realtionId = realtionId;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }
}
