package com.example.adeolu.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.adeolu.bakingapp.myfragments.DescriptionFragment;
import com.example.adeolu.bakingapp.myfragments.TabPaneDetailFragment;
import com.example.adeolu.bakingapp.utils.JSonResponse;
import com.example.adeolu.bakingapp.utils.StepsAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivityTab extends AppCompatActivity implements StepsAdapter.StepsListener {
    public static List<JSonResponse.Steps> steps;
    public static List<JSonResponse.Ingredients> ingredients;
    public static String name;
    private ActionBar bar;
    private int imageid;
    public static String description;
    @BindView(R.id.list_steps)  RecyclerView step_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tab);
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
            description = steps.get(0).getDescription();
        }

        step_list.setLayoutManager(new LinearLayoutManager(this));
        step_list.setAdapter(new StepsAdapter(steps, this, this));
        step_list.addItemDecoration(new DividerItemDecoration(step_list.getContext(), DividerItemDecoration.VERTICAL));

        ShowFirstStep();
    }
    private void ShowFirstStep() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new TabPaneDetailFragment().initialize(steps.get(0).getVideoURL())).commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
         if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(int id, String stepsList) {
        description = steps.get(id).getDescription();
        Uri uristring = Uri.parse(steps.get(id).getVideoURL());
        DescriptionFragment.swapDescription(description);
        TabPaneDetailFragment.swapVideo(uristring);
    }

}
