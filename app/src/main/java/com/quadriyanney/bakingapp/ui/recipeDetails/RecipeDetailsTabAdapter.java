package com.quadriyanney.bakingapp.ui.recipeDetails;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.quadriyanney.bakingapp.data.model.Recipe;
import com.quadriyanney.bakingapp.ui.ingredientsList.IngredientsFragment;
import com.quadriyanney.bakingapp.ui.stepsList.StepsListFragment;

import static com.quadriyanney.bakingapp.util.Constants.INGREDIENTS;
import static com.quadriyanney.bakingapp.util.Constants.STEPS;

public class RecipeDetailsTabAdapter extends FragmentStatePagerAdapter {

    private Recipe recipe;

    RecipeDetailsTabAdapter(FragmentManager fragmentManager, Recipe recipe) {
        super(fragmentManager);
        this.recipe = recipe;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return StepsListFragment.newInstance(recipe.getSteps());
            case 1:
                return IngredientsFragment.newInstance(recipe.getIngredients());
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
        return position == 0 ? STEPS : INGREDIENTS;
    }
}
