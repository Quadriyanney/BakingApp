package com.quadriyanney.bakingapp.dependencyInjection.component;

import com.quadriyanney.bakingapp.dependencyInjection.module.AppModule;
import com.quadriyanney.bakingapp.dependencyInjection.module.DependencyModule;
import com.quadriyanney.bakingapp.ui.recipeDetails.RecipeDetailsActivity;
import com.quadriyanney.bakingapp.ui.recipesList.RecipesListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DependencyModule.class})
public interface DependencyComponent {
    void inject(RecipesListActivity recipesListActivity);
    void inject(RecipeDetailsActivity recipeDetailsActivity);
}
