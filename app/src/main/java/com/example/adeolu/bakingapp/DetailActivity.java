package com.example.adeolu.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.adeolu.bakingapp.myfragments.DescriptionFragment;
import com.example.adeolu.bakingapp.myfragments.StepsFragment;
import com.example.adeolu.bakingapp.myfragments.TabPaneDetailFragment;
import com.example.adeolu.bakingapp.utils.JSonResponse;
import com.example.adeolu.bakingapp.utils.StepsAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements StepsAdapter.StepsListener {
    public static List<JSonResponse.Steps> steps;
    public static List<JSonResponse.Ingredients> ingredients;
    public static String name;
    private ActionBar bar;
    private int imageid;
    public static String description;
     RecyclerView step_list;
    private boolean TwoPane;
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
            imageid = intent.getIntExtra(getString(R.string.imageid),0);
            bar.setTitle(name);
        }

        step_list = (RecyclerView) findViewById(R.id.list_steps);
        if(step_list != null){
            step_list = (RecyclerView) findViewById(R.id.list_steps_tab);
            step_list.setLayoutManager(new LinearLayoutManager(this));
            step_list.setAdapter(new StepsAdapter(steps, this, this));
            step_list.addItemDecoration(new DividerItemDecoration(step_list.getContext(), DividerItemDecoration.VERTICAL));
            description = steps.get(0).getDescription();
            TwoPane = true;
        }


        if(savedInstanceState == null){
            if(TwoPane)
                ShowFirstStep();
            else
                ShowMaster();
        }

    }

    private void ShowMaster() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new StepsFragment())
                .addToBackStack(STACK_RECIPE_DETAIL)
                .commit();
    }

    private void ShowFirstStep() {
        getSupportFragmentManager().beginTransaction().add(R.id.container, new TabPaneDetailFragment().initialize(steps.get(0).getVideoURL())).commit();
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

    @Override
    public void onClick(int id, String stepsList) {
        if(TwoPane){
            description = steps.get(id).getDescription();
            Uri uristring = Uri.parse(steps.get(id).getVideoURL());
            DescriptionFragment.swapDescription(description);
            TabPaneDetailFragment.swapVideo(uristring);
        }
        else{

        }
    }

}
