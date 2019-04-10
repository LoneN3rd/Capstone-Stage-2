package com.example.android.katsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.katsapp.Adapters.CategoryDetailsAdapter;
import com.example.android.katsapp.model.Images;
import com.example.android.katsapp.utils.JsonUtils;
import com.example.android.katsapp.utils.UrlUtils;

import java.net.URL;

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

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.btn_retry)
    Button buttonRetry;

    @BindView(R.id.tv_error)
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retry();
            }
        });

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        if (!(CheckNetwork.isInternetAvailable(this))) {

            mRecyclerView.setVisibility(View.GONE);
            networkError();

            return;
        }

        Intent intent = getIntent();

        if (intent != null){
            categoryId = intent.getIntExtra("categoryId", 0);
            categoryName = intent.getStringExtra("categoryName");

            Log.i(LOG_TAG, "categoryName,,, "+categoryName);

            new GetCategoryImagesTask().execute();
        }

        setTitle("Cats in "+categoryName);
    }

    public void networkError(){
        progressBar.setVisibility(View.INVISIBLE);
        buttonRetry.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.VISIBLE);
    }

    private void retry(){
        if (!(CheckNetwork.isInternetAvailable(this))){
            networkError();
            return;
        }

        hideViews();

        // Refresh Activity
        finish();
        startActivity(getIntent());
    }

    public void hideViews(){
        tvError.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        buttonRetry.setVisibility(View.INVISIBLE);
    }

    // Get Breed Images AsyncTask
    @SuppressLint("StaticFieldLeak")
    private class GetCategoryImagesTask extends AsyncTask<String, Void, Images[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mRecyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Images[] doInBackground(String... strings) {

            if (!(CheckNetwork.isInternetAvailable(CategoryDetailsActivity.this))){
                networkError();
                return null;
            }

            if (UrlUtils.API_KEY.equals("")){
                networkError();
                tvError.setText(R.string.api_error_message);
                return null;
            }

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

                    mRecyclerView.setVisibility(View.VISIBLE);
                    hideViews();
                }

            }
        }
    }
}
