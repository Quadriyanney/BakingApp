package com.quadriyanney.bakingapp.ui.recipeIngredientsList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.model.Ingredient;

import java.util.ArrayList;


public class RecipeIngredientsFragment extends Fragment {

    RecyclerView recyclerView;
    RecipeIngredientsAdapter recipeIngredientsAdapter;
    ArrayList<Ingredient> ingredients;

    public RecipeIngredientsFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            this.ingredients = savedInstanceState.getParcelableArrayList("list");
        }
    }

    public static RecipeIngredientsFragment newInstance(ArrayList<Ingredient> ingredients) {
       RecipeIngredientsFragment recipeIngredientsFragment = new RecipeIngredientsFragment();
       recipeIngredientsFragment.setIngredients(ingredients);
       return recipeIngredientsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredients_layout, container, false);

        recipeIngredientsAdapter = new RecipeIngredientsAdapter(ingredients);
        recyclerView = view.findViewById(R.id.ingredientsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recipeIngredientsAdapter);

        return view;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list", ingredients);
    }
}
