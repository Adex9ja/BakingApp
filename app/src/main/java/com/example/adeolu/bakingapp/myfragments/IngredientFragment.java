package com.example.adeolu.bakingapp.myfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adeolu.bakingapp.DetailActivity;
import com.example.adeolu.bakingapp.R;

import com.example.adeolu.bakingapp.utils.IngredientAdapter;
import com.example.adeolu.bakingapp.utils.JSonResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class IngredientFragment extends Fragment {
    private static List<JSonResponse.Ingredients> ingredients;
    public IngredientFragment() {

    }


   public static IngredientFragment newInstance(String ingredient) {
       IngredientFragment fragment = new IngredientFragment();
       Type typeingredient = new TypeToken<List<JSonResponse.Ingredients>>(){}.getType();
       ingredients =  new Gson().fromJson(ingredient,typeingredient);
       return fragment;
   }

    @BindView(R.id.list_ingredient)
    RecyclerView ingredient_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ingredient_table, container, false);
        ButterKnife.bind(this,rootView);

        ingredient_list.setLayoutManager(new LinearLayoutManager(container.getContext()));
        ingredient_list.setAdapter(new IngredientAdapter(ingredients));
        return rootView;
    }

}
