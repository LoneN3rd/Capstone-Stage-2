package com.example.android.katsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.katsapp.CategoriesFragment;
import com.example.android.katsapp.CategoryDetailsActivity;
import com.example.android.katsapp.R;
import com.example.android.katsapp.model.Categories;

import static android.support.v4.content.ContextCompat.startActivity;

public class CategoriesAdapter extends ArrayAdapter<Categories> {

    private Categories[] categoriesList;

    //private BreedAdapterClickListener mBreedClickListener;
    private CatsAdapterClickListener mCatsAdapterClickListener;

    public CategoriesAdapter(Context context, Categories[] categoriesList) {
        super(context, 0);

        this.categoriesList = categoriesList;
        //mCatsAdapterClickListener = catsAdapterClickListener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final String categoryName = categoriesList[position].getCategoryName();
        final int categoryId = categoriesList[position].getId();

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_categories, parent, false);
        }

        TextView tv_category = convertView.findViewById(R.id.tv_category);

        tv_category.setText(categoryName);

        tv_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(CategoriesAdapter.class.getSimpleName(), "categoryId passed:" + categoryId);

                try {
                    Intent intent = new Intent(getContext(), CategoryDetailsActivity.class);
                    intent.putExtra("categoryId", categoryId);
                    intent.putExtra("categoryName", categoryName);
                    startActivity(getContext(), intent, null);
                } catch (Exception e){
                    Log.i(CategoriesAdapter.class.getSimpleName(), "not working");
                }

            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return categoriesList.length;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface CatsAdapterClickListener{
        void onCatClicked(int position, String catId);
    }
}
