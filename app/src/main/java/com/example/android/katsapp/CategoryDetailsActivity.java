package com.example.android.katsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.android.katsapp.Adapters.CategoryDetailsAdapter;
import com.example.android.katsapp.model.Images;
import com.example.android.katsapp.utils.JsonUtils;
import com.example.android.katsapp.utils.UrlUtils;

import java.net.URL;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryDetailsActivity extends AppCompatActivity {

    int categoryId;
    String categoryName, imageQueryResponse;
    Images[] categoryImages;

    private static final String LOG_TAG = CategoryDetailsActivity.class.getSimpleName();

    @BindView(R.id.rv_category_images)
    RecyclerView mRecyclerView;

    @BindView(R.id.image_not_found)
    ImageView image_not_found;

    @BindView(R.id.card_image_not_found)
    CardView card_image_not_found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        Intent intent = getIntent();

        if (intent != null){
            categoryId = intent.getIntExtra("categoryId", 0);
            categoryName = intent.getStringExtra("categoryName");

            Log.i(LOG_TAG, "categoryName,,, "+categoryName);

            setTitle("yfhyhfyhf");

            new GetCategoryImagesTask().execute();
        }
    }

    // Get Breed Images AsyncTask
    @SuppressLint("StaticFieldLeak")
    private class GetCategoryImagesTask extends AsyncTask<String, Void, Images[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Images[] doInBackground(String... strings) {

            URL categoryImagesUrl = UrlUtils.buildCategoryImagesUrl(categoryId);

            try {
                imageQueryResponse = UrlUtils.getResponseFromHttp(categoryImagesUrl);
                categoryImages = JsonUtils.parseImagesJson(imageQueryResponse);

            } catch (Exception e){
                e.printStackTrace();
            }

            return categoryImages;
        }

        @Override
        protected void onPostExecute(Images[] images) {
            new GetCategoryImagesTask().cancel(true);

            if (images != null){
                categoryImages = images;

                if (categoryImages.length == 0){
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    card_image_not_found.setVisibility(View.VISIBLE);
                } else {
                    CategoryDetailsAdapter adapter = new CategoryDetailsAdapter(CategoryDetailsActivity.this, categoryImages);
                    mRecyclerView.setAdapter(adapter);
                }

            }
        }
    }
}
