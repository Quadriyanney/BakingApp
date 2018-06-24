package com.quadriyanney.bakingapp.ui.recipeIngredientsList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quadriyanney.bakingapp.data.model.Ingredient;
import com.quadriyanney.bakingapp.R;

import java.util.ArrayList;


public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.ViewHolder>{

    private ArrayList<Ingredient> ingredients;

    public RecipeIngredientsAdapter(ArrayList<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.ingredients_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ingredientName.setText(ingredients.get(position).getIngredient());
        String s = "measurement : " + (String.valueOf(ingredients.get(position).getQuantity())
                + " " + ingredients.get(position).getMeasure());
        holder.ingredientMeasurement.setText(s);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName, ingredientMeasurement;

        ViewHolder(View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredient);
            ingredientMeasurement = itemView.findViewById(R.id.measure);
        }
    }
}
