package com.example.adeolu.bakingapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.adeolu.bakingapp.utils.JSonResponse;
import com.example.adeolu.bakingapp.utils.JSonResponse.*;
import com.example.adeolu.bakingapp.utils.RecipeAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RecipeAdapter.RecipeClickListener,LoaderManager.LoaderCallbacks<List<Recipe>> {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.mylist) RecyclerView recyclerView;
    private ProgressBar pb_loading_indicator,pb_loading_indicator_tab;
    private boolean isTwoPane = false;

    private RecipeAdapter recipeAdapter;
    private final int RECIPE_LOAD_ID = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        pb_loading_indicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        pb_loading_indicator_tab = (ProgressBar) findViewById(R.id.pb_loading_indicator_tab);
        if(pb_loading_indicator != null){
            recipeAdapter = new RecipeAdapter(new ArrayList<Recipe>(),this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(recipeAdapter);
        }
        else{
            recipeAdapter = new RecipeAdapter(new ArrayList<Recipe>(),this);
            GridLayoutManager layout = new GridLayoutManager(this,2);
            recyclerView.setLayoutManager(layout);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(recipeAdapter);
            isTwoPane = true;
        }

        if(savedInstanceState == null)
            getSupportLoaderManager().initLoader(RECIPE_LOAD_ID,null,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(RECIPE_LOAD_ID,null,this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(List<Ingredients> ingredient, List<Steps> step, String name, int imageid) {
        if(!isTwoPane){
            Intent intent = new Intent(this,DetailActivity.class);
            intent.putExtra(getString(R.string.recipe_ingreddient),new Gson().toJson(ingredient));
            intent.putExtra(getString(R.string.recipe_steps),new Gson().toJson(step));
            intent.putExtra(getString(R.string.recipe_name),name);
            intent.putExtra(getString(R.string.imageid),imageid);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this,DetailActivityTab.class);
            intent.putExtra(getString(R.string.recipe_ingreddient),new Gson().toJson(ingredient));
            intent.putExtra(getString(R.string.recipe_steps),new Gson().toJson(step));
            intent.putExtra(getString(R.string.recipe_name),name);
            intent.putExtra(getString(R.string.imageid),imageid);
            startActivity(intent);
        }

    }


    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Recipe>>(this) {
            List<Recipe> recipes;
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(recipes == null){
                    if(!isTwoPane)
                        pb_loading_indicator.setVisibility(View.VISIBLE);
                    else
                        pb_loading_indicator_tab.setVisibility(View.VISIBLE);
                    forceLoad();
                }else
                    deliverResult(recipes);
            }

            @Override
            public List<Recipe> loadInBackground() {
                try {
                    AssetManager assetManager = getAssets();
                    InputStream inputStream = assetManager.open("baking.json");
                    JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
                    Type type = new TypeToken<List<Recipe>>(){}.getType();
                    recipes = new Gson().fromJson(jsonReader,type);
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
                return recipes;
            }
        };
    }


    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        if(!isTwoPane)
            pb_loading_indicator.setVisibility(View.INVISIBLE);
        else
            pb_loading_indicator_tab.setVisibility(View.INVISIBLE);
        if(data != null)
            recipeAdapter.swapRecipe(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }
}
