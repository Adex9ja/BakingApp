package com.example.adeolu.bakingapp.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.example.adeolu.bakingapp.R;
import com.example.adeolu.bakingapp.utils.JSonResponse.Recipe;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by ADEOLU on 6/26/2017.
 */

public class BakingAppIntentService extends IntentService {


    public BakingAppIntentService() {
        super("BakingAppIntentService");
    }

    public static void startBakingService(Context context, List<Recipe> recipes) {
        Intent intent = new Intent(context, BakingAppIntentService.class);
        intent.putExtra(context.getString(R.string.ingredients),new Gson().toJson(recipes));
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String recipesJson = intent.getStringExtra(getString(R.string.ingredients));
            handleActionUpdateBakingWidgets(recipesJson);
        }
    }



    private void handleActionUpdateBakingWidgets(String recipesJson) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setAction("com.example.adeolu.bakingapp.ACTION_DATA_UPDATED");
        intent.putExtra(getString(R.string.ingredients),recipesJson);
        sendBroadcast(intent);
    }
}
