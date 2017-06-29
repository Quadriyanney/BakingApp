package com.quadriyanney.bakingapp.data;

/**
 * Created by quadriy on 6/23/17.
 */

public class IngredientsInfo{

    private int quantity;
    private String measurement, ingredient_name;

    public IngredientsInfo(int quantity, String measurement, String ingredient_name){
        this.quantity = quantity;
        this.measurement = measurement;
        this.ingredient_name = ingredient_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }
}
