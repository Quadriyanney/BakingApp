package com.quadriyanney.bakingapp.data;

/**
 * Created by quadriy on 6/7/17.
 */

public class RecipesInfo{

    private String name, image, ingredientsList, stepsList;

    public RecipesInfo(String name, String image, String ingredientsList, String stepsList) {
        this.name = name;
        this.image = image;
        this.ingredientsList = ingredientsList;
        this.stepsList = stepsList;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getIngredientsList() {
        return ingredientsList;
    }

    public String getStepsList() {
        return stepsList;
    }

}