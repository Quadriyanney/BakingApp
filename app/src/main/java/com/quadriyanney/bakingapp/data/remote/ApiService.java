package com.quadriyanney.bakingapp.data.remote;

import com.quadriyanney.bakingapp.data.model.Recipe;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Observable<ArrayList<Recipe>> getRecipes();
}
