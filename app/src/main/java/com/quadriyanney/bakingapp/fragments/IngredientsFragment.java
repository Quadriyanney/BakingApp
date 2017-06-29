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
import com.quadriyanney.bakingapp.data.IngredientsInfo;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quadriy on 6/21/17.
 */

public class IngredientsFragment extends Fragment {

    RecyclerView recyclerView;
    IngredientsAdapter ingredientsAdapter;
    List<IngredientsInfo> ingredientList = new ArrayList<>();
    String mIngredientsList, mIngredientName, mMeasurement;
    JSONArray jsonIngredients;
    int counter = 0, mQuantity;

    public IngredientsFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredients_layout, container, false);

        setAttributes();

        ingredientsAdapter = new IngredientsAdapter(ingredientList);
        recyclerView = (RecyclerView) view.findViewById(R.id.ingredientsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(ingredientsAdapter);

        return view;
    }

    public void setAttributes(){
        try {
            jsonIngredients = new JSONArray(mIngredientsList);

            while (counter < jsonIngredients.length()){
                mQuantity = jsonIngredients.getJSONObject(counter).getInt("quantity");
                mMeasurement = jsonIngredients.getJSONObject(counter).getString("measure");
                mIngredientName = jsonIngredients.getJSONObject(counter).getString("ingredient");

                ingredientList.add(new IngredientsInfo(mQuantity, mMeasurement, mIngredientName));
                counter++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getIngredientsList(String ingredientsList){
        mIngredientsList = ingredientsList;
    }
}
