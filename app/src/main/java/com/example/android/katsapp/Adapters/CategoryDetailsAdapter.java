package com.example.android.katsapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.katsapp.R;
import com.example.android.katsapp.model.Images;
import com.squareup.picasso.Picasso;

public class CategoryDetailsAdapter extends RecyclerView.Adapter<CategoryDetailsAdapter.ViewHolder> {

    private Images[] categoryImages;
    private Context mContext;

    private static final String LOG_TAG = CategoryDetailsAdapter.class.getSimpleName();

    public CategoryDetailsAdapter(Context context, Images[] categoryImages){
        this.mContext = context;
        this.categoryImages = categoryImages;
    }

    @NonNull
    @Override
    public CategoryDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_category_images, parent, false);

        return new CategoryDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryDetailsAdapter.ViewHolder viewHolder, int position) {

        String cate_image = categoryImages[position].getImageUrl();

        Picasso.with(mContext)
                .load(cate_image)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .into(viewHolder.imageViewHolder);

    }

    @Override
    public int getItemCount() {
        return categoryImages.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageViewHolder;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewHolder = itemView.findViewById(R.id.category_image);

        }
    }
}
