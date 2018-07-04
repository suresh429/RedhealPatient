package com.medoske.www.redhealpatient.Items;

/**
 * Created by root on 2/3/16.
 */
public class Mobile {

    public String testId;
    public String testName;

    public Mobile(String testId, String testName) {
        this.testId = testId;
        this.testName = testName;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

}
