package com.example.adeolu.bakingapp.widget;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Binder;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.adeolu.bakingapp.MainActivity;
import com.example.adeolu.bakingapp.R;
import com.example.adeolu.bakingapp.utils.JSonResponse.Ingredients;
import com.example.adeolu.bakingapp.utils.JSonResponse.Recipe;

import java.util.List;

/**
 * Created by ADEOLU on 6/20/2017.
 */

public class BakingAppRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            @Override
            public void onCreate() {

            }
            List<Recipe> recipes;
            @Override
            public void onDataSetChanged() {
               recipes = BakingAppWidget.recipes;
            }

            @Override
            public void onDestroy() {
                if(recipes != null)
                    recipes = null;
            }

            @Override
            public int getCount() {
                return recipes == null ? 0 : recipes.size();
            }

            @Override
            public RemoteViews getViewAt(int i) {
                if (recipes == null || i > recipes.size()) {
                    return null;
                }
                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.ingredient_list_item);
                remoteViews.setTextViewText(R.id.recipe_name, recipes.get(i).getName());
                remoteViews.setTextViewText(R.id.recipe_ingredient, getDescription(i));
                remoteViews.setOnClickFillInIntent(R.id.recipe_ingredient, new Intent());
                return remoteViews;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(),R.layout.recipe_card_view);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
            private String getDescription(int i){
                String concat = "";
                for(Ingredients s: recipes.get(i).getIngredients())
                    concat += TextUtils.isEmpty(concat) ? s.getIngredient() : ", " + s.getIngredient();
                return concat;
            }


        };

    }

}
