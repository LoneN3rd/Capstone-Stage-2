package com.example.android.katsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.katsapp.Adapters.BreedsAdapter;
import com.example.android.katsapp.Adapters.FavoritesAdapter;
import com.example.android.katsapp.database.DatabaseClient;
import com.example.android.katsapp.database.FavoriteBreeds;
import com.example.android.katsapp.model.Breeds;
import com.example.android.katsapp.utils.JsonUtils;
import com.example.android.katsapp.utils.UrlUtils;

import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class BreedsFragment extends Fragment implements BreedsAdapter.BreedAdapterClickListener, FavoritesAdapter.FavAdapterClickListener {

    private static RecyclerView mRecyclerView;
    static Breeds[] cat_breeds = null;
    GetBreedsTask getBreedsTask;
    GetFavoritesTask getFavoritesTask;
    OnBreedClickListener mCallback;
    public static String breedsQueryResponse;
    private static final String LOG_TAG = BreedsFragment.class.getSimpleName();
    private boolean loadFav = false;
    private String loadingFromFav;
    private static ProgressBar progressBar;
    private static Button buttonRetry;
    private static TextView tvError;

    private static List<FavoriteBreeds> breedsList;

    public void loadFromFav(){
        this.loadFav = true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // attach callback to fragment
        try {
            mCallback = (OnBreedClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement Listeners");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_breeds, container, false);

        progressBar = rootView.findViewById(R.id.progressbar);
        buttonRetry = rootView.findViewById(R.id.btn_retry);
        buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retry();
            }
        });

        tvError = rootView.findViewById(R.id.tv_error);

        mRecyclerView = rootView.findViewById(R.id.rv_breeds);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        // if loadFav = false, get breeds data from database
        if (loadFav) {

            loadingFromFav = "yeees";

            getFavoritesTask = new GetFavoritesTask(getContext());
            getFavoritesTask.execute();

            try {
                breedsList = getFavoritesTask.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (breedsList.size() != 0){

                FavoritesAdapter favoritesAdapter = new FavoritesAdapter(this, getActivity());
                favoritesAdapter.setBreeds(breedsList);

                mRecyclerView.setAdapter(favoritesAdapter);

                Log.i(LOG_TAG, "some data");
                Log.i(LOG_TAG, "the data = " + breedsList);
            } else {
                Log.i(LOG_TAG, "no data");
            }

        } else {

            if (!(CheckNetwork.isInternetAvailable(getContext()))) {

                networkError();

                return rootView;
            }

            getBreedsTask = new GetBreedsTask(getContext());
            getBreedsTask.execute();

            try {
                cat_breeds = getBreedsTask.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            loadingFromFav = "nooo";

            if (cat_breeds != null) {

                BreedsAdapter breedsAdapter = new BreedsAdapter(this, getActivity());
                breedsAdapter.setBreeds(cat_breeds);

                mRecyclerView.setAdapter(breedsAdapter);
            }
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    public static void networkError(){
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

    public static void hideViews(){
        tvError.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        buttonRetry.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBreedClicked(int position, String breedId, String breedName) {
        mCallback.onBreedClick(position, breedsQueryResponse, breedId, loadingFromFav, breedName);
    }

    public interface OnBreedClickListener{
        void onBreedClick(int position, String breedsQueryResponse, String breedId, String loadingFromFav, String breedName);
    }

    @SuppressLint("StaticFieldLeak")
    private static class GetFavoritesTask extends AsyncTask<String, Void, List<FavoriteBreeds>> {

        static Context mContext;

        GetFavoritesTask(Context context){
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mRecyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<FavoriteBreeds> doInBackground(String... strings) {
            return DatabaseClient
                    .getInstance(mContext)
                    .getAppDatabase()
                    .breedsDao()
                    .getAll();
        }

        @Override
        protected void onPostExecute(List<FavoriteBreeds> favoriteBreeds) {
            new GetFavoritesTask(mContext).cancel(true);

            if (favoriteBreeds != null){
                breedsList = favoriteBreeds;

                mRecyclerView.setVisibility(View.VISIBLE);
                hideViews();
            } else {
                Log.d(LOG_TAG, "Problems with the favorites adapter");
            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    private static class GetBreedsTask extends AsyncTask<String, Void, Breeds[]> {

        static Context mContext;

        GetBreedsTask(Context context){
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mRecyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Breeds[] doInBackground(String... strings) {

            if (!(CheckNetwork.isInternetAvailable(mContext))){
                networkError();
                return null;
            }

            if (UrlUtils.API_KEY.equals("")){
                networkError();
                tvError.setText(R.string.api_error_message);
                return null;
            }

            URL theBreedsUrl = UrlUtils.buildUrl();

            try {

                breedsQueryResponse = UrlUtils.getResponseFromHttp(theBreedsUrl);
                cat_breeds = JsonUtils.parseBreedsJson(breedsQueryResponse);

            } catch (Exception e){
                e.printStackTrace();
            }

            return cat_breeds;
        }

        @Override
        protected void onPostExecute(Breeds[] breeds) {
            new GetBreedsTask(mContext).cancel(true);

            if (breeds != null){
                cat_breeds = breeds;

                mRecyclerView.setVisibility(View.VISIBLE);
                hideViews();
            } else {
                Log.d(LOG_TAG, "Problems with the adapter");
            }
        }
    }

}
