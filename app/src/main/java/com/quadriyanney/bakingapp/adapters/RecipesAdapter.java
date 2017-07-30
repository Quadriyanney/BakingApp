package com.quadriyanney.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.RecipesInfo;

import java.util.List;

/**
 * Created by quadriy on 6/7/17.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder>{

    private List<RecipesInfo> recipesInfoList;
    final private ListItemClickListener mListener;
    private Context mContext;

    public RecipesAdapter(Context context, List<RecipesInfo> recipesInfoList, ListItemClickListener mListener) {
        this.mContext = context;
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
    public void onBindViewHolder(RecipesAdapter.ViewHolder holder, int position) {
        if (!TextUtils.isEmpty(recipesInfoList.get(position).getImage())) {
            Glide.with(mContext).load(recipesInfoList.get(position).getImage())
                    .into(holder.recipeImage);
            holder.recipeImage.setVisibility(View.VISIBLE);
        } else {
            holder.itemLayout.setBackgroundResource(R.color.colorListItem);
        }
        holder.recipeName.setText(recipesInfoList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return recipesInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView recipeName;
        RelativeLayout itemLayout;
        ImageView recipeImage;

        ViewHolder(View itemView) {
            super(itemView);
            recipeName = (TextView) itemView.findViewById(R.id.recipe_name);
            itemLayout = (RelativeLayout) itemView.findViewById(R.id.recipe_item);
            recipeImage = (ImageView) itemView.findViewById(R.id.recipe_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clicked = getAdapterPosition();
            mListener.onListItemClick(clicked);
        }
    }
}