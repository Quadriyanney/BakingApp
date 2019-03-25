package com.quadriyanney.bakingapp.dependencyInjection.component;

import com.quadriyanney.bakingapp.dependencyInjection.module.AppModule;
import com.quadriyanney.bakingapp.ui.recipeDetails.RecipeDetailsActivity;
import com.quadriyanney.bakingapp.ui.recipesList.RecipesListActivity;
import com.quadriyanney.bakingapp.ui.stepDetails.StepDetailsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface DependencyComponent {
    void inject(RecipesListActivity recipesListActivity);

    void inject(StepDetailsFragment stepDetailsFragment);

    void inject(RecipeDetailsActivity recipeDetailsActivity);
}
