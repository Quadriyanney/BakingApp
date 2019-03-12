package com.quadriyanney.bakingapp.ui.recipesList;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.model.Recipe;
import com.quadriyanney.bakingapp.util.ImageUtil;

import java.util.List;


public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.ViewHolder> {

    private List<Recipe> recipes;
    private ListItemClickListener mListener;
    private ImageUtil imageUtil;

    RecipesListAdapter(ImageUtil imageUtil, List<Recipe> recipes, ListItemClickListener mListener) {
        this.imageUtil = imageUtil;
        this.mListener = mListener;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesListAdapter.ViewHolder holder, int position) {
        if (!TextUtils.isEmpty(recipes.get(position).getImage())) {
            imageUtil.loadImage(recipes.get(position).getImage(), holder.recipeImage);
            holder.recipeImage.setVisibility(View.VISIBLE);
        } else {
            holder.itemLayout.setBackgroundResource(R.color.colorPrimary);
        }
        holder.recipeName.setText(recipes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public interface ListItemClickListener {
        void onRecipeClicked(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recipeName;
        ConstraintLayout itemLayout;
        ImageView recipeImage;

        ViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.tvRecipeName);
            itemLayout = itemView.findViewById(R.id.layout_recipe_item);
            recipeImage = itemView.findViewById(R.id.ivRecipeImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onRecipeClicked(getAdapterPosition());
        }
    }
}