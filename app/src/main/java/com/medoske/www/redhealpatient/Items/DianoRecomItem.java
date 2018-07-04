package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 1.9.17.
 */

public class DianoRecomItem {

    String categoryName;
    String categoryId;
    String testName;
    String testId;

    public DianoRecomItem(String categoryName, String categoryId, String testName, String testId) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.testName = testName;
        this.testId = testId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }
}
