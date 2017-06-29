package com.quadriyanney.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.fragments.IngredientsFragment;
import com.quadriyanney.bakingapp.fragments.StepsFragment;

public class RecipeDetails extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    String recipeName, recipeIngredients, recipeSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        if (getIntent().getExtras().getString("recipe") != null){
            recipeName = getIntent().getExtras().getString("recipe");
        }
        if (getIntent().getExtras().getString("ingredients") != null){
            recipeIngredients = getIntent().getExtras().getString("ingredients");
        }
        if (getIntent().getExtras().getString("steps") != null){
            recipeSteps = getIntent().getExtras().getString("steps");
        }

        CustomAdapter adapter = new CustomAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void launchDetailActivity(String description, String videoUrl, String thumbnailUrl){
        Intent intent = new Intent(RecipeDetails.this, StepDetails.class);
        intent.putExtra("description", description);
        intent.putExtra("vUrl", videoUrl);
        intent.putExtra("tUrl", thumbnailUrl);
        startActivity(intent);
    }

    private class CustomAdapter extends FragmentStatePagerAdapter{

        CustomAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    IngredientsFragment ingredientsFragment = new IngredientsFragment();
                    ingredientsFragment.getIngredientsList(recipeIngredients);
                    return ingredientsFragment;
                case 1:
                    StepsFragment stepsFragment = new StepsFragment();
                    stepsFragment.getStepsList(recipeSteps);
                    return stepsFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String name = null;
            if (position == 0){
                name = "Ingredients";
            }
            if (position == 1){
                name = "Steps";
            }
            return name;
        }
    }
}
