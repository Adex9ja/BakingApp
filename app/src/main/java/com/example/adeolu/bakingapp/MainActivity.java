package com.example.adeolu.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.core.deps.guava.annotations.VisibleForTesting;
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
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.adeolu.bakingapp.utils.JSonResponse.*;
import com.example.adeolu.bakingapp.utils.NetworkUtils;
import com.example.adeolu.bakingapp.utils.RecipeAdapter;
import com.example.adeolu.bakingapp.utils.Recipies;
import com.example.adeolu.bakingapp.utils.SimpleIdlingResource;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


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
        getIdlingResource();
        if(pb_loading_indicator != null){
            recipeAdapter = new RecipeAdapter(new ArrayList<Recipe>(),this,this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(recipeAdapter);
        }
        else{
            recipeAdapter = new RecipeAdapter(new ArrayList<Recipe>(),this,this);
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

        if (id == R.id.nav_about) {
            showAbout();
        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_repo) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showAbout() {
    }

    @Override
    public void onClick(List<Ingredients> ingredient, List<Steps> step, String name, int imageid) {
        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra(getString(R.string.recipe_ingreddient),new Gson().toJson(ingredient));
        intent.putExtra(getString(R.string.recipe_steps),new Gson().toJson(step));
        intent.putExtra(getString(R.string.recipe_name),name);
        intent.putExtra(getString(R.string.imageid),imageid);
        startActivity(intent);
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
                    mIdlingResource.setIdleState(false);
                }else
                    deliverResult(recipes);
            }

            @Override
            public List<Recipe> loadInBackground() {
                try {
                   Recipies iRecipe = NetworkUtils.Retrieve();
                    Call<List<Recipe>> recipe = iRecipe.getRecipe();
                    recipe.enqueue(new Callback<List<Recipe>>() {
                        @Override
                        public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                            recipes = response.body();
                            if(!isTwoPane)
                                pb_loading_indicator.setVisibility(View.INVISIBLE);
                            else
                                pb_loading_indicator_tab.setVisibility(View.INVISIBLE);
                            if(recipes != null){
                                recipeAdapter.swapRecipe(recipes);
                                mIdlingResource.setIdleState(true);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Recipe>> call, Throwable t) {

                        }
                    });
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


    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }


}
