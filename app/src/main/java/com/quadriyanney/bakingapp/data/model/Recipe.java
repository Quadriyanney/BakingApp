package com.quadriyanney.bakingapp.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    private int id, servings;
    private String name, image;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;

    public int getId() {
        return id;
    }

    public int getServings() {
        return servings;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        servings = in.readInt();
        name = in.readString();
        image = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(servings);
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeTypedList(ingredients);
        parcel.writeTypedList(steps);
    }
}