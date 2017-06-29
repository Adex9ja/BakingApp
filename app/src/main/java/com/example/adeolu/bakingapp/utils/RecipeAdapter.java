package com.example.adeolu.bakingapp.utils;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adeolu.bakingapp.R;

import java.util.List;
import com.example.adeolu.bakingapp.utils.JSonResponse.*;
import com.example.adeolu.bakingapp.widget.BakingAppIntentService;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ADEOLU on 6/4/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>  {
    private List<Recipe> recipes;
    private RecipeClickListener mListener;
    private Context context;
    public RecipeAdapter(List<Recipe> vrecipes, RecipeClickListener vListener, Context vContext){
        recipes = vrecipes;
        mListener = vListener;
        context = vContext;
    }
    public interface RecipeClickListener{
        void onClick(List<Ingredients> ingredient, List<Steps> step, String name);
    }
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_card_view,parent,false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.itemView.setTag(recipes.get(position).getId());
        holder.ingredient.setText(getIngredients(recipes.get(position).getIngredients()) + " \n" + recipes.get(position).getServings() + " Servings");
        holder.name.setText(recipes.get(position).getName());
        String imageUrl=recipes.get(position).getImage();
        if (!TextUtils.isEmpty(imageUrl)) {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            Picasso.with(context).load(builtUri).error(R.drawable.cake).into(holder.recipe_image);
        }
        else{
            Picasso.with(context).load(R.drawable.cake).into(holder.recipe_image);
        }
    }




    private String getIngredients(List<Ingredients> ingredients) {
        String s = "";
        for(Ingredients ingredient : ingredients)
            s += s == null || TextUtils.isEmpty(s) ? ingredient.getIngredient() : ", " + ingredient.getIngredient();
         return s;
    }

    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.size();
    }
    public void swapRecipe(List<Recipe> vrecipes){
        recipes = vrecipes;
        notifyDataSetChanged();
        BakingAppIntentService.startBakingService(context,vrecipes);
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.recipe_name) TextView name;
        @BindView(R.id.recipe_ingredient) TextView ingredient;
        @BindView(R.id.recipe_image) ImageView recipe_image;
        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClick(recipes.get(getPosition()).getIngredients(),
                            recipes.get(getPosition()).getSteps(),
                            recipes.get(getPosition()).getName());
                }
            });
        }
    }
}
