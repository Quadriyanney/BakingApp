package com.quadriyanney.bakingapp.ui.recipeDetails;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.quadriyanney.bakingapp.App;
import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.model.Recipe;

import javax.inject.Inject;

import static com.quadriyanney.bakingapp.util.Constants.EXTRA_RECIPE;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsView{

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Inject
    RecipeDetailsPresenter recipeDetailsPresenter;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        App.getDependencyComponent().inject(this);
        recipeDetailsPresenter.attachView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_recipe_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.widgetButton) {
            recipeDetailsPresenter.addToWidget(recipe);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setUpView() {
        recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);

        toolbar = findViewById(R.id.toolbarRecipeDetails);
        toolbar.setTitle(recipe.getName());
        setSupportActionBar(toolbar);

        RecipeDetailsTabAdapter recipeDetailsTabAdapter =
                new RecipeDetailsTabAdapter(getSupportFragmentManager(), recipe);

        viewPager = findViewById(R.id.vpRecipeDetails);
        viewPager.setAdapter(recipeDetailsTabAdapter);

        tabLayout = findViewById(R.id.tlRecipeDetails);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
