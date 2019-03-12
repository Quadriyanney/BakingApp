package com.quadriyanney.bakingapp.ui.ingredientsList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.model.Ingredient;

import java.util.ArrayList;


public class IngredientsFragment extends Fragment {

    private static final String ARGUMENT_INGREDIENTS_LIST = "ARGUMENT_INGREDIENTS_LIST";
    private static final String EXTRA_INGREDIENTS_LIST = "EXTRA_INGREDIENTS_LIST";

    RecyclerView recyclerView;
    IngredientsAdapter ingredientsAdapter;
    ArrayList<Ingredient> ingredients;

    public IngredientsFragment() {
    }

    public static IngredientsFragment newInstance(ArrayList<Ingredient> ingredients) {
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_INGREDIENTS_LIST, ingredients);
        ingredientsFragment.setArguments(args);
        return ingredientsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            ingredients = savedInstanceState.getParcelableArrayList(EXTRA_INGREDIENTS_LIST);
        } else {
            ingredients = getArguments().getParcelableArrayList(ARGUMENT_INGREDIENTS_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredients_layout, container, false);

        ingredientsAdapter = new IngredientsAdapter(ingredients);

        recyclerView = view.findViewById(R.id.ingredientsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(ingredientsAdapter);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_INGREDIENTS_LIST, ingredients);
    }
}
