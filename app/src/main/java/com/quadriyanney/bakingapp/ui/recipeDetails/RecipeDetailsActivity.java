package com.quadriyanney.bakingapp.ui.recipeDetails;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.quadriyanney.bakingapp.App;
import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.model.Recipe;

import javax.inject.Inject;

import static com.quadriyanney.bakingapp.helper.Constants.EXTRA_RECIPE;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsView {

    @Inject
    RecipeDetailsPresenter recipeDetailsPresenter;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        App.getDependencyComponent().inject(this);

        recipeDetailsPresenter.attachView(this);
    }

    @Override
    public void setUpView() {
        recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);

        toolbar = findViewById(R.id.toolbar_recipe_details);
        toolbar.setTitle(recipe.getName());
        setSupportActionBar(toolbar);

        RecipeDetailsTabAdapter recipeDetailsTabAdapter =
                new RecipeDetailsTabAdapter(getSupportFragmentManager(), recipe);

        viewPager = findViewById(R.id.view_pager_recipe_details);
        viewPager.setAdapter(recipeDetailsTabAdapter);

        tabLayout = findViewById(R.id.tab_layout_recipe_details);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void showProgress(boolean show) {
    }

    @Override
    public void showMessage(String message) {
    }

}
