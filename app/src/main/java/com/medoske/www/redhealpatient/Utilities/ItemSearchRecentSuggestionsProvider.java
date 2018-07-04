package com.medoske.www.redhealpatient.Utilities;

import android.content.SearchRecentSuggestionsProvider;

/**
 * SearchRecentSuggestionsProvider
 */
public class ItemSearchRecentSuggestionsProvider extends SearchRecentSuggestionsProvider {
    // package name + class name
    public static final String AUTHORITY = "com.java_lang_programming.android_recycleview_demo.provider.ItemSearchRecentSuggestionsProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public ItemSearchRecentSuggestionsProvider() {
        super();
        setupSuggestions(AUTHORITY, MODE);
    }
}

