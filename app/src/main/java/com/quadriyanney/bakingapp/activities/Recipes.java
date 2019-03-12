package com.quadriyanney.bakingapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.RecipeIdlingResource;
import com.quadriyanney.bakingapp.data.model.Recipe;
import com.quadriyanney.bakingapp.ui.recipesList.RecipesListAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Recipes extends AppCompatActivity implements RecipesListAdapter.ListItemClickListener {

    String urlToQuery, jsonTag = "json_tag", recipeName, recipeIngredients, recipeSteps, recipeImage;
    int iterator = 0;
    List<Recipe> recipesInfoList;
    RecipesListAdapter adapter;
    LinearLayout root_layout;
    RecyclerView recyclerView;
    JSONArray jsonArray;

    @Nullable
    private RecipeIdlingResource recipeIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        root_layout = findViewById(R.id.root_layout);
        recipesInfoList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

        if (savedInstanceState != null) {
            try {
                JSONArray array = new JSONArray(savedInstanceState.getString("list"));
                toList(array);
//                adapter = new RecipesListAdapter(this, recipesInfoList, this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                jsonArray = array;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            urlToQuery = new Uri.Builder().scheme("https")
                    .authority("d17h27t6h515a5.cloudfront.net").appendPath("topher")
                    .appendPath("2017").appendPath("May")
                    .appendPath("59121517_baking").appendPath("baking.json").toString();

            setUp();
        }
    }

    @Override
    public void onRecipeClicked(int clicked) {
        Intent intent = new Intent(Recipes.this, RecipeDetails.class);
//        intent.putExtra("recipe", recipesInfoList.get(clicked).getName());
//        intent.putExtra("ingredients", recipesInfoList.get(clicked).setIngredients());
//        intent.putExtra("steps", recipesInfoList.get(clicked).setSteps());
//        startActivity(intent);
    }

    public void setUp() {
        setRecipeIdlingResource(false);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Recipes...");
        progressDialog.setCancelable(false);
        progressDialog.show();

//        adapter = new RecipesListAdapter(this, recipesInfoList, this);
        recyclerView.setAdapter(adapter);
//
//        JsonArrayRequest request = new JsonArrayRequest(urlToQuery,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        progressDialog.hide();
//                        jsonArray = response;
//                        toList(response);
//                        adapter.notifyDataSetChanged();
//                        setRecipeIdlingResource(true);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        setRecipeIdlingResource(false);
//                        progressDialog.hide();
//                        Snackbar.make(root_layout, "Poor/No Connection", Snackbar.LENGTH_INDEFINITE).setAction(
//                                "Refresh", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        setUp();
//                                    }
//                                }).show();
//                    }
//                });
//        Controller.getInstance().addToRequestQueue(request, jsonTag);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("list", jsonArray.toString());
    }

    public void toList(JSONArray array) {
        try {
            while (iterator < array.length()) {
                recipeName = array.getJSONObject(iterator).getString("name");
                recipeImage = array.getJSONObject(iterator).getString("image");
                recipeIngredients = array.getJSONObject(iterator).getJSONArray("ingredients").toString();
                recipeSteps = array.getJSONObject(iterator).getJSONArray("steps").toString();

//                recipesInfoList.add(new Recipe(recipeName, recipeImage, recipeIngredients, recipeSteps));
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
        if (recipeIdlingResource == null) {
            return;
        }
        recipeIdlingResource.setIsIdleState(state);
    }
}
