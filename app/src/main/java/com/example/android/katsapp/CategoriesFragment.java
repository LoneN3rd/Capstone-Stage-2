package com.example.android.katsapp;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.katsapp.Adapters.CategoriesAdapter;
import com.example.android.katsapp.model.Categories;
import com.example.android.katsapp.utils.JsonUtils;
import com.example.android.katsapp.utils.UrlUtils;

import java.net.URL;
import java.util.concurrent.ExecutionException;

public class CategoriesFragment extends Fragment implements AdapterView.OnItemClickListener {

    Categories[] cat_categories;
    GetCategoriesTask getCategoriesTask;
    public String categoriesQueryResponse;
    CategoriesAdapter adapter;

    private static final String LOG_TAG = CategoriesFragment.class.getSimpleName();

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getCategoriesTask = new GetCategoriesTask();

        getCategoriesTask.execute();

        try {
            cat_categories = getCategoriesTask.get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_categories, container, false);

        adapter = new CategoriesAdapter(getContext(), cat_categories);

        ListView lvItems = rootView.findViewById(R.id.list);
        lvItems.setAdapter(adapter);

        lvItems.setOnItemClickListener(this);

        return rootView;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {

            Log.d(LOG_TAG, "Something is going on");

        } catch (Exception e){
            Log.d(LOG_TAG, "Something is wrong");
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class GetCategoriesTask extends AsyncTask<String, Void, Categories[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Categories[] doInBackground(String... strings) {

            URL theCategoriesUrl = UrlUtils.buildCategoriesUrl();

            try {

                categoriesQueryResponse = UrlUtils.getResponseFromHttp(theCategoriesUrl);
                cat_categories = JsonUtils.parseCategoriesJson(categoriesQueryResponse);

            } catch (Exception e){
                e.printStackTrace();
            }

            return cat_categories;
        }

        @Override
        protected void onPostExecute(Categories[] categories) {
            new GetCategoriesTask().cancel(true);

            if (categories != null){
                cat_categories = categories;
            } else {
                Log.d(LOG_TAG, "Problems with the adapter");
            }
        }

    }
}
