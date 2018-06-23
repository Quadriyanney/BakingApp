package com.quadriyanney.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.adapters.IngredientsAdapter;
import com.quadriyanney.bakingapp.data.model.Ingredient;

import java.util.ArrayList;

/**
 * Created by quadriy on 6/21/17.
 */

public class IngredientsFragment extends Fragment {

    RecyclerView recyclerView;
    IngredientsAdapter ingredientsAdapter;
    ArrayList<Ingredient> mIngredientList = new ArrayList<>();

    public IngredientsFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            mIngredientList = savedInstanceState.getParcelableArrayList("list");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredients_layout, container, false);

        ingredientsAdapter = new IngredientsAdapter(mIngredientList);
        recyclerView = (RecyclerView) view.findViewById(R.id.ingredientsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(ingredientsAdapter);

        return view;
    }

    public void getIngredientsList(ArrayList<Ingredient> ingredientsList){
        mIngredientList = ingredientsList;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list", mIngredientList);
    }
}
