package com.medoske.www.redhealpatient.Utilities;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by USER on 25.7.17.
 */

public class MySuggestionProvide extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.example.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvide() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
