package com.example.android.katsapp;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.example.android.katsapp.ViewModels.SingleFavViewModel;
import com.example.android.katsapp.ViewModels.SingleFavViewModelFactory;
import com.example.android.katsapp.database.BreedsDatabase;
import com.example.android.katsapp.database.FavoriteBreeds;
import com.example.android.katsapp.model.Breeds;
import com.example.android.katsapp.model.Images;
import com.example.android.katsapp.utils.JsonUtils;
import com.example.android.katsapp.utils.UrlUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BreedDetailsActivity extends AppCompatActivity {

    int clickedBreedPosition;
    String breedsQueryResponse, breedName, breedOrigin, description, temperament, wikipedia_url, breedId;
    String loadingFromFav, breed_id, country_code, country_code_image_url;
    private int affection_level, adaptability, child_friendly, dog_friendly, energy_level, grooming, health_issues;
    private int intelligence, shedding_level, social_needs, stranger_friendly, hypoallergenic;
    Breeds[] breedDetails;
    Images[] breedImageArray;
    GetBreedImageTask getBreedImageTask;

    private static final String LOG_TAG = BreedsFragment.class.getSimpleName();

    private BreedsDatabase mDb;
    private FavoriteBreeds favoriteBreeds;

    @BindView(R.id.origin)
    TextView origin;

    @BindView(R.id.hypoallergenic)
    TextView tv_hypoallergenic;

    @BindView(R.id.description)
    TextView tv_description;

    @BindView(R.id.temperament)
    TextView tv_temperament;

    @BindView(R.id.affection)
    RatingBar rb_affection;

    @BindView(R.id.adaptability)
    RatingBar rb_adaptability;

    @BindView(R.id.child_friendly)
    RatingBar rb_child_friendly;

    @BindView(R.id.dog_friendly)
    RatingBar rb_dog_friendly;

    @BindView(R.id.energy_level)
    RatingBar rb_energy_level;

    @BindView(R.id.grooming)
    RatingBar rb_grooming;

    @BindView(R.id.health_issues)
    RatingBar rb_health_issues;

    @BindView(R.id.intelligence)
    RatingBar rb_intelligence;

    @BindView(R.id.shedding_level)
    RatingBar rb_shedding_level;

    @BindView(R.id.social_needs)
    RatingBar rb_social_needs;

    @BindView(R.id.stranger_friendly)
    RatingBar rb_stranger_friendly;

    @BindView(R.id.wikipedia)
    TextView tv_wikipedia;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.fab_remove)
    FloatingActionButton fab_remove;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.country_code_image)
    ImageView country_code_image;

    @BindView(R.id.breed_image)
    ImageView iv_breed_image;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.btn_retry)
    Button buttonRetry;

    @BindView(R.id.tv_error)
    TextView tvError;

    @BindView(R.id.affection_label)
    TextView affection_label;

    @BindView(R.id.adaptability_label)
    TextView adaptability_label;

    @BindView(R.id.child_friendly_label)
    TextView child_friendly_label;

    @BindView(R.id.dog_friendly_label)
    TextView dog_friendly_label;

    @BindView(R.id.energy_level_label)
    TextView energy_level_label;

    @BindView(R.id.grooming_label)
    TextView grooming_label;

    @BindView(R.id.health_issues_label)
    TextView health_issues_label;

    @BindView(R.id.intelligence_label)
    TextView intelligence_label;

    @BindView(R.id.shedding_level_label)
    TextView shedding_level_label;

    @BindView(R.id.social_needs_label)
    TextView social_needs_label;

    @BindView(R.id.stranger_friendly_label)
    TextView stranger_friendly_label;

    @BindView(R.id.cv_origin)
    CardView cv_origin;

    @BindView(R.id.cv_hypoallergenic)
    CardView cv_hypoallergenic;

    @BindView(R.id.lly_data_handling)
    LinearLayout lly_data_handling;

    @BindView(R.id.lly2_data_handling)
    LinearLayout lly2_data_handling;

    @BindView(R.id.tv_no_data)
    TextView tv_no_data;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed_details);

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

        mDb = BreedsDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();

        if (intent != null) {

            clickedBreedPosition = intent.getIntExtra("clickedBreedPosition", 0);
            breed_id = intent.getStringExtra("breed_id");
            loadingFromFav = intent.getStringExtra("loadingFromFav");
            breedsQueryResponse = intent.getStringExtra("breedsQueryResponse");

            if (loadingFromFav.equals("yeees")) {

                breedId = breed_id;

                //Declare a AddEntryViewModelFactory using mDb and mEntryId
                SingleFavViewModelFactory factory = new SingleFavViewModelFactory(mDb, breedId);

                final SingleFavViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(SingleFavViewModel.class);

                //Observe the LiveData object in the ViewModel
                viewModel.getFavorite().observe(this, new Observer<FavoriteBreeds>() {
                    @Override
                    public void onChanged(@Nullable FavoriteBreeds favoriteBreed) {
                        viewModel.getFavorite().removeObserver(this);
                        // populateUI(favoriteBreed);
                        favoriteBreeds = favoriteBreed;
                    }
                });

                breedName = favoriteBreeds.getBreedName();

                hypoallergenic = favoriteBreeds.getHypoallergenic();
                description = favoriteBreeds.getDescription();
                temperament = favoriteBreeds.getTemperament();
                breedOrigin = favoriteBreeds.getOrigin();
                country_code = favoriteBreeds.getCountryCode();
                affection_level = favoriteBreeds.getAffection();
                adaptability = favoriteBreeds.getAdaptability();
                child_friendly = favoriteBreeds.getChildFriendly();
                dog_friendly = favoriteBreeds.getDogFriendly();
                energy_level = favoriteBreeds.getEnergyLevel();
                grooming = favoriteBreeds.getGrooming();
                health_issues = favoriteBreeds.getHealthIssues();
                intelligence = favoriteBreeds.getIntelligence();
                shedding_level = favoriteBreeds.getSheddingLevel();
                social_needs = favoriteBreeds.getSocialNeeds();
                stranger_friendly = favoriteBreeds.getStrangerFriendly();
                wikipedia_url = favoriteBreeds.getWikipediaUrl();

            } else {

                if (!(CheckNetwork.isInternetAvailable(this))){
                    hideDataViews();
                    networkError();
                    return;
                }

                try {
                    breedDetails = JsonUtils.parseBreedsJson(breedsQueryResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                hypoallergenic = breedDetails[clickedBreedPosition].getHypoallergenic();
                description = breedDetails[clickedBreedPosition].getDescription();
                breedName = breedDetails[clickedBreedPosition].getName();
                temperament = breedDetails[clickedBreedPosition].getTemperament();
                breedOrigin = breedDetails[clickedBreedPosition].getOrigin();
                country_code = breedDetails[clickedBreedPosition].getCountryCode();
                affection_level = breedDetails[clickedBreedPosition].getAffection_level();
                adaptability = breedDetails[clickedBreedPosition].getAdaptability();
                child_friendly = breedDetails[clickedBreedPosition].getChild_friendly();
                dog_friendly = breedDetails[clickedBreedPosition].getDog_friendly();
                energy_level = breedDetails[clickedBreedPosition].getEnergy_level();
                grooming = breedDetails[clickedBreedPosition].getGrooming();
                health_issues = breedDetails[clickedBreedPosition].getHealth_issues();
                intelligence = breedDetails[clickedBreedPosition].getIntelligence();
                shedding_level = breedDetails[clickedBreedPosition].getShedding_level();
                social_needs = breedDetails[clickedBreedPosition].getSocial_needs();
                stranger_friendly = breedDetails[clickedBreedPosition].getStranger_friendly();
                wikipedia_url = breedDetails[clickedBreedPosition].getWikipedia_url();

                breedId = breedDetails[clickedBreedPosition].getId();

            }

            String the_country_code = country_code.toLowerCase(Locale.ROOT);
            country_code_image_url = "https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.2.1/flags/1x1/" + the_country_code + ".svg";

            setTitle(breedName);

            String breedImage = "";

            if (CheckNetwork.isInternetAvailable(BreedDetailsActivity.this)) {
                SvgLoader.pluck()
                        .with(this)
                        .load(country_code_image_url, country_code_image);

                getBreedImageTask = new GetBreedImageTask();

                getBreedImageTask.execute();

                try {
                    breedImageArray = getBreedImageTask.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (breedImageArray != null) {
                    breedImage = breedImageArray[0].getImageUrl();
                }
            } else {
                breedImage = "https://cdn2.thecatapi.com/images/27777g.jpg";
            }

            Picasso.with(this)
                    .load(breedImage)
                    .placeholder(R.drawable.abys_3)
                    .error(R.drawable.abys_2)
                    .into(iv_breed_image);

            origin.setText(breedOrigin);

            if (hypoallergenic != 0){
                tv_hypoallergenic.setText(R.string.hypoallergenic);
            } else {
                tv_hypoallergenic.setVisibility(View.GONE);
            }

            tv_description.setText(description);
            tv_temperament.setText(temperament);
            rb_affection.setRating(affection_level);
            rb_adaptability.setRating(adaptability);
            rb_child_friendly.setRating(child_friendly);
            rb_dog_friendly.setRating(dog_friendly);
            rb_energy_level.setRating(energy_level);
            rb_grooming.setRating(grooming);
            rb_health_issues.setRating(health_issues);
            rb_intelligence.setRating(intelligence);
            rb_shedding_level.setRating(shedding_level);
            rb_social_needs.setRating(social_needs);
            rb_stranger_friendly.setRating(stranger_friendly);
            tv_wikipedia.setText(wikipedia_url);

        }

        updateUi();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // Save breed to DB
            saveBreedData();

            updateUi();

            Snackbar.make(view, breedName + " "+ getString(R.string.marked_favorite), Snackbar.LENGTH_LONG)
                    .setAction(R.string.action, null).show();

            // send a broadcast to update the appwidget
            // CatsWidget.sendRefreshBroadcast(BreedDetailsActivity.this);
            }
        });

        fab_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            deleteBreed();

            Snackbar.make(view, breedName + " " + getString(R.string.removed_favorite), Snackbar.LENGTH_LONG)
                        .setAction(R.string.action, null).show();

            updateUi();

            // send a broadcast to update the appwidget
            // CatsWidget.sendRefreshBroadcast(BreedDetailsActivity.this);
            }
        });

    }

    private void populateUI(FavoriteBreeds favoriteBreed) {
        if (favoriteBreed == null) {
            return;
        }
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
        tv_no_data.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("RestrictedApi")
    public void hideDataViews(){

        lly_data_handling.setVisibility(View.GONE);
        lly2_data_handling.setVisibility(View.GONE);
    }

    @SuppressLint("RestrictedApi")
    public void showDataViews(){

        lly_data_handling.setVisibility(View.VISIBLE);
        lly2_data_handling.setVisibility(View.VISIBLE);

    }

    @SuppressLint("RestrictedApi")
    public void updateUi(){

        if ((isFavorite(breedId))){
            fab_remove.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);

            Log.i(LOG_TAG, "FAB FAV HIDDEN. FAB REMOVE SHOWN.");
        } else {
            fab_remove.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);

            Log.i(LOG_TAG, "FAB FAV SHOWN. FAB REMOVE HIDDEN.");
        }

    }

    // Checks if a breed exists in the DB
    private boolean isFavorite(String _id) throws SQLException {

        int count = -1;
        Cursor cursor = null;
        try {
            String query = "SELECT COUNT(*) FROM breeds WHERE breed_id = :_id";

            cursor = mDb.query(query, new String[] {_id});
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            return count > 0;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    // delete breed
    @SuppressLint("StaticFieldLeak")
    private void deleteBreed() {


        class RemoveFavorite extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                FavoriteBreeds favoriteBreeds = new FavoriteBreeds(breedId);
                favoriteBreeds.setBreed_id(breedId);

                /*
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .breedsDao()
                        .delete(breedId);
                 */

                return null;
            }
        }

        RemoveFavorite removeFavorite = new RemoveFavorite();
        removeFavorite.execute();

    }

    private void saveBreedData(){

        @SuppressLint("StaticFieldLeak")
        class SaveBreedData extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                FavoriteBreeds favoriteBreeds = new FavoriteBreeds(breedId, breedName, breedOrigin,
                        country_code, hypoallergenic, description, temperament, affection_level,
                        adaptability, child_friendly, dog_friendly, energy_level, grooming,
                        health_issues, intelligence, shedding_level, social_needs, stranger_friendly,
                        wikipedia_url);

                favoriteBreeds.setBreed_id(breedId);
                favoriteBreeds.setBreedName(breedName);
                favoriteBreeds.setOrigin(breedOrigin);
                favoriteBreeds.setCountryCode(country_code);
                favoriteBreeds.setHypoallergenic(hypoallergenic);
                favoriteBreeds.setDescription(description);
                favoriteBreeds.setTemperament(temperament);
                favoriteBreeds.setAffection(affection_level);
                favoriteBreeds.setAdaptability(adaptability);
                favoriteBreeds.setChildFriendly(child_friendly);
                favoriteBreeds.setDogFriendly(dog_friendly);
                favoriteBreeds.setEnergyLevel(energy_level);
                favoriteBreeds.setGrooming(grooming);
                favoriteBreeds.setHealthIssues(health_issues);
                favoriteBreeds.setIntelligence(intelligence);
                favoriteBreeds.setSheddingLevel(shedding_level);
                favoriteBreeds.setSocialNeeds(social_needs);
                favoriteBreeds.setStrangerFriendly(stranger_friendly);
                favoriteBreeds.setWikipediaUrl(wikipedia_url);

                /*
                BreedsDatabase.getInstance(getApplicationContext()).getAppDatabase()
                        .breedsDao()
                        .insertBreed(favoriteBreeds);
                 */

                return null;
            }
        }

        SaveBreedData saveFavorite = new SaveBreedData();
        saveFavorite.execute();
    }

    public void refresh(){
        finish();
        startActivity(getIntent());
    }

    public static void logInfo(String str){
        if (str.length() > 4000){
            Log.i(LOG_TAG, str.substring(0, 4000));
            logInfo(str.substring(4000));
        } else {
            Log.i(LOG_TAG, str);
        }
    }

    // Get Breed Images
    @SuppressLint("StaticFieldLeak")
    private class GetBreedImageTask extends AsyncTask<String, Void, Images[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            hideDataViews();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Images[] doInBackground(String... strings) {

            if (!(CheckNetwork.isInternetAvailable(BreedDetailsActivity.this))){
                networkError();
                return null;
            }

            if (UrlUtils.API_KEY.equals("")){
                networkError();
                tvError.setText(R.string.api_error_message);
                return null;
            }

            URL breedImageUrl = UrlUtils.buildBreedImageUrl(breedId);

            String breedImageQueryResponse = null;

            try {
                breedImageQueryResponse = UrlUtils.getResponseFromHttp(breedImageUrl);
                breedImageArray = JsonUtils.parseImagesJson(breedImageQueryResponse);
            } catch (Exception e){
                e.printStackTrace();
            }

            return breedImageArray;
        }


        @Override
        protected void onPostExecute(Images[] images) {
            new GetBreedImageTask().cancel(true);

            if (images != null){
                breedImageArray = images;

                showDataViews();
                hideViews();

                Log.i(LOG_TAG, "breedImageArray,,"+ Arrays.toString(breedImageArray));
            }
        }
    }

}
