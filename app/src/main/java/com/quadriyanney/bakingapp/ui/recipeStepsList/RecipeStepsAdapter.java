package com.quadriyanney.bakingapp.ui.recipeStepsList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quadriyanney.bakingapp.R;
import com.quadriyanney.bakingapp.data.model.Step;

import java.util.ArrayList;


public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.ViewHolder>{

    private ArrayList<Step> stepsList;
    private StepItemClickListener listener;

    public interface StepItemClickListener{
        void onStepItemClicked(int clicked);
    }

    public RecipeStepsAdapter(ArrayList<Step> steps, StepItemClickListener listener){
        this.stepsList = steps;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeStepsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                            int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsAdapter.ViewHolder holder, int position) {
        holder.stepName.setText(stepsList.get(position).getShortDescription());
        String video = stepsList.get(position).getVideoUrl();
        String image = stepsList.get(position).getThumbnailUrl();

        String s;
        String fixed = "Click to view details";

        if (TextUtils.isEmpty(video) && TextUtils.isEmpty(image)){
            holder.stepDetails.setText(fixed);
        } else if (!TextUtils.isEmpty(video) && TextUtils.isEmpty(image)){
            s = fixed + " and videos";
            holder.stepDetails.setText(s);
        } else if (TextUtils.isEmpty(video) && !TextUtils.isEmpty(image)){
            s = fixed + " and images";
            holder.stepDetails.setText(s);
        } else {
            s = fixed + ", images and videos";
            holder.stepDetails.setText(s);
        }
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView stepName, stepDetails;

        ViewHolder(View itemView) {
            super(itemView);
            stepName = itemView.findViewById(R.id.step_name);
            stepDetails = itemView.findViewById(R.id.details);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clicked = getAdapterPosition();
            listener.onStepItemClicked(clicked);
        }
    }
}
