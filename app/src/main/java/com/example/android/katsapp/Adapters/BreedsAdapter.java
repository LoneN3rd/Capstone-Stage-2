package com.example.android.katsapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.katsapp.R;
import com.example.android.katsapp.model.Breeds;
import com.squareup.picasso.Picasso;

public class BreedsAdapter extends RecyclerView.Adapter<BreedsAdapter.BreedsAdapterViewHolder> {

    private static final String LOG_TAG = BreedsAdapter.class.getSimpleName();

    private BreedAdapterClickListener mBreedClickListener;

    private Context mContext;
    private Breeds[] mBreeds;
    private TextView breedName;
    private ImageView imageViewHolder;

    private String breed_name, breed_id;

    public BreedsAdapter(BreedAdapterClickListener mBreedClickListener){
        this.mBreedClickListener = mBreedClickListener;
    }

    public void setBreeds(Breeds[] breeds){
        this.mBreeds = breeds;
    }

    @NonNull
    @Override
    public BreedsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_breeds, parent, false);

        return new BreedsAdapter.BreedsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BreedsAdapterViewHolder breedsAdapterViewHolder, int position) {
        breed_name = mBreeds[position].getName();
        breed_id = mBreeds[position].getId();

        breedName.setText(breed_name);
        imageViewHolder.setImageResource(R.drawable.abys_1);
    }

    private void setImage(String image){
        Picasso.with(mContext)
                .load(image)
                .fit()
                .into(imageViewHolder);
    }

    @Override
    public int getItemCount() {
        return mBreeds.length;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class BreedsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        BreedsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            breedName = itemView.findViewById(R.id.breed_title);
            imageViewHolder = itemView.findViewById(R.id.breed_image);

            imageViewHolder.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();

            String breed_id_to_pass = mBreeds[clickedPosition].getId();

            mBreedClickListener.onBreedClicked(clickedPosition, breed_id_to_pass);
        }
    }

    public interface BreedAdapterClickListener{
        void onBreedClicked(int position, String breedId);
    }
}
