package com.quadriyanney.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quadriyanney.bakingapp.data.model.Ingredient;
import com.quadriyanney.bakingapp.R;

import java.util.List;

/**
 * Created by quadriy on 6/25/17.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder>{

    private List<Ingredient> ingredientsList;

    public IngredientsAdapter(List<Ingredient> ingredientsList){
        this.ingredientsList = ingredientsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ingredientName.setText(ingredientsList.get(position).getIngredient());
        String s = "measurement : " + (String.valueOf(ingredientsList.get(position).getQuantity()) + " " +
                ingredientsList.get(position).getMeasure());
        holder.ingredientMeasurement.setText(s);
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName, ingredientMeasurement;

        ViewHolder(View itemView) {
            super(itemView);
//            ingredientName = (TextView) itemView.findViewById(R.id.ingredient_name);
//            ingredientMeasurement = (TextView) itemView.findViewById(R.id.measurement);
        }
    }
}
