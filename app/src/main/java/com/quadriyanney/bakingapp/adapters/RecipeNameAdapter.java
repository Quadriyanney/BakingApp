package com.quadriyanney.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.RecipesInfo;

import java.util.List;

/**
 * Created by quadriy on 6/7/17.
 */

public class RecipeNameAdapter extends RecyclerView.Adapter<RecipeNameAdapter.ViewHolder>{

    private List<RecipesInfo> recipesInfoList;
    final private ListItemClickListener mListener;

    public RecipeNameAdapter(List<RecipesInfo> recipesInfoList, ListItemClickListener mListener) {
        this.mListener = mListener;
        this.recipesInfoList = recipesInfoList;
    }

    public interface ListItemClickListener{
        void onListItemClick(int clicked);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipes_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeNameAdapter.ViewHolder holder, int position) {
        holder.recipeName.setText(recipesInfoList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return recipesInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView recipeName;

        ViewHolder(View itemView) {
            super(itemView);
            recipeName = (TextView) itemView.findViewById(R.id.recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clicked = getAdapterPosition();
            mListener.onListItemClick(clicked);
        }
    }
}