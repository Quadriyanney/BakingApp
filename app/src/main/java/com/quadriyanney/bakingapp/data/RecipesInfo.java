package com.quadriyanney.bakingapp.data;

/**
 * Created by quadriy on 6/7/17.
 */

public class RecipesInfo{

    private String name, ingredientsList, stepsList;

    public RecipesInfo(String name, String ingredientsList, String stepsList) {
        this.name = name;
        this.ingredientsList = ingredientsList;
        this.stepsList = stepsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(String ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public String getStepsList() {
        return stepsList;
    }

    public void setStepsList(String stepsList) {
        this.stepsList = stepsList;
    }
}