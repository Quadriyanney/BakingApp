package com.quadriyanney.bakingapp.ui.recipesList;

import com.quadriyanney.bakingapp.data.model.Recipe;
import com.quadriyanney.bakingapp.ui.base.BaseView;

import java.util.ArrayList;
import java.util.List;

public interface RecipesListView extends BaseView {
    void showRecipesList(ArrayList<Recipe> recipes);
    void showRetryButton(boolean show);
    void showRecipeDetails(Recipe recipe);
}
