package com.example.android.katsapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.android.katsapp.model.Breeds;

public class MainActivity extends AppCompatActivity implements BreedsFragment.OnBreedClickListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;

    private Breeds[] mBreeds = null;

    private static final String LOG_TAG = BreedsFragment.class.getSimpleName();

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        drawerLayout = findViewById(R.id.drawer_layout);

        drawerToggle = setupDrawerToggle();

        // tie DrawerLayout events to the ActionBarDrawerToggle
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView = findViewById(R.id.nav_view);

        setupDrawerContent(navigationView);

        setTitle(R.string.cat_breeds);

        // Load BreedsFragment by default
        BreedsFragment breedsFragment = new BreedsFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, breedsFragment)
                .commit();

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // sync the toggle state after the onRestoreInstanceState has occurred
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // pass any config change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    public void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                }
        );
    }

    private void selectDrawerItem(MenuItem menuItem) {

        //FragmentManager fragmentManager = getSupportFragmentManager();

        switch (menuItem.getItemId()){
            case R.id.nav_home:

                BreedsFragment breedsFragment = new BreedsFragment();

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, breedsFragment)
                        .commit();
                break;
            case R.id.nav_categories:

                CategoriesFragment categoriesFragment = new CategoriesFragment();

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, categoriesFragment)
                        .commit();
                break;
            case R.id.nav_favorites:

                BreedsFragment favoritesFragment = new BreedsFragment();

                favoritesFragment.loadFromFav();

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, favoritesFragment)
                        .commit();
                break;
            default:

                BreedsFragment breedsFragmentDef = new BreedsFragment();

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, breedsFragmentDef)
                        .commit();
                break;
        }

        // highlight the selected item
        menuItem.setChecked(true);

        // set action bar title
        setTitle(menuItem.getTitle());

        // close the navigation drawer
        drawerLayout.closeDrawers();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBreedClick(int position, String breedsQueryResponse, String breedId, String loadingFromFav) {

        Intent intent = new Intent(this, BreedDetailsActivity.class);
        intent.putExtra("breed_id", breedId);
        intent.putExtra("loadingFromFav", loadingFromFav);
        intent.putExtra("clickedBreedPosition", position);
        intent.putExtra("breedsQueryResponse", breedsQueryResponse);
        startActivity(intent);


    }
}
