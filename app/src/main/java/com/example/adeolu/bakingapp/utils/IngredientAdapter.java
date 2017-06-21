package com.example.adeolu.bakingapp.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adeolu.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ADEOLU on 6/5/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {
    private List<JSonResponse.Ingredients> ingredientsList;
    public IngredientAdapter(List<JSonResponse.Ingredients> vingredientsList){
        ingredientsList = vingredientsList;
    }
    @Override
    public IngredientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredient_card_view,parent,false);
        return new IngredientHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientHolder holder, int position) {
        holder.ingredient.setText(ingredientsList.get(position).getIngredient());
        holder.measure.setText(ingredientsList.get(position).getMeasure());
        holder.quantity.setText(ingredientsList.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return ingredientsList == null ? 0 : ingredientsList.size();
    }

    public class IngredientHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.recipe_ingredient)TextView ingredient;
        @BindView(R.id.recipe_measure) TextView measure;
        @BindView(R.id.recipe_quantity) TextView quantity;
        public IngredientHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
