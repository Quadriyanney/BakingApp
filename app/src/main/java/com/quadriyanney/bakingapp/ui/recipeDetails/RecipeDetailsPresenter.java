package com.quadriyanney.bakingapp.ui.recipeDetails;

import com.quadriyanney.bakingapp.ui.base.BasePresenter;

import javax.inject.Inject;

public class RecipeDetailsPresenter implements BasePresenter<RecipeDetailsView> {

    private RecipeDetailsView recipeDetailsView;

    @Inject
    public RecipeDetailsPresenter() {
    }

    @Override
    public void attachView(RecipeDetailsView recipeDetailsView) {
        this.recipeDetailsView = recipeDetailsView;
        this.recipeDetailsView.setUpView();
    }

    @Override
    public void detachView() {

    }
}
