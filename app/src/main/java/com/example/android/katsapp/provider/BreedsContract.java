package com.example.android.katsapp.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class BreedsContract {

    static final String AUTHORITY = "com.example.android.katsapp";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    static final String PATH_BREEDS = "breeds";

    public static final class BreedsEntry implements BaseColumns {

        public static final Uri CONTENT_URI=
                BASE_CONTENT_URI.buildUpon()
                        .appendPath(PATH_BREEDS)
                        .build();

        public static final String TABLE_NAME = "breeds";
        public static final String COLUMN_ID = "breed_id";
        public static final String COLUMN_BREED_NAME = "breed_name";
        public static final String COLUMN_ORIGIN = "origin";
        public static final String COLUMN_COUNTRY_CODE = "country_code";
        public static final String COLUMN_HYPO_ALLERGENIC = "hypoallergenic";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_TEMPERAMENT = "temperament";
        public static final String COLUMN_AFFECTION = "affection";
        public static final String COLUMN_ADAPTABILITY = "adaptability";
        public static final String COLUMN_CHILD_FRIENDLY = "child_friendly";
        public static final String COLUMN_DOG_FRIENDLY = "dog_friendly";
        public static final String COLUMN_ENERGY_LEVEL = "energy_level";
        public static final String COLUMN_GROOMING = "grooming";
        public static final String COLUMN_HEALTH_ISSUES = "health_issues";
        public static final String COLUMN_INTELLIGENCE = "intelligence";

        public static final String COLUMN_SHEDDING_LEVEL = "shedding_level";
        public static final String COLUMN_SOCIAL_NEEDS = "social_needs";
        public static final String COLUMN_STRANGER_FRIENDLY = "stranger_friendly";
        public static final String COLUMN_WIKIPEDIA = "wikipedia_url";

    }
}
