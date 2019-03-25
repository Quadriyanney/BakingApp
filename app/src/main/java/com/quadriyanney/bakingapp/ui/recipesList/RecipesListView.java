package com.quadriyanney.bakingapp.ui.recipesList;

import com.quadriyanney.bakingapp.data.model.Recipe;
import com.quadriyanney.bakingapp.ui.base.BaseView;

import java.util.ArrayList;

public interface RecipesListView extends BaseView {
    void showRecipesList(ArrayList<Recipe> recipes);

    void showRetryButton(boolean show);

    void showRecipeDetails(Recipe recipe);

    void showProgress(boolean show);

    void showMessage(String message);
}
