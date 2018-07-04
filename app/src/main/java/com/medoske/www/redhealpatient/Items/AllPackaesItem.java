package com.medoske.www.redhealpatient.Items;

/**
 * Created by USER on 1.9.17.
 */

public class AllPackaesItem {

    String packageName;
    String testCount;
    String offerPrice;
    String discountPrice;
    String labName;
    String address;

    public AllPackaesItem(String packageName, String testCount, String offerPrice, String discountPrice, String labName, String address) {
        this.packageName = packageName;
        this.testCount = testCount;
        this.offerPrice = offerPrice;
        this.discountPrice = discountPrice;
        this.labName = labName;
        this.address = address;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTestCount() {
        return testCount;
    }

    public void setTestCount(String testCount) {
        this.testCount = testCount;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
