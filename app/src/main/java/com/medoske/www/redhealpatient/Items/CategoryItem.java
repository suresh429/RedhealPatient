package com.medoske.www.redhealpatient.Items;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 2.9.17.
 */

public class CategoryItem {

    public String categoryId;
    public String categoryName;
    public List<Mobile> tests =new ArrayList<Mobile>();;

    public CategoryItem(String categoryId, String categoryName, List<Mobile> tests) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.tests = tests;
    }
}
