package com.quadriyanney.bakingapp.ui.recipesList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.quadriyanney.bakingapp.App;
import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.model.Recipe;
import com.quadriyanney.bakingapp.ui.recipeDetails.RecipeDetailsActivity;
import com.quadriyanney.bakingapp.util.ImageUtil;

import java.util.List;

import javax.inject.Inject;

import static com.quadriyanney.bakingapp.helper.Constants.RECIPE_EXTRA;

public class RecipesListActivity extends AppCompatActivity implements RecipesListView,
        RecipesListAdapter.ListItemClickListener {

    @Inject
    ImageUtil imageUtil;
    @Inject
    RecipesListPresenter recipesListPresenter;
    RecipesListAdapter recipesListAdapter;
    Toolbar toolbar;
    Button retryButton;
    ProgressBar progressBar;
    RecyclerView recipesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        App.getDependencyComponent().inject(this);

        recipesListPresenter.attachView(this);
    }

    @Override
    public void setUpView() {
        retryButton = findViewById(R.id.button_retry);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipesListPresenter.fetchRecipes();
            }
        });

        progressBar = findViewById(R.id.progress_recipes_list);

        toolbar = findViewById(R.id.toolbar_recipes_list);
        setSupportActionBar(toolbar);

        recipesRecyclerView = findViewById(R.id.recycler_view_recipes);
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipesListPresenter.fetchRecipes();
    }

    @Override
    public void showRecipesList(List<Recipe> recipes) {
        recipesListAdapter = new RecipesListAdapter(imageUtil, recipes, this);
        recipesRecyclerView.setAdapter(recipesListAdapter);
        recipesRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRecipeDetails(Recipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE_EXTRA, recipe);

        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtras(bundle);
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
}
