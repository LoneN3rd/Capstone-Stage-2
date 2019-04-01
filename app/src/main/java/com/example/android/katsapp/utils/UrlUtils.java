package com.example.android.katsapp.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class UrlUtils {

    //private static final String API_KEY = "defc4eff-5bfb-4802-8af4-910018d99194";
    private static final String API_KEY = "4250b566-cadf-4afd-8be7-125065454777";
    //public static final String API_KEY = BuildConfig.MOVIE_DB_API_KEY;
    private static final String BREEDS_BASE_URL = "https://api.thecatapi.com/v1/breeds?attach_breed=0";
    private static final String CATEGORIES_BASE_URL = "https://api.thecatapi.com/v1/categories";
    private static final String IMAGES_BASE_URL = "https://api.thecatapi.com/v1/images/search";
    private static final String CATEGORY_PARAM = "category_ids";
    private static final String BREED_PARAM = "breed_ids";
    private static final String IMAGE_LIMIT = "limit";
    private static final String CATS_QUERY_API = "x-api-key";

    private static final String LOG_TAG = UrlUtils.class.getSimpleName();

    public static URL buildUrl(){
        Uri uri = Uri.parse(BREEDS_BASE_URL)
                .buildUpon()
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problems creating breeds url", e);
        }

        return url;
    }

    public static URL buildCategoriesUrl(){

        Uri uri = Uri.parse(CATEGORIES_BASE_URL)
                .buildUpon()
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problems creating categories url", e);
        }

        return url;
    }

    public static URL buildCategoryImagesUrl(int imageId){

        Uri uri = Uri.parse(IMAGES_BASE_URL)
                .buildUpon()
                .appendQueryParameter(CATEGORY_PARAM, Integer.toString(imageId))
                .appendQueryParameter(IMAGE_LIMIT, "8")
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problems creating images url", e);
        }

        return url;
    }

    public static URL buildBreedImageUrl(String imageId){

        Uri uri = Uri.parse(IMAGES_BASE_URL)
                .buildUpon()
                .appendQueryParameter(BREED_PARAM, imageId)
                .appendQueryParameter(IMAGE_LIMIT, "1")
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problems creating images url", e);
        }

        return url;
    }

    public static String getResponseFromHttp(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty(CATS_QUERY_API, API_KEY);
        urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scn = new Scanner(inputStream);
            scn.useDelimiter("\\A");

            boolean hasInput = scn.hasNext();
            if (hasInput) {
                return scn.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }
}
