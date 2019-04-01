package com.example.android.katsapp.utils;

import com.example.android.katsapp.model.Breeds;
import com.example.android.katsapp.model.Categories;
import com.example.android.katsapp.model.Images;

import org.json.JSONArray;
import org.json.JSONException;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();
    private static final String BREED_ORIGIN = "origin";
    private static final String COUNTRY_CODE = "country_code";
    private static final String BREED_HYPOALLERGENIC = "hypoallergenic";
    private static final String BREED_NAME = "name";
    private static final String ID = "id";
    private static final String BREED_DESCRIPTION = "description";
    private static final String BREED_TEMPERAMENT = "temperament";
    private static final String BREED_AFFECTION_LEVEL = "affection_level";
    private static final String BREED_ADAPTABILITY = "adaptability";
    private static final String BREED_CHILD_FRIENDLY = "child_friendly";
    private static final String BREED_DOG_FRIENDLY = "dog_friendly";
    private static final String BREED_ENERGY_LEVEL = "energy_level";
    private static final String BREED_GROOMING = "grooming";
    private static final String BREED_HEALTH_ISSUES = "health_issues";
    private static final String BREED_INTELLIGENCE = "intelligence";
    private static final String BREED_SHEDDING_LEVEL = "shedding_level";
    private static final String BREED_SOCIAL_NEEDS = "social_needs";
    private static final String BREED_STRANGER_FRIENDLY = "stranger_friendly";
    private static final String BREED_WIKIPEDIA = "wikipedia_url";
    private static final String CATEGORY_IMAGE_URL = "url";

    // Parse Breeds
    public static Breeds[] parseBreedsJson(String jsonAllBreeds) throws JSONException {
        final JSONArray jsonArray = new JSONArray(jsonAllBreeds);
        Breeds[] theResult = new Breeds[jsonArray.length()];

        for (int i=0; i < jsonArray.length(); i++){
            Breeds breeds = new Breeds();

            breeds.setName(jsonArray.getJSONObject(i).optString(BREED_NAME));
            breeds.setId(jsonArray.getJSONObject(i).optString(ID));

            breeds.setOrigin(jsonArray.getJSONObject(i).optString(BREED_ORIGIN));
            breeds.setCountryCode(jsonArray.getJSONObject(i).optString(COUNTRY_CODE));
            breeds.setHypoallergenic(jsonArray.getJSONObject(i).optInt(BREED_HYPOALLERGENIC));

            breeds.setDescription(jsonArray.getJSONObject(i).optString(BREED_DESCRIPTION));
            breeds.setTemperament(jsonArray.getJSONObject(i).optString(BREED_TEMPERAMENT));
            breeds.setAffection_level(jsonArray.getJSONObject(i).optInt(BREED_AFFECTION_LEVEL));
            breeds.setAdaptability(jsonArray.getJSONObject(i).optInt(BREED_ADAPTABILITY));
            breeds.setChild_friendly(jsonArray.getJSONObject(i).optInt(BREED_CHILD_FRIENDLY));
            breeds.setDog_friendly(jsonArray.getJSONObject(i).optInt(BREED_DOG_FRIENDLY));
            breeds.setEnergy_level(jsonArray.getJSONObject(i).optInt(BREED_ENERGY_LEVEL));
            breeds.setGrooming(jsonArray.getJSONObject(i).optInt(BREED_GROOMING));
            breeds.setHealth_issues(jsonArray.getJSONObject(i).optInt(BREED_HEALTH_ISSUES));
            breeds.setIntelligence(jsonArray.getJSONObject(i).optInt(BREED_INTELLIGENCE));
            breeds.setShedding_level(jsonArray.getJSONObject(i).optInt(BREED_SHEDDING_LEVEL));
            breeds.setSocial_needs(jsonArray.getJSONObject(i).optInt(BREED_SOCIAL_NEEDS));
            breeds.setStranger_friendly(jsonArray.getJSONObject(i).optInt(BREED_STRANGER_FRIENDLY));
            breeds.setWikipedia_url(jsonArray.getJSONObject(i).optString(BREED_WIKIPEDIA));

            theResult[i] = breeds;
        }

        return theResult;
    }

    // Parse Categories
    public static Categories[] parseCategoriesJson(String jsonAllCategories) throws JSONException {

        final JSONArray jsonArray = new JSONArray(jsonAllCategories);

        Categories[] theResult = new Categories[jsonArray.length()];

        for (int i=0; i < jsonArray.length(); i++){
            Categories categories = new Categories();

            categories.setCategoryName(jsonArray.getJSONObject(i).optString(BREED_NAME));
            categories.setId(jsonArray.getJSONObject(i).optInt(ID));

            theResult[i] = categories;
        }

        return theResult;
    }

    // Parse Category Images
    public static Images[] parseImagesJson(String jsonAllImagesData) throws JSONException {

        final JSONArray jsonArray = new JSONArray(jsonAllImagesData);

        Images[] theResult = new Images[jsonArray.length()];

        for (int i=0; i < jsonArray.length(); i++){
            Images categoryImages = new Images();

            categoryImages.setImageUrl(jsonArray.getJSONObject(i).optString(CATEGORY_IMAGE_URL));

            theResult[i] = categoryImages;
        }

        return theResult;
    }


}
