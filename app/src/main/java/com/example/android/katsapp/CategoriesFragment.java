package com.example.android.katsapp;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    ProgressBar progressBar;
    private Button buttonRetry;
    private TextView tvError;
    ListView lvItems;

    private static final String LOG_TAG = CategoriesFragment.class.getSimpleName();

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_categories, container, false);

        lvItems = rootView.findViewById(R.id.list);
        lvItems.setOnItemClickListener(this);

        progressBar = rootView.findViewById(R.id.progressbar);
        buttonRetry = rootView.findViewById(R.id.btn_retry);
        buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retry();
            }
        });
        tvError = rootView.findViewById(R.id.tv_error);

        if (!(CheckNetwork.isInternetAvailable(getContext()))){

            networkError();

            return rootView;
        }

        getCategoriesTask = new GetCategoriesTask();

        getCategoriesTask.execute();

        try {
            cat_categories = getCategoriesTask.get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adapter = new CategoriesAdapter(getContext(), cat_categories);
        lvItems.setAdapter(adapter);

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

    public void networkError(){
        progressBar.setVisibility(View.INVISIBLE);
        buttonRetry.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.VISIBLE);
    }

    private void retry(){
        if (!(CheckNetwork.isInternetAvailable(getContext()))){
            networkError();
            return;
        }

        hideViews();

        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    public void hideViews(){
        tvError.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        buttonRetry.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("StaticFieldLeak")
    private class GetCategoriesTask extends AsyncTask<String, Void, Categories[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            lvItems.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
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

                lvItems.setVisibility(View.VISIBLE);
                hideViews();
            } else {
                Log.d(LOG_TAG, "Problems with the adapter");
            }
        }

    }
}
