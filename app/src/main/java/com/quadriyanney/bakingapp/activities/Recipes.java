package com.quadriyanney.bakingapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.quadriyanney.bakingapp.Controller;
import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.RecipeIdlingResource;
import com.quadriyanney.bakingapp.adapters.RecipeNameAdapter;
import com.quadriyanney.bakingapp.data.RecipesInfo;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Recipes extends AppCompatActivity implements RecipeNameAdapter.ListItemClickListener {

    String urlToQuery, jsonTag = "json_tag", recipeName, recipeIngredients, recipeSteps;
    int iterator = 0;
    List<RecipesInfo> recipesInfoList;
    RecipeNameAdapter nameAdapter;
    LinearLayout root_layout;
    RecyclerView recyclerView;
    JSONArray jsonArray;
    private boolean isTwoPane;

    @Nullable private RecipeIdlingResource recipeIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        if (findViewById(R.id.detail_container) != null) isTwoPane = true;

        if (isTwoPane) setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        root_layout = (LinearLayout) findViewById(R.id.root_layout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

        recipesInfoList = new ArrayList<>();

        if (savedInstanceState != null){
            try {
                JSONArray array = new JSONArray(savedInstanceState.getString("list"));
                toList(array);
                nameAdapter = new RecipeNameAdapter(recipesInfoList, this);
                recyclerView.setAdapter(nameAdapter);
                nameAdapter.notifyDataSetChanged();
                jsonArray = array;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            urlToQuery = new Uri.Builder().scheme("https")
                    .authority("d17h27t6h515a5.cloudfront.net").appendPath("topher")
                    .appendPath("2017").appendPath("May")
                    .appendPath("59121517_baking").appendPath("baking.json").toString();

            setUp();
        }
    }

    @Override
    public void onListItemClick(int clicked) {
        Intent intent = new Intent(Recipes.this, RecipeDetails.class);
        intent.putExtra("recipe", recipesInfoList.get(clicked).getName());
        intent.putExtra("ingredients", recipesInfoList.get(clicked).getIngredientsList());
        intent.putExtra("steps", recipesInfoList.get(clicked).getStepsList());
        startActivity(intent);
    }

    public void  setUp(){
        setRecipeIdlingResource(false);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Recipes...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        nameAdapter = new RecipeNameAdapter(recipesInfoList, this);
        recyclerView.setAdapter(nameAdapter);

        JsonArrayRequest request = new JsonArrayRequest(urlToQuery,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                        jsonArray = response;
                        toList(response);
                        nameAdapter.notifyDataSetChanged();
                        setRecipeIdlingResource(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        setRecipeIdlingResource(false);
                        Snackbar.make(root_layout, "Poor/No Connection", Snackbar.LENGTH_INDEFINITE).setAction(
                                "Refresh", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        setUp();
                                    }
                                }).show();
                    }
                });
        Controller.getInstance().addToRequestQueue(request, jsonTag);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("list", jsonArray.toString());
    }

    public void toList(JSONArray array){
        try {
            while (iterator < array.length()){
                recipeName = array.getJSONObject(iterator).getString("name");
                recipeIngredients = array.getJSONObject(iterator).getJSONArray("ingredients").toString();
                recipeSteps = array.getJSONObject(iterator).getJSONArray("steps").toString();
                recipesInfoList.add(new RecipesInfo(recipeName, recipeIngredients, recipeSteps));

                iterator++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public IdlingResource getIdlingResource() {
        if (recipeIdlingResource == null) {
            recipeIdlingResource = new RecipeIdlingResource();
        }
        return recipeIdlingResource;
    }

    public void setRecipeIdlingResource(boolean state) {
        if (recipeIdlingResource == null){
            return;
        }
        recipeIdlingResource.setIsIdleState(state);
    }
}
