package com.example.adeolu.bakingapp.utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ADEOLU on 6/25/2017.
 */

public interface Recipies {
    @GET("baking.json")
    Call<List<JSonResponse.Recipe>> getRecipe();
}