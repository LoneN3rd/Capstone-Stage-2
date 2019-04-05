package com.example.android.katsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
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

import com.example.android.katsapp.Adapters.BreedsAdapter;
import com.example.android.katsapp.model.Breeds;
import com.example.android.katsapp.provider.BreedsContract.BreedsEntry;
import com.example.android.katsapp.utils.JsonUtils;
import com.example.android.katsapp.utils.UrlUtils;

import java.net.URL;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class BreedsFragment extends Fragment implements BreedsAdapter.BreedAdapterClickListener {

    RecyclerView mRecyclerView;
    Breeds[] cat_breeds;
    GetBreedsTask getBreedsTask;
    OnBreedClickListener mCallback;
    public String breedsQueryResponse;
    private static final String LOG_TAG = BreedsFragment.class.getSimpleName();
    private boolean loadFav = false;
    private String loadingFromFav;

    public BreedsFragment() {
        // Required empty public constructor
    }

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

        mRecyclerView = rootView.findViewById(R.id.rv_breeds);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        else{
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        // if loadFav = false, get breeds data from database
        if (loadFav){

            loadingFromFav = "yeees";
            cat_breeds = getBreedNames();

        } else {

            getBreedsTask = new GetBreedsTask();

            getBreedsTask.execute();

            try {
                cat_breeds = getBreedsTask.get();

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            loadingFromFav = "nooo";
        }

        BreedsAdapter breedsAdapter = new BreedsAdapter(this, getActivity());
        breedsAdapter.setBreeds(cat_breeds);

        mRecyclerView.setAdapter(breedsAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onBreedClicked(int position, String breedId) {
        mCallback.onBreedClick(position, breedsQueryResponse, breedId, loadingFromFav);
    }

    public interface OnBreedClickListener{
        void onBreedClick(int position, String breedsQueryResponse, String breedId, String loadingFromFav);
    }

    private Breeds[] getBreedNames(){

        Uri uri = BreedsEntry.CONTENT_URI;
        Breeds[] theResult = new Breeds[0];

        Cursor mCursor = getContext().getContentResolver()
                .query(uri, null, null, null, "breed_name ASC");

        if (mCursor != null) {
            mCursor.moveToFirst();

            theResult = new Breeds[mCursor.getCount()];

            for (int i=0; i < mCursor.getCount(); i++){
                Breeds breeds = new Breeds();

                breeds.setName(mCursor.getString(mCursor.getColumnIndex(BreedsEntry.COLUMN_BREED_NAME)));
                breeds.setId(mCursor.getString(mCursor.getColumnIndex(BreedsEntry.COLUMN_ID)));
                breeds.setCountryCode(mCursor.getString(mCursor.getColumnIndex(BreedsEntry.COLUMN_COUNTRY_CODE)));

                theResult[i] = breeds;

                mCursor.moveToNext();
            }

            cat_breeds = theResult;

            mCursor.close();
        }

        return theResult;

    }

    @SuppressLint("StaticFieldLeak")
    private class GetBreedsTask extends AsyncTask<String, Void, Breeds[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Breeds[] doInBackground(String... strings) {

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
            new GetBreedsTask().cancel(true);

            if (breeds != null){
                cat_breeds = breeds;
            } else {
                Log.d(LOG_TAG, "Problems with the adapter");
            }
        }
    }

}
