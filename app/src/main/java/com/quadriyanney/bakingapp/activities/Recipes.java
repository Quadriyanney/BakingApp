package com.quadriyanney.bakingapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        root_layout = (LinearLayout) findViewById(R.id.root_layout);

        urlToQuery = new Uri.Builder().scheme("https")
                .authority("d17h27t6h515a5.cloudfront.net").appendPath("topher")
                .appendPath("2017").appendPath("May")
                .appendPath("59121517_baking").appendPath("baking.json").toString();

        recipesInfoList = new ArrayList<>();
        setUp();
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
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Recipes...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        nameAdapter = new RecipeNameAdapter(recipesInfoList, this);
        recyclerView.setAdapter(nameAdapter);

        JsonArrayRequest request = new JsonArrayRequest(urlToQuery,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                        try {
                            while (iterator < response.length()){
                                recipeName = response.getJSONObject(iterator).getString("name");
                                recipeIngredients = response.getJSONObject(iterator).getJSONArray("ingredients").toString();
                                recipeSteps = response.getJSONObject(iterator).getJSONArray("steps").toString();
                                recipesInfoList.add(new RecipesInfo(recipeName, recipeIngredients, recipeSteps));

                                iterator++;
                            }
                        } catch (JSONException e) {
                        e.printStackTrace();
                        }
                        nameAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
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

        outState.putString("list", recipesInfoList.toString());
    }
}
