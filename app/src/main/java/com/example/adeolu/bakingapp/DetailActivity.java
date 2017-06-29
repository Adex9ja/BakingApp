package com.example.adeolu.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.adeolu.bakingapp.myfragments.StepsFragment;
import com.example.adeolu.bakingapp.myfragments.TabPaneDetailFragment;
import com.example.adeolu.bakingapp.utils.JSonResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity  {
    public static List<JSonResponse.Steps> steps;
    public static List<JSonResponse.Ingredients> ingredients;
    public static String name;
    private ActionBar bar;
    public static boolean TwoPane;
    static String STACK_RECIPE_DETAIL="STACK_RECIPE_DETAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent.hasExtra(getString(R.string.recipe_ingreddient))){
            Type typestep = new TypeToken<List<JSonResponse.Steps>>(){}.getType();
            steps =  new Gson().fromJson(intent.getStringExtra(getString(R.string.recipe_steps)),typestep);
            Type typeingredient = new TypeToken<List<JSonResponse.Ingredients>>(){}.getType();
            ingredients =  new Gson().fromJson(intent.getStringExtra(getString(R.string.recipe_ingreddient)),typeingredient);
            name = intent.getStringExtra(getString(R.string.recipe_name));
            bar.setTitle(name);
        }

        if(findViewById(R.id.container) != null){
            TwoPane = true;
            ShowFirstStep();
        }

        if(savedInstanceState == null && !TwoPane)
            ShowMaster();



    }

    private void ShowMaster() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new StepsFragment())
                .addToBackStack(STACK_RECIPE_DETAIL)
                .commit();
    }

    private void ShowFirstStep() {
        ShowMaster();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new TabPaneDetailFragment()
                 .initialize(steps.get(0).getVideoURL(),steps.get(0).getDescription()))
                .commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
         if (id == android.R.id.home) {
             if(TwoPane)
                finish();
             else{
                 FragmentManager fm = getSupportFragmentManager();
                 if (fm.getBackStackEntryCount() > 1)
                     fm.popBackStack(STACK_RECIPE_DETAIL, 0);
                 else if (fm.getBackStackEntryCount() > 0) {
                     finish();
                 }
             }
            return true;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if(TwoPane)
            finish();
        else{
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 1)
                fm.popBackStack(STACK_RECIPE_DETAIL, 0);
            else if (fm.getBackStackEntryCount() > 0) {
                finish();
            }
        }
    }

}
