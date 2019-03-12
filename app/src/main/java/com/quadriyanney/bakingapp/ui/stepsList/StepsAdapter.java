package com.quadriyanney.bakingapp.ui.stepsList;

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


public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private ArrayList<Step> steps;
    private StepItemClickListener listener;

    StepsAdapter(ArrayList<Step> steps, StepItemClickListener listener) {
        this.steps = steps;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_step, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.ViewHolder holder, int position) {
        holder.stepName.setText(steps.get(position).getShortDescription());
        String video = steps.get(position).getVideoURL();
        String image = steps.get(position).getThumbnailURL();

        String s;
        String fixed = "Click to view details";

        if (TextUtils.isEmpty(video) && TextUtils.isEmpty(image)) {
            holder.stepDetails.setText(fixed);
        } else if (!TextUtils.isEmpty(video) && TextUtils.isEmpty(image)) {
            s = fixed + " and videos";
            holder.stepDetails.setText(s);
        } else if (TextUtils.isEmpty(video) && !TextUtils.isEmpty(image)) {
            s = fixed + " and images";
            holder.stepDetails.setText(s);
        } else {
            s = fixed + ", images and videos";
            holder.stepDetails.setText(s);
        }
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public interface StepItemClickListener {
        void onStepItemClicked(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView stepName, stepDetails;

        ViewHolder(View itemView) {
            super(itemView);
            stepName = itemView.findViewById(R.id.tvStepDescription);
            stepDetails = itemView.findViewById(R.id.tvClickToViewDetails);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onStepItemClicked(getAdapterPosition());
        }
    }
}
