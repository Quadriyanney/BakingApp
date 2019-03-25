package com.quadriyanney.bakingapp.ui.recipesList;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.quadriyanney.bakingapp.App;
import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.RecipeIdlingResource;
import com.quadriyanney.bakingapp.data.model.Recipe;
import com.quadriyanney.bakingapp.ui.recipeDetails.RecipeDetailsActivity;
import com.quadriyanney.bakingapp.util.ImageUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.quadriyanney.bakingapp.util.Constants.EXTRA_RECIPE;

public class RecipesListActivity extends AppCompatActivity implements RecipesListView,
        RecipesListAdapter.ListItemClickListener {

    Toolbar toolbar;
    Button retryButton;
    ProgressBar progressBar;
    RecyclerView recipesRecyclerView;

    @Inject
    ImageUtil imageUtil;
    @Inject
    RecipesListPresenter recipesListPresenter;
    RecipesListAdapter recipesListAdapter;
    ArrayList<Recipe> recipes;

    @Nullable
    private RecipeIdlingResource recipeIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        App.getDependencyComponent().inject(this);
        recipesListPresenter.attachView(this);
    }

    @Override
    public void setUpView() {
        setRecipeIdlingResource(false);

        toolbar = findViewById(R.id.toolbarRecipesList);
        setSupportActionBar(toolbar);

        retryButton = findViewById(R.id.btnRetry);
        retryButton.setOnClickListener(view -> recipesListPresenter.fetchRecipes());

        progressBar = findViewById(R.id.pbRecipesList);
        recipesRecyclerView = findViewById(R.id.rvRecipes);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recipesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

        if (recipes == null) {
            recipesListPresenter.fetchRecipes();
        } else {
            showRecipesList(recipes);
        }
    }

    @Override
    public void showRecipesList(ArrayList<Recipe> recipes) {
        recipesListAdapter = new RecipesListAdapter(imageUtil, recipes, this);
        recipesRecyclerView.setAdapter(recipesListAdapter);
        recipesRecyclerView.setVisibility(View.VISIBLE);
        this.recipes = recipes;
        setRecipeIdlingResource(true);
    }

    @Override
    public void showRecipeDetails(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipe);
        startActivity(intent);
    }

    @Override
    public void showRetryButton(boolean show) {
        retryButton.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecipeClicked(int position) {
        recipesListPresenter.onRecipeClicked(position);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recipes != null) {
            outState.putParcelableArrayList(EXTRA_RECIPE, recipes);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recipesListPresenter.detachView();
    }
}
